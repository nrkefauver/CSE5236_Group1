package com.hink.hinkling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class PlayerWaiting extends Activity  {
	
	TextView timeSub;
	boolean active = false;
	public static String subjectString = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_waiting);
		
		
		timeSub=(TextView)findViewById(R.id.timeSub2);

	    new CountDownTimer(5000, 1000) { 

	        public void onTick(long secondsUntilFinished) {

	            		 timeSub.setText("" + secondsUntilFinished / 1000);          
	        }

	        public void onFinish() {
	        	
	   
	           runChat();
	        	
	        	}
	     }
	    .start();      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subject, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_player_waiting,
					container, false);
			return rootView;
		}
	}
	
	public void runChat(){
		this.startActivity(new Intent(this, PRound1.class));
	}

				


}