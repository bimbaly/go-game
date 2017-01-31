package server;

import java.io.PrintWriter;

public class PlayerData {
	
	private final PrintWriter output;
	private boolean busy = false;
	private boolean playing = false;
	private int enemyId;
	
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

	public int getEnemyId() {
		return enemyId;
	}

	public void setEnemyId(int enemyId) {
		this.enemyId = enemyId;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
}