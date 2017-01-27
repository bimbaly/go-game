package server;

import java.io.IOException;
import java.net.Socket;

public class Player implements Runnable {
	
	private final int playerId;
	private View view;
	private Protocol protocol;
	private Reader reader;

	public Player(int playerId, PlayerHandler handler, Socket socket, View view) throws IOException {
		this.playerId = playerId;
		this.view = view;
		reader = new Reader(socket);
		protocol = new Protocol(playerId, handler);
	}

	public void run() {
		view.setLog(playerId + " connected");
		String input;
		while(true) {
			try {
				input = reader.getInput();
				view.setLog(input);
				if(protocol.process(input)) 
					protocol.nextPhase();
			} catch (ClassNotFoundException e) {
				break;
			} catch (IOException e) {
				
				break;
			}
		}
	}
}