package ca.teamTen.recitopia;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


public class EditRecipe extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_recipe, menu);
        return true;
    }

}
