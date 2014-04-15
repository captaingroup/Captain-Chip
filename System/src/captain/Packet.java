package captain;

import java.sql.Timestamp;

public class Packet {
	
	Sensor sensor;
	String data;
	String timestamp;
	
	public Packet(Sensor sensor, String data, String timestamp) {
		this.sensor = sensor;
		this.data = data;
		this.timestamp = timestamp;
	}
	
	public String toString() {
		return "ID: " + sensor.ID + ", data: " + data + ", " + timestamp;
	}

}
