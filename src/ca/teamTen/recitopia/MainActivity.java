package ca.teamTen.recitopia;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**
 * Launch activity, lets the user launch different activites.
 * 
 * From here, the user can: create a recipe, view their recipes,
 * search for recipes, or manage their fridge.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void toCreate(View view) {
		Intent intent = new Intent(this, RecipeEditActivity.class);
		startActivity(intent);
	}
	
	public void toSearch(View view) {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}
	
	public void toFridge(View view) {
		Intent intent = new Intent(this, FridgeActivity.class);
		startActivity(intent);
	}
	
	public void toMyRecipes(View view) {
		Intent intent = new Intent(this, RecipeListActivity.class);
		startActivity(intent);
	}
	


}
