package com.hink.hinkling;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashScreen extends Activity {
    protected boolean active = true;
    protected int splashTime = 5000;
    protected int timeIncrement = 100;
    protected int sleepTime = 100;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash_screen);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.stp);
        final MediaPlayer wistle = MediaPlayer.create(this, R.raw.swis);
        wistle.start();
        // thread for displaying the SplashScreen
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {

                    int elapsedTime = 0;
                    while (SplashScreen.this.active
                            && (elapsedTime < SplashScreen.this.splashTime)) {
                        sleep(SplashScreen.this.sleepTime);
                        if (SplashScreen.this.active) {
                            elapsedTime = elapsedTime
                                    + SplashScreen.this.timeIncrement;
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                    SplashScreen.this.startActivity(new Intent(
                            "com.hink.hinkling.MainActivity"));
                    mp.start();
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