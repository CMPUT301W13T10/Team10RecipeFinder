package ca.teamTen.recitopia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;


/**
 *  This class stores a limited number of recipes that are not created by current user
 * 
 */
public class LocalCache implements RecipeBook{

	private Queue<Recipe>	recipeQueue;
	private int			maxEntries;
	
	public LocalCache(int maxEntries) {
		recipeQueue = new LinkedList<Recipe>();
		this.maxEntries = maxEntries;
	}
	
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
		// TODO Auto-generated method stub
		try {  
			FileOutputStream fos = ctx.openFileOutput(fileName,Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(recipeQueue);
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
			this.recipeQueue = (Queue<Recipe>) in.readObject();
			in.close();
		} 
		catch (IOException e) {  
			e.printStackTrace();  
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();  
		}  
	}

}
