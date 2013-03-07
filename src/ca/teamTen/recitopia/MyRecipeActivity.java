package ca.teamTen.recitopia;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

public class MyRecipeActivity extends Activity {

	private ShareActionProvider mShareActionProvider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_recipe);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_my_recipe, menu);
		
		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.menu_item_share);
		
		// Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) item.getActionProvider();

		shareRecipe();
	    // Return true to display menu
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
		Intent emailIntent = new Intent();
		//emailIntent.setAction(Intent.ACTION_SEND);
		//emailIntent.putExtra(Intent.EXTRA_TEXT, value)
		emailIntent.setType("text/plain");
		emailIntent.putExtra(Intent.EXTRA_TEXT, "Recipe to share");
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Title");
		startActivity(emailIntent);

	}
	/*
	public void shareViaEmail() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("plain/text");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "some@email.address" });
		intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
		intent.putExtra(Intent.EXTRA_TEXT, "mail body");
		startActivity(Intent.createChooser(intent, ""));
	}
	*/
}
