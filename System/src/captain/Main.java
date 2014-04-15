package captain;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

	public static List<Sensor> sensors;
	
	public static ConcurrentLinkedQueue<DataPoint> dataStore = new ConcurrentLinkedQueue<DataPoint>();
	
	public static void main (String[] args) throws SocketException, UnknownHostException {

		/**********************
		 * Note that the sensor and receiver are being defined in the main method just for demo
		 * purposes. In the actual application, a thread will be scanning for incoming connections
		 * at a regular time interval, creating new sensor objects to receive the data. The mock
		 * sensors themselves will be launched somewhere entirely different to stress the point that
		 * the sensor and receiver don't know of each others existence to begin with.
		 * 
		 * The sensors are also not kept in the Main class, but will be stored in some instantiation of
		 * the data access layer.
		 * 
		 */
		
		sensors = new LinkedList<Sensor>();
		
		Sensor random1 = new RandomConnector(1, "random", 5001);
		UDPRandomServer dataOut = new UDPRandomServer(5001, 100, 1, "thread1");
		sensors.add(random1);
		
		dataOut.start();
		random1.start();
		
		while (true) {
			int numSensors = sensors.size();
			//System.out.println("number of sensors: " + numSensors);
			for (int i = 0; i < numSensors; i++) {
				Packet packet = sensors.get(i).packets.poll();
				if (packet != null) System.out.println(packet);
				//else System.out.println("this shit's null yo");
			}
		}
		
	}

}
