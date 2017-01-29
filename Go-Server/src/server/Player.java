package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Player {
	
	private final int id;

	private int enemyId;
	private boolean connected = true;
	
	private Phase currentPhase;

	public Player(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getEnemyId() {
		return enemyId;
	}

	public void setEnemyId(int enemyId) {
		this.enemyId = enemyId;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}