package captain;

import java.util.ArrayList;
import java.util.List;

public class MockDataLayer implements DataAccessLayer {

	List<SensorGroup> sensorGroups;

	public MockDataLayer() {
		List<Sensor> group1Sensors = new ArrayList<Sensor>();
		//group1Sensors.add(new Sensor(1, "Random1", ))
		//sensorGroups.add(new SensorGroup(1, "group1", ))
	}
	
	public List<Sensor> returnSensors() {
		// TODO Auto-generated method stub
		return null;
	}


	public List<SensorGroup> returnSensorGroups() {
		// TODO Auto-generated method stub
		return null;
	}

}
