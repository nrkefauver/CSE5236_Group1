package com.hink.hinkling;


import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class PRound1 extends Activity implements OnClickListener {

	TextView timeSub;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pround1);

		View mainBtn = (Button) findViewById(R.id.guess1Btn);
		mainBtn.setOnClickListener(this);
		
		EditText answer=(EditText)findViewById(R.id.guess1);

	
		
		timeSub=(TextView)findViewById(R.id.timeSub2);
		  new CountDownTimer(15000, 1000) { 

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
		getMenuInflater().inflate(R.menu.pround1, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_pround1,
					container, false);
			return rootView;
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.guess1Btn:
			checkGuess();
			break;

		}
	}
    public void runRound(){
    	
    	if(MainActivity.runRound2 == false){
		this.startActivity(new Intent(this, RoundTwo.class));
		MainActivity.runRound2 = true;
    	} else if(MainActivity.runRound2 == true && MainActivity.runRound3== false){
    		this.startActivity(new Intent(this, RoundThree.class));
    		MainActivity.runRound3 = true;
    	}else{
    		this.startActivity(new Intent(this, Loser.class));
    	}
    	}
    
    public void checkGuess(){
    	final MediaPlayer noWay = MediaPlayer.create(this, R.raw.no);
    	EditText answer=(EditText)findViewById(R.id.guess1);
    	
    	if(answer.getText().toString().equals(MainActivity.subject)){
    		this.startActivity(new Intent(this, Winner.class));
    	}else{
    		noWay.start();
    		answer.setText("");
    	}
    	
    }
    
}


