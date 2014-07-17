package com.hink.hinkling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

public class InfoJumper extends Activity  {
	
	TextView timeSub;
	boolean active = false;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_jumper);
		
		
		timeSub=(TextView)findViewById(R.id.timeSub2);

	    new CountDownTimer(12000, 1000) { 

	        public void onTick(long secondsUntilFinished) {

	            		 timeSub.setText("" + secondsUntilFinished / 1000);          
	        }

	        public void onFinish() {
	        	
	   
	           runRound();
	        	
	        	}
	     }
	    .start();      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info_jumper, menu);
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

	
	}
	
	public void runRound(){
		this.startActivity(new Intent(this, DroidRunJumpActivity.class));
	}
	
	@Override
	public void onBackPressed() {
	}
				

	   @Override
	    public boolean onTouchEvent(MotionEvent event) {
	        if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        this.active = false;
	        }
	        return true;
	    }
}