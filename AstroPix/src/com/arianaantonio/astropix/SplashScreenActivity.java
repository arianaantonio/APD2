package com.arianaantonio.astropix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		
		 new Handler().postDelayed(new Runnable() {
             
	            // Using handler with postDelayed called runnable run method
	  
	            @Override
	            public void run() {
	                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
	                startActivity(i);
	  
	                // close this activity
	                finish();
	            }
	        }, 5*1000);
	}

	
}
