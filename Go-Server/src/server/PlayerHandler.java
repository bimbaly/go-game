package server;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class PlayerHandler {
	
	private Map<Integer, PlayerData> players;
	private Map<Integer, String> games;
	
	public PlayerHandler(Map<Integer, PlayerData> players, Map<Integer, String> games) {
		this.players = players;
		this.games = games;
	}
	
	public boolean getStatus(int id) {
		return players.get(id).getStatus();
	}
	
	public void setStatus(int id, boolean status) {
		players.get(id).setStatus(status);
	}
	
	public ObjectOutputStream getOutput(int id) {
		return players.get(id).getOutput();
	}
	
	public ArrayList<Integer> getKeys() {
		ArrayList<Integer> keys = new ArrayList<Integer>();
		for (Integer key : players.keySet()) {
			keys.add(key);
		}
		return keys;
	}
	public Map<Integer, String> getGames() {
		return games;
	}
	
	public void addGame(String game, int gameId) {
		games.put(gameId, game);
	}
	
	public void removeGame(int gameId) {
		games.remove(gameId);
	}
}
