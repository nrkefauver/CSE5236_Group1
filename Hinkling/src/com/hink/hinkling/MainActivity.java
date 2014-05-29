package com.hink.hinkling;




import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity implements OnClickListener  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		View btnNewGame = findViewById(R.id.buttonNewGame);
		btnNewGame.setOnClickListener(this);
		View btnAudio = findViewById(R.id.buttonAudio);
		btnAudio.setOnClickListener(this);
		View btnSettings = findViewById(R.id.buttonSettings);
		btnSettings.setOnClickListener(this);
		View btnHelp = findViewById(R.id.buttonHelp);
		btnHelp.setOnClickListener(this);
		View btnExit = findViewById(R.id.buttonExit);
		btnExit.setOnClickListener(this);
	}
	private void quitApplication(){
		new AlertDialog.Builder(this)
		.setTitle(R.string.exit)
		.setMessage("Quit Hinkling??? WHY?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
			}
		})
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {}
		})
		.show();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		   MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.main, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.buttonNewGame:
			startActivity(new Intent(this, GameSession.class));
			break;
		case R.id.buttonAudio:
			startActivity(new Intent(this, Audio.class));
			break;
		case R.id.buttonSettings:
			startActivity(new Intent(this, Settings.class));
			break;
		case R.id.buttonHelp:
			startActivity(new Intent(this, Help.class));
			break;
		case R.id.buttonExit:
			{
				quitApplication();
				stopService(new Intent(this, MyPlaybackService.class));
			}
			break;
		}
		
	}

}
