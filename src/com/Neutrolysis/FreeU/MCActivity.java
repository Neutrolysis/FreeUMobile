package com.Neutrolysis.FreeU;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.Neutrolysis.FreeU.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MCActivity extends Activity implements
		android.view.View.OnClickListener {

	TextView data1;
	TextView data2;
	TextView data3;
	boolean bil;
	ViewFlipper flup;
	Button blueOn, lighSet, lighBack, lighOn, lighOff, acOn, acOff, acSet,projSet,projOn,projOff,proBk,pIn,pOut,
			airUp, airDown, airBack,winOn,winOff,winSet,winBk,stmode,endmode,slidemode;
	CheckBox Checkligh1, Checkligh2,chkWO,chkWT,chkCO,chkCT;
	TextView myLabel,monHum,monTmp,monCurr;
	EditText myTextbox;
	ProgressBar spinni;

	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
 

	BluetoothAdapter mBluetoothAdapter;
	BluetoothSocket mmSocket;
	BluetoothDevice mmDevice;
	OutputStream mmOutputStream;
	InputStream mmInputStream;
	Thread workerThread;
	byte[] readBuffer;
	int readBufferPosition;
	int counter;
	int k;
	byte chkl1 = 1;
	byte chkl2 = 1;

	byte a = 0;
	byte b = 0;
	byte clik = 0;
	// byte d=0;

	volatile boolean stopWorker;
	Button bu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_mc);
		// create the TabHost that will contain the Tabs
		// mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
		// mBluetoothAdapter.enable();
     

		Intent extras = getIntent();
		final BluetoothDevice device = extras
				.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		mmDevice = device;

		flup = (ViewFlipper) findViewById(R.id.viewFlipper1);
	spinni=(ProgressBar) findViewById(R.id.progrzz);
	spinni.setVisibility(View.GONE);

		blueOn = (Button) findViewById(R.id.blueOn);// 1
		blueOn.setOnClickListener(this);
		lighSet = (Button) findViewById(R.id.lighSet);// 1
		lighSet.setOnClickListener(this);
		Checkligh1 = (CheckBox) findViewById(R.id.Checkligh1);
		Checkligh2 = (CheckBox) findViewById(R.id.Checkligh2);
		chkWO=(CheckBox) findViewById(R.id.chkWO);
		chkWT=(CheckBox) findViewById(R.id.chkWT);
		chkCO=(CheckBox) findViewById(R.id.chkCO);
		chkCT=(CheckBox) findViewById(R.id.chkCT);
		monTmp=(TextView) findViewById(R.id.monTmp);
		monHum=(TextView) findViewById(R.id.monHum);
		monCurr=(TextView) findViewById(R.id.monCurr);


		lighBack = (Button) findViewById(R.id.lighBack);// 1
		lighBack.setOnClickListener(this);
		lighOn = (Button) findViewById(R.id.lighOn);// 1
		lighOn.setOnClickListener(this);
		lighOff = (Button) findViewById(R.id.lighOff);// 1
		lighOff.setOnClickListener(this);
		acOn = (Button) findViewById(R.id.acOn);// 1
		acOn.setOnClickListener(this);
		acOff = (Button) findViewById(R.id.acOff);// 1
		acOff.setOnClickListener(this);
		acSet = (Button) findViewById(R.id.acSet);// 1
		acSet.setOnClickListener(this);
		airUp = (Button) findViewById(R.id.airUp);// 1
		airUp.setOnClickListener(this);
		airDown = (Button) findViewById(R.id.airDown);// 1
		airDown.setOnClickListener(this);
		airBack = (Button) findViewById(R.id.airBack);// 1
		airBack.setOnClickListener(this);
		projSet = (Button) findViewById(R.id.projSet);// 1
		projSet.setOnClickListener(this);
		projOn = (Button) findViewById(R.id.projOn);// 1
		projOn.setOnClickListener(this);
		projOff = (Button) findViewById(R.id.projOff);// 1
		projOff.setOnClickListener(this);
		proBk = (Button) findViewById(R.id.proBk);// 1
		proBk.setOnClickListener(this);
		pIn = (Button) findViewById(R.id.pIn);// 1
		pIn.setOnClickListener(this);
		pOut = (Button) findViewById(R.id.pOut);// 1
		pOut.setOnClickListener(this);
		stmode = (Button)findViewById(R.id.stmode);
		stmode.setOnClickListener(this);
		endmode = (Button)findViewById(R.id.endmode);
		endmode.setOnClickListener(this);
		slidemode=(Button)findViewById(R.id.slidemode);
		slidemode.setOnClickListener(this);
		winSet = (Button) findViewById(R.id.winSet);// 1
		winSet.setOnClickListener(this);
		winBk = (Button) findViewById(R.id.winBk);// 1
		winBk.setOnClickListener(this);
		winOn = (Button) findViewById(R.id.winOn);// 1
	winOn.setOnTouchListener(new View.OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
			    //this is touch
				if(chkWO.isChecked()){a=5;
				b=1;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
				if(chkWT.isChecked()){a=6;
				b=1;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				if(chkCO.isChecked()){a=7;
				b=1;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				if(chkCT.isChecked()){
				a=8;
				b=1;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				

			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
			
				if(chkWO.isChecked()){a=5;
				b=11;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
				if(chkWT.isChecked()){a=6;
				b=11;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				if(chkCO.isChecked()){a=7;
				b=11;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				if(chkCT.isChecked()){
				a=8;
				b=11;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			return false;
		}
	});
	winOff = (Button) findViewById(R.id.winOff);// 1
	winOff.setOnTouchListener(new View.OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
			    //this is touch
				if(chkWO.isChecked()){a=5;
				b=2;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
				if(chkWT.isChecked()){a=6;
				b=2;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				if(chkCO.isChecked()){a=7;
				b=2;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				if(chkCT.isChecked()){
				a=8;
				b=2;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				

			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
			
				if(chkWO.isChecked()){a=5;
				b=22;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
				if(chkWT.isChecked()){a=6;
				b=22;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				if(chkCO.isChecked()){a=7;
				b=22;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				if(chkCT.isChecked()){
				a=8;
				b=22;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			return false;
		}
	});


	}

	void openBT() throws IOException {

		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Standard
																				// SerialPortService
																				// ID
		mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
		mmSocket.connect();
		mmOutputStream = mmSocket.getOutputStream();
		mmInputStream = mmSocket.getInputStream();
		beginListenForData();

	}

	void beginListenForData() {
		final Handler handler = new Handler();
		final byte delimiter = 10; // This is the ASCII code for a newline
									// character

		stopWorker = false;
		readBufferPosition = 0;
		readBuffer = new byte[1024];
		workerThread = new Thread(new Runnable() {
			public void run() {
				while (!Thread.currentThread().isInterrupted() && !stopWorker) {
					try {
						int bytesAvailable = mmInputStream.available();
						if (bytesAvailable > 0) {
							byte[] packetBytes = new byte[bytesAvailable];
							mmInputStream.read(packetBytes);
							for (int i = 0; i < bytesAvailable; i++) {
								byte b = packetBytes[i];
								if (b == delimiter) {
									byte[] encodedBytes = new byte[readBufferPosition];
									System.arraycopy(readBuffer, 0,
											encodedBytes, 0,
											encodedBytes.length);
									final String data = new String(
											encodedBytes, "US-ASCII");
									readBufferPosition = 0;

									handler.post(new Runnable() {
										public void run() {
											if(data.contains("T"))
											{
												String data1="TEMP:"+data.substring(1);
												
												monTmp.setText(data1);
												
												
											}
											else if(data.contains("H"))
											{
												String data1="HUM:"+data.substring(1);

												monHum.setText(data1);
												
												
											}
											else if(data.contains("C"))
											{
												String data1="CURRENT:"+data.substring(1);

												monCurr.setText(data1);
												
												
											}
											
											
											
											
										}
									});
								} else {
									readBuffer[readBufferPosition++] = b;
								}
							}
						}
					} catch (IOException ex) {
						stopWorker = true;
					}
				}
			}
		});

		workerThread.start();
	}

	void sendData() throws IOException {
		// String msg = myTextbox.getText().toString();
		// msg += "\n";
if(mmOutputStream!=null)
{
		mmOutputStream.write(a);
		mmOutputStream.write(b);
}
else {
	Toast.makeText(getBaseContext(), "Connection Lost...please try again", Toast.LENGTH_SHORT).show();

}
	}

	void closeBT() throws IOException {
		stopWorker = true;
		if (mmOutputStream != null) {
			mmOutputStream.close();
		}
		if (mmInputStream != null) {
			mmInputStream.close();
		}
		// mBluetoothAdapter.disable();
		if (mmSocket != null) {
			mmSocket.close();
		}
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.blueOn:
		//	setProgressBarIndeterminateVisibility(true);

			for(int iz=0;iz<=5;iz++)
			{
			       
			try {
				openBT();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			clik=0;
			if((mmDevice.ACTION_ACL_CONNECTED != null)&&(mmOutputStream!=null))
			{	
			flup.setDisplayedChild(1);

			clik = 1;
			
			break;

			
			}
			}

			//setProgressBarIndeterminateVisibility(false);

			if(clik==0)
			{
				
				Toast.makeText(getBaseContext(), "Bluetooth not connected....try again", Toast.LENGTH_SHORT).show();

			}

			break;
		case R.id.lighSet:
			flup.setDisplayedChild(3);

			break;
		case R.id.lighBack:
			flup.setDisplayedChild(1);

			break;
		case R.id.lighOn:
			if (Checkligh1.isChecked()) {
				a = 1;
				b = 1;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (Checkligh2.isChecked()) {
				a = 2;
				b = 1;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case R.id.lighOff:
			if (Checkligh1.isChecked()) {
				a = 1;
				b = 2;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (Checkligh2.isChecked()) {
				a = 2;
				b = 2;
				try {
					sendData();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case R.id.acOn:
			a=3;
			b=1;
			try {
				sendData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.acOff:
			a=3;
			b=2;
			try {
				sendData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.airUp:
			a=3;
			b=3;
			try {
				sendData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.airDown:
			a=3;
			b=4;
			try {
				sendData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.acSet:
			flup.setDisplayedChild(4);

		
			break;
		case R.id.airBack:
			flup.setDisplayedChild(1);

		
			break;
		case R.id.projSet:
			flup.setDisplayedChild(2);

		
			break;
		case R.id.projOn:
			a=4;
			b=1;
			try {
				sendData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
			break;
		case R.id.projOff:
			a=4;
			b=2;
			try {
				sendData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
			break;
		case R.id.pIn:
			a=4;
			b=3;
			try {
				sendData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
			break;
		case R.id.pOut:
			a=4;
			b=4;
			try {
				sendData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
			break;

		 
	case R.id.proBk:
		flup.setDisplayedChild(1);

	
		break;
	case R.id.winSet:
		flup.setDisplayedChild(5);

	
		break;
	case R.id.winBk:
		flup.setDisplayedChild(1);

	
		break;
		
	case R.id.stmode :
		task stmodi=new task(mmOutputStream,mmInputStream);
		a=1; b=1;
			stmodi.sendi(a,b);
			a=4;b=1;
			stmodi.sendi(a,b);
		a=3; b=1;
		stmodi.sendi(a,b);
		a=2; b=1;
		stmodi.sendi(a,b);
		a=5; b=1;
		stmodi.sendi(a,b);
		
		stmodi=null;
		System.gc();
		break;
	case R.id.endmode :
		a=1; b=2;
		try {
			sendData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a=2; b=2;
		try {
			sendData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a=3; b=2;
		try {
			sendData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a=4; b=6;
		try {
			sendData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a=5; b=2;
		try {
			sendData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		break;
	case R.id.slidemode :
		task stmodi1=new task(mmOutputStream,mmInputStream);
		a=1; b=2;
			stmodi1.sendi(a,b);
			a=4;b=1;
			stmodi1.sendi(a,b);
			a=4;b=1;
			stmodi1.sendi(a,b);
		a=2; b=2;
		stmodi1.sendi(a,b);
		a=5; b=2;
		stmodi1.sendi(a,b);
		
		stmodi1=null;
		System.gc();
		}
		

	}


	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		if(flup.getDisplayedChild()==1||flup.getDisplayedChild()==0)
		{
			try {

				closeBT();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.onBackPressed();
		}
		else{
			
			flup.setDisplayedChild(1);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (clik == 1) {
			MenuInflater menuInflater = getMenuInflater();
			menuInflater.inflate(R.layout.menu, menu);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_c:
			// Single menu item is selected do something
			// Ex: launching new activity/screen or show alert message
			flup.setDisplayedChild(1);

			Toast.makeText(MCActivity.this, "control is Selected",
					Toast.LENGTH_SHORT).show();
			return true;

		case R.id.menu_m:
			Toast.makeText(MCActivity.this, "modes is Selected",
					Toast.LENGTH_SHORT).show();
			flup.setDisplayedChild(6);

			return true;

		case R.id.menu_s:
			Toast.makeText(MCActivity.this, "monitor is Selected",
					Toast.LENGTH_SHORT).show();
			flup.setDisplayedChild(7);

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}
class task implements Runnable
{

	OutputStream mOutputStream;
	InputStream mInputStream;
	byte a;
	byte b;
	public task(OutputStream l,InputStream f)
	{
		this.mOutputStream=l;
		this.mInputStream=f;
	

		
	}
	@Override
	
	public void run( ) {
		// TODO Auto-generated method stub
		
		
	}
	void sendi(byte c,byte d)
	{
		try {
			mOutputStream.write(c);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			mOutputStream.write(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
