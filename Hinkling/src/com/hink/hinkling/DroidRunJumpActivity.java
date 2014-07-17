package com.hink.hinkling;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.hink.hinkling.DroidRunJumpView.DroidRunJumpThread;


public class DroidRunJumpActivity extends Activity {
	
	public static final String PREFS_NAME = "DRJPrefsFile";
	
	DroidRunJumpView drjView;
	DroidRunJumpThread drjThread;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.runner);        
        drjView = (DroidRunJumpView) findViewById(R.id.droidrunjump);
        
    	CountDownTimer timer = new CountDownTimer(35000, 1000) {


			View h = findViewById(R.id.hitme);
			View a  = findViewById(R.id.again);
			public void onTick(long millisUntilFinished) {

			}

			public void onFinish() {
				  runRound();
			}
		}.start();
        
    }
    
    @Override
    protected void onPause() {
    	super.onPause();

    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
       	SharedPreferences.Editor editor = settings.edit();
       	
       	drjThread = drjView.getThread();
       	       	    	
       	// if player wants to quit then reset the game
    	if (isFinishing()) {
    		drjThread.resetGame();
    	}
    	else {	    	
    		drjThread.pause();
    	}
    	
       	drjThread.saveGame(editor);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();    
    	// restore game
    	drjThread = drjView.getThread();    
	    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	   	//drjThread.restoreGame(settings);  
    }   
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.reflex, menu);
		return true;
	}

	public void runRound(){
		this.startActivity(new Intent(this, PRound1.class));
	}
}