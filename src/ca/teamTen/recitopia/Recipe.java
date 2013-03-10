package ca.teamTen.recitopia;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


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
}
