package ca.teamTen.recitopia.models;

import android.annotation.SuppressLint;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;


/**
 * RecipeBook class which can be used to store, query recipes
 * without interacting with (many) external systems.
 * 
 * Good for storing recipes locally on the filesystem, for
 * instance.
 * 
 * Child classes can implement hook methods to ensure the
 * recipe book can satisfy their constraints.
 * 
 * @see RecipeBook
 */
@SuppressLint("DefaultLocale")
public class SimpleRecipeBook implements RecipeBook
{
	protected ArrayList<Recipe> recipes = new ArrayList<Recipe>();
	private IOFactory ioFactory;
	
	/**
	 * Create a recipe book using the given IOFactory for loading
	 * and saving.
	 * 
	 * @param io
	 */
	public SimpleRecipeBook(IOFactory io) {
		ioFactory = io;
	}
	
	/**
	 * Create a recipe book that doesn't load/save.
	 */
	public SimpleRecipeBook() {
		ioFactory = new IOFactory.NullIOFactory();
	}

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
	 * @see ca.teamTen.recitopia.models.RecipeBook#addRecipe(ca.teamTen.recitopia.models.Recipe)
	 */
	@Override
	public void addRecipe(Recipe recipe)
	{
		for (int i = 0; i < recipes.size(); i++) {
			Recipe current = recipes.get(i);
			if (current.getRecipeName().equals(recipe.getRecipeName()) && current.getAuthor().equals(recipe.getAuthor())) {
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
	protected void recipeAdded(Recipe recipe) {
	}

	/**
	 * Hook method called when a Recipe is updated.
	 * 
	 * @param recipe the new value of the recipe
	 * @param i the index of the recipe
	 */
	protected void recipeUpdated(Recipe recipe, int i) {
	}

	/**
	 * Checks whether recipe matches any terms from query. All text fields
	 * (author, title, instructions, ingredients) of the recipe are searched.
	 * 
	 * Uses case-insensitive searching.
	 * 
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
			
			term = term.toLowerCase();

			if (recipe.getAuthor().toLowerCase().contains(term)
					|| recipe.getRecipeName().toLowerCase().contains(term)
					|| recipe.getCookingInstructions().toLowerCase().contains(term)) {
				return true;
			}

			for (String ingredient: recipe.getIngredients()) {
				if (ingredient.toLowerCase().contains(term)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Saves recipes to the loutput stream returned by the IOFactory.
	 */
	public void save() {
		try {  
			OutputStream rawOutputStream = ioFactory.getOutputStream();
			if (rawOutputStream == null) {
				return;
			}
			
			ObjectOutputStream out = new ObjectOutputStream(rawOutputStream);
			out.writeObject(recipes);
			out.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  

		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}

	/**
	 * Loads recipes from an InputStream returned by the
	 * IOFactory.
	 */
	public void load() {
		try {
			InputStream rawStream = ioFactory.getInputStream();
			if (rawStream == null) {
				return;
			}
			ObjectInputStream in = new ObjectInputStream(rawStream);  
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
