package server;

import java.io.IOException;

public class ConfigPhase implements Phase {
	
	private final int id;
	private PlayerHandler handler;
	
	public ConfigPhase(int id, PlayerHandler handler) {
		this.id = id;
		this.handler = handler;
	}
	
	public boolean process(String input) throws IOException {
		String[] inputDataArray = input.split("/");
		
		switch (inputDataArray[0]) {
			case "connect":
				//sendGames(id);
				return false;
				
			case "create":
				handler.addGame(inputDataArray[1] + "/" + inputDataArray[2], id);
				handler.getData(id).setGameStatus(true);
				//sendGamesToAll(id);
				handler.send("start/", id);
				return true;
			
			case "join":
				int enemyId = Integer.parseInt(inputDataArray[1]);
				if(handler.getData(enemyId).getGameStatus()) {
					handler.getData(enemyId).setGameStatus(false);
					handler.removeGame(enemyId);
					handler.getData(id).setEnemyId(enemyId);
					handler.getData(enemyId).setEnemyId(id);
					handler.send("start/", id);
					//sendGamesToAll(id);
					return true;
				} else {
					handler.send("busy/", id);
					//sendGames(id);
					return false;
				}
				
			default: 
				return false;
		}
	}
}
