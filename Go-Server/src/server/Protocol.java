package server;

public class Protocol {
	
	private Phase phase;
	
	public Protocol(Connection connection) {
		phase = new ConfigPhase(connection, phase);
	}
	
	public boolean process(String input) {
		return phase.process(input);
	}
}
