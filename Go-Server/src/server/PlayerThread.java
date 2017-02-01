package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PlayerThread implements Runnable {
	
	private View view;
	private Player player;
	private Socket socket;
	private Phase phase;
	private PlayerDataHandler handler;
	
	public PlayerThread(int id, PlayerDataHandler handler, Socket socket, View view) throws IOException {
		this.socket = socket;
		this.view = view;
		this.handler = handler;
		player = new Player(id);
		phase = new Phase(player, handler);
	}

	public void run() {
		view.setLog("thread " + player.getId() + " connected");
		String input;
		try {
			while(player.isConnected() && (input = getInput()) != null) {
				view.setLog(input);
				phase.process(input);
			}
		} catch (IOException e) {
			close();
		} finally {
			if(!player.isConnected())
				close();
			view.setLog("thread " + player.getId() + " disconnected");
		}
	}
	
	private String getInput() throws IOException {
		return new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
	}
	
	private void close() {
		try {
			socket.close();
		} catch (IOException e) {
			view.setLog("unable to close socket");
		} finally {
			if (handler.isPlaying(player.getId()))
				try {
					handler.send("left", handler.getEnemyId(player.getId()));
				} catch (IOException e) {
					view.setLog("unable to send leave command");
				}	
		}
	}
}