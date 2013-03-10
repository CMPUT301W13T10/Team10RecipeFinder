package ca.teamTen.recitopia;

import java.util.List;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class IngredientAdapter extends ArrayAdapter<String> {

	private List<String> ingredients;
	private Context context;
	public IngredientAdapter(Context context, List<String> objects) {
		super(context, R.layout.ingredient_layout, objects);
		this.ingredients = objects;
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		if(convertView == null){
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.ingredient_layout, parent, false);
		}

		final TextView name = (TextView) convertView.findViewById(R.id.enterIngredient);
		final EditText editName = (EditText) convertView.findViewById(R.id.editIngredient);
		String s = ingredients.get(position);
		name.setText(s);
		editName.setText(s, BufferType.EDITABLE);

		
		name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name.setVisibility(View.GONE);
				editName.setVisibility(View.VISIBLE);
				//				editName.requestFocus();
			}
		});

		editName.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				if(actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN){
					editName.setVisibility(View.GONE);
					name.setText(editName.getText());
					name.setVisibility(View.VISIBLE);
				}
				return true;
			}
		});
		/*
		 * TODO When the Delete button is pressed the ingredient is removed from the
		 * Recipe
		 */
		Button deleteIngredient = (Button) convertView.findViewById(R.id.deleteIngredient);
		deleteIngredient.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		return convertView;
	}

}
