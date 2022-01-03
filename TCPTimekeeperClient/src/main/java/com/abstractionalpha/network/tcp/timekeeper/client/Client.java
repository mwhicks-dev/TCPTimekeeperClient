package com.abstractionalpha.network.tcp.timekeeper.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	/** Socket for host-client communication */
	Socket socket = null;
	
	/** Input stream for socket communication */
	DataInputStream input = null;
	
	/** Output stream for socket communication */
	DataOutputStream output = null;
	
	/** Host used for TCP connection */
	private static final String HOST = "localhost";
	
	/** Port used for TCP connection */
	private static final int PORT = 26500;
	
	
	/**
	 * Client instance code.
	 * 
	 * @param args -- command-line arguments
	 */
	private void run(String[] args) {
		// Establish a connection
		try {
			// Connect to host
			socket = new Socket(HOST, PORT);
			System.out.println("Connected");
			
			// Set up IO streams
			input = new DataInputStream(System.in);
			output = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException uhe) {
			// Handle error
			System.err.println(String.format("Couldn't connect to host %s: %s", HOST, uhe));
			uhe.printStackTrace();
		} catch (IOException ioe) {
			// Handle error
			System.err.println(String.format("IO Exception: %s", ioe));
			ioe.printStackTrace();
		}
		
		// Run arguments
		String line = "";
		String end = "done";
		
		while (!line.equals(end)) {
			try {
				line = input.readUTF();
				output.writeUTF(line);
			} catch (IOException ioe) {
				// Handle error
				System.err.println(String.format("IO Exception: %s", ioe));
				ioe.printStackTrace();
			}
		}
		
		// Clean up
		try {
			input.close();
			output.close();
			socket.close();
		} catch (IOException ioe) {
			// Do nothing
		}
	}
	
	/**
	 * Main method. Runs a Client instance. Single-threaded.
	 * 
	 * @param args -- command-line arguments
	 */
	public static void main(String[] args) {
		Client client = new Client();
		client.run(args);
	}

}
