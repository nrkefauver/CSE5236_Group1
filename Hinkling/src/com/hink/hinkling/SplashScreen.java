package com.hink.hinkling;

import java.util.ArrayList;
import java.util.Random;

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
    ArrayList<MediaPlayer> okay = new ArrayList<MediaPlayer>();
    public static MediaPlayer good;
    
    Random rand = new Random();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splash_screen);

        final MediaPlayer m = MediaPlayer.create(this, R.raw.fly);
        final MediaPlayer b = MediaPlayer.create(this, R.raw.bday);
        final MediaPlayer d = MediaPlayer.create(this, R.raw.dis);
        final MediaPlayer p = MediaPlayer.create(this, R.raw.fear);
        final MediaPlayer z = MediaPlayer.create(this, R.raw.take);
        final MediaPlayer x = MediaPlayer.create(this, R.raw.tele);
        final MediaPlayer u = MediaPlayer.create(this, R.raw.ya);
        final MediaPlayer n = MediaPlayer.create(this, R.raw.ag);
        final MediaPlayer q = MediaPlayer.create(this, R.raw.lk);
        
        okay.add(m);
        okay.add(b);
        okay.add(d);
        okay.add(p);
        okay.add(z);
        okay.add(x);
        okay.add(u);
        okay.add(n);
        okay.add(q);
        
        good = okay.get(rand.nextInt(okay.size()));
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
                    good.start();
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