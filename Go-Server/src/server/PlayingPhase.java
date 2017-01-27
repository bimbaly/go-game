package server;

import java.io.IOException;

public class PlayingPhase extends Phase {
	
	private int enemyId;

	public PlayingPhase(int id, PlayerHandler handler) {
		super(id, handler);
		// TODO Auto-generated constructor stub
	}

	public boolean process(String input) throws IOException {
		String[] inputDataArray = input.split("/");
		switch (inputDataArray[0]) {
		case "setup":
			enemyId = Integer.parseInt(inputDataArray[1]);
			return false;
		case "move":
			sendToEnemy(inputDataArray[1], enemyId);
			return false;
			
		case "pass": 
			
			return false;
		
		case "count":
			
			return true;
			
		default: return false;
		}
	}

}
