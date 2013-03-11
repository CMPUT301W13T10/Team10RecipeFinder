package ca.teamTen.recitopia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class UserRecipeBook extends RecipeBookBase {

	@Override
	protected void recipeAdded(Recipe recipe) {
		// TODO: save?
	}

	@Override
	protected void recipeUpdated(Recipe recipe, int i) {
		// TODO: save?
	}
}
