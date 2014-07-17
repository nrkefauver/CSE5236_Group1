package com.hink.hinkling;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;


public class RoundTwo extends Activity {
	 Random rand = new Random();
	 ArrayList<String> value = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round_two);


	    new CountDownTimer(4000, 1000) { 

	        public void onTick(long secondsUntilFinished) {

	                 
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
		getMenuInflater().inflate(R.menu.round_two, menu);
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
	
	public void runRound(){
		value.add("1");
		value.add("2");
		value.add("3");
		boolean cango = false;
		
		while (cango == false){
			
		String check = value.get(rand.nextInt(value.size()));
		
		if(check == "1" && MainActivity.sc == false){
		this.startActivity(new Intent(this, InfoScreenClear.class));
		MainActivity.sc = true;
		cango = true;
		}else if(check == "2" && MainActivity.dr == false){
			this.startActivity(new Intent(this, InfoJumper.class));
			MainActivity.dr = true;
			cango = true;
		}else if (check == "3"&& MainActivity.rf == false){
			this.startActivity(new Intent(this, InfoReflex.class));
			MainActivity.rf = true;
			cango = true;
			
		}
		}
	}

	@Override
	public void onBackPressed() {
	}
				
}
