package ca.teamTen.recitopia;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
            ApplicationManager appMgr = ApplicationManager.getInstance();
            recipe = new Recipe("", ingredientList, "", appMgr.getUserID());
        }


        ingredientLayout = (LinearLayout) findViewById(R.id.ingredientList);
        editRecipeName = (EditText)findViewById(R.id.editTextRecipeName);
        editInstructions = (EditText) findViewById(R.id.editTextInstructions);
        addIngredient = (Button) findViewById(R.id.addIngredient); 
        
        editRecipeName.setText(recipe.getRecipeName());
        editInstructions.setText(recipe.showCookingInstructions());

        //Setting the ingredient adapter
        ingredientAdapter = new IngredientAdapter(this, this, recipe.showIngredients());
      
        addIngredient.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                addIngredient(new String(""));
                drawIngredients();
            }
        });
        
        //Drawing the ingredients
        drawIngredients();

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
        		//Launching the TakePhotoActivity class
        		//TODO Implement startActivityWithResult and handle the photo that was taken
        		Intent intent = new Intent();
        		intent.setClass(getBaseContext(), TakePhotoActivity.class);
        		startActivity(intent);
        		return false;
        	}
        });
        
        return true;
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
     * 
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
        String author = recipe.showAuthor();
        ArrayList<String> ingredients = ingredientAdapter.getIngredients();
        return new Recipe(name, ingredients, instructions, author);
    }
}
