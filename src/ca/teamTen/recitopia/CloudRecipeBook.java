package ca.teamTen.recitopia;


/**
 * RecipeBook interface to ElasticSearch recipe service.
 * Currently not implemented, comments document expected
 * implementation.
 */
public class CloudRecipeBook implements RecipeBook{

	/**
	 * Create an ElasticSearch query command object, serialize to
	 * JSON and use HTTP to send query.
	 */
	@Override
	public Recipe[] query(String searchTerms) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create an ElasticSearch update or create command object,
	 * serialize to JSON and use HTTP to send to server.
	 * 
	 * Alternatively, cache the update commands and send them
	 * when save() is called.
	 */
	@Override
	public void addRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Check whether a connection can be made to the server.
	 * @return true if ElasticSearch server can be reached.
	 */
	public boolean isAvailable() {
		return false;
	}

	/**
	 * Flush any cached update/insert commands to server.
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
	}
}
