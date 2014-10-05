package com.Neutrolysis.FreeU;

import com.Neutrolysis.FreeU.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class Splash extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.test);
		Thread timer=new Thread ()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					sleep(3000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				Intent i=new Intent(Splash.this,MainActivity.class);
				startActivity(i);
				
				
			}
			
			
			
		};
timer.start();

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
