package server;

import java.io.IOException;

public class ConfigPhase implements Phase {
	
	private Player player;
	private PlayerDataHandler handler;
	
	public ConfigPhase(Player player, PlayerDataHandler handler) {
		this.player = player;
		this.handler = handler;
	}
	
	public boolean process(String input) throws IOException {
		
		String[] splittedInput = input.split("/");
		
		switch (splittedInput[0]) {
		case "connected": 
			
		default: return false;
		}
	}
}