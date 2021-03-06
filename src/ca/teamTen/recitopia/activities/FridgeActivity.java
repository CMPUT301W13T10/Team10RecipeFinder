package ca.teamTen.recitopia.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import ca.teamTen.recitopia.R;
import ca.teamTen.recitopia.models.ApplicationManager;
import ca.teamTen.recitopia.models.Fridge;

/**
 * An activity that allows the user to add/edit/remove ingredients from their fridge
 */
public class FridgeActivity extends Activity implements IngredientAdapter.Callbacks{
	private LinearLayout ingredientLayout;
	private IngredientAdapter ingredientAdapter;
	private Fridge fridge;

	/**
	 * This method is called to redraw the LinearLayout that contains the list of
	 * items in the Fridge
	 */
	private void drawIngredients(){
		ingredientLayout.removeAllViews();      
		for (int i = 0; i < ingredientAdapter.getCount(); i++){
			ingredientLayout.addView(ingredientAdapter.getView(i, null, null));
		}
		ingredientLayout.requestFocus();
	}

	/**
	 * Add a new ingredient
	 * @param ingredient
	 */
	public void addIngredient(String ingredient){
		//Add an ingredient to the ingredientAdapter
		ingredientAdapter.addIngredient(ingredient);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fridge);

		fridge = ApplicationManager.getInstance(getApplication()).getFridge();
		fridge.load();

		ingredientLayout = (LinearLayout) findViewById(R.id.fridgeIngredientList);
		ingredientAdapter = new IngredientAdapter(this, this, fridge.getIngredients());
		drawIngredients();

		final Button addIngredient = (Button) findViewById(R.id.fridgeAddIngredient);      
		addIngredient.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				addIngredient.requestFocusFromTouch();
				if(ingredientAdapter.hasEmptyIngredient()) {
					//Already has an empty Ingredient
				} else {
					addIngredient(new String(""));
				}
				drawIngredients();
			}
		});
	}

	/**
	 * Called when an ingredient has been deleted.
	 * Update display.
	 */
	@Override
	public void ingredientDeleted()
	{
		drawIngredients();        
	}

	@Override
	protected void onPause() {
		super.onPause();

		for(int i = 0; i < ingredientAdapter.getCount(); i++) {
			if (ingredientAdapter.getItem(i).length() == 0) {
				//Empty String - Do not Save!
			} else {
				//Not Empty Ingredient - Add it to the fridge!
				fridge.addIngredient(ingredientAdapter.getItem(i));
			}
		}
		fridge.save();
	}

	/**
	 * Launch SearchByIngredientActivity
	 * @param v
	 */
	public void searchByIngredients(View v){
		Intent intent = new Intent(this, SearchByIngredientActivity.class);
		startActivity(intent);
	}
}
