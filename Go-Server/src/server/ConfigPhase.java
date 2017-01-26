package server;

import java.io.IOException;

public class ConfigPhase implements Phase {
	
	private Connection connection;
	private Phase phase;
	
	public ConfigPhase(Connection connection, Phase phase) {
		this.connection = connection;
	}

	public boolean process(String input) {
		String[] inputDataArray = input.split("/");
		switch (inputDataArray[0]) {
		case "create":
			String output = "game/" + inputDataArray[1] + inputDataArray[2] + inputDataArray[3];
			try {
				connection.sendToAll(output);
				//phase = new PlayingPhase();
				return true;
			} catch (IOException e) {
				return false;
			}
		
		case "join":
			
			phase = new PlayingPhase();
			return true;
			
		case "bot":
			
			phase = new BotPhase();
			return true;
			
		default: 
			return false;
		}
	}
}
