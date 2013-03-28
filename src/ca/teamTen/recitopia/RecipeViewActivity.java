package ca.teamTen.recitopia;

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


/**
 * Displays a simple view of a recipe. (Without pictures, for now).
 * From here, a user can launch the edit activity for the recipe.
 */
public class RecipeViewActivity extends Activity {

    private static final int RECIPE_EDITED_RESULT = 1;
    private ShareActionProvider mShareActionProvider;
	private LinearLayout photoContainer;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        photoContainer = (LinearLayout) findViewById(R.id.photoContainer);
        if (getIntent().hasExtra("RECIPE")) {
            recipe = (Recipe)getIntent().getSerializableExtra("RECIPE");
            updateContentView();
        } else {
            finish();
        }
        
    }

    private void updateContentView()
    {
        TextView contentView = (TextView)findViewById(R.id.content);
        contentView.setText(recipe.toString());
		drawPhotos();

    }
    
    private void drawPhotos() {
    	
		if (recipe.getPhotos().length == 0){
			//No Photos Means that there is nothing to populate
		}
		else {
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

    public void editRecipe(View v) {
        Intent viewIntent = new Intent(this, RecipeEditActivity.class);
        viewIntent.putExtra("RECIPE", recipe);

        startActivityForResult(viewIntent, RECIPE_EDITED_RESULT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECIPE_EDITED_RESULT && resultCode == RESULT_OK) {
            recipe = (Recipe)data.getSerializableExtra("RECIPE");
            updateContentView();
            RecipeBook recipeBook = ApplicationManager.getInstance(getApplication())
            		.getUserRecipeBook();
            recipeBook.addRecipe(recipe);
            recipeBook.save();
        }
    }

    // Call to update the share intent
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    public void shareRecipe() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, recipe.toString());
        sendIntent.setType("text/plain");
        setShareIntent(sendIntent);

    }
}
