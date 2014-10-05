package com.Neutrolysis.FreeU;


import com.Neutrolysis.FreeU.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


	public class MapActivity extends Activity{

		ImageButton bi;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_map);
			 bi=(ImageButton) findViewById(R.id.imageButton1);
			bi.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(MapActivity.this,Listig.class);
					startActivity(i);
					
				}
			});
			
			
				
				
		}


		
	}
