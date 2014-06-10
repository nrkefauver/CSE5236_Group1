package com.hink.hinkling;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateProfile extends Activity implements OnClickListener {
	private EditText etUsername;
	private EditText etPassword;
	private EditText etConfirm;
	private DatabaseHelper dh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		etUsername = (EditText) findViewById(R.id.username);
		etPassword = (EditText) findViewById(R.id.password);
		etConfirm = (EditText) findViewById(R.id.password_confirm);
		View btnAdd = (Button) findViewById(R.id.done_button);
		btnAdd.setOnClickListener(this);
		View btnCancel = (Button) findViewById(R.id.cancel_button);
		btnCancel.setOnClickListener(this);
		// if (savedInstanceState == null) {
		// getSupportFragmentManager().beginTransaction()
		// .add(R.id.container, new PlaceholderFragment()).commit();
		// }
	}

	private void MakeProfile() {
		// this.output = (TextView) this.findViewById(R.id.out_text);
		String username = etUsername.getText().toString();
		String password = etPassword.getText().toString();
		String confirm = etConfirm.getText().toString();
		if ((password.equals(confirm)) && (!username.equals(""))
				&& (!password.equals("")) && (!confirm.equals(""))) {
			this.dh = new DatabaseHelper(this);
			this.dh.insert(username, password);
			// this.labResult.setText("Added");
			Toast.makeText(CreateProfile.this, "new record inserted",
					Toast.LENGTH_SHORT).show();
			finish();
		} else if ((username.equals("")) || (password.equals(""))
				|| (confirm.equals(""))) {
			Toast.makeText(CreateProfile.this, "Missing entry",
					Toast.LENGTH_SHORT).show();
		} else if (!password.equals(confirm)) {
			new AlertDialog.Builder(this)
					.setTitle("Error")
					.setMessage("passwords do not match")
					.setNeutralButton("Try Again",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							})

					.show();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.done_button:
			MakeProfile();
			finish();
			break;
		case R.id.cancel_button:
			finish();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_profile,
					container, false);
			return rootView;
		}
	}

}
