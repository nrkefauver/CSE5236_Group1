package com.hink.hinkling;

import com.hink.hinkling.chat.GuestbookActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Winner extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_winner);

		View replay=findViewById(R.id.Replay);
		replay.setOnClickListener(this);
		View EG=findViewById(R.id.EndGame);
		EG.setOnClickListener(this);
		View MM=findViewById(R.id.BackToMain);
		MM.setOnClickListener(this);

		MainActivity.sc = false;
		MainActivity.dr = false;
		MainActivity.rf = false;
		MainActivity.runRound2 = false;
		MainActivity.runRound3 = false;
		TextView answer=(TextView)findViewById(R.id.subjectAnswer);
		answer.setText(MainActivity.subject);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.winner, menu);
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
	public void onClick(View v) {
		switch(v.getId())
		{	case R.id.Replay:
			startActivity(new Intent(this, PlayerWaiting.class));
			break;
		case R.id.EndGame:
			startActivity(new Intent(this, GuestbookActivity.class));
			break;
		case R.id.BackToMain:
			startActivity(new Intent(this, MainActivity.class));
			break;
		}
		
	}
}
