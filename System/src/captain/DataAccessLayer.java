package captain;

import java.util.List;

public interface DataAccessLayer {
	
	public abstract List<Sensor> returnSensors();
	public abstract List<SensorGroup> returnSensorGroups();

}
