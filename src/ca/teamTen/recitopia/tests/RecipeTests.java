package ca.teamTen.recitopia.tests;

import ca.teamTen.recitopia.Recipe;
import junit.framework.TestCase;


public class RecipeTests extends TestCase
{

	private Recipe recipe;
	
	protected void setUp() throws Exception
	{
		super.setUp();

		recipe = new Recipe();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
}
