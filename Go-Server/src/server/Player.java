package server;

public class Player {
	
	private final int id;

	private boolean connected = true;
	
	public Player(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}