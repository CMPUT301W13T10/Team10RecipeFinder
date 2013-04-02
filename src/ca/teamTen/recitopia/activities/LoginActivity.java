package ca.teamTen.recitopia.activities;

import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import ca.teamTen.recitopia.R;
import ca.teamTen.recitopia.models.ApplicationManager;

public class LoginActivity extends Activity {
	EditText emailField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		emailField = (EditText) findViewById(R.id.emailField);
		emailField.setText(getCandidateEmail());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	private String getCandidateEmail() {
		Pattern emailPattern = Patterns.EMAIL_ADDRESS;
		Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		        return account.name;
		    }
		}
		return "";
	}
	
	public void onSubmit(View button) {
		String email = emailField.getText().toString();
		if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			ApplicationManager.getInstance(getApplication()).setUserID(email);
			finish();
		} else {
	    	Toast toast = Toast.makeText(getApplicationContext(),
	    			"Please enter a valid email.", Toast.LENGTH_SHORT);
	    	toast.show();
		}
	}
}
