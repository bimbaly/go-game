package server;

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
			
			phase = new PlayingPhase();
			return true;

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
