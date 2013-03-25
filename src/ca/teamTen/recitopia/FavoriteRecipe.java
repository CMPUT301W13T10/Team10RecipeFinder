package ca.teamTen.recitopia;

/**
 * A collection of recipes which the user has saved for
 * offline access.
 */
public class FavoriteRecipe extends RecipeBookBase {

	public FavoriteRecipe() {
	}
	
	public FavoriteRecipe(IOFactory ioFactory) {
		super(ioFactory);
	}

	@Override
	protected void recipeAdded(Recipe recipe) {
		// TODO save?
	}

	@Override
	protected void recipeUpdated(Recipe recipe, int i) {
		// TODO save?
	}
}
