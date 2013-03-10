package ca.teamTen.recitopia;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;


public class RecipeEditActivity extends Activity {
	
	List<String> ingredientList = new ArrayList<String>();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        initList();
        ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(new IngredientAdapter(this, ingredientList));
        
    } 
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_recipe, menu);
        return true;
    }
    
    /*
     * TODO Create a Custom View Ingredients String
     * http://developer.android.com/guide/topics/ui/layout/listview.html
     * http://www.survivingwithandroid.com/2012/10/android-listview-custom-adapter-and.html
     * http://developer.android.com/training/improving-layouts/smooth-scrolling.html
     */
    

}
