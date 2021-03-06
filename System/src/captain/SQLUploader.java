package captain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLUploader extends DataUploadLayer {
	
	static Connection connect = null;
	
	void initialise() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://sql3.freemysqlhosting.net/sql331497?"
				+ "user=sql331497&password=sI2*yG2*");
		System.out.println("Connection worked!");
	}
	
	void upload(Packet p, SensorGroup g) throws SQLException, ClassNotFoundException {
		if (connect == null) initialise();
		
		String uploadPacketQuery = "INSERT `sql331497`.`Data` (`SensorID`, `GroupID`, `Data`, `Timestamp`) "
				+ "VALUES (?, ?, ?, ?)";
		PreparedStatement uploadPacket = connect.prepareStatement(uploadPacketQuery);
		//uploadPacket.setInt(1, g.getID());
		uploadPacket.setInt(1, p.getSensor().getID());
		//uploadPacket.setInt(2, p.getSensor().getID());
		uploadPacket.setInt(2, g.getID());
		uploadPacket.setString(3, p.getData().trim());
		uploadPacket.setString(4, p.getTimestamp());
		
		System.out.println("Adding data to table now");
		
		uploadPacket.executeUpdate();
		
		
		/*System.out.println("Upload method being called");
		System.out.println("Uploading " + p + ", sensor group ID " + g.getID());
		System.out.println(g.getID());
		
		//checking if table already exists - if not, create new table
		PreparedStatement allTables = connect.prepareStatement("SHOW TABLES");
		ResultSet tables = allTables.executeQuery();
		boolean tableFound = false;
		
		while (tables.next()) {
			String tableNameResult = tables.getString(1);
			if (tableNameResult.equals(g.getGroupName())) {
				tableFound = true;
				break;
			}
		}
		
		if (!tableFound) {
			String createTableQuery = 
					"CREATE TABLE " + g.getGroupName() + " ("
					+ "id INT NOT NULL AUTO_INCREMENT, "
					+ "sensorID INT(4) NOT NULL, "
					+ "data VARCHAR(50) NOT NULL, "
					+ "timestamp VARCHAR(50) NOT NULL, "
					+ "PRIMARY KEY(id))";
			PreparedStatement createTable = connect.prepareStatement(createTableQuery);
			System.out.println("Create table statement: " + createTable);
			createTable.executeUpdate();
			System.out.println("Created new table");
		}
		
		//uploading packet
		//String uploadQuery = 
		//		"INSERT INTO `sql331497`.`" + g.getGroupName() + 
		//		"` (`sensorID`, `data`, `timestamp`) "
		//		+ "VALUES (?, " + p.getData() + " , ?)";
		String uploadQuery = "INSERT INTO `sql331497`.`" + g.getGroupName() + "` (`sensorID`, `data`, `timestamp`) "
				+ "VALUES (?, '" + p.getData().trim() + "' , ?)";
		PreparedStatement upload = connect.prepareStatement(uploadQuery);
		upload.setInt(1, p.getSensor().getID());
		System.out.println("This is the data I'm uploading: " + p.getData());
		//byte[] dataBytes = p.getData().trim().getBytes();
		//System.out.println("HERE BE BYTES");
		//System.out.println("Original: " + p.getData() + ", " + p.getData().trim());
		//for (byte b : dataBytes) System.out.print(b);
		//upload.setString(2, p.getData());
		upload.setString(2, p.getTimestamp());
		System.out.println("Upload SQL statement: " + upload);
		upload.executeUpdate();
		System.out.println("Packet uploaded!");*/
		
	}

	void uploadGroup(SensorGroup g) throws Exception {
		
		if (connect == null) initialise();
		
		String doesGroupExistQuery = "SELECT * FROM `sql331497`.`GroupInformation`";
		PreparedStatement doesGroupExist = connect.prepareStatement(doesGroupExistQuery);
		ResultSet results = doesGroupExist.executeQuery();
		if (!results.next()) {
			String createGroupQuery = "INSERT INTO `sql331497`.`GroupInformation` (`Name`) VALUES (?)";
			PreparedStatement createGroup = connect.prepareStatement(createGroupQuery);
			createGroup.setString(1, g.getGroupName().trim());
			System.out.println("Adding new group");
			createGroup.executeUpdate();
			
			List<Sensor> sensors = g.getSensors();
			for (Sensor s : sensors) {
				String uploadPairingQuery = "INSERT INTO `sql331497`.`SensorGroup` (`GroupID`, `SensorID`) "
						+ "VALUES (?, ?)";
				PreparedStatement uploadPairing = connect.prepareStatement(uploadPairingQuery);
				
				uploadPairing.setInt(1, g.getID());
				uploadPairing.setInt(2, s.getID());
				System.out.println("Uploading pairing");
				uploadPairing.executeUpdate();
				
			}
		}
		
	}

	@Override
	void uploadSensor(Sensor s) throws Exception {

		if (connect == null) initialise();
		
		//Check for existing sensor
		//String doesSensorExist = "SELECT * FROM `sql331497`.`SensorCorrect` WHERE "
		
		String addSensorQuery = "INSERT INTO `sql331497`.`SensorCorrect` (`Type`, `Category`) "
				+ "VALUES (? ?)	";
		PreparedStatement addSensor = connect.prepareStatement(addSensorQuery);
		addSensor.setString(1, s.getName());
		addSensor.setString(2, s.getDeviceType());
		System.out.println("Adding sensor now");
	}

}
