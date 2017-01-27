package server;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Phase {
	
	private final int id;
	private PlayerHandler handler;
	
	public Phase(int id, PlayerHandler handler) {
		this.id = id;
		this.handler = handler;
	}
	
	public void send(String msg) throws IOException {
		handler.getOutput(id).writeObject(msg);
		handler.getOutput(id).flush();
	}
	
	public void sendToEnemy(String msg, int enemyId) throws IOException {
		handler.getOutput(enemyId).writeObject(msg);
		handler.getOutput(enemyId).flush();
	}
	
	public synchronized void sendToAll(String msg) throws IOException {
		ArrayList<Integer> keys = handler.getKeys();
		for(int key : keys) {
			if(key != id) {
				handler.getOutput(key).writeObject(msg);
				handler.getOutput(key).flush();
			}
		}
	}
	public void sendToAllExcept(String msg, int exceptId) throws IOException {
		ArrayList<Integer> keys = handler.getKeys();
		for(int key : keys) {
			if(key != id && key != exceptId) {
				handler.getOutput(key).writeObject(msg);
				handler.getOutput(key).flush();
			}
		}
	}
	
	public void sendGames() throws IOException {
		handler.getOutput(id).writeObject(handler.getGames());
		handler.getOutput(id).flush();
	}
	
	public abstract boolean process(String input) throws IOException;
}
