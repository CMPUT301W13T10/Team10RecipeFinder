package ca.teamTen.recitopia;

import java.util.ArrayList;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.BufferType;


/**
 * The IngredientAdapter handles inflating the layout of ingredients
 * within the FridgeActivity and RecipeEditActivity classes.
 */
public class IngredientAdapter extends ArrayAdapter<String> {

	
    interface Callbacks{
        public void ingredientDeleted(int location);
    }

    private ArrayList<String> ingredients;
    private Context context;
    private Callbacks callbacks;
    
    
    public IngredientAdapter(Context context, Callbacks callbacks, ArrayList<String> objects) {
        super(context, R.layout.ingredient_layout, objects);
        this.ingredients = objects;
        this.context = context;
        this.callbacks = callbacks;
    }
    
    /**
     * @return the current list of Ingredients
     */
    public ArrayList<String> getIngredients(){
        return ingredients;
    }

    /**
     * 
     * @param the ingredient you would like to add
     */
    public void addIngredient(String string){
        ingredients.add(string);
    }

    /**
     * 
     * @param the location of the ingredient within the list
     * 
     */
    public void removeIngredient(int location){
        ingredients.remove(location);
        callbacks.ingredientDeleted(location);
    }



    /**
     * 
     * @param position representing the ingredient within the list
     * @param convertView 
     * @param parent of the current View
     * @return a view inflated within the layout of an ingredient
     */
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ingredient_layout, parent, false);
        }

       
        final TextView name = (TextView) convertView.findViewById(R.id.enterIngredient);
        final EditText editName = (EditText) convertView.findViewById(R.id.editIngredient);
        final Button deleteIngredient = (Button) convertView.findViewById(R.id.deleteIngredient);
        final String ingredientName = ingredients.get(position);

        
        //Setting the name of the ingredient
        name.setText(ingredientName);

        //Setting the tag for the name
        name.setTag(position);
        
        name.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	//Setting the text that the EditText will use
                editName.setText(ingredientName, BufferType.EDITABLE);
                //The TextView is now invisible
                name.setVisibility(View.GONE);
                //The EditText is now visible
                editName.setVisibility(View.VISIBLE);
            }
        });

        editName.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            	
                if(actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN){
                	//Handling hardware input of the 'Enter' key
                    editName.setVisibility(View.GONE);
                    name.setText(editName.getText());
                    name.setVisibility(View.VISIBLE);
                    Integer position = (Integer) (name.getTag());     
                    String s = name.getText().toString();
                    ingredients.set(position, s);

                } else if (actionId == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    //Handling software input of the 'Enter' key
                    editName.setVisibility(View.GONE);
                    name.setText(editName.getText());
                    name.setVisibility(View.VISIBLE);
                    Integer position = (Integer) (name.getTag());     
                    String s = name.getText().toString();
                    ingredients.set(position, s);

                }
                return true;
            }
        });
        
        //Setting a tag associated with the position of the ingredient
        deleteIngredient.setTag(position);
        deleteIngredient.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	//Call the removeIngredient method
                IngredientAdapter.this.removeIngredient((Integer) deleteIngredient.getTag());

            }
        });
        return convertView;
    }

}
