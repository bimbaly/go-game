package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PlayerThread implements Runnable {
	
	private View view;
	private Player player;
	private Socket socket;
	private PlayerDataHandler handler;
	
	public PlayerThread(int id, PlayerDataHandler handler, Socket socket, View view) throws IOException {
		player = new Player(id);
		this.handler = handler;
		this.socket = socket;
		this.view = view;
	}

	public void run() {
		try (BufferedReader bufferedInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
			String input;
			while((input = bufferedInput.readLine()) != null) {
				System.out.println(input);
			}
		} catch(IOException e) {
			view.setLog("no connection");
		}
	}
}