package com.hink.hinkling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.os.Build;

public class ThirdRound extends ActionBarActivity {

	static String randomWord;
	static String shuffledWord;
	private TextView scrambledWordTextView;
	private TextView levelTextView;
	private EditText userGuessEditText;
	private TextView timer;
	private int score;
	private TextView scoreText;
	private int level;
	private SeekBar levelSeekBar;
	CountDownTimer countDownTimer;

	@SuppressLint("DefaultLocale")
	public void setNewRandomWord() {
		String[] randomWords = { "Seventy", "Football", "Skip", "Rabbit",
				"Arm", "Crayon", "Jump", "Pig", "Monkey", "Baby", "Happy",
				"Hopscotch", "Spider", "Bird", "Doll", "Wings", "Turtle",
				"Room", "Drum", "Ear", "Cheek", "Smile", "Jar", "Telephone",
				"Mouth", "Basketball", "Airplane", "Tree", "Star", "Point",
				"Scissors", "Elephant", "Jump", "Chair", "Pinch", "Mosquito",
				"Sunglasses", "Head", "Kick", "Football", "Skip", "Dance",
				"Alligator", "Stop", "Door", "Blinking", "Swing", "Pen",
				"Apple", "Seven" };
		Random rand = new Random();
		int randomNum = rand.nextInt((randomWords.length - 2) + 1);
		randomWord = randomWords[randomNum].toLowerCase();
		if (randomWord.length() == level) {
			ArrayList<Character> chars = new ArrayList<Character>(
					randomWord.length());
			for (char c : randomWord.toCharArray()) {
				chars.add(c);
			}
			Collections.shuffle(chars);
			char[] shuffled = new char[chars.size()];
			for (int i = 0; i < shuffled.length; i++) {
				shuffled[i] = chars.get(i);
			}
			shuffledWord = new String(shuffled);
			if (shuffledWord.equals(randomWord)) {
				setNewRandomWord();
			} else {
				scrambledWordTextView.setText(shuffledWord);
				if (countDownTimer != null) {
					countDownTimer.cancel();
					countDownTimer = null;
				}
				countDownTimer = new CountDownTimer(30000, 1000) {
					public void onTick(long millisUntilFinished) {
						long secondsLeft = millisUntilFinished / 1000;
						timer.setText(String.valueOf(secondsLeft));
						if (secondsLeft > 10) {
							timer.setTextColor(Color.parseColor("#000000"));
						} else {
							timer.setTextColor(Color.parseColor("#FF0000"));
						}
					}

					public void onFinish() {
						score = 0;
						scoreText.setText(Integer.toString(score));
						userGuessEditText.setText("");
						setNewRandomWord();
					}
				}.start();
			}
		} else {
			setNewRandomWord();
		}
	}

	public void checkGuess() {
		if (userGuessEditText.getText().toString().equals(randomWord)) {
			startActivity(new Intent(this, Victory.class));
			// int roundScore = randomWord.length() - 2;
			// score += roundScore;
			// scoreText.setText(Integer.toString(score));
			// userGuessEditText.setText("");
			// setNewRandomWord();
			// Toast toast = Toast.makeText(getApplicationContext(), "CORRECT!",
			// Toast.LENGTH_SHORT);
			// toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
			// toast.show();
		} else {
			Toast toast = Toast.makeText(getApplicationContext(), "INCORRECT!",
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 50);
			toast.show();
			userGuessEditText.setText("");
			startActivity(new Intent(this, Loser.class));
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third_round);
		scrambledWordTextView = (TextView) findViewById(R.id.scrambledWordTextView);
		scoreText = (TextView) findViewById(R.id.score);
		//levelTextView = (TextView) findViewById(R.id.levelTextView);
		timer = (TextView) findViewById(R.id.timer);
		userGuessEditText = (EditText) findViewById(R.id.userGuessEditText);
		// levelSeekBar = (SeekBar) findViewById(R.id.levelSeekBar);
		score = 0;
		level = 3;
		setNewRandomWord();
		userGuessEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() == shuffledWord.length()) {
					checkGuess();
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
		});
		// levelSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
		// {
		// // update currentCustomPercent, then call updateCustom
		// @Override
		// public void onProgressChanged(SeekBar seekBar, int progress,
		// boolean fromUser) {
		// // sets currentCustomPercent to position of the SeekBar's thumb
		// level = seekBar.getProgress() + 3;
		// levelTextView.setText(Integer.toString(level));
		// score = 0;
		// scoreText.setText(Integer.toString(score));
		// userGuessEditText.setText("");
		// setNewRandomWord();
		// } // end method onProgressChanged
		//
		// @Override
		// public void onStartTrackingTouch(SeekBar seekBar) {
		// } // end method onStartTrackingTouch
		//
		// @Override
		// public void onStopTrackingTouch(SeekBar seekBar) {
		// } // end method onStopTrackingTouch
		// });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
