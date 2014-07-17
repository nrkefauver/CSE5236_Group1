package com.hink.hinkling;

import java.util.Random;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class Reflex extends Activity {
	/*Display idisplay = getWindowManager().getDefaultDisplay();

	public int displayWidth = idisplay.getWidth();
	public int displayHeight = idisplay.getHeight();*/
	public int displayWidth;
	public int displayHeight;
	private int score;
	public int k = 1;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reflex);
		 k = 1;
		 
		
		CountDownTimer timer = new CountDownTimer(25000, 1000) {

			TextView timerField = (TextView) findViewById(R.id.timer);
			View h = findViewById(R.id.hitme);
			View a  = findViewById(R.id.again);
			public void onTick(long millisUntilFinished) {
				timerField.setText("Time: " + millisUntilFinished / 1000);
			}

			public void onFinish() {
				timerField.setText("Next");
				h.setVisibility(View.GONE);
				a.setVisibility(View.VISIBLE);
				  runRound();
			}
		}.start();
	}
	
	public void onStartClick(View view){
		Display display = getWindowManager().getDefaultDisplay();
		displayWidth = display.getWidth();
		displayHeight = display.getHeight();


		View t = findViewById(R.id.hitme);
		t.setVisibility(View.VISIBLE);

		CountDownTimer timer = new CountDownTimer(30000, 1000) {

			TextView timerField = (TextView) findViewById(R.id.timer);
			View h = findViewById(R.id.hitme);
			View a  = findViewById(R.id.again);
			public void onTick(long millisUntilFinished) {
				timerField.setText("Time: " + millisUntilFinished / 1000);
			}

			public void onFinish() {
				timerField.setText("Time's Up Bro!");
				h.setVisibility(View.GONE);
				a.setVisibility(View.VISIBLE);
			}
		}.start();

		View s = findViewById(R.id.start);
		s.setVisibility(View.GONE);

		Random r = new Random();

		int x = r.nextInt(displayWidth);
		int y = r.nextInt(displayHeight);
		
		System.out.println(""+x + ", " + y);
		if(y<100){
			y+=100;
		}
		if(x<100){
			x+=100;
		}
		if(x> (displayWidth-150)){
			x-=150;
		}
		if(y> (displayHeight-150)){
			y-=150;
		}
		t.setX(x);  
		t.setY(y);
	}
	public void onAgainClick(View view){
		Display display = getWindowManager().getDefaultDisplay();
		displayWidth = display.getWidth();
		displayHeight = display.getHeight();
		score =0;
		
		TextView s = (TextView)(findViewById(R.id.score));
		s.setText("Score: "+ score);
		getHintLetter();
		View t = findViewById(R.id.hitme);
		t.setVisibility(View.VISIBLE);

		CountDownTimer timer = new CountDownTimer(30000, 1000) {

			TextView timerField = (TextView) findViewById(R.id.timer);
			View h = findViewById(R.id.hitme);
			View a = findViewById(R.id.again);
			public void onTick(long millisUntilFinished) {
				timerField.setText("Time: " + millisUntilFinished / 1000);
			}

			public void onFinish() {
				timerField.setText("next");
				h.setVisibility(View.GONE);
				a.setVisibility(View.VISIBLE);
				runRound();
			}
		}.start();

		View a = findViewById(R.id.again);
		a.setVisibility(View.GONE);

		Random r = new Random();

		int x = r.nextInt(displayWidth);
		int y = r.nextInt(displayHeight);
		System.out.println(""+x + ", " + y);
		if(y<100){
			y+=100;
		}
		if(x<100){
			x+=100;
		}
		if(x> (displayWidth-150)){
			x-=150;
		}
		if(y> (displayHeight-150)){
			y-=150;
		}
		t.setX(x);  
		t.setY(y);
	}


	public void onTapClick(View view){
		View t = findViewById(R.id.hitme);

		score++;
		TextView s = (TextView)(findViewById(R.id.score));
		s.setText("Score: "+ score);

		Random r = new Random();

		int x = r.nextInt(displayWidth);
		int y = r.nextInt(displayHeight);
		
		System.out.println(""+x + ", " + y);
		if(y<100){
			y+=100;
		}
		if(x<100){
			x+=100;
		}
		if(x> (displayWidth-150)){
			x-=150;
		}
		if(y> (displayHeight-150)){
			y-=151;
		}
		t.setX(x);  
		t.setY(y);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.reflex, menu);
		return true;
	}

	public void runRound(){
		this.startActivity(new Intent(this, PRound1.class));
	}
	
	public void getHintLetter(){

		char [] temp = MainActivity.subject.toCharArray();
		TextView answer=(TextView)findViewById(R.id.hinklingLetters);
		if(score % 10 == 0 && k <= temp.length){
			
			int pos = temp.length;
			char x = temp[pos-k];
			String y = ""+x;
			answer.setText(y + answer);
			k++;
			
			
		}
		
	}
	
}
