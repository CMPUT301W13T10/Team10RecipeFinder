package ca.teamTen.recitopia.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * A Recipe with a name, ingredients, instructions, photos
 * and an author. Recipes can be published (stored on the server)
 * or not (stored on the phone).
 * 
 * Recipe instances are somewhat immutable.
 * 
 * @see RecipeBook
 */
public class Recipe implements Serializable
{
	/**
	 * Serializable version id.
	 */
	private static final long serialVersionUID = 4658750314538117911L;
	
	@Expose private String recipeName;
	@Expose private ArrayList<String> ingredients;
	@Expose private String cookingInstruction;
	@Expose private String author;
	@Expose private ArrayList<Photo> photos;
	
	private boolean isPublished;
		// not persisted via GSON
	
	/**
	 * simple constructor for use with GSon
	 * @param published 
	 */
	public Recipe(boolean published) {
		isPublished = published;
	}
	
	/**
	 * Create a new Recipe
	 * @param recipeName name of the recipe
	 * @param ingredients an array of ingredients
	 * @param instruction a string containing the instructions
	 * @param author the userid (email) of the author of this recipe
	 */
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
	
	/**
	 * Get the name/title of the recipe
	 * @return the name
	 */
	public String getRecipeName() {
		return this.recipeName;
	}
	
	/**
	 * Get the recipe author's userid (email)
	 * @return the author
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Get the recipe ingredients
	 * @return the ingredients
	 */
	public ArrayList<String> getIngredients() {
		return this.ingredients;
	}
	
	/**
	 * Get the recipe instructions
	 * @return instructions
	 */
	public String getCookingInstructions() {
		return this.cookingInstruction;
	}

	/**
	 * Gets the photos associated with this recipe
	 * @return an array of photos associated with this recipe
	 */
	public Photo[] getPhotos() {
		return (Photo[]) this.photos.toArray(new Photo[photos.size()]);
	}
	
	/**
	 * Adds a photo to the recipe.
	 * @param photo
	 */
	public void addPhoto(Photo photo) {
		this.photos.add(photo);
	}

	/**
	 * Gets whether the recipe has been published
	 * @return true if the recipe is published
	 */
	public boolean publishRecipe() {
		return isPublished;
	}
	
	/**
	 * Sets whether the recipe has been published
	 */
	public void setPublished(boolean published) {
		isPublished = published;
	}
	
	/**
	 * Checks for equality of all members.
	 * 
	 * isPublished is not checked, as this is meta-data, not
	 * data. Photos are also not checked.
	 * 
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
				;
	}
	
	/**
	 *  toString() method converts a Recipe object into a string for sharing/display purposes
	 */
	public String toString() {
		String ingredientsString  = new String();
		for(int i = 0; i < ingredients.size(); i++) {
			if(i == (ingredients.size()-1))
				ingredientsString = ingredientsString.concat(ingredients.get(i)).concat("\n");
			else
				ingredientsString = ingredientsString.concat(ingredients.get(i)).concat(", ");
		}
		
		return "Recipe Name: \n\t\t" + getRecipeName() + "\n" + "\n"
		+ "Author: \n\t\t" + author + "\n" + "\n"
		+ "Ingredients: \n\t\t" + ingredientsString + "\n"
		+ "Cooking Instructions: \n\t\t" + cookingInstruction + "\n";
	}
}
