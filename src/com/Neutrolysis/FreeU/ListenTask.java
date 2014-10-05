package com.Neutrolysis.FreeU;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.LinkedList;

import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ListenTask implements Runnable {
	public LinkedList<String> queue;
	Handler handler = new Handler();

	public ListenTask(LinkedList<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		Looper.prepare();
		MulticastSocket multicastSocket = null;
		DatagramPacket datagramPacket = null;
		byte[] inBuf = new byte[WifiConstants.DGRAM_LEN];
		try {
			// Prepare to join multicast group
			multicastSocket = new MulticastSocket(WifiConstants.PORT_NO);
			InetAddress address = InetAddress
					.getByName(WifiConstants.GROUP_ADDRESS);
			multicastSocket.joinGroup(address);

			while (true) {
				datagramPacket = new DatagramPacket(inBuf, inBuf.length);
				multicastSocket.receive(datagramPacket);
				String msg = new String(inBuf, 0, datagramPacket.getLength());
				if (msg.contains("FIRE")) {
					try {
						Log.d("N1amr", "Trying to send");

						SmsManager smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage("01203021510", null,
								"Fire !!!", null, null);
						Log.d("N1amr", "Message is sent");

					} catch (Exception e) {
						Log.d("N1amr", e.getMessage());
					}
				}
				final String dispMsg = msg;

				// final String dispMsg = "From :" + datagramPacket.getAddress()
				// + " Msg : " + msg;
				queue.addLast(dispMsg);
			}
		} catch (Exception ioe) {
			Log.e("N1amr", ioe.getMessage());
		}
	}
}