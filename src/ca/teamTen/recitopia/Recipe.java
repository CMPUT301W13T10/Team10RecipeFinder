package ca.teamTen.recitopia;


public class Recipe
{
	
	private String name;
	private String author;

	public Recipe(String name, String[] ingredients, String instructions,
			String author)
	{
		this.name = name;
		this.author = author;
	}

	public String getName()
	{
		return name;
	}

	public String getAuthor()
	{
		return author;
	}

}
