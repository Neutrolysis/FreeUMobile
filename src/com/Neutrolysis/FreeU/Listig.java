package com.Neutrolysis.FreeU;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import com.Neutrolysis.FreeU.R;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Listig extends Activity {
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	BluetoothAdapter mBluetoothAdapter;
	BluetoothDevice mmDevice;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discovery2);

		// Find the ListView resource.
		mainListView = (ListView) findViewById(R.id.liMe);

		// Create and populate a List of planet names.
		String[] rooms = new String[] { "First", "Second", "Third", "Fourth" };
		ArrayList<String> roomlist = new ArrayList<String>();
		roomlist.addAll(Arrays.asList(rooms));

		// Create ArrayAdapter using the planet list.
		listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow,
				roomlist);

		// Set the ArrayAdapter as the ListView's adapter.
		mainListView.setAdapter(listAdapter);
		mainListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

				Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
						.getBondedDevices();
				if (pairedDevices.size() > 0) {
					for (BluetoothDevice device : pairedDevices) {
						if (device.getName().equals("HC-06")) {
							mmDevice = device;
							Intent result = new Intent(Listig.this,
									MCActivity.class);
							result.putExtra(BluetoothDevice.EXTRA_DEVICE,
									mmDevice);
							startActivity(result);
							Toast.makeText(
									getBaseContext(),
									mmDevice.getName()
											+ "  is the your choice....please wait",
									Toast.LENGTH_LONG).show();

							break;

						}

					}

				}

			}
		});

	}

}
