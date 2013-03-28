package ca.teamTen.recitopia;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * An activity that allows the user to add/edit/remove ingredients from their fridge
 * TODO Use the Fridge class for saving/loading ingredients
 */
public class FridgeActivity extends Activity implements IngredientAdapter.Callbacks{
    private LinearLayout ingredientLayout;
    private IngredientAdapter ingredientAdapter;
    
    /**
     * This method is called to redraw the LinearLayout that contains the list of
     * items in the Fridge
     */
 
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        initList();

        ingredientLayout = (LinearLayout) findViewById(R.id.fridgeIngredientList);
        ingredientAdapter = new IngredientAdapter(this, this, ingredientList);
        drawIngredients();

        Button addIngredient = (Button) findViewById(R.id.fridgeAddIngredient);      
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_fridge, menu);
        return true;
    }

    @Override
    public void ingredientDeleted(int location)
    {
        drawIngredients();        
    }
    
    public void searchByIngredients(View v){
    	Intent intent = new Intent(this, SearchByIngredientActivity.class);
        startActivity(intent);
    	
    }
}
