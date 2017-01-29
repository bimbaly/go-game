package server;

import java.io.PrintWriter;

public class PlayerData {
	
	private final PrintWriter output;
	private boolean busy = true;
	
	public PlayerData(PrintWriter output) {
		this.output = output;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public PrintWriter getOutput() {
		return output;
	}
}