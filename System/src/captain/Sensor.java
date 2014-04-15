package captain;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Sensor extends Thread{
	
	int ID;	//ID is set when first packet is read in
	String deviceType;
	
	public ConcurrentLinkedQueue<Packet> packets;
	
	public Sensor(int ID, String deviceType) {
		//super();
		this.ID = ID;
		this.deviceType = deviceType;
	}

}
