package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Server {
	
	private View view;
	
	private int port;
	private ServerSocket serverSocket;
	private Socket playerSocket;
	
	private int playerId = 0;
	private Map<Integer, PlayerData> players;
	private Map<Integer, String> games;
	
	private PlayerHandler handler;
	
	public Server (int port) {
		this.port = port;
		view = new View();
		players = Collections.synchronizedMap(new HashMap<Integer, PlayerData>());
		games = Collections.synchronizedMap(new HashMap<Integer, String>());
		handler = new PlayerHandler(players, games);
	}
	
	public void launch() {
		try {
			serverSocket = new ServerSocket(port);
			view.setLog("started");
			while(!serverSocket.isClosed()) {
				playerSocket = serverSocket.accept();
				players.put(playerId, new PlayerData(new ObjectOutputStream(playerSocket.getOutputStream())));
				new Thread(new Player(playerId++, handler, playerSocket, view)).start();
			}
			serverSocket.close();
		} catch (IOException e) {
			view.setLog("unable to create server on specific port");
		}
	}
	
}  