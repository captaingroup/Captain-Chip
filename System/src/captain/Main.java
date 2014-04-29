package captain;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {

	public static List<Sensor> sensors;
	
	public static ConcurrentLinkedQueue<DataPoint> dataStore = new ConcurrentLinkedQueue<DataPoint>();
	
	public static void main (String[] args) throws Exception {

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
		
		Sensor random1 = new RandomSensor(1, "random1", 5001);
		Sensor random2 = new RandomSensor(2, "random2", 6001);
		Sensor random3 = new RandomSensor(3, "random1", 7001);
		
		UDPRandomServer dataOut = new UDPRandomServer(5001, 100, 1, "thread1");
		UDPRandomServer dataOut2 = new UDPRandomServer(6001, 1000, 1, "thread2");
		UDPRandomServer dataOut3 = new UDPRandomServer(7001, 150, 1, "thread3");
		
		List<String> sensorGroup = new ArrayList<String>();		//represents profile
		sensorGroup.add("123");
		sensorGroup.add("TestGroup");
		sensorGroup.add("random2");
		sensorGroup.add("random1");
		
		List<List<String>> groups = new ArrayList<List<String>>();
		groups.add(sensorGroup);
		
		//Data uploader
		SQLUploader uploader = new SQLUploader();
		UploadController uc = new UploadController(uploader);
		
		//Adding mock data
		GlobalSensorState.initialise();
		GlobalSensorState.addSensor(random1, uploader);
		GlobalSensorState.addSensor(random2, uploader);
		GlobalSensorState.addSensor(random3, uploader);
		GlobalSensorState.generateSensorGroups(groups, uploader);
		
		//Starting sensors and listeners
		dataOut.start();
		dataOut2.start();
		dataOut3.start();
		random1.start();
		random2.start();
		random3.start();
		
		System.out.println("About to initialise other stuff");
		
		System.out.println("Created upload controller");
		
		uc.start();
		
		System.out.println("Running upload controller");
		
		System.out.println("All threads are running");
		
//		sensors.add(random1);
//		sensors.add(random2);
//		
//		dataOut.start();
//		dataOut2.start();
//		random1.start();
//		random2.start();
//		
//		while (true) {
//			Thread.sleep(100);
//			int numSensors = sensors.size();
//			//System.out.println("number of sensors: " + numSensors);
//			for (int i = 0; i < numSensors; i++) {
//				Packet packet = sensors.get(i).packets.poll();
//				if (packet != null) System.out.println("Received packet: " + packet);
//				//else System.out.println("this shit's null yo");
//			}
//		}
		
	}

}
