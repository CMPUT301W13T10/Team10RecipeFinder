package ca.teamTen.recitopia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;


public abstract class RecipeBookBase implements RecipeBook
{
	private ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	
	@Override
	public Recipe[] query(String searchTerms)
	{
		Recipe[] result = new Recipe[recipes.size()];
		return recipes.toArray(result);
	}

	/**
	 * Adds a recipe to this recipe book. If a recipe already exists with the
	 * same title and author, it will be updated.
	 * 
	 * @see ca.teamTen.recitopia.RecipeBook#addRecipe(ca.teamTen.recitopia.Recipe)
	 */
	@Override
	public void addRecipe(Recipe recipe)
	{
		for (int i = 0; i < recipes.size(); i++) {
			Recipe current = recipes.get(i);
			if (current.getRecipeName().equals(recipe.getRecipeName()) && current.showAuthor().equals(recipe.showAuthor())) {
				recipes.set(i, recipe);
				return;
			}
		}
		recipes.add(recipe);
	}
	
	protected boolean recipeMatchesQuery(Recipe recipe, String[] query) {
		for (String term: query) {
			if (recipe.showAuthor().contains(term)
					|| recipe.getRecipeName().contains(term)
					|| recipe.showCookingInstructions().contains(term)) {
				return true;
			}
			
			for (String ingredient: recipe.showIngredients()) {
				if (ingredient.contains(term)) {
					return true;
				}
			}
		}
		return false;
	}

	protected void save(String fileName, Context ctx, Collection<Recipe> recipes) {
		try {  
			FileOutputStream fos = ctx.openFileOutput(fileName,Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(recipes);
			out.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  

		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}
	
	protected ArrayList<Recipe> load(String fileName, Context ctx) {
		ArrayList<Recipe> recipes;
		try {  
			FileInputStream fis = ctx.openFileInput(fileName);
			ObjectInputStream in = new ObjectInputStream(fis);  
			recipes = (ArrayList<Recipe>) in.readObject();
			in.close();
			return recipes;
		} 
		catch (IOException e) {  
			e.printStackTrace();  
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();  
		}
		return null;
	}
}
