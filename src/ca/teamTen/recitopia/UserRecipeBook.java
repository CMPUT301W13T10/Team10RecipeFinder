package ca.teamTen.recitopia;

/**
 * RecipeBook that stores the user's own recipes, whether
 * they are published or unpublished.
 */
public class UserRecipeBook extends RecipeBookBase {

	public UserRecipeBook(IOFactory ioFactory) {
		super(ioFactory);
	}

	public UserRecipeBook() {
	}

	@Override
	protected void recipeAdded(Recipe recipe) {
		// TODO: save?
	}

	@Override
	protected void recipeUpdated(Recipe recipe, int i) {
		// TODO: save?
	}
}
