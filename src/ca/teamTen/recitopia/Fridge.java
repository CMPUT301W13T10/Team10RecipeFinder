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
	
	public Fridge() {
		ingredients = new HashSet<String>();
	}
	
	public boolean addIngredient(String ingredient) {
		return ingredients.add(ingredient);
	}
	
	public void removeIngredient(String ingredient) {
		ingredients.remove(ingredient);
	}
	
	public Collection<String> getIngredients()
	{
		return new ArrayList<String>(ingredients);
	}	

	public int countIngredients() {
		return ingredients.size();
	}
	
	public void saveFridgeInfo(String fileName, Context ctx) {
		try {  
			FileOutputStream fos = ctx.openFileOutput(fileName,Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(ingredients);
			out.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  

		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}
	
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
