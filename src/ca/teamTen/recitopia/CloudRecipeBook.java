package ca.teamTen.recitopia;

import android.content.Context;

// Interface to ElasticSearch recipe service
public class CloudRecipeBook implements RecipeBook{

	@Override
	public Recipe[] query(String[] ingredients, String title, String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(String url, Context ctx) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isAvailable() {
		return false;
	}



}
