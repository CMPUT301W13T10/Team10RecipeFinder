package ca.teamTen.recitopia;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

/**
 * Launch activity, lets the user launch different activities.
 * 
 * From here, the user can: create a recipe, view their recipes,
 * search for recipes, or manage their fridge.
 */
public class MainActivity extends Activity {

	private static final int RECIPE_CREATED_RESULT = 1;

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
		startActivityForResult(intent, RECIPE_CREATED_RESULT);
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
		intent.putExtra("type", "My Recipes");
		startActivity(intent);
	}
	
	public void toMyFavorite(View view) {
		Intent intent = new Intent(this, RecipeListActivity.class);
		intent.putExtra("type", "My Favorite");
		startActivity(intent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RECIPE_CREATED_RESULT && resultCode == RESULT_OK) {
			Recipe recipe = (Recipe)data.getSerializableExtra("RECIPE");
			RecipeBook userRecipeBook = ApplicationManager.getInstance(getApplication())
				.getUserRecipeBook();
			userRecipeBook.addRecipe(recipe);
			userRecipeBook.save();
		}
	}
}
