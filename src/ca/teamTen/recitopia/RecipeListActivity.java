package ca.teamTen.recitopia;

import java.util.ArrayList;
import java.util.Arrays;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class RecipeListActivity extends ListActivity {

	Recipe recipes[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipelist);
		
		this.recipes = new Recipe[] {
		new Recipe("Spiky Melon Salad",
			new ArrayList<String>(Arrays.asList("spiky melon", "lettuce", "cucumber")),
			"Cube the melon, chop the lettuce and cumbers. Mix in bowl and enjoy",
			"alex@test.com"),
		new Recipe("Spiky Melon Soup",
			new ArrayList<String>(Arrays.asList("spiky melon", "cream", "spices")),
			"mix, heat and enjoy",
			"zhexin@test.com"),
		new Recipe("Spiky Melon Shake",
			new ArrayList<String>(Arrays.asList("spiky melon", "cream", "sugar")),
			"mix, blend and enjoy",
			"osipovas@test.com"),
		new Recipe("Spiky Melon Fries",
			new ArrayList<String>(Arrays.asList("spiky melon", "salt", "cooking oil")),
			"chop, fry, eat",
			"zou@test.com")
		};
			
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
