package captain;

public class DataPoint {
	
	SensorGroup group;
	Packet packet;

	public DataPoint(SensorGroup group, Packet packet) {
		this.group = group;
		this.packet = packet;
	}
	
}
