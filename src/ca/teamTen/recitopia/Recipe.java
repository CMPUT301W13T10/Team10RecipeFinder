package ca.teamTen.recitopia;

import java.util.ArrayList;


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
		this.ingredients = ingredients;
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
	

}
