package com.hink.hinkling;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;

public class WelcomeSM extends Activity {
    protected boolean active = true;
    protected int splashTime = 5000;
    protected int timeIncrement = 100;
    protected int sleepTime = 100;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_welcome_sm);

        //final MediaPlayer mp = MediaPlayer.create(this, R.raw.);
        //final MediaPlayer wistle = MediaPlayer.create(this, R.raw.);
        //wistle.start();
        // thread for displaying the WelcomeSM
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {

                    int elapsedTime = 0;
                    while (WelcomeSM.this.active
                            && (elapsedTime < WelcomeSM.this.splashTime)) {
                        sleep(WelcomeSM.this.sleepTime);
                        if (WelcomeSM.this.active) {
                            elapsedTime = elapsedTime
                                    + WelcomeSM.this.timeIncrement;
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    WelcomeSM.this.finish();
                    WelcomeSM.this.startActivity(new Intent(
                            "com.hink.hinkling.Subject"));
                    //mp.start();
                }
            }
        };
        splashThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.active = false;
        }
        return true;
    }
}