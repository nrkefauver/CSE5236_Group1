package com.hink.hinkling;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ScreenClearMiniGame extends Activity implements
        SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    private long lastUpdate;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    private String hint = "winner";
    private boolean arrayMade = false;
    private ArrayList<Character> lettersInString;
    private boolean ball1Set = false;
    private boolean ball2Set = false;
    private boolean ball3Set = false;
    private boolean ball4Set = false;
    private boolean ball5Set = false;
    private boolean ball6Set = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_screen_clear_mini_game);

        this.senSensorManager = (SensorManager) this
                .getSystemService(Context.SENSOR_SERVICE);
        this.senAccelerometer = this.senSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.senSensorManager.registerListener(this, this.senAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);

        if (savedInstanceState == null) {
            //this.getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.screen_clear_mini_game, menu);
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
            View rootView = inflater.inflate(
                    R.layout.fragment_screen_clear_mini_game, container, false);
            return rootView;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - this.lastUpdate) > 500) {
                long diffTime = (curTime - this.lastUpdate);
                this.lastUpdate = curTime;

                float speed = Math.abs(x + y + z - this.last_x - this.last_y
                        - this.last_z)
                        / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    this.getNextLetter();
                }

                this.last_x = x;
                this.last_y = y;
                this.last_z = z;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.senSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.senSensorManager.registerListener(this, this.senAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void getNextLetter() {
        if (!this.arrayMade) {
            this.lettersInString = new ArrayList<Character>();
            for (int i = 0; i < this.hint.length(); i++) {
                char letterI = this.hint.charAt(i);
                this.lettersInString.add(letterI);
            }
        }

        FrameLayout ball1 = (FrameLayout) this.findViewById(R.id.ball_1);
        FrameLayout ball2 = (FrameLayout) this.findViewById(R.id.ball_2);
        FrameLayout ball3 = (FrameLayout) this.findViewById(R.id.ball_3);
        FrameLayout ball4 = (FrameLayout) this.findViewById(R.id.ball_4);
        FrameLayout ball5 = (FrameLayout) this.findViewById(R.id.ball_5);
        FrameLayout ball6 = (FrameLayout) this.findViewById(R.id.ball_6);

        TextView text = (TextView) this.findViewById(R.id.number_1);
        text.setText("" + this.lettersInString.get(0));

        if (this.hint.length() > 1) {
            text = (TextView) this.findViewById(R.id.number_2);
            text.setText("" + this.lettersInString.get(1));
        }

        if (this.hint.length() > 2) {
            text = (TextView) this.findViewById(R.id.number_3);
            text.setText("" + this.lettersInString.get(2));
        }

        if (this.hint.length() > 3) {
            text = (TextView) this.findViewById(R.id.number_4);
            text.setText("" + this.lettersInString.get(3));
        }

        if (this.hint.length() > 4) {
            text = (TextView) this.findViewById(R.id.number_5);
            text.setText("" + this.lettersInString.get(4));
        }

        if (this.hint.length() > 5) {
            text = (TextView) this.findViewById(R.id.number_6);
            text.setText("" + this.lettersInString.get(5));
        }

        Animation a = AnimationUtils.loadAnimation(this,
                R.anim.move_down_ball_first);

        if (!this.ball1Set) {
            ball1.setVisibility(View.INVISIBLE);
        }
        if (!this.ball2Set) {
            ball2.setVisibility(View.INVISIBLE);
        }
        if (!this.ball3Set) {
            ball3.setVisibility(View.INVISIBLE);
        }
        if (!this.ball4Set) {
            ball4.setVisibility(View.INVISIBLE);
        }
        if (!this.ball5Set) {
            ball5.setVisibility(View.INVISIBLE);
        }
        if (!this.ball6Set) {
            ball6.setVisibility(View.INVISIBLE);
        }

        if (!this.ball6Set) {
            this.ball6Set = true;
            ball6.setVisibility(View.VISIBLE);
            ball6.clearAnimation();
            ball6.startAnimation(a);
        } else if (!this.ball5Set) {
            this.ball5Set = true;
            ball5.setVisibility(View.VISIBLE);
            ball5.clearAnimation();
            ball5.startAnimation(a);
        } else if (!this.ball4Set) {
            this.ball4Set = true;
            ball4.setVisibility(View.VISIBLE);
            ball4.clearAnimation();
            ball4.startAnimation(a);
        } else if (!this.ball3Set) {
            this.ball3Set = true;
            ball3.setVisibility(View.VISIBLE);
            ball3.clearAnimation();
            ball3.startAnimation(a);
        } else if (!this.ball2Set) {
            this.ball2Set = true;
            ball2.setVisibility(View.VISIBLE);
            ball2.clearAnimation();
            ball2.startAnimation(a);
        } else if (!this.ball1Set) {
            this.ball1Set = true;
            ball1.setVisibility(View.VISIBLE);
            ball1.clearAnimation();
            ball1.startAnimation(a);
        }
    }
}
