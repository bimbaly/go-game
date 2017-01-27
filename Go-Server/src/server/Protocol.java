package server;

import java.io.IOException;

public class Protocol {
	
	private Phase[] currentPhase;
	private int currentPhaseNumber = 0;
	
	public Protocol(int playerId, PlayerHandler handler) {
		currentPhase = new Phase[4];
		currentPhase[0] = new ConfigPhase(playerId, handler);
		currentPhase[1] = new PlayingPhase(playerId, handler);
	}
	
	public boolean process(String input) throws IOException {
		if (currentPhase[currentPhaseNumber].process(input))
			return true;
		else
			return false;
	}

	public void nextPhase() {
		currentPhaseNumber++;
	}
}
