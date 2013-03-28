package ca.teamTen.recitopia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.util.Log;

/**
 * Fridge class stores a set of ingredients, loads/saves
 * from a file. Duplicate ingredients are ignored.
 */
public class Fridge{

	private Set<String> ingredients;
	
	/**
	 * Build an empty fridge.
	 */
	public Fridge() {
		ingredients = new HashSet<String>();
	}
	
	public void clearFridge() {
		ingredients = new HashSet<String>();
	}
	/**
	 * Add a new ingredient to the fridge.
	 * @return true if the ingredient was added (not a duplicate).
	 */
	public boolean addIngredient(String ingredient) {
		return ingredients.add(ingredient);
	}
	
	/**
	 * Remove ingredient from the fridge.
	 */
	public void removeIngredient(String ingredient) {
		ingredients.remove(ingredient);
	}
	
	/**
	 * Get a collection of all ingredients in the fridge
	 * @return A new Collection with the ingredients
	 */
	public ArrayList<String> getIngredients()
	{
		return new ArrayList<String>(ingredients);
	}	

	/**
	 * get the number of ingredients in the fridge
	 * @return the number of ingredients
	 */
	public int countIngredients() {
		return ingredients.size();
	}
	
	/**
	 * Save the fridge to a file.
	 * @param fileName the file to save to
	 * @param ctx an Android Context used for io
	 */
	public void saveFridgeInfo(String fileName, Context ctx) {
		try {  
			FileOutputStream fos = ctx.openFileOutput(fileName,Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(ingredients);
			Log.v("Write", "Write");
			out.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  

		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}
	
	/**
	 * Load fridge contents from file.
	 * 
	 * @param fileName the file to read from
	 * @param ctx an Android Context used for io
	 */
	public void loadFridgeInfo(String fileName, Context ctx) {
		try {  
			FileInputStream fis = ctx.openFileInput(fileName);
			ObjectInputStream in = new ObjectInputStream(fis);  
			this.ingredients = (Set<String>) in.readObject();
			Log.v("Read", "Read");
			in.close();
		} 
		catch (IOException e) {  
			e.printStackTrace();  
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();  
		}  
	}
}
