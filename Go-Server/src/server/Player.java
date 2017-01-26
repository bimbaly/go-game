package server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Player implements Runnable {
	
	private View view;	
	private Connection connection;
	private Protocol protocol;
	private int id;
	
	public Player(int id, Socket socket, List<Player> players, View view) throws IOException {
		this.id = id;
		this.view = view;
		connection = new Connection(socket);
		protocol = new Protocol(connection);
	}
		
	public void run() {
		
	}
	
	public Connection getConnection() {
		return connection;
	}
}