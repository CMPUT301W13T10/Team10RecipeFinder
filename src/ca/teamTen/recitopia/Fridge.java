package ca.teamTen.recitopia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Fridge class stores a set of ingredients, loads/saves
 * from a file. Duplicate ingredients are ignored.
 */
public class Fridge{
	private Set<String> ingredients;
	private IOFactory ioFactory;

	/**
	 * Create a new Fridge that doesn't save/load.s
	 */
	public Fridge() {
		this(new IOFactory.NullIOFactory());
	}
	
	/**
	 * Build an empty fridge.
	 */
	public Fridge(IOFactory ioFactory) {
		this.ioFactory = ioFactory;
		ingredients = new HashSet<String>();
	}
	
	/**
	 * Delete all ingredients.
	 */
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
	public void save() {
		try {  
			OutputStream rawOutputStream = ioFactory.getOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(rawOutputStream);
			out.writeObject(ingredients);
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
	 */
	public void load() {
		try {  
			InputStream rawInputStream = ioFactory.getInputStream();
			ObjectInputStream in = new ObjectInputStream(rawInputStream);  
			this.ingredients = (Set<String>) in.readObject();
			in.close();
		} catch (IOException e) {  
			e.printStackTrace();  
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();  
		}  
	}
}
