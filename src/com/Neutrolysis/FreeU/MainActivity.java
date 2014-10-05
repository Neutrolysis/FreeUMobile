package com.Neutrolysis.FreeU;

import com.Neutrolysis.FreeU.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	public static int flag = 1;
	public final static String TAG = "U Control";
	Button auto, man;
	EditText editpazzi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		Intent i = new Intent(this, ListenService.class);
		startService(i);
		BluetoothAdapter Main;
		Main = BluetoothAdapter.getDefaultAdapter();
		auto = (Button) findViewById(R.id.AutoDetect);
		man = (Button) findViewById(R.id.GoToMap);
		auto = (Button) findViewById(R.id.AutoDetect);
		editpazzi = (EditText) findViewById(R.id.etPassword);

		auto.setOnClickListener(this);
		man.setOnClickListener(this);

		if (Main.isEnabled()) {
			Log.d(TAG, "Bluetooth ON");
		} else {
			Log.d(cBluetooth.TAG, "Request Bluetooth Enable in main");
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, 1);

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String pazzi = editpazzi.getText().toString();

		switch (v.getId()) {

		case R.id.AutoDetect:
			if (pazzi.equals("12345")) {
				Toast.makeText(getBaseContext(), "Welcome :D",
						Toast.LENGTH_LONG).show();

				Intent resulta = new Intent(MainActivity.this,
						DiscoveryActivity.class);
				startActivity(resulta);
			} else {
				Toast.makeText(getBaseContext(), "wrong pass...try again :D ",
						Toast.LENGTH_LONG).show();

			}

			break;
		case R.id.GoToMap:
			if (pazzi.equals("12345")) {
				Toast.makeText(getBaseContext(), "Welcome :D",
						Toast.LENGTH_LONG).show();

				Intent resulti = new Intent(MainActivity.this,
						MapActivity.class);
				startActivity(resulti);
			} else {
				Toast.makeText(getBaseContext(), "wrong pass...try again :D ",
						Toast.LENGTH_LONG).show();

			}

			break;

		}

	}

}
