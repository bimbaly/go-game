package server;

import java.io.IOException;

public class Phase {
	
	private int id;
	private Player player;
	private PlayerDataHandler handler;
	
	public Phase(Player player, PlayerDataHandler handler) {
		this.player = player;
		this.handler = handler;
		id = player.getId();
	}
	
	public void process(String input) throws IOException {
		
		String[] inputArray = input.split("/");
		
		switch (inputArray[0]) {
		
		case "connect":
			//send active games to just connected player
			loadGames();
			break;
		
		case "disconnect":	
			//close connection, remove player and end his thread
			break;
			
		case "refresh":
			loadGames();
			handler.send("done", id);
			break;
			
		case "create":
			//create "game" entry in games HashMap, set "busy" to true and refresh games to other players
			handler.addGame(inputArray[1] + "/" + inputArray[2], id);
			handler.setBusy(id, true);
			handler.sendToAll("game/" + id + "/" + handler.getGames().get(id), id);
			break;
			
		case "join":
			//check if host is still not busy, remove game from HasMap, set enemy ID
			int enemyId = Integer.parseInt(inputArray[1]);
			if(handler.isBusy(enemyId)) {
				handler.setBusy(enemyId, false);
				handler.sendToAllExcept("remove_game/" + enemyId + "/" + handler.getGames().get(enemyId), id, enemyId);
				handler.setEnemyId(id, enemyId);
				handler.setEnemyId(enemyId, id);
				handler.setPlaying(id, true);
				handler.setPlaying(enemyId, true);
				handler.removeGame(enemyId);
				handler.send("found", enemyId);
				handler.send("allow", id);
			} else {
				handler.send("busy", id);
				loadGames();
			}			
			break;
			
		case "bot":
			//bot phase
			break;
			
		case "move":
			handler.send(inputArray[1], handler.getEnemyId(id));
			break;
			
		case "surrender": 
			handler.send("surrender", handler.getEnemyId(id));
			break;
			
		case "pass": 
			handler.send("pass", handler.getEnemyId(id));
			break;
			
		case "count": 
			handler.send("count", handler.getEnemyId(id));
			break;
			
		default: break;
		}
	}
	
	private void loadGames() throws IOException {
		if(!handler.getGames().isEmpty()) {
			for (Integer key : handler.getGames().keySet()) {
				if (key != id)
					handler.send("game/" + key + "/" + handler.getGames().get(key), id);
			}
		} else {
			handler.send("no games found", id);
		}
	}
}