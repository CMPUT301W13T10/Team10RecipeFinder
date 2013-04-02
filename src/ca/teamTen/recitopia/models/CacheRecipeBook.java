package ca.teamTen.recitopia.models;




/**
 *  This class stores a limited number of recipes that are not created by current user
 * 
 */
public class CacheRecipeBook extends SimpleRecipeBook {
	private int	maxEntries;
	
	/**
	 * Create a LocalCache that holds the maxEntries most-
	 * recently added Recipes.
	 * @param maxEntries the maximum number of entries to store
	 * @param ioFactory the ioFactory used for persistence
	 */
	public CacheRecipeBook(int maxEntries) {
		this.maxEntries = maxEntries;
	}
	
	/**
	 * Create a LocalCache that holds the maxEntries most-
	 * recently added Recipes.
	 * @param maxEntries the maximum number of entries to store
	 * @param ioFactory the ioFactory used for persistence
	 */
	public CacheRecipeBook(int maxEntries, IOFactory ioFactory) {
		super(ioFactory);
		this.maxEntries = maxEntries;
	}

	/**
	 * Ensures size constraint is not violated
	 * after recipe add.
	 */
	@Override
	protected void recipeAdded(Recipe recipe) {
		if (recipes.size() > maxEntries) {
			recipes.remove(0);
		}
	}
}
