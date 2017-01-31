package server;

import java.io.IOException;
import java.util.Map;

public class PlayerDataHandler {
	
	private Map<Integer, PlayerData> players;
	private Map<Integer, String> games;
	
	public PlayerDataHandler(Map<Integer, PlayerData> players, Map<Integer, String> games) {
		this.players = players;
		this.games = games;
	}
	
	public void send(String out, int id) throws IOException {
		players.get(id).getOutput().println(out);
	}
	
	public void sendToAll(String out, int id) throws IOException {
		for (Integer key : players.keySet()) {
			if (key != id)
				send(out, key);
		}
	}
	
	public void sendToAllExcept(String out, int id, int enemyId) throws IOException {
		for (Integer key : players.keySet()) {
			if (key != id && key != enemyId)
				send(out, key);
		}
	}
	
	public int getEnemyId(int id) {
		return players.get(id).getEnemyId();
	}
	
	public void setEnemyId(int id, int enemyId) {
		players.get(id).setEnemyId(enemyId);
	}
	
	public boolean isPlaying(int id) {
		return players.get(id).isPlaying();
	}
	
	public void setPlaying(int id, boolean playing) {
		players.get(id).setPlaying(playing);
	}
	
	public boolean isBusy(int id) {
		return players.get(id).isBusy();
	}
	
	public void setBusy(int id, boolean busy) {
		players.get(id).setBusy(busy);
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
	
	public synchronized void removePlayer(int id) {
		players.remove(id);
	}
}