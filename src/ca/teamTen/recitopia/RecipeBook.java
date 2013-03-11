package ca.teamTen.recitopia;

public interface RecipeBook {

	public Recipe[] query(String searchTerms);
	
	public void addRecipe(Recipe recipe);
	
	public void save();
}
