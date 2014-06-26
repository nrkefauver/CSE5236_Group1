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
import android.widget.TextView;

public class Subject extends Activity implements OnClickListener {
	
	TextView timeSub;
	boolean active = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject);

		View btnNext=findViewById(R.id.imageNext);
		btnNext.setOnClickListener(this);
		
		timeSub=(TextView)findViewById(R.id.timeSub1);

	    new CountDownTimer(10000, 1000) { 

	        public void onTick(long secondsUntilFinished) {

	            		 timeSub.setText("" + secondsUntilFinished / 1000);          
	        }

	        public void onFinish() {
	        	
	        	if (active == false){
	            timeSub.setText("done!");
	            startActivity(new Intent("com.hink.hinkling.Loser"));
	        	}
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
			View rootView = inflater.inflate(R.layout.fragment_subject,
					container, false);
			return rootView;
		}
	}
	
	@Override
	public void onClick(View v) {
		 //btnEdit   = (EditText)findViewById(R.id.editText1);
		switch(v.getId())
		{	case R.id.imageNext:
			startActivity(new Intent(this, SMRoundOne.class));
			active = true;
			break;
		}
	}

}
