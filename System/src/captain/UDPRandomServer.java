package captain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;


public class UDPRandomServer extends Thread {
	
	//IP and port that data will be sent to
	InetAddress address;
	int port;
	int range;
	int ID;
	protected DatagramSocket socket;
	
	public UDPRandomServer(int port, int range, int ID, String threadName) throws UnknownHostException, SocketException {
		super(threadName);
		address = InetAddress.getByName("localhost");
		socket = new DatagramSocket();
		this.port = port;
		this.range = range;
		this.ID = ID;
	}
	
	public void run() {
		System.out.println("Random number generator being launched");
		Random random = new Random();
		while (true) {
			//String value = String.valueOf(random.nextInt(100));
			//byte[] valueBytes = value.getBytes();
			
			int num = random.nextInt(range) + 1;							//random number (i.e. data being sent)
			String numberString = String.valueOf(num); 						//random number as string
			Date time = new Date();
			Timestamp timestamp = new Timestamp(time.getTime());			//timestamp
			byte[] data = numberString.getBytes();							//random number as byte array
			byte[] tsBytes = timestamp.toString().getBytes();				//timestamp as byte array
			byte[] packetData = new byte[5 + data.length + tsBytes.length];	//holds ID and value (i.e. full packet)
			byte[] idBytes = ByteBuffer.allocate(4).putInt(ID).array();		//ID as byte array
			//System.out.println("Length of ID bytes: " + idBytes.length);
			//System.out.println("Length of packetData " + packetData.length);
			//copying in ID and random number into final byte array
			for (int i = 0; i < 4; i++) {
				packetData[i] = idBytes[i];
			}
			for (int i = 0; i < tsBytes.length; i++) {
				packetData[i + 4] = tsBytes[i];
			}
			
			packetData[4 + tsBytes.length] = (byte) 0xFF;					//splits timestamp and payload
			
			if (data.length != 0) {
				//System.out.println(data.length);
				for (int i = 0; i < data.length; i++) {
					//System.out.println("Now considering byte " + i);
					packetData[i + 5 + tsBytes.length] = data[i];
				}
			}
			
			
			//sending packet
			DatagramPacket packet = new DatagramPacket(packetData, packetData.length, address, port);
			try {
				//System.out.println("Sensor " + ID + " generated " + numberString);
				//System.out.println("Sending " + packetData.length + " bytes");
				//System.out.println("Sending packet " + "ID: " + ID + ", data: " + num + ", " + timestamp);
				socket.send(packet);
			} catch (IOException e) {
				System.out.println("Could not send packet");
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
