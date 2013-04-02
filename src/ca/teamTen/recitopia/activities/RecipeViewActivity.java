package ca.teamTen.recitopia.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import ca.teamTen.recitopia.R;
import ca.teamTen.recitopia.models.ApplicationManager;
import ca.teamTen.recitopia.models.Photo;
import ca.teamTen.recitopia.models.Recipe;
import ca.teamTen.recitopia.models.RecipeBook;


/**
 * Displays a simple view of a recipe. 
 * From here, a user can launch the edit activity for the recipe.
 */
public class RecipeViewActivity extends Activity {

    private static final int RECIPE_EDITED_RESULT = 1;
    private final String SUBJECT_LINE = "[Recitopia Recipe]";
    private ShareActionProvider mShareActionProvider;
	private LinearLayout photoContainer;
    private Recipe recipe;
    private View publishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        photoContainer = (LinearLayout) findViewById(R.id.photoContainer);
        publishButton = findViewById(R.id.publishButton);
        if (getIntent().hasExtra("RECIPE")) {
            recipe = (Recipe)getIntent().getSerializableExtra("RECIPE");
            updateContentView();
        } else {
            finish();
        }
        
    }

    /*
     * Updates the content to match what is in the recipe field.
     */
    private void updateContentView()
    {
        TextView contentView = (TextView)findViewById(R.id.content);
        contentView.setText(recipe.toString());
		drawPhotos();
		
		if (recipe.publishRecipe()) {
			publishButton.setVisibility(View.GONE);
		} else {
			publishButton.setVisibility(View.VISIBLE);
		}

    }
    
    /*
     * Add photos to display.
     */
    private void drawPhotos() {
		if (recipe.getPhotos().length == 0) {
			//No Photos Means that there is nothing to populate
		} else {
			//There exists at least one photo
			
			//Clear the Photo Container
			photoContainer.removeAllViews();
		
			Photo[] photos = recipe.getPhotos();
			for (int i = 0; i < photos.length; i++){
				ImageView iv = new ImageView(this);
				iv.setImageBitmap(photos[i].getBitmap());
				photoContainer.addView(iv,i);
			}
		}
	}

    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_recipe_view, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        shareRecipe();
        return true;
    }

    /*
     * Open this recipe in the recipe editor.
     */
    public void editRecipe(View v) {
        Intent viewIntent = new Intent(this, RecipeEditActivity.class);
        viewIntent.putExtra("RECIPE", recipe);

        startActivityForResult(viewIntent, RECIPE_EDITED_RESULT);
    }

    /*
     * Handle results from the editor activity.
     * Basically, save (and possibly fork) the recipe, then update the
     * display.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECIPE_EDITED_RESULT && resultCode == RESULT_OK) {
            Recipe newRecipe = (Recipe)data.getSerializableExtra("RECIPE");
            newRecipe.setPublished(recipe.publishRecipe());
            	// RecipeEdit has trouble keeping track of this
            
            ApplicationManager appMgr = ApplicationManager.getInstance(getApplication());
            RecipeBook.AsyncAddRecipeTask addRecipeTask = new RecipeBook.AsyncAddRecipeTask();
            
            if (!recipeAuthorIsUser(recipe) && !newRecipe.equalData(recipe)) {
            	recipe = forkRecipe(newRecipe);
            	addRecipeTask.setCallbacks(new RecipeBook.AsyncAddRecipeTask.Callbacks() {
					@Override
					public void recipesAdded()
					{
		            	Toast toast = Toast.makeText(getApplicationContext(),
		                	R.string.recipeForkedToast, Toast.LENGTH_SHORT);
		                toast.show();
					}
				});
            } else {
            	recipe = newRecipe;
            }
            
            if (recipeAuthorIsUser(recipe)) {
            	// save to UserRecipeBook
            	addRecipeTask.addRecipeBook(appMgr.getUserRecipeBook());
            	
            }
            
            if (recipe.publishRecipe()) {
            	// save to cloudRecipeBook
            	addRecipeTask.addRecipeBook(appMgr.getCloudRecipeBook());
            }
            
            addRecipeTask.execute(recipe);
            updateContentView();
        }
    }

    /*
     * Create a fork of the recipe - a recipe with a different author, but
     * otherwise all the same data.
     */
    private Recipe forkRecipe(Recipe source)
	{
    	Recipe newRecipe = new Recipe(
    		source.getRecipeName(),
    		source.getIngredients(),
    		source.getCookingInstructions(),
    		ApplicationManager.getInstance(getApplication()).getUserID());
    	for (Photo photo: source.getPhotos()) {
    		newRecipe.addPhoto(photo);
    	}
    	newRecipe.setPublished(false);
    	return newRecipe;
	}

	private boolean recipeAuthorIsUser(Recipe recipeToCheck)
	{
		String user = ApplicationManager.getInstance(getApplication()).getUserID();
		return user.equals(recipeToCheck.getAuthor());
	}

	// Call to update the share intent
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    /*
     * Share the recipe text using android's sharing mechanism.
     */
    public void shareRecipe() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, recipe.toString());
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, generateSubjectLine());
        sendIntent.setType("text/plain");
        setShareIntent(sendIntent);
    }
    
    /**
     * 
     * @return a subject line formatted in the following way:
     * [Recitopia Recipe] Recipe Name by Recipe Auther
     */
    private String generateSubjectLine(){
    	return SUBJECT_LINE + " " + recipe.getRecipeName() + " by " + recipe.getAuthor();
    }
    
    /*
     * Publish the current recipe.
     * 
     * This entails saving it to the CloudRecipeBook and also setting isPublished
     * to true and saving to the user's recipe book.
     */
    public void publishRecipe(View view){
    	ApplicationManager appMgr = ApplicationManager.getInstance(getApplication());
    	RecipeBook.AsyncAddRecipeTask addTask = new RecipeBook.AsyncAddRecipeTask();
    	addTask.addRecipeBook(appMgr.getCloudRecipeBook());
    	if (recipeAuthorIsUser(recipe)) {
    		addTask.addRecipeBook(appMgr.getUserRecipeBook());
    	}
    	addTask.setCallbacks(new RecipeBook.AsyncAddRecipeTask.Callbacks() {
			@Override
			public void recipesAdded()
			{
		    	Toast toast = Toast.makeText(getApplicationContext(),
		    			"Recipe published!", Toast.LENGTH_SHORT);
		    	toast.show();
			}
		});
    	recipe.setPublished(true);
    	addTask.execute(recipe);
    }
    
    /*
     * Add the recipe to the user's favorites book.
     */
    public void saveFavorite(View view) {
        RecipeBook recipeBook = ApplicationManager.getInstance(getApplication())
        		.getFavoriteRecipesBook();
        recipeBook.addRecipe(recipe);
        recipeBook.save();
        Toast toast = Toast.makeText(this, "Recipe saved as favorite!", Toast.LENGTH_LONG);
    	toast.show();
    }
}
