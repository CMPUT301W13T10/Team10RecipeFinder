package ca.teamTen.recitopia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class Fridge{

	private ArrayList<String> ingredients;
	
	public Fridge() {
		ingredients = new ArrayList();
	}
	
	public void addIngredient(String ingredient) {
		ingredients.add(ingredient);
	}
	
	public void removeIngredient(int index) {
		ingredients.remove(index);
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
			this.ingredients = (ArrayList<String>) in.readObject();
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
