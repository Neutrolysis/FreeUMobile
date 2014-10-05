package com.Neutrolysis.FreeU;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;



import com.Neutrolysis.FreeU.R;

import android.R.array;
import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DiscoveryActivity extends ListActivity {
	
	private Handler _handler = new Handler();
	public final static int NumberOFShields = 2; //2 for now it should be equal to the number of the rooms 
	BluetoothAdapter mBluetoothAdapter;
	private volatile boolean _discoveryFinished;
	public  String[] MacArray = {"80:50:1B:A8:90:87","00:14:03:18:36:23"};
	private List<BluetoothDevice> _devices = new ArrayList<BluetoothDevice>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		setContentView(R.layout.discovery);
		
		
	 mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); 
	 
	 
		
		
	}
	
	

	public void onStart() {
        super.onStart();
        
         /* Register Receiver*/
		IntentFilter discoveryFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(_discoveryReceiver, discoveryFilter);
        // Register the BroadcastReceiver 
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        
        mBluetoothAdapter.startDiscovery();
       
       
	
    }
	
	private BroadcastReceiver _discoveryReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent)  
		{
			/* unRegister Receiver */
			Log.d("U Control", ">>unregisterReceiver");
			unregisterReceiver(mReceiver);
			unregisterReceiver(this);
			_discoveryFinished = true;
		}
	};
	
	
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
	    public void onReceive(Context context, Intent intent) { 
	        String action = intent.getAction(); 
	        // When discovery finds a device 
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) { 
	            // Get the BluetoothDevice object from the Intent 
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE); 
				String MACA;
	            MACA = device.getAddress();
	           // _devices.add(device);
	            
	          for(int i=0;i<NumberOFShields;i++){
	            Log.d("Filter", "in here"+device.getName());
	            if (MACA.contains(MacArray[i])){
	            	
	            	_devices.add(device);
	            	
	            	
				
	            	}
	          }
	            
				showDevices();

	        } 
	    } 
	}; 
	
	

	protected void showDevices()
	{	
		List<String> list = new ArrayList<String>();

		for (int i = 0, size = _devices.size(); i < size; ++i)
		{
			StringBuilder b = new StringBuilder();
			BluetoothDevice d = _devices.get(i);
			String name =d.getName();
			
			if (name.contains("HC-06")){
				b.append("Connect To the test device! ");
				}else{b.append(d.getName());}
			String s = b.toString();
			list.add(s);
		}
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		_handler.post(new Runnable() {
			public void run()
			{
				setListAdapter(adapter);
			}
		});
	}
	
	
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Log.d("UControl", ">> Click device << ");
		Intent result = new Intent(this,MCActivity.class);
		result.putExtra(BluetoothDevice.EXTRA_DEVICE, _devices.get(position));
		Toast.makeText(getBaseContext(), _devices.get(position).getName(), Toast.LENGTH_LONG).show();
		startActivity(result);
		finish();
	}
}

