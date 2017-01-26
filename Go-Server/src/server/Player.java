package server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Player implements Runnable {
	
	private View view;	
	private Connection connection;
	private Protocol protocol;
	private int id;
	private List<Player> players;
	
	public Player(int id, Socket socket, List<Player> players, View view) throws IOException {
		this.id = id;
		this.players = players;
		this.view = view;
		connection = new Connection(id, socket, players);
		protocol = new Protocol(connection);
	}
		
	public void run() {
		view.setLog(id + " connected");
			while(!connection.isClosed()) {
				try {
					String n = connection.read();
					view.setLog(n);
					protocol.process(n);
					
					
				} catch (IOException e) {
					players.remove(id);
					try {
						connection.close();
					} catch (IOException e1) {
						view.setLog("222222");
						
					}
				} catch (ClassNotFoundException e1) {
					view.setLog("3333");
					
				}
			}
	}
	
	public int getId() {
		return id;
	}
	public Connection getConnection() {
		return connection;
	}
}