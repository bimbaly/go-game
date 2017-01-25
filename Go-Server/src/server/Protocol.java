package server;

import java.net.Socket;
import java.util.List;

public class Protocol {
	
	int id;
	Socket socket;
	List<Player> players;
	
	public Protocol(int id, Socket socket, List<Player> players) {
		this.id = id;
		this.socket = socket;
		this.players = players;
	}
	
	public void process(String input) {
		
	}
}
