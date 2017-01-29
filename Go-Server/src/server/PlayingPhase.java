package server;

import java.io.IOException;

public class PlayingPhase implements Phase {
	
	private final int id;
	private final int enemyId;
	private PlayerHandler handler;

	public PlayingPhase(int id, PlayerHandler handler) {
		this.id = id;
		this.handler = handler;
		enemyId = handler.getData(id).getEnemyId();
	}

	public boolean process(String input) throws IOException {
		
		String[] inputDataArray = input.split("/");
		switch (inputDataArray[0]) {
		case "move":
			handler.send(inputDataArray[1], enemyId);
			return false;
			
		case "pass": 
			handler.send("pass/", enemyId);
			return false;
		
		case "count":
			handler.send("count/", enemyId);
			return true;
			
		case "exit":
			
			return false;
			
		case "surrender": 
			
			handler.send("win/", enemyId);
			return false;
			
		default: return false;
		}
	}
}