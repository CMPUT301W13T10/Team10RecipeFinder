package ca.teamTen.recitopia;

import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Multiple item selection courtesy Stack Overflow
 * http://stackoverflow.com/questions/1362602/selecting-multiple-items-in-listview
 *
 */
public class SearchByIngredientActivity extends Activity {
	private ListView ingredientList;
	private String[] ingredients;
	private final String FRIDGE_FILE = "fridge.dat";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_ingredient);

		populateIngredientsArray();
		ingredientList = (ListView) findViewById(R.id.ingredientList);
		ingredientList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		ingredientList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, ingredients));

	}

	private void populateIngredientsArray()
	{
		ApplicationManager appMgr = ApplicationManager.getInstance(getApplication());
		Fridge fridge = appMgr.getFridge();
		fridge.loadFridgeInfo(FRIDGE_FILE, this);
		Collection<String> ingredientsCollection = fridge.getIngredients();
		ingredients = new String[ingredientsCollection.size()];
		ingredientsCollection.toArray(ingredients);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_by_ingredient, menu);
		return true;
	}
	
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
