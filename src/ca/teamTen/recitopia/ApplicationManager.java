package ca.teamTen.recitopia;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Application;
import android.content.Context;

/**
 * Singleton class for managing shared/heavy resources.
 * 
 * This class manages user identification. Eventually,
 * all recipe books will be instantiated/managed by this
 * class.
 */
public class ApplicationManager {
	private final static int CACHE_RECIPEBOOK_SIZE = 40;

	private static final String USER_RECIPEBOOK_PATH = "myRecipes.dat";
	private static final String FAVORITES_RECIPEBOOK_PATH = "myFavoriteRecipes.dat";
	private static final String CACHE_RECIPEBOOK_PATH = "cachedRecipes.dat";

	private static ApplicationManager appMgr;

	private Context appContext;
	private String userid;	
	private SimpleRecipeBook userRecipeBook = null;
		// userRecipeBook stores the user's recipes
	private SimpleRecipeBook favoriteRecipesBook = null;
		// favoriteRecipesBook stores other users' recipes offline
	private CloudRecipeBook cloudRecipeBook = null;
	private CacheRecipeBook cacheRecipeBook = null;

	public ApplicationManager(Application application) {
		this.userid = "test@test.com";
		// dummy id thing, to be removed when #24 is closed
		appContext = application.getApplicationContext();
	}

	/**
	 * Set the user id (should be an email)
	 * @param user
	 */
	public void setUserID(String user) {
		this.userid = user;
	}

	/**
	 * Get the current user's id.
	 * @return the current user's id (email)
	 */
	public String getUserID() {
		return this.userid;
	}

	/**
	 * Get the UserRecipeBook. Requires a Context due to
	 * possible file io.
	 * @param Context an Android Context for file io
	 * @return user RecipeBook
	 */
	public RecipeBook getUserRecipeBook() {
		if (userRecipeBook == null) {
			userRecipeBook = new SimpleRecipeBook(new FileSystemIOFactory(USER_RECIPEBOOK_PATH));
			userRecipeBook.load();
		}
		return userRecipeBook;
	}

	public RecipeBook getFavoriteRecipesBook() {
		if (favoriteRecipesBook == null) {
			favoriteRecipesBook = new SimpleRecipeBook(new FileSystemIOFactory(FAVORITES_RECIPEBOOK_PATH));
			favoriteRecipesBook.load();
		}
		return favoriteRecipesBook;
	}

	public RecipeBook getCloudRecipeBook() {
		if (cloudRecipeBook == null) {
			cloudRecipeBook = new CloudRecipeBook(getCacheRecipeBook());
		}
		return cloudRecipeBook;
	}

	public RecipeBook getCacheRecipeBook() {
		if (cacheRecipeBook == null) {
			cacheRecipeBook = new CacheRecipeBook(CACHE_RECIPEBOOK_SIZE,
					new FileSystemIOFactory(CACHE_RECIPEBOOK_PATH));
			cacheRecipeBook.load();
		}
		return cacheRecipeBook;
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
	
	private class FileSystemIOFactory implements SimpleRecipeBook.IOFactory {
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
	}
}
