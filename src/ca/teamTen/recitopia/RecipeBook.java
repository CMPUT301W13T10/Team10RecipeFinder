package ca.teamTen.recitopia;

import android.content.Context;

public interface RecipeBook {

	public Recipe[] query(String[] ingredients, String title, String user);
	
	public void addRecipe(Recipe recipe);
	
	public void save(String fileName, Context ctx);
}
