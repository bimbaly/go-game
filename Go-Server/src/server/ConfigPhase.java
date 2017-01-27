package server;

import java.io.IOException;

public class ConfigPhase extends Phase {
	
	private final int id;
	private PlayerHandler handler;
	
	public ConfigPhase(int id, PlayerHandler handler) {
		super(id, handler);
		this.id = id;
		this.handler = handler;
	}
	
	public boolean process(String input) throws IOException {
		String[] inputDataArray = input.split("/");
		
		switch (inputDataArray[0]) {
			case "connect":
				sendGames();
				return false;
				
			case "create":
				handler.addGame(inputDataArray[1] + "/" + inputDataArray[2], id);
				handler.setStatus(id, true);
				sendToAllExcept("game/" + id + "/" + inputDataArray[1] + "/" + inputDataArray[2], id);
				return true;
			
			case "join":
				int enemyId = Integer.parseInt(inputDataArray[1]);
				if(handler.getStatus(enemyId)) {
					handler.setStatus(enemyId, false);
					handler.removeGame(enemyId);
					//sendGames();
					sendToEnemy("found/"+ id, enemyId);
					send("allow/");
					return true;
				} else {
					//sendGames();
					return false;
				}
				
			default: 
				return false;
		}
	}
}
