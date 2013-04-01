package ca.teamTen.recitopia;

import java.util.ArrayList;

import ca.teamTen.recitopia.RecipeBook.AsyncAddRecipeTask.Callbacks;

import android.os.AsyncTask;

/**
 * RecipeBook stores a set of recipes, allowing for persistence and querying.
 * 
 * Recipes are identified by their title and author; no two recipes in a book
 * should have the same title and author.
 *
 */
public interface RecipeBook {

	/**
	 * Query recipes with Google-style search. The searchTerms are split based
	 * on whitespace, and all text fields of the recipes are searched.
	 * 
	 * An empty searchTerm will match all recipes.
	 * 
	 * @param searchTerms a string containing whitespace-separated search terms.
	 * @return An array of recipes that match the query
	 */
	public Recipe[] query(String searchTerms);
	
	/**
	 * Add or update a recipe.
	 * 
	 * If a recipe with the same title and author already exists, it will
	 * be updated. Otherwise, the recipe will be added.
	 * @param recipe
	 */
	public void addRecipe(Recipe recipe);
	
	/**
	 * Write RecipeBook to long-term storage.
	 */
	public void save();
	
	/**
	 * AsyncTask for adding recipes to one or more recipe books.
	 */
	public static class AsyncAddRecipeTask extends AsyncTask<Recipe, Void, Void> {
		public interface Callbacks {
			public void recipesAdded();
		}
		
		private ArrayList<RecipeBook> recipeBooks = new ArrayList<RecipeBook>();
		private Callbacks callbacks = null;
		
		/**
		 * Add a recipe book, which will have the recipes passed to
		 * execute added to it.
		 */
		public void addRecipeBook(RecipeBook recipeBook) {
			recipeBooks.add(recipeBook);
		}

		/**
		 * Add a Callbacks object which will have its recipesAdded() method
		 * called once this task is complete.
		 * 
		 * @param callbacks
		 */
		public void setCallbacks(Callbacks callbacks)
		{
			this.callbacks = callbacks;
		}

		@Override
		protected Void doInBackground(Recipe... recipes) {
			for (Recipe recipe: recipes) {
				for (RecipeBook recipeBook: recipeBooks) {
					recipeBook.addRecipe(recipe);
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			if (callbacks != null) {
				callbacks.recipesAdded();
			}
		}
	}
}
