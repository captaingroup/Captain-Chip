package captain;

public abstract class DataUploadLayer {
	
	/**
	 * Packet holds a reference to the sensor it has been received from. Upload will
	 * see which groups that sensor is part of, and uploads the packet to all relevant
	 * groups in the data structure being uploaded to.
	 */
	abstract void upload(Packet p, SensorGroup g) throws Exception;
	
	/**
	 * Add new sensor to data store
	 */
	abstract void uploadSensor(Sensor s) throws Exception;
	
	/**
	 * Records a new sensor group to the data store, including the sensors held in the group
	 */
	abstract void uploadGroup(SensorGroup g) throws Exception;

}
