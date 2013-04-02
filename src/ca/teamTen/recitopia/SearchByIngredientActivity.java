package ca.teamTen.recitopia;

import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Multiple item selection courtesy Stack Overflow
 * http://stackoverflow.com/questions/1362602/selecting-multiple-items-in-listview
 *
 */

/**
 * This activity loads the ingredients within the Fridge and populates a ListView
 * with them. The layout used contains check boxes so that the user can search for
 * recipes by a subset or by all of the ingredients.
 * 
 */
public class SearchByIngredientActivity extends Activity {
	private ListView ingredientList;
	private String[] ingredients;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_ingredient);

		populateIngredientsArray();
		ingredientList = (ListView) findViewById(R.id.ingredientList);
		ingredientList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		ingredientList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, ingredients));

	}

	/**
	 * Populate the ingredients array
	 */
	private void populateIngredientsArray()
	{
		ApplicationManager appMgr = ApplicationManager.getInstance(getApplication());
		Fridge fridge = appMgr.getFridge();
		fridge.load();
		Collection<String> ingredientsCollection = fridge.getIngredients();
		ingredients = new String[ingredientsCollection.size()];
		ingredientsCollection.toArray(ingredients);
	}

	/**
	 * This method generates a QUERY string that is then passed in an
	 * Intent to the SearchActivity class. 
	 * 
	 * The format of the QUERY string is a space-separated list
	 * of the user selected ingredients.
	 * 
	 * @param the View corresponding to the Search button
	 */
	public void onSearchClicked(View button) {
		SparseBooleanArray positions = ingredientList.getCheckedItemPositions();
		StringBuilder query = new StringBuilder();
		
		for (int i = 0; i < ingredients.length; i++) {
			if (positions.get(i)) {
				query.append(ingredients[i])
					.append(" ");
			}
		}
		
		Intent intent = new Intent(this, SearchActivity.class);
		intent.putExtra("QUERY", query.toString());
		startActivity(intent);
	}

}
