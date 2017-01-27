package server;

import java.io.ObjectOutputStream;

public class PlayerData {
	
	private boolean gameStatus = false;
	private boolean winner = false;
	private boolean looser = false;
	private int enemyId;
	
	private final ObjectOutputStream output;
	
	public PlayerData(ObjectOutputStream output) {
		this.output = output;
	}

	public ObjectOutputStream getOutput() {
		return output;
	}
	
	public int getEnemyId() {
		return enemyId;
	}

	public void setEnemyId(int enemyId) {
		this.enemyId = enemyId;
	}
	
	public boolean getGameStatus() {
		return gameStatus;
	}
	
	public void setGameStatus(boolean gameStatus) {
		this.gameStatus = gameStatus;
	}
	
	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public boolean isLooser() {
		return looser;
	}

	public void setLooser(boolean looser) {
		this.looser = looser;
	}
}