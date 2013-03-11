package ca.teamTen.recitopia;


// Interface to ElasticSearch recipe service
public class CloudRecipeBook implements RecipeBook{

	@Override
	public Recipe[] query(String searchTerms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isAvailable() {
		return false;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
	}
}
