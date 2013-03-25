package ca.teamTen.recitopia;

import java.util.ArrayList;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * This activity loads or creates an activity when launched. When the activity
 * finishes it returns the modified or created recipe.
 */
public class RecipeEditActivity extends Activity implements IngredientAdapter.Callbacks{

	private Recipe recipe;
	private LinearLayout ingredientLayout;
	private IngredientAdapter ingredientAdapter;
	private EditText editRecipeName;
	private EditText editInstructions;
	private ArrayList<String> ingredientList = new ArrayList<String>();
	private Button addIngredient;
	private LinearLayout photoContainer;


	private static final int CAMERA_PIC_REQUEST = 2500; //TODO Put this somewhere else


	/**
	 * This method is called to redraw the LinearLayout that contains the list of Ingredients
	 * 
	 */
	private void drawIngredients(){
		ingredientLayout.removeAllViews();      
		for (int i = 0; i < ingredientAdapter.getCount(); i++){
			ingredientLayout.addView(ingredientAdapter.getView(i, null, null));
		}

	}

	/**
	 *   
	 * @param A string representing the Ingredient
	 * 
	 * Adds a new ingredient to the list of of ingredients within the adapter.
	 */
	public void addIngredient(String ingredient){
		//Add an ingredient to the ingredientAdapter
		ingredientAdapter.addIngredient(ingredient);
	}

	/**
	 * @param a Bundle from the activity that called this activity
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_recipe);

		//Getting the intent
		Intent intent = getIntent();
		if(intent.hasExtra("RECIPE")){
			//An existing recipe was sent
			recipe = (Recipe) intent.getSerializableExtra("RECIPE");
		} else {
			//No recipe was sent - therefore create a new one
			ApplicationManager appMgr = ApplicationManager.getInstance(getApplication());
			recipe = new Recipe("", ingredientList, "", appMgr.getUserID());
		}

		photoContainer = (LinearLayout) findViewById(R.id.photoContainer);
		drawPhotos();

		ingredientLayout = (LinearLayout) findViewById(R.id.ingredientList);
		editRecipeName = (EditText)findViewById(R.id.editTextRecipeName);
		editInstructions = (EditText) findViewById(R.id.editTextInstructions);
		addIngredient = (Button) findViewById(R.id.addIngredient);

		editRecipeName.setText(recipe.getRecipeName());
		editInstructions.setText(recipe.getCookingInstructions());

		//Setting the ingredient adapter
		ingredientAdapter = new IngredientAdapter(this, this, recipe.getIngredients());

		addIngredient.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				addIngredient.requestFocusFromTouch();
				addIngredient(new String(""));
				drawIngredients();
			}
		});

		//Drawing the ingredients
		drawIngredients();

	}

	private void drawPhotos() {
		/*
		 * Populating the photoContainer
		 * As far as Refactoring is concerned this could be turned into a method
		 */
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_edit_recipe, menu);
		//Adding a photo button to the menu
		MenuItem item = menu.findItem(R.id.menu_item_photo);
		item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
		{

			@Override
			public boolean onMenuItemClick(MenuItem item)
			{

				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

				return false;
			}
		});

		return true;
	}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_PIC_REQUEST) {
			if(resultCode == RESULT_OK) {
				Bitmap image = (Bitmap) data.getExtras().get("data");
				Photo photo = new Photo(image);
				//Needed to update the model now that a new photo has been created
				recipe.addPhoto(photo);
				//Redrawing ALL the photos
				drawPhotos();


			}
			else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}

		}

	}
	/**
	 * @param redrawing the list of ingredients when a ingredient has been deleted
	 */
	@Override
	public void ingredientDeleted(int location)
	{
		drawIngredients();
	}

	/**
	 * When the user is finished return the Recipe
	 */
	public void userFinished(View v){
		Intent intent = new Intent();    
		intent.putExtra("RECIPE", createRecipe());
		this.setResult(RESULT_OK,intent);
		finish();
	}

	/**
	 * @return a Recipe object representing the current Recipe
	 */
	public Recipe createRecipe(){
		String name = editRecipeName.getText().toString();
		String instructions = editInstructions.getText().toString();
		String author = recipe.getAuthor();
		ArrayList<String> ingredients = ingredientAdapter.getIngredients();
		Photo[] photos = recipe.getPhotos();
		
		Recipe currentRecipe = new Recipe(name, ingredients, instructions, author);
		for (Photo photo: photos){
			currentRecipe.addPhoto(photo);
		}
		return currentRecipe;
	}
}
