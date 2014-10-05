package com.Neutrolysis.FreeU;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;



import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;


public class cBluetooth{
	
	public final static String TAG = "U Control";
	public int Flag = 0 ;
	private static BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;
	private ConnectedThread mConnectedThread;

    // SPP UUID service 
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // UUID 00001101-0000-1000-8000-00805F9B34FB is code is included in any bluetooth boards .
    private  Handler mHandler;
	private Handler _handler = new Handler();			
    public final static int BL_NOT_AVAILABLE = 1;        // Handler message 
    public final static int BL_INCORRECT_ADDRESS = 2;
    public final static int BL_REQUEST_ENABLE = 3;
    public final static int BL_SOCKET_FAILED = 4;
    public final static int RECIEVE_MESSAGE = 5;
    public final static int BL_CONNECTED = 6;
    public final static int BL_SEND_FAILED = 7;
    public final static int  RECIEVE_MESSAGE_BAD = 8 ;

   cBluetooth(Handler handler) {
    	
    	mHandler = handler;
		// TODO Auto-generated constructor stub
	}
   
   
   cBluetooth(Context context, Handler handler){
    	btAdapter = BluetoothAdapter.getDefaultAdapter();
    	mHandler = handler;
        if (btAdapter == null) {
        	mHandler.sendEmptyMessage(BL_NOT_AVAILABLE);
            return;
        }
    }
   
    public void checkBTState() {
    	if(btAdapter == null) { 
     		mHandler.sendEmptyMessage(BL_NOT_AVAILABLE);
    	} else {
    		if (btAdapter.isEnabled()) {
    			Log.d(TAG, "Bluetooth ON");
    		} else {
    			mHandler.sendEmptyMessage(BL_REQUEST_ENABLE);
    		}
    	}
	}
   
    
    
    public void BT_Connect(BluetoothDevice Device) {   	
    	Log.d(TAG, "...On Resume...");   	

    	if(!BluetoothAdapter.checkBluetoothAddress(Device.getAddress())){
    		mHandler.sendEmptyMessage(BL_INCORRECT_ADDRESS);
    		return;
    	}
    	else{
	    	//BluetoothDevice device = btAdapter.getRemoteDevice(address);
    		BluetoothDevice device =Device ;
	        try {
	        	btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
	        } catch (IOException e) {
	        	Log.d(TAG, "In onResume() and socket create failed: " + e.getMessage());
	        	mHandler.sendEmptyMessage(BL_SOCKET_FAILED);
	    		return ;
	        }
	        
	        btAdapter.cancelDiscovery();
	        Log.d(TAG, "...Connecting...");
	        try {
	          btSocket.connect();
	          Log.d(TAG, "...Connection ok...");
	          mHandler.sendEmptyMessage(BL_CONNECTED);
	        } 
	        catch (IOException e) {
	          try {
	            btSocket.close();
	          } catch (IOException e2) {
	        	  Log.d(TAG, "In onResume() and unable to close socket during connection failure" + e2.getMessage());
	        	  mHandler.sendEmptyMessage(BL_SOCKET_FAILED);
	        	  return;
	          }
	        }
	         
	        // Create a data stream so we can talk to server.
	        Log.d(TAG, "...Create Socket...");
	     
	        try {
	        	outStream = btSocket.getOutputStream();
	        } catch (IOException e) {
	        	Log.d(TAG, "In onResume() and output stream creation failed:" + e.getMessage());
	    
	        	mHandler.sendEmptyMessage(BL_SOCKET_FAILED);
	        	return;
	        }
			   
		    mConnectedThread = new ConnectedThread();
		    mConnectedThread.start();
    	}
	}
    public void BT_Connect(String address) {   	
    	Log.d(TAG, "...On Resume...");   	

    	if(!BluetoothAdapter.checkBluetoothAddress(address)){
    		mHandler.sendEmptyMessage(BL_INCORRECT_ADDRESS);
    		return;
    	}
    	else{
	    	BluetoothDevice device = btAdapter.getRemoteDevice(address);
	        try {
	        	btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
	        } catch (IOException e) {
	        	Log.d(TAG, "In onResume() and socket create failed: " + e.getMessage());
	        	mHandler.sendEmptyMessage(BL_SOCKET_FAILED);
	    		return ;
	        }
	        
	        btAdapter.cancelDiscovery();
	        Log.d(TAG, "...Connecting...");
	        try {
	          btSocket.connect();
	          Log.d(TAG, "...Connection ok...");
	          mHandler.sendEmptyMessage(BL_CONNECTED);
	        } 
	        catch (IOException e) {
	          try {
	            btSocket.close();
	          } catch (IOException e2) {
	        	  Log.d(TAG, "In onResume() and unable to close socket during connection failure" + e2.getMessage());
	        	  mHandler.sendEmptyMessage(BL_SOCKET_FAILED);
	        	  return;
	          }
	        }
	         
	        // Create a data stream so we can talk to server.
	        Log.d(TAG, "...Create Socket...");
	     
	        try {
	        	outStream = btSocket.getOutputStream();
	        } catch (IOException e) {
	        	Log.d(TAG, "In onResume() and output stream creation failed:" + e.getMessage());
	    
	        	mHandler.sendEmptyMessage(BL_SOCKET_FAILED);
	        	return;
	        }
			   
		    mConnectedThread = new ConnectedThread();
		    mConnectedThread.start();
    	}
	}
    
    
    public void BT_onPause() {
    	Log.d(TAG, "...On Pause...");
    	if (outStream != null) {
    		try {
    	        outStream.flush();
    	    } catch (IOException e) {
	        	Log.d(TAG, "In onPause() and failed to flush output stream: " + e.getMessage());
	        	mHandler.sendEmptyMessage(BL_SOCKET_FAILED);
	        	return;
    	    }
    	}

    	if (btSocket != null) {
	    	try {
	    		btSocket.close();
	    	} catch (IOException e2) {
	        	Log.d(TAG, "In onPause() and failed to close socket." + e2.getMessage());
	        	mHandler.sendEmptyMessage(BL_SOCKET_FAILED);
	        	return;
	    	}
    	}
    }
        //Finally I solved my problem using createInsecureRfcommSocketToServiceRecord(SERIAL_UUID) instead createRfcommSocketToServiceRecord(SERIAL_UUID)
   
    public int getstate(){
    	
    	if(btSocket==null){
    		
    		Log.d(TAG, "no socket ");
    		return 0;
    		
    	}else 
    		return 1;
    	
    	
    }
   
   //#### sendData Methode for the voice buffer///
   public void sendData(byte[] buffer) {
       byte[] msgBuffer = buffer;
       if (outStream != null) {
	        try {
	        	outStream.write(msgBuffer);
	        } catch (IOException e) {
	        	Log.d(TAG, "In onResume() and an exception occurred during write: " + e.getMessage());
	        	mHandler.sendEmptyMessage(BL_SEND_FAILED);
	        	
	        	return;      
	        }
       } else Log.d(TAG, "Error Send data: outStream is Null");
	}
   //### sendData Method for text commands ##//
    public void sendData(String message) {
    	
        byte[] msgBuffer = message.getBytes();
     
        Log.i(TAG, "Send data: " + message);
        
        if (outStream != null) {
	        try {
	        	outStream.write(msgBuffer);
	        } catch (IOException e) {
	        	Log.d(TAG, "In onResume() and an exception occurred during write: " + e.getMessage());
	        	mHandler.sendEmptyMessage(BL_SEND_FAILED);
	        	return;      
	        }
        } else Log.d(TAG, "Error Send data: outStream is Null");
	}
    
    private class ConnectedThread extends Thread {

		  	private final InputStream mmInStream;
		 
		    public ConnectedThread() {
		        InputStream tmpIn = null;
		 
		        // Get the input and output streams, using temp objects because
		        // member streams are final
		        
		        try {
		          tmpIn = btSocket.getInputStream();
		        } catch (IOException e) { }
		 
		        mmInStream = tmpIn;
		    }
		 //http://stackoverflow.com/questions/12294705/error-in-reading-data-from-inputstream-in-bluetooth-on-android
		    public void run() 
		    {	
		    	
		   
		    		int bytes; // bytes returned from read()
		    		int StartBYTE = 72 ;// byte H
		    		int EndBYTE = 69 ; //byte E 
		    	    // Keep listening to the InputStream until an exception occurs
		    	    while (true) {
		    	       try {
						if (mmInStream.read()==StartBYTE)
								{	
									byte[] buffer = null ;
									bytes =mmInStream.read(buffer ,0,3);
									for (int i =0 ; i<4;i++){
									Log.d("TestData" , buffer[i]+" " );
									String TEST = new String(buffer);
									Log.d("TestString", TEST);
									mHandler.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer);
															}
								}
		    	       		} catch (IOException e1)
		    	       			{
		    	       				// TODO Auto-generated catch block
		    	       				e1.printStackTrace();
		    	       			}
		    	       		
		    	       			
		    	    	  
		    	    			}
		    	   	 
		   
		    
			
		    
		  	}
		 }
		    
	public void stopsend(){
		    
		   	try {
		   		
		   		mConnectedThread.interrupt();
		   		outStream.flush();
		   		btSocket.close();
				
				
			} catch (IOException e) {
				Log.d(TAG, "Unable to close Socket On user Request: " + e.getMessage());
			}
		   }


}
    