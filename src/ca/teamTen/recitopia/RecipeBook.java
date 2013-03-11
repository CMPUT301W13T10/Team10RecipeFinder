package ca.teamTen.recitopia;

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
}
