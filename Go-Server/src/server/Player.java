package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class Player implements Runnable {
	
	int id;
	Protocol protocol;
	private Socket socket;
	private View view;
	private BufferedReader input;
	
	public Player(int id, Socket socket, List<Player> players, View view) {
		this.id = id;
		this.socket = socket;
		this.view = view;
		protocol = new Protocol(id, socket, players);
	}
		
	public void run() {
		view.setLog(id + ": connected");
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String inputMsg;
			try {
				while((inputMsg = input.readLine()) != null) {
					protocol.process(inputMsg);
					//view.setLog(id + " : " + inputMsg);
				}
			} catch (IOException e1) {
				view.setLog(id + ": disconnected");
			}
		} catch (IOException e) {
			view.setLog(id + ": unable to initialize streams");
		}		
	}
}
