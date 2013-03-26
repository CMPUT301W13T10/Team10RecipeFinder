package ca.teamTen.recitopia;



/**
 *  This class stores a limited number of recipes that are not created by current user
 * 
 */
public class CacheRecipeBook extends SimpleRecipeBook {
	private int	maxEntries;
	
	/**
	 * Create a LocalCache that holds the maxEntries most-
	 * recently added Recipes.
	 * @param maxEntries
	 */
	public CacheRecipeBook(int maxEntries) {
		this.maxEntries = maxEntries;
	}
	
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
		// TODO save?
	}
}