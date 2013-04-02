package ca.teamTen.recitopia.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import ca.teamTen.recitopia.R;
import ca.teamTen.recitopia.models.ApplicationManager;
import ca.teamTen.recitopia.models.Recipe;
import ca.teamTen.recitopia.models.RecipeBook;

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
	
	/**
	 * Start LoginActivity if we don't have a userid.	
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (ApplicationManager.getInstance(getApplication()).getUserID() == null) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.clearCache:
	    	onMenuClearCache();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * Called when the Clear Cache Button is pressed on the Main Activity Menu
	 */
	public void onMenuClearCache(){
		ApplicationManager appmgr = ApplicationManager.getInstance(getApplication());
		appmgr.clearCache();
		Toast toast = Toast.makeText(this, R.string.cacheClearedToast, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	/**
	 * Launch edit activity to create a new recipe
	 * @param view
	 */
	public void toCreate(View view) {
		Intent intent = new Intent(this, RecipeEditActivity.class);
		startActivityForResult(intent, RECIPE_CREATED_RESULT);
	}
	
	/**
	 * Launch search activity
	 * @param view
	 */
	public void toSearch(View view) {
		Intent intent = new Intent(this, SearchActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Launch fridge activity
	 * @param view
	 */
	public void toFridge(View view) {
		Intent intent = new Intent(this, FridgeActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Launch RecipeListActivity to view the user's recipes.
	 * @param view
	 */
	public void toMyRecipes(View view) {
		Intent intent = new Intent(this, RecipeListActivity.class);
		intent.putExtra("type", "My Recipes");
		startActivity(intent);
	}
	
	/**
	 * Launch RecipeListActivity to view the user's favorited
	 * recipes.
	 * 
	 * @param view
	 */
	public void toMyFavorite(View view) {
		Intent intent = new Intent(this, RecipeListActivity.class);
		intent.putExtra("type", "My Favorite");
		startActivity(intent);
	}
	
	/**
	 * Handle newly created recipes from RecipeEditActivity by saving them to
	 * the UserRecipeBook.
	 */
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
