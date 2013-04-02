package ca.teamTen.recitopia;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import android.app.Application;
import android.content.Context;

/**
 * Singleton class for managing shared/heavy resources.
 * 
 * This class manages user identification and all locally
 * stored data.
 */
public class ApplicationManager {
	private final static int CACHE_RECIPEBOOK_SIZE = 40;

	private static final String USER_RECIPEBOOK_PATH = "myRecipes.dat";
	private static final String FAVORITES_RECIPEBOOK_PATH = "myFavoriteRecipes.dat";
	private static final String CACHE_RECIPEBOOK_PATH = "cachedRecipes.dat";
	private static final String FRIDGE_PATH = "fridge.dat";
	private static final String USERID_PATH = "userid.txt";

	private static ApplicationManager appMgr;

	private Context appContext;
	private String userid = null;
	private SimpleRecipeBook userRecipeBook = null;
		// userRecipeBook stores the user's recipes
	private SimpleRecipeBook favoriteRecipesBook = null;
		// favoriteRecipesBook stores other users' recipes offline
	private CloudRecipeBook cloudRecipeBook = null;
	private CacheRecipeBook cacheRecipeBook = null;
	
	private Fridge fridge = null;

	private ApplicationManager(Application application) {
		appContext = application.getApplicationContext();
	}

	/**
	 * Set the user id (should be an email)
	 * @param user
	 */
	public void setUserID(String user) {
		userid = user;
		saveUserID();
	}

	/**
	 * Get the current user's id.
	 * @return the current user's id (email)
	 */
	public String getUserID() {
		if (userid == null) {
			loadUserID();
		}
		return userid;
	}

	/**
	 * Get (and load) the user's RecipeBook.
	 * @return user RecipeBook
	 */
	public RecipeBook getUserRecipeBook() {
		if (userRecipeBook == null) {
			userRecipeBook = new SimpleRecipeBook(new FileSystemIOFactory(USER_RECIPEBOOK_PATH));
			userRecipeBook.load();
		}
		return userRecipeBook;
	}

	/**
	 * Get (and load) the user's favorites RecipeBook
	 * @return favorites RecipeBook
	 */
	public RecipeBook getFavoriteRecipesBook() {
		if (favoriteRecipesBook == null) {
			favoriteRecipesBook = new SimpleRecipeBook(new FileSystemIOFactory(FAVORITES_RECIPEBOOK_PATH));
			favoriteRecipesBook.load();
		}
		return favoriteRecipesBook;
	}

	/**
	 * Get the CloudRecipeBook, attached to the cache RecipeBook.
	 * @return the CloudRecipeBook.
	 */
	public RecipeBook getCloudRecipeBook() {
		if (cloudRecipeBook == null) {
			cloudRecipeBook = new CloudRecipeBook(getCacheRecipeBook());
		}
		return cloudRecipeBook;
	}

	/**
	 * Get (and load) the cache recipe book.
	 * This book is automatically filled with a number of previous
	 * search results.
	 * @return the CacheRecipeBook used by the CloudRecipeBook
	 */
	public RecipeBook getCacheRecipeBook() {
		if (cacheRecipeBook == null) {
			cacheRecipeBook = new CacheRecipeBook(CACHE_RECIPEBOOK_SIZE,
					new FileSystemIOFactory(CACHE_RECIPEBOOK_PATH));
			cacheRecipeBook.load();
		}
		return cacheRecipeBook;
	}
	
	/**
	 * Clears the cache by deleting the cache file
	 */
	public void clearCache(){
		FileSystemIOFactory fsiofactory = new FileSystemIOFactory(CACHE_RECIPEBOOK_PATH);
		fsiofactory.deleteFile();
	}
	
	/**
	 * Get the user's fridge
	 * @return the Fridge
	 */
	public Fridge getFridge() {
		if (fridge == null) {
			fridge = new Fridge(new FileSystemIOFactory(FRIDGE_PATH));
		}
		
		return fridge;
	}
	
	public void saveUserID() {
		try {
			FileOutputStream outStream = appContext.openFileOutput(USERID_PATH, Context.MODE_PRIVATE);
			PrintStream outPrinter = new PrintStream(outStream);
			outPrinter.print(userid);
			outPrinter.close();
		} catch (Exception e) {
			// we'll just try again next time...
		}
	}
	
	public void loadUserID() {
		try {
			FileInputStream inStream = appContext.openFileInput(USERID_PATH);
			BufferedReader inReader = new BufferedReader(new InputStreamReader(inStream));
			userid = inReader.readLine().trim();
			inReader.close();
		} catch (Exception e) {
			userid = null;
		}
	}

	/**
	 * Singleton-pattern getter
	 * @return The instance of ApplicationManager
	 */
	public static ApplicationManager getInstance(Application application) {
		if (appMgr == null){
			appMgr = new ApplicationManager(application);
		}

		return appMgr;
	}
	
	private class FileSystemIOFactory implements IOFactory {
		private String filePath;

		public FileSystemIOFactory(String filePath) {
			this.filePath = filePath;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return appContext.openFileInput(filePath);
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			return appContext.openFileOutput(filePath, Context.MODE_PRIVATE);
		}
		
		/**
		 * Deletes the file given at filePath
		 * 
		 * Used for Clearing the Cache
		 */
		public void deleteFile(){
			appContext.deleteFile(this.filePath);
			
		}
	}
}
