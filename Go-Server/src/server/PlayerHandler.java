package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class PlayerHandler {
	
	private Map<Integer, PlayerData> players;
	private Map<Integer, String> games;
	
	public PlayerHandler(Map<Integer, PlayerData> players, Map<Integer, String> games) {
		this.players = players;
		this.games = games;
	}
	
	public PlayerData getData(int id) {
		return players.get(id);
	}
	
	public void send(Object out, int id) throws IOException {
		players.get(id).getOutput().writeObject(out);
		players.get(id).getOutput().flush();
	}
	
	public void sendToAll(Object out, int id) throws IOException {
		for (Integer key : players.keySet()) {
			send(out, key);
		}
	}
		
	public synchronized void removePlayer(int id) {
		players.remove(id);
	}
	
	public Map<Integer, String> getGames() {
		return games;
	}
	
	public synchronized void addGame(String game, int gameId) {
		games.put(gameId, game);
	}
	
	public synchronized void removeGame(int gameId) {
		games.remove(gameId);
	}
}