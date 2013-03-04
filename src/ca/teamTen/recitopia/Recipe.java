package ca.teamTen.recitopia;

import java.util.ArrayList;


public class Recipe
{

	private String recipeName;
	private ArrayList<String> ingredients;
	private String cookingInstruction;
	private String author;
	private boolean isPublished;
	
	// Recipe constructor
	public Recipe(String recipeName, ArrayList<String> ingredients, String instruction, String author) {
		this.recipeName = recipeName;
		this.ingredients = ingredients;
		this.cookingInstruction = instruction;
		this.author = author;
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
		return null;
	}
	
	public void addPhotos(Photo photo) {
	
	}

	public boolean publishRecipe() {
		return false;
	}
	

}
