package ca.teamTen.recitopia;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class RecipeEditActivity extends Activity implements IngredientAdapter.Callbacks{

    private Recipe recipe;
    private LinearLayout ingredientLayout;
    private IngredientAdapter ingredientAdapter;
    
    /*
     * Used for Testing Purpose/Porpoises
     */
    private ArrayList<String> ingredientList = new ArrayList<String>();
    private void initList(){
        ingredientList.add("Bacon");
        ingredientList.add("Spikey Melon");
        ingredientList.add("Milk");
        ingredientList.add("Salmon");
        ingredientList.add("Meat (not horse meat)");
        ingredientList.add("Lettuce");
        ingredientList.add("Mustard");

    }

    private void drawIngredients(){
        ingredientLayout.removeAllViews();      
        for (int i = 0; i < ingredientAdapter.getCount(); i++){
            ingredientLayout.addView(ingredientAdapter.getView(i, null, null));
        }

    }
    public void addIngredient(){
        ingredientAdapter.addIngredient("");
        drawIngredients();
        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
//        initList();
        
        /*
         * Getting the Intent
         */
        Intent intent = getIntent();
        if(intent.hasExtra("RECIPE")){
            recipe = (Recipe) intent.getSerializableExtra("RECIPE");
        }
        else {
            ApplicationManager appMgr = ApplicationManager.getInstance();
            recipe = new Recipe("Test Name", ingredientList, "Test Instructions", appMgr.getUserID());
        }
        
        
        ingredientLayout = (LinearLayout) findViewById(R.id.ingredientList);
        
        EditText editRecipeName = (EditText)findViewById(R.id.editTextRecipeName);
        editRecipeName.setText(recipe.getRecipeName());
        
        EditText editInstructions = (EditText) findViewById(R.id.editTextInstructions);
        editInstructions.setText(recipe.showCookingInstructions());
        
        
        ingredientAdapter = new IngredientAdapter(this, this, recipe.showIngredients());
        drawIngredients();
   
        Button addIngredient = (Button) findViewById(R.id.addIngredient);      
        addIngredient.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
                addIngredient();                
            }
        });
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_recipe, menu);
        return true;
    }


    @Override
    public void ingredientDeleted(int location)
    {
        drawIngredients();
    }

   public void userFinished(){
       Intent intent = new Intent();    
       intent.putExtra("RECIPE", recipe);
       this.setResult(RESULT_OK,intent);
       finish();
   }

 
   



    /*
     * TODO Create a Custom View Ingredients String
     * http://developer.android.com/guide/topics/ui/layout/listview.html
     * http://www.survivingwithandroid.com/2012/10/android-listview-custom-adapter-and.html
     * http://developer.android.com/training/improving-layouts/smooth-scrolling.html
     */


}
