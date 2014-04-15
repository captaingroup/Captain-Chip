package captain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;


public class RandomConnector extends Sensor {
	
	protected DatagramSocket socket = null;
	int port;
	
	public RandomConnector(int ID, String deviceType, int port) throws SocketException {
		super(ID, deviceType);
		this.port = port;
		this.socket = new DatagramSocket(port);
		packets = new ConcurrentLinkedQueue<Packet>();
	}
	
	public void run() {
		
		while (true) {
			byte[] buffer = new byte[256];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				System.out.println("Couldn't receive packet");
			}
			//get raw packet
			byte[] valueBytes = packet.getData();
			//System.out.println("Received packet: " + valueBytes.length + " bytes");
			/*for (int i = 0; i < valueBytes.length; i++) {
				System.out.println("Byte " + i + ": " + valueBytes[i]);
			}*/
			
			//processing incoming data
			byte[] idBytes = new byte[4];
			byte[] payloadBytes = new byte[valueBytes.length - idBytes.length];
			for (int i = 0; i < 4; i++) {
				idBytes[i] = valueBytes[i];
			}
			
			int ffIndex = 4;
			//actually getting 0xFF in packet needs to be dealt with
			while (valueBytes[ffIndex] != (byte) 0xFF) ffIndex++;				//finding index of 0xFF
			byte[] tsBytes = new byte[ffIndex - 4];
			for (int j = 4; j < ffIndex; j++) {
				tsBytes[j - 4] = valueBytes[j];
			}
			
			if (valueBytes.length > 4) {
				for (int k = ffIndex + 1; k < valueBytes.length; k++) {
					payloadBytes[k - ffIndex - 1] = valueBytes[k];
				}
			}
			int ID = ByteBuffer.wrap(idBytes).getInt();
			String timestamp = new String(tsBytes);
			String payload = new String(payloadBytes);
			
			Packet packetToAdd = new Packet(this, payload, timestamp);
			
			System.out.println("About to add packet");
			packets.add(packetToAdd);
			
			//System.out.println("Received ID " + ID + " on port " + port);
			//System.out.println("Received timestamp " + timestamp + " on port " + port);
			//System.out.println("Received payload " + payload + " on port " + port);
			
		}
	}

}

