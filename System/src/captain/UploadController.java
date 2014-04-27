package captain;

import java.util.List;

public class UploadController extends Thread {

	DataUploadLayer uploader;
	
	public UploadController(DataUploadLayer uploader) {
		this.uploader = uploader;
	}
	
	public void run() {
		System.out.println("Uploader controller has been launched");
		while (true) {
			//System.out.println("Successfully in UC loop");
			List<Sensor> sensors = GlobalSensorState.getSensors();
			if (sensors.size() == 0) System.out.println("No sensors detected");
			for (Sensor s : sensors) {
				//System.out.println("UC is considering sensor ID " + s.getID());
				Packet p = s.packets.poll();
				if (p == null) System.out.println("Null packet :(");
				if (p != null) {
					//System.out.println("packet isn't null!");
					System.out.println(p);
					List<SensorGroup> relevantGroups = GlobalSensorState.getSensorGroups(s);
					for (SensorGroup g : relevantGroups) {
						System.out.println("About to call upload");
						try {
							uploader.upload(p, g);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
