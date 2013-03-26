package ca.teamTen.recitopia;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Basic searching activity.
 */
public class SearchActivity extends Activity implements OnItemClickListener {

	private Recipe[] recipes;
	private String query;
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
		
		// TODO: check for some message in our intent and laucnh a search
		// TODO: add progress spinner or something
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}

	public void onSearch(View button) {
		query = searchField.getText().toString();
		executeQuery();
	}
	
	private void executeQuery() {
		new QueryTask(ApplicationManager.getInstance(getApplication()))
			.execute(query);
	}
	
	private void displayQueryResults(Recipe[] recipes) {
		ListAdapter adapter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1,
				makeListItems(recipes));

		recipeListView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
		Recipe recipe = this.recipes[position];

		Intent viewIntent = new Intent(this, RecipeViewActivity.class);
		viewIntent.putExtra("RECIPE", recipe);

		startActivity(viewIntent);
	}

	private String[] makeListItems(Recipe[] recipes) {
		String[] result = new String[recipes.length];

		for (int i = 0; i < recipes.length; i++) {
			result[i] = recipes[i].getRecipeName() + "\n" + recipes[i].getAuthor();
		}
		return result;
	}
	
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
			displayQueryResults(result);
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
	 * multiple RecipeBooks.
	 */
	private static class CompositeRecipeBook implements RecipeBook {
		private RecipeBook books[];
		
		CompositeRecipeBook(RecipeBook books[]) {
			this.books = books;
		}

		@Override
		public Recipe[] query(String searchTerms) {
			ArrayList<Recipe[]> results = new ArrayList<Recipe[]>();
			int totalCount = 0;
			
			for (RecipeBook book: books) {
				Recipe[] recipes = book.query(searchTerms);
				totalCount += recipes.length;
				results.add(recipes);
			}
			Recipe[] allRecipes = new Recipe[totalCount];
			int recipesAdded = 0;
			for (Recipe[] singleResult: results) {
				System.arraycopy(singleResult, 0, allRecipes, recipesAdded, singleResult.length);
				recipesAdded += singleResult.length;
			}
			
			return allRecipes;
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