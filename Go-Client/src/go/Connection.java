package go;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {
	
	private int port;
	private String ip;
	
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	
	public Connection(String ip, int port) throws UnknownHostException, IOException {
		this.ip = ip;
		this.port = port;
		socket = new Socket(ip, port);
		output = new PrintWriter(socket.getOutputStream(), true);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public String readInput() throws IOException {
		return input.readLine();
	}
	
	public void send(String msg) {
		output.println(msg);
	}
	
	public void close() {
		output.close();
		try {
			input.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("error");
		}
		
	}
	
	public boolean isConnected() {
		return socket.isConnected();
	}
}
