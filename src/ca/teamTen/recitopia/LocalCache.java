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
public class LocalCache extends RecipeBookBase {
	private int	maxEntries;
	
	public LocalCache(int maxEntries) {
		this.maxEntries = maxEntries;
	}

	@Override
	protected void recipeAdded(Recipe recipe) {
		if (recipes.size() > maxEntries) {
			recipes.remove(0);
		}
		// TODO save?
	}

	@Override
	protected void recipeUpdated(Recipe recipe, int i) {
		// TODO save?
	}
}
