package server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
	private PlayerDataHandler data;
	
	public Server (int port) {
		this.port = port;
		view = new View();
		players = Collections.synchronizedMap(new HashMap<Integer, PlayerData>());
		games = Collections.synchronizedMap(new HashMap<Integer, String>());
		data = new PlayerDataHandler(players, games);
	}
	
	public void launch() {
		try {
			serverSocket = new ServerSocket(port);
			view.setLog("started");
			while(true) {
				playerSocket = serverSocket.accept();
				players.put(playerId, new PlayerData(new PrintWriter(playerSocket.getOutputStream(), true)));
				new Thread(new PlayerThread(playerId++, data, playerSocket, view)).start();
			}
		} catch (IOException e) {
			view.setLog("unable to create server on specific port");
		}
	}
}  