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

/**
 * Activity that is launched when the userid is unknown.
 * 
 * We need an email from the user, so we make our best
 * guess (using the AccounManager), but let them edit
 * it before saving.
 */
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
	
	/*
	 * Get the first email we can find from the
	 * account manager, or an empty string.
	 */
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
	
	/**
	 * Respond to login button press.
	 *
	 * Validate email, save it and exit, or toast user if
	 * the email is invalid.
	 */
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
