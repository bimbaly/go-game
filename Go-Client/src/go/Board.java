package go;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Board {
	
	private int size;
	private StoneColor PlayerColor = StoneColor.BLACK;

	private Map<Integer, Stone> stones = new HashMap<Integer, Stone>();
	private Map<Integer, HashSet<Stone>> groups = new HashMap<Integer, HashSet<Stone>>();
	
	public Board(int size) {
		
		this.size = size;
		
	}
	
	public Stone getStone(int row, int col) {
		
		int index = row + 1 + col * size;
		return stones.get(index);
		
	}
	
	public void addStone(int row, int col, StoneColor color) {
		
		PlayerColor = color;
		
		int index = row + 1 + col * size;
		
		Stone stone = new Stone(color, index);
		
		stones.put(index, stone);
		groups.put(stone.getGroup(), new HashSet<Stone>());
		groups.get(stone.getGroup()).add(stone);
		
		connectWithNeighbours(index);
		updateBoard();
		
	}
	
	private void connectWithNeighbours(int index) {
		
		if (stones.get(index - size) != null && stones.get(index - size).getColor() == PlayerColor) {		//up
			int neighbourGroup = stones.get(index - size).getGroup();
			mergeGroups(neighbourGroup, stones.get(index).getGroup());
		}
		if (stones.get(index + size) != null && stones.get(index + size).getColor() == PlayerColor) {		//down
			int neighbourGroup = stones.get(index + size).getGroup();
			mergeGroups(neighbourGroup, stones.get(index).getGroup());
		}
		if (stones.get(index - 1) != null && stones.get(index - 1).getColor() == PlayerColor) {			//left
			int neighbourGroup = stones.get(index - 1).getGroup();
			mergeGroups(neighbourGroup, stones.get(index).getGroup());
		}
		if (stones.get(index + 1) != null && stones.get(index + 1).getColor() == PlayerColor) {			//right
			int neighbourGroup = stones.get(index + 1).getGroup();
			mergeGroups(neighbourGroup, stones.get(index).getGroup());
		}
		
	}
	
	private void mergeGroups(int oldGroupIndex, int newGroupIndex) {
		
		if (oldGroupIndex == newGroupIndex)
			return;
		
		for (Stone s : groups.get(oldGroupIndex)) {
			s.setGroup(newGroupIndex);
		}
		
		groups.get(newGroupIndex).addAll(groups.get(oldGroupIndex));
		groups.remove(oldGroupIndex);
		
		
		//print modified group
		for (Stone s : groups.get(newGroupIndex)) {
			System.out.print(s.toString());
		}
		System.out.println();
		
	}
	
	private void updateBoard() {

		groups: for (HashSet<Stone> g : groups.values()) {
			for (Stone s : g) {
			    if (searchLiberty(s.getIndex())) {
			    	continue groups;
			    }
			}
			//group captured
			
//			for (Stone s : g) {
//			   stones.remove(s.getIndex());
//			}
//			g.clear();
		}
		
	}
	
	private boolean searchLiberty(int index) {
		if (stones.get(index - size) == null || stones.get(index + size) == null || stones.get(index - 1) == null || stones.get(index + 1) == null) {
			return true;
		}
		return false;
	}
	
	private boolean searchLibertyOpponent(int index, int occupiedIndex) {
		if ((stones.get(index - size) == null && index - size != occupiedIndex) || 
			(stones.get(index + size) == null && index + size != occupiedIndex) || 
			(stones.get(index - 1) == null && index - 1 != occupiedIndex) || 
			(stones.get(index + 1) == null && index + 1 != occupiedIndex)) {
			return true;
		}
		return false;
	}
	
	public boolean checkIfLegal(int row, int col, StoneColor color) {
		
		PlayerColor = color;
		int index = row + 1 + col * size;
		
		if (stones.get(index) != null) {
			System.out.println("illegal - OCCUPIED");
			return false;
		}
		
		
		if (searchLiberty(index)) {
			System.out.println("legal - at least one liberty");
			return true;
		}
		up: if (stones.get(index - size).getColor() != color) {
			int opponentGroup = stones.get(index - size).getGroup();
			for (Stone s : groups.get(opponentGroup)) {
				if (searchLibertyOpponent(s.getIndex(), index)) {
					break up;
				}
			}
			System.out.println("legal - killing group above");
			return true;
		}
		up: if (stones.get(index + size).getColor() != color) {
			int opponentGroup = stones.get(index + size).getGroup();
			for (Stone s : groups.get(opponentGroup)) {
				if (searchLibertyOpponent(s.getIndex(), index)) {
					break up;
				}
			}
			System.out.println("legal - killing group under");
			return true;
		}
		up: if (stones.get(index - 1).getColor() != color) {
			int opponentGroup = stones.get(index - 1).getGroup();
			for (Stone s : groups.get(opponentGroup)) {
				if (searchLibertyOpponent(s.getIndex(), index)) {
					break up;
				}
			}
			System.out.println("legal - killing group on the left");
			return true;
		}
		up: if (stones.get(index + 1).getColor() != color) {
			int opponentGroup = stones.get(index + 1).getGroup();
			for (Stone s : groups.get(opponentGroup)) {
				if (searchLibertyOpponent(s.getIndex(), index)) {
					break up;
				}
			}
			System.out.println("legal - killing group on the right");
			return true;
		}
		
		
		
		
		System.out.println("illegal - none condition true");
		return false;
	}
	
}
