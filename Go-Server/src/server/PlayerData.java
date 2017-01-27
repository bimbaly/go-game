package server;

import java.io.ObjectOutputStream;

public class PlayerData {
	
	private boolean status;
	private final ObjectOutputStream output;
	
	public PlayerData(boolean status, ObjectOutputStream output) {
		this.status = status;
		this.output = output;
	}

	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public ObjectOutputStream getOutput() {
		return output;
	}
}
