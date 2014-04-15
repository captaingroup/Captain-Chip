package captain;

public abstract class Connection extends Thread {
	
	String connectionType;
	//List<String> credentials				//credentials should be added in subclasses given unknown nature
	
	
	public Connection(String threadName) {
		super(threadName);
	}
	
	//abstract void connect(ConcurrentLinkedQueue<Packet> packets);

}
