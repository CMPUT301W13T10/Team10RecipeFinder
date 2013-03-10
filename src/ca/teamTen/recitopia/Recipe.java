package ca.teamTen.recitopia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.util.Log;


public class Recipe
{
	private String recipeName;
	private ArrayList<String> ingredients;
	private String cookingInstruction;
	private String author;
	private boolean isPublished;
	private ArrayList<Photo> photos;
	
	// Recipe constructor
	public Recipe(String recipeName, ArrayList<String> ingredients, String instruction, String author) {
		this.recipeName = recipeName;
		if (ingredients != null) {
			this.ingredients = new ArrayList<String>(ingredients);
		} else {
			this.ingredients = new ArrayList<String>();
		}
		this.cookingInstruction = instruction;
		this.author = author;
		this.photos = new ArrayList<Photo>();
	}
	
	public String getRecipeName() {
		return this.recipeName;
	}
	
	public String showAuthor() {
		return this.author;
	}
	
	public ArrayList<String> showIngredients() {
		return this.ingredients;
	}
	
	public String showCookingInstructions() {
		return this.cookingInstruction;
	}

	public Photo[] getPhotos() {
		return (Photo[]) this.photos.toArray(new Photo[photos.size()]);
	}
	
	public void addPhotos(Photo photo) {
		this.photos.add(photo);
	}

	public boolean publishRecipe() {
		return false;
	}
	
	/**
	 * Checks for equality of all members.
	 * @return true if all members are equal
	 */
	public boolean equalData(Recipe other) {
		if (other == null) {
			return false;
		}
		
		Set<String> myIngredients = new HashSet<String>(ingredients);
		for (String ingredient: other.ingredients) {
			if (!myIngredients.contains(ingredient)) {
				return false;
			}
		}
		
		return recipeName.equals(other.recipeName)
				&& cookingInstruction.equals(other.cookingInstruction)
				&& author.equals(other.author)
				&& (isPublished == other.isPublished)
				;
		// TODO: check photos?
	}
	
	// toString() method converts a Recipe object into a string for sharing purpose
	public String toString() {
		String ingredientsString  = new String();
		for(int i = 0; i < ingredients.size(); i++) {
			if(i == (ingredients.size()-1))
				ingredientsString = ingredientsString.concat(ingredients.get(i)).concat("\n");
			else
				ingredientsString = ingredientsString.concat(ingredients.get(i)).concat(", ");
		}
		
		return "Recipe Name: " + getRecipeName() + "\n" + "\n"
		+ "Author: " + author + "\n" + "\n"
		+ "Ingredients: " + ingredientsString + "\n" + "\n"
		+ "Cooking Instructions: " + cookingInstruction;
	}
}
