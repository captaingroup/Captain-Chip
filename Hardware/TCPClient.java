//TCPClient.java

import java.io.*;
import java.net.*;

class TCPClient {
	public static void main(String argv[]) throws Exception {

		Socket clientSocket = new Socket("localhost", 5005);

		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
				clientSocket.getInputStream()));
		
		System.out.println("Connection made");

		while (true) {
			String fromServer;
			fromServer = inFromServer.readLine();
			System.out.println("RECEIVED: " + fromServer);
		}
	}
}