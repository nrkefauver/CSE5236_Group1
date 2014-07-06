package com.hink.hinkling;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SMRoundOne extends Activity implements OnClickListener {
	
	TextView text1;
	ImageView imgFavorite;
	public static boolean go = false;
	public static boolean activeRoundOne = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smround_one);
	 
		View btnNext=findViewById(R.id.imageNext1);
		btnNext.setOnClickListener(this);
		
	      imgFavorite = (ImageView)findViewById(R.id.imageView1);
	      imgFavorite.setOnClickListener(new OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            open();
	         }
	      });
		text1=(TextView)findViewById(R.id.textView1);

	    new CountDownTimer(40000, 1000) { 

	        public void onTick(long secondsUntilFinished) {

	            		 text1.setText("" + secondsUntilFinished / 1000);          
	        }

	        public void onFinish() {
	        	if (go == false){
		            text1.setText("done!");
		            startActivity(new Intent("com.hink.hinkling.Loser"));
		        	}
	        }
	     }.start();             

	}

	 public void open(){
	      Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	      startActivityForResult(intent, 0);
	   }

	 @Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Bitmap bp = (Bitmap) data.getExtras().get("data");
	      imgFavorite.setImageBitmap(bp);
	   }
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.smround_one, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_smround_one,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{	case R.id.imageNext1:
			go = true;
			this.startActivity(new Intent(this, SMRoundTwo.class));
			activeRoundOne = true;
			break;
		}
	}

}
