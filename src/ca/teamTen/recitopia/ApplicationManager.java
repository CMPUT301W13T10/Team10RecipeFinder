package ca.teamTen.recitopia;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;

/**
 * Singleton class for managing shared/heavy resources.
 * 
 * This class manages user identification. Eventually,
 * all recipe books will be instantiated/managed by this
 * class.
 */
public class ApplicationManager {
	private static ApplicationManager appMgr;

	private String userid;	
	private RecipeBook userRecipeBook = null;

	public ApplicationManager(String user) {
		this.userid = user;
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
	public RecipeBook getUserRecipeBook(Context context) {
		if (userRecipeBook == null) {
			userRecipeBook = new PrototypeRecipeBook();
		}
		return userRecipeBook;
	}

	/**
	 * Singleton-pattern getter
	 * @return The instance of ApplicationManager
	 */
	public static ApplicationManager getInstance(){
		if (appMgr == null){
			appMgr = new ApplicationManager("test@test.com");
		}

		return appMgr;
	}

	// simple stubbed out RecipeBook for prototype
	private class PrototypeRecipeBook extends RecipeBookBase {		
		public PrototypeRecipeBook() {
			addRecipe(new Recipe("Spiky Melon Salad",
					new ArrayList<String>(Arrays.asList("spiky melon", "lettuce", "cucumber")),
					"Cube the melon, chop the lettuce and cumbers. Mix in bowl and enjoy",
					"alex@test.com"));
			addRecipe(new Recipe("Spiky Melon Soup",
					new ArrayList<String>(Arrays.asList("spiky melon", "cream", "spices")),
					"mix, heat and enjoy",
					"zhexin@test.com"));
			addRecipe(new Recipe("Spiky Melon Shake",
					new ArrayList<String>(Arrays.asList("spiky melon", "cream", "sugar")),
					"mix, blend and enjoy",
					"osipovas@test.com"));
			addRecipe(new Recipe("Spiky Melon Fries",
					new ArrayList<String>(Arrays.asList("spiky melon", "salt", "cooking oil")),
					"chop, fry, eat",
					"zou@test.com"));
		}

		@Override
		protected void recipeAdded(Recipe recipe) {			
		}

		@Override
		protected void recipeUpdated(Recipe recipe, int i) {
		}

		@Override
		public void save() {
			// unimplemented
		}
	}
}
