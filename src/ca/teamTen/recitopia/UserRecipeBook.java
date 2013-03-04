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

public class UserRecipeBook implements RecipeBook{

	private ArrayList<Recipe> recipeCollection;
	
	@Override
	public Recipe[] query(String[] ingredients, String title, String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(String fileName, Context ctx) {
		try {  
			FileOutputStream fos = ctx.openFileOutput(fileName,Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(recipeCollection);
			out.close();  
		} catch (FileNotFoundException e) {  
			e.printStackTrace();  

		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		
	}
	
	public void load(String fileName, Context ctx) {
		try {  
			FileInputStream fis = ctx.openFileInput(fileName);
			ObjectInputStream in = new ObjectInputStream(fis);  
			this.recipeCollection = (ArrayList<Recipe>) in.readObject();
			in.close();
		} 
		catch (IOException e) {  
			e.printStackTrace();  
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();  
		}  
	}

}
