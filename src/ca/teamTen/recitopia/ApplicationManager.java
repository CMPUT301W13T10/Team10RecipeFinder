package ca.teamTen.recitopia;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;

public class ApplicationManager {
	private static ApplicationManager appMgr;

	private String userid;	
	private RecipeBook userRecipeBook = null;

	public ApplicationManager(String user) {
		this.userid = user;
	}

	public void setUserID(String user) {
		this.userid = user;
	}

	public String getUserID() {
		return this.userid;
	}

	public RecipeBook getUserRecipeBook(Context context) {
		if (userRecipeBook == null) {
			userRecipeBook = new PrototypeRecipeBook();
		}
		return userRecipeBook;
	}

	public static ApplicationManager getInstance(){
		if (appMgr == null){
			appMgr = new ApplicationManager("test@test.com");
		}

		return appMgr;
	}

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
		public void save(String fileName, Context ctx)
		{
			// TODO Auto-generated method stub	
		}
	}
}
