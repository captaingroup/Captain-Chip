package captain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Holds the current list of registered sensors as well as groups they are part of
 */
public class GlobalSensorState {
	
	private static List<Sensor> sensors;// = null;
	
	//Sensor groups are added when a new profile is read in. If the profile is updated (i.e. a new
	//profile has been uploaded) all existing groups are deleted and the new ones are added.
	private static List<SensorGroup> sensorGroups;
	
	//fast way of determining groups a sensor is part of (but sensor will not be part of many groups:
	//is a hashmap worth it, given that it also has to be kept consistent?)
	private static ConcurrentHashMap<Sensor, List<SensorGroup>> sensorLookup;
	
	public static void initialise() {
		sensors = new ArrayList<Sensor>();
		sensorGroups = new ArrayList<SensorGroup>();
		sensorLookup = new ConcurrentHashMap<Sensor, List<SensorGroup>>();
	}
	
	static synchronized public void addSensor(Sensor s) {
		sensors.add(s);
		if (sensorLookup == null) System.out.println("Hashmap is null");
		List<SensorGroup> emptyGroup = new ArrayList<SensorGroup>();
		sensorLookup.put(s, emptyGroup);
	}
	
	static synchronized public void removeSensor(int id) {
		for (Sensor s : sensors) {
			if (s.getId() == id) {
				sensors.remove(s);
				sensorLookup.remove(s);
				break;
			}
		}
	}
	
	static synchronized public List<Sensor> getSensors() {
		return sensors; 
	}
	
	/**
	 * The sensor groups are set remotely, and this information is passed down the wire as JSON.
	 * The 'sensor group' section of the profile data is parse in the following way (for one group):
	 * <Group ID>, <Group name>, <Sensor type 1>, <Sensor type 2> ...
	 * This is stored as List<String>. Thus, a set of all the sensor groups will be stored as
	 * List<List<String>>. The SensorGroup objects are built with this structure in mind.
	 * These groups are also uploaded to the data store.
	 * @throws Exception 
	 */
	static synchronized public void generateSensorGroups(List<List<String>> groupStructure, 
			DataUploadLayer du) throws Exception {
		//this method is called when a new profile is read in, so old groupings are deleted
		sensorGroups.clear();
		for (List<String> l : groupStructure) {
			SensorGroup g = new SensorGroup();
			g.setID(Integer.valueOf(l.get(0)));
			g.setGroupName(l.get(1));
			for (int i = 2; i < l.size(); i++) {
				//find sensor matching sensor type
				String sensorType = l.get(i);
				for (Sensor s : sensors) {
					if (s.getDeviceType().equals(sensorType)) {
						g.getSensors().add(s);
						sensorLookup.get(s).add(g);
					}
				}
			}
			sensorGroups.add(g);
			du.uploadGroup(g);
		}
	}
	
	static synchronized public List<SensorGroup> getSensorGroups(Sensor s) {
		return sensorLookup.get(s);
	}
	
	static synchronized public List<SensorGroup> getAllSensorGroups() {
		return sensorGroups;
	}

}
