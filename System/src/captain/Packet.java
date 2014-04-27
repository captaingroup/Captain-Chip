package captain;

//import java.sql.Timestamp;

public class Packet {
	
	private Sensor sensor;
	private String data;
	private String timestamp;
	
	public Packet(Sensor sensor, String data, String timestamp) {
		this.sensor = sensor;
		this.data = data;
		this.timestamp = timestamp;
	}
	
	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String toString() {
		return "ID: " + sensor.getID() + ", data: " + data + ", " + timestamp;
	}

}
