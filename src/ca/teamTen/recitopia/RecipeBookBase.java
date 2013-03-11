package ca.teamTen.recitopia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;

/**
 * Implements common routines for RecipeBook implementers.
 * Child classes implement hook methods to ensure the recipe
 * book can satisfy their constraints.
 * 
 * @see RecipeBook
 */
public abstract class RecipeBookBase implements RecipeBook
{
	protected ArrayList<Recipe> recipes = new ArrayList<Recipe>();

	/**
	 * Implement Google-style searching of recipes, with no indexing.
	 */
	@Override
	public Recipe[] query(String searchTerms)
	{
		String[] queryTerms = searchTerms.split("\\s+");
			// split query into tokens by whitespace
		ArrayList<Recipe> results = new ArrayList<Recipe>();
		for (Recipe recipe: recipes) {
			if (recipeMatchesQuery(recipe, queryTerms)) {
				results.add(recipe);
			}
		}
		Recipe[] resultArray = new Recipe[results.size()];
		return results.toArray(resultArray);
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
				this.recipeUpdated(recipe, i);
				return;
			}
		}
		recipes.add(recipe);
		this.recipeAdded(recipe);
	}

	/**
	 * Hook method called when a new Recipe is added.
	 * @param recipe which was added at index recipes.size() - 1
	 */
	abstract protected void recipeAdded(Recipe recipe);

	/**
	 * Hook method called when a Recipe is updated.
	 * 
	 * @param recipe the new value of the recipe
	 * @param i the index of the recipe
	 */
	abstract protected void recipeUpdated(Recipe recipe, int i);

	/**
	 * Checks whether recipe matches any terms from query. All text fields
	 * (author, title, instructions, ingredients) of the recipe are searched.
	 * @param recipe
	 * @param query An array of search terms. These need no special formatting.
	 * @return
	 */
	protected boolean recipeMatchesQuery(Recipe recipe, String[] query) {
		// for an empty query, everything matches
		if (query.length == 1 && query.equals("")) {
			return true;
		}

		for (String term: query) {
			if (query.equals("")) {
				continue;
				// if we have multiple terms, we should ignore empty ones.
			}

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

	/**
	 * Saves recipes to the local filesystem
	 * @param fileName file to save to
	 * @param ctx Android Context used for io
	 */
	public void save(String fileName, Context ctx) {
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

	/**
	 * Loads recipes from the local filesystem
	 * @param fileName file to load from
	 * @param ctx Android Context used for io
	 */
	public void load(String fileName, Context ctx) {
		try {  
			FileInputStream fis = ctx.openFileInput(fileName);
			ObjectInputStream in = new ObjectInputStream(fis);  
			recipes = (ArrayList<Recipe>) in.readObject();
			in.close();
		} 
		catch (IOException e) {  
			e.printStackTrace();  
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();  
		}
	}
}
