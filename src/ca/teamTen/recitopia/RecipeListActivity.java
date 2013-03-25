package ca.teamTen.recitopia;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Displays a list of recipes. Selecting a recipe
 * will open it in RecipeViewActivity.
 * 
 * The list is refreshed every time the activity is resumed.
 *
 */
public class RecipeListActivity extends ListActivity {

	Recipe recipes[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipelist);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// Refresh list contents, as they may have changed.
		// TODO: be more sophisticated
		recipes = ApplicationManager.getInstance(getApplication())
				.getUserRecipeBook().query("");
		
		ListAdapter adapter = new ArrayAdapter<String>(
				this,
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1,
				makeListItems(recipes));
		
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Recipe recipe = this.recipes[position];
		
		Intent viewIntent = new Intent(this, RecipeViewActivity.class);
		viewIntent.putExtra("RECIPE", recipe);
		
		startActivity(viewIntent);
	}
	
	private String[] makeListItems(Recipe[] recipes) {
		String[] result = new String[recipes.length];
		
		for (int i = 0; i < recipes.length; i++) {
			result[i] = recipes[i].getRecipeName() + "\n" + recipes[i].showAuthor();
		}
		return result;
	}
}
