package ca.teamTen.recitopia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Multiple item selection courtesy Stack Overflow
 * http://stackoverflow.com/questions/1362602/selecting-multiple-items-in-listview
 *
 */
public class SearchByIngredientActivity extends Activity {
	private ListView ingredientList;
	private String[] GENRES;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_by_ingredient);

		GENRES = new String[] {
				"Action", "Adventure", "Animation", "Children", "Comedy", "Documentary", "Drama",
				"Foreign", "History", "Independent", "Romance", "Sci-Fi", "Television", "Thriller"
		};
		ingredientList = (ListView) findViewById(R.id.ingredientList);
		ingredientList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		ingredientList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, GENRES));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_by_ingredient, menu);
		return true;
	}

}
