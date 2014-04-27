package captain;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Sensor extends Thread {
	
	private int ID;	//ID is set when first packet is read in
	private String deviceType;
	//add data retrieval frequency
	
	public ConcurrentLinkedQueue<Packet> packets;
	
	public Sensor(int ID, String deviceType) {
		//super();
		this.ID = ID;
		this.deviceType = deviceType;
	}
	
	public int getID() { return ID; }
	public String getDeviceType() { return deviceType; }

}
