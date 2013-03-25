package ca.teamTen.recitopia;



/**
 *  This class stores a limited number of recipes that are not created by current user
 * 
 */
public class LocalCache extends RecipeBookBase {
	private int	maxEntries;
	
	/**
	 * Create a LocalCache that holds the maxEntries most-
	 * recently added Recipes.
	 * @param maxEntries
	 */
	public LocalCache(int maxEntries) {
		this.maxEntries = maxEntries;
	}
	
	public LocalCache(int maxEntries, IOFactory ioFactory) {
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

	@Override
	protected void recipeUpdated(Recipe recipe, int i) {
		// TODO save?
	}
}
