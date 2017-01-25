package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
	
	private View view;
	
	private int port;
	private ServerSocket serverSocket;
	private Socket playerSocket;
	private int playerId = 0;
	private List<Player> players;
	
	public Server (int port) {
		this.port = port;
		view = new View();
		players = Collections.synchronizedList(new ArrayList<Player>());
	}
	
	public void launch() {
		try {
			serverSocket = new ServerSocket(port);
			view.setLog("started");
			while(!serverSocket.isClosed()) {
				playerSocket = serverSocket.accept();
				players.add(new Player(playerId, playerSocket, players, view));
				new Thread(players.get(playerId++)).start();
			}
			serverSocket.close();
		} catch (IOException e) {
			view.setLog("unable to create server on specific port");
		}
	}
	
}  