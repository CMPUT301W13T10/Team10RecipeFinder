package ca.teamTen.recitopia;



/**
 *  This class stores a limited number of recipes that are not created by current user
 * 
 */
public class LocalCache extends RecipeBookBase {
	private int	maxEntries;
	
	public LocalCache(int maxEntries) {
		this.maxEntries = maxEntries;
	}

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

	@Override
	public void save() {
		// TODO Auto-generated method stub
	}
}
