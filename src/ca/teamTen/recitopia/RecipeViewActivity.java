package ca.teamTen.recitopia;

import java.io.Serializable;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class RecipeViewActivity extends Activity {
	
	private ShareActionProvider mShareActionProvider;
	private Recipe recipe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_view);
		
		if (getIntent().hasExtra("RECIPE")) {
			recipe = (Recipe)getIntent().getSerializableExtra("RECIPE");
			
			TextView contentView = (TextView)findViewById(R.id.content);
			contentView.setText(recipe.toString());
		} else {
			finish();
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
