package captain;

import java.util.ArrayList;
import java.util.List;

public class SensorGroup {
	
	private int ID;
	private String groupName;
	private List<Sensor> sensors;
	
	public SensorGroup() {
		sensors = new ArrayList<Sensor>();
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<Sensor> getSensors() {
		return sensors;
	}
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

}
