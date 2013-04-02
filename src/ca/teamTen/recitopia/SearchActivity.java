package ca.teamTen.recitopia;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Basic searching activity. Displays search form and results list.
 * 
 * If this activity is launched with a string in the "QUERY" extra, it will
 * launch a query with the provided string immediately upon starting.
 * 
 * Queries are executed asynchronously using AsyncTask.
 */
public class SearchActivity extends Activity implements OnItemClickListener {

	private Recipe[] recipes;
	private ListView recipeListView;
	private EditText searchField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		recipeListView = (ListView)findViewById(R.id.resultsList);
		recipeListView.setOnItemClickListener(this);
		recipeListView.setEmptyView(findViewById(R.id.placeholder));
		searchField = (EditText)findViewById(R.id.searchField);
		
		// TODO: add progress spinner or something

		// check for query extra, launch query if it is present
		Intent intent = getIntent();
		if (intent.hasExtra("QUERY")) {
			String query = intent.getStringExtra("QUERY");
			searchField.setText(query);
			executeQuery(query);
		}
	}

	/**
	 * Called when the search button is pressed
	 * @param button
	 */
	public void onSearch(View button) {
		executeQuery(searchField.getText().toString());
	}
	
	/**
	 * Executes a query asynchronously
	 * @param query
	 */
	private void executeQuery(String query) {
		new QueryTask(ApplicationManager.getInstance(getApplication()))
			.execute(query);
	}
	
	/**
	 * Handles new query results.
	 */
	private void queryResultsReceived(Recipe[] recipes) {
		this.recipes = recipes;
		ListAdapter adapter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1,
				makeListItems(recipes));

		recipeListView.setAdapter(adapter);
	}

	/**
	 * Launches RecipeViewActivity when a recipe is clicked.
	 */
	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		Recipe recipe = this.recipes[position];

		Intent viewIntent = new Intent(this, RecipeViewActivity.class);
		viewIntent.putExtra("RECIPE", recipe);

		startActivity(viewIntent);
	}

	/*
	 * Makes an array of strings for displaying the given recipes.
	 */
	private String[] makeListItems(Recipe[] recipes) {
		String[] result = new String[recipes.length];

		for (int i = 0; i < recipes.length; i++) {
			result[i] = recipes[i].getRecipeName() + "\n" + recipes[i].getAuthor();
		}
		return result;
	}
	
	/*
	 * AsyncTask for running search queries.
	 */
	@SuppressLint("NewApi")
	private class QueryTask extends AsyncTask<String, Void, Recipe[]> {
		ApplicationManager appMgr;
		
		QueryTask(ApplicationManager appMgr) {
			this.appMgr = appMgr;
		}

		@Override
		protected void onPreExecute() {
			// TODO show progress bar or something
		}
		
		@Override
		protected Recipe[] doInBackground(String... queryStrings) {
			return getRecipeBookForQuerying().query(queryStrings[0]);
		}
		
		@Override
		protected void onPostExecute(Recipe[] result) {
			queryResultsReceived(result);
		}
		
		/*
		 * Returns a composite recipe book, using CloudRecipeBook.isAvailable() to
		 * determine whether we should use the cloud recipe book.
		 */
		private RecipeBook getRecipeBookForQuerying() {
			CloudRecipeBook cloudBook = (CloudRecipeBook) appMgr.getCloudRecipeBook();
			if (cloudBook.isAvailable()) {
				return new CompositeRecipeBook(
					new RecipeBook[] {cloudBook, appMgr.getUserRecipeBook()});
			} else {
				return new CompositeRecipeBook(new RecipeBook[] { 
					appMgr.getUserRecipeBook(),
					appMgr.getFavoriteRecipesBook(),
					appMgr.getCacheRecipeBook()
				});
			}
		}
	}
	
	/*
	 * RecipeBook implementation that combines the search results of
	 * multiple RecipeBooks. Because a recipe may be present in multiple
	 * books, they are deduplicated as well.
	 */
	private static class CompositeRecipeBook implements RecipeBook {
		private RecipeBook books[];
		
		CompositeRecipeBook(RecipeBook books[]) {
			this.books = books;
		}

		@Override
		public Recipe[] query(String searchTerms) {
			ArrayList<Recipe[]> results = new ArrayList<Recipe[]>();
			
			for (RecipeBook book: books) {
				Recipe[] recipes = book.query(searchTerms);
				if (recipes != null) {
					results.add(recipes);
				}
			}
			
			return deduplicate(results);
		}
		
		/*
		 * Merges a set of query results into a single result with
		 * no duplicates.
		 */
		private Recipe[] deduplicate(ArrayList<Recipe[]> queryResults) {
			// Make a TreeSet that sorts based on author and title 
			Set<Recipe> set = new TreeSet<Recipe>(new Comparator<Recipe>() {
				@Override
				public int compare(Recipe lhs, Recipe rhs)
				{
					int result = lhs.getAuthor().compareTo(rhs.getAuthor());
					if (result == 0) {
						result = lhs.getRecipeName().compareTo(rhs.getRecipeName());
					}
					return result;
				}
			});
			
			for (Recipe[] queryResult: queryResults) {
				for (Recipe recipe: queryResult) {
					set.add(recipe);
				}
			}
			
			Recipe[] dedupedResult = new Recipe[set.size()];
			set.toArray(dedupedResult);
			return dedupedResult;
		}

		@Override
		public void addRecipe(Recipe recipe) {
			throw new UnsupportedOperationException("addRecipe unsupported");			
		}

		@Override
		public void save() {
			throw new UnsupportedOperationException("save unsupported");
		}
	}
}