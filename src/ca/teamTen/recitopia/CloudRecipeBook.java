package ca.teamTen.recitopia;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.annotations.Expose;


/**
 * RecipeBook interface to ElasticSearch recipe service.
 * Currently not implemented, comments document expected
 * implementation.
 * 
 * I found the following gist:
 * https://gist.github.com/orip/3635246
 * 
 * http://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/TypeAdapter.html
 * 
 * http://stackoverflow.com/questions/11271375/gson-custom-seralizer-for-one-variable-of-many-in-an-object-using-typeadapter
 */
public class CloudRecipeBook implements RecipeBook{
	
	private final String RECIPE_INDEX_URL = "http://cmput301.softwareprocess.es:8080/cmput301w13t10/recipe/";

	private HttpClient httpClient;
	private Gson gson;
	private RecipeBook cache;
	
	/**
	 * Create a new cache-backed CloudRecipeBook.
	 * @param cache a Recipe book which will have all search results added to it.
	 */
	public CloudRecipeBook(RecipeBook cache) {
		httpClient = new DefaultHttpClient();
		this.cache = cache;
		//Creating a GsonBuilder
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Photo.class, new PhotoAdapter());
		gsonBuilder.registerTypeAdapter(Recipe.class, new InstanceCreator<Recipe>() {
			public Recipe createInstance(Type type) {
				return new Recipe(true);
					// create a new, published recipe.
			}
		});
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		
		// create Gson object that will always build published recipes
		gson = gsonBuilder.create();
	}
	
	/**
	 * Used for Testing Photo Serialization
	 * @return a string representing the Recipe in JSON format
	 */
	public String recipeToJson(Recipe recipe){
		String recipeToJson = gson.toJson(recipe);
		return recipeToJson;
	}
	
	/**
	 * Used for Testing Photo Deserialization
	 */
	public Recipe recipeFromJson(String string){
		Recipe recipe = gson.fromJson(string, Recipe.class);
		return recipe;
	}
	
	/**
	 * Create an ElasticSearch query command object, serialize to
	 * JSON and use HTTP to send query.
	 * 
	 * See http://www.elasticsearch.org/guide/reference/api/
	 */
	@Override
	public Recipe[] query(String searchTerms) {
		String searchUrlParam;
		try {
			searchUrlParam = URLEncoder.encode(new QueryRequest(searchTerms).toJSON(gson), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			return null;
		}
		
		HttpGet getRequest = new HttpGet(RECIPE_INDEX_URL + "_search?source=" + searchUrlParam);
		
		HttpResponse response;
		String resultBody;
		try {
			response = httpClient.execute(getRequest);
			resultBody = readInputStreamToString(response.getEntity().getContent());
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
			
		Recipe[] results = gson.fromJson(resultBody, QueryResult.class).getResults();
		if (results == null) {
			results = new Recipe[0];
		}
		addRecipesToCache(results);
		return results;
	}

	/*
	 * Add an array of recipes to the cache.
	 */
	private void addRecipesToCache(Recipe[] recipes) {
		for (Recipe recipe: recipes) {
			this.cache.addRecipe(recipe);
		}
		this.cache.save();
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
		HttpResponse response = null;
		try	{
			String url = buildRecipeURL(recipe) + "/_update";
		
			HttpPost postRequest = new HttpPost(url);
			String postBody = gson.toJson(new UpdateInsertRequest(recipe));
			
			postRequest.setHeader("Accept", "application/json");
			postRequest.setEntity(new StringEntity(postBody));
			
			response = httpClient.execute(postRequest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		// status 200 = ok
		if (response != null && response.getStatusLine().getStatusCode() != 200) {
			// TODO notify user? cache add request for later?
		}
	}
	
	/**
	 * Check whether a connection can be made to the server.
	 * 
	 * Implemented by attempting a HEAD request on RECIPE_INDEX_URL
	 * @return true if ElasticSearch server can be reached.
	 */
	public boolean isAvailable() {
		try {
			HttpHead headRequest = new HttpHead(RECIPE_INDEX_URL);
			HttpResponse response = httpClient.execute(headRequest);
			return response.getStatusLine().getStatusCode() == 200;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Flush any cached update/insert commands to server.
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Public for testing purposes.
	 * @param recipe the recipe for which to build the URL
	 * @return a URL for the recipe's entry in ElasticSearch
	 * @throws UnsupportedEncodingException 
	 */
	public String buildRecipeURL(Recipe recipe) throws UnsupportedEncodingException {
		return RECIPE_INDEX_URL + URLEncoder.encode(recipe.getAuthor()
				+ "/" + recipe.getRecipeName(), "UTF-8");
	}
	
	/*
	 * Class to be serialized into an ElasticSearch update request.
	 * 
	 * Since we regard update/insert as basically the same thing, we
	 * use a bit of a hack to achieve this with ElasticSearch.
	 * 
	 * If the record already exists, ES will update it with fields from
	 * the doc member. If it does not exist, it will first create the
	 * record using the upsert member.
	 */
	@SuppressWarnings("unused")
	private static class UpdateInsertRequest {
		@Expose public Recipe doc;
		@Expose public Recipe upsert;
		
		UpdateInsertRequest(Recipe recipe) {
			doc = recipe;
			upsert = recipe;
		}
	}
	
	/*
	 * ElasticSearch query object.
	 * 
	 * query: {
	 * 		query_string {
	 * 			query: bla
	 * 		}
	 * }
	 */
	@SuppressWarnings("unused")
	private static class QueryRequest {
		private static class Query {
			@Expose Map<String, String> query_string;
			
			Query(String query) {
				query_string = new HashMap<String, String>();
				query_string.put("query", query);
			}
		}
		
		@Expose Query query;
		
		public QueryRequest(String query) {
			this.query = new Query(query);
		}
		
		public String toJSON(Gson gson) {
			return gson.toJson(this);
		}
	}
	
	/*
	 * Class for reading query results. The strange structure
	 * of this class is due to needing to match the structure of
	 * ElasticSearch query results.
	 */
	@SuppressWarnings("unused")
	private static class QueryResult {
		public static class QueryHitData {
			@Expose int total;
			@Expose double max_score;
			@Expose QueryHit[] hits;
		}
		
		public static class QueryHit {
			@Expose Recipe _source;
		}
		
		@Expose public QueryHitData hits;
		
		public Recipe[] getResults() {
			if (hits == null) {
				return null;
			}
			
			ArrayList<Recipe> results = new ArrayList<Recipe>();
			for (QueryHit hit: hits.hits) {
				results.add(hit._source);
			}
			
			Recipe asArray[] = new Recipe[results.size()];
			results.toArray(asArray);
			return asArray;
		}
	}
	
	/*
	 * Ridiculous hack to get a string from an InputStream, from
	 * StackOverflow. Reads the InputStream, splitting by the "\\A"
	 * regexp, which only occurs once - at the beginning of the stream.
	 * 
	 * http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
	 */
	private String readInputStreamToString(InputStream input) {
		java.util.Scanner s = new java.util.Scanner(input).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}
