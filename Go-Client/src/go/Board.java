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
	
	public Map<Integer, Stone> getStones() {
		return stones;
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
		updateBoard(index);
		
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
		
		
//		//print modified group
//		for (Stone s : groups.get(newGroupIndex)) {
//			System.out.print(s.toString());
//		}
//		System.out.println();
		
	}
	
	private void updateBoard(int index) {

		groups: for (HashSet<Stone> g : groups.values()) {
			for (Stone s : g) {
			    if (s.getIndex() == index || hasLiberty(s.getIndex())) {
			    	continue groups;
			    }
			}
			if (g.size() == 1) {
				Stone.setLastCapturedIndex(g.iterator().next().getIndex());
			}
			for (Stone s : g) {
				System.out.println(s.toString() + " captured");
			   stones.remove(s.getIndex());
			}
			g.clear();
		}
		
	}
	
	private boolean hasLiberty(int index) {
		if (stones.get(index - size) == null && index - size > 0 || 
			stones.get(index + size) == null && index + size <= size*size || 
			stones.get(index - 1) == null && index - 1 > 0 || 
			stones.get(index + 1) == null && index + 1 <= size*size) {
			return true;
		}
		return false;
	}
	
	private boolean hasLibertyAfterMove(int index, int occupiedIndex) {
		if (stones.get(index - size) == null && index - size > 0 && index - size != occupiedIndex || 
				stones.get(index + size) == null && index + size <= size*size && index + size != occupiedIndex || 
				stones.get(index - 1) == null && index - 1 > 0 && index - 1 != occupiedIndex || 
				stones.get(index + 1) == null && index + 1 <= size*size && index + 1 != occupiedIndex) {
				return true;
			}
			return false;
	}
	
	
	private boolean areStonesCaptured(int index, int opponentIndex, StoneColor color) {
		
		if (stones.get(opponentIndex) != null && stones.get(opponentIndex).getColor() == color) {
			return false;
		}
		int opponentGroup = stones.get(opponentIndex).getGroup();
		
		
		for (Stone s : groups.get(opponentGroup)) {
			if (hasLibertyAfterMove(s.getIndex(), index)) {
				return false;
			}
		}
		
		//KO
		if (groups.get(opponentGroup).size() == 1 && index == Stone.getLastCapturedIndex()) {
			System.out.println("ILLEGAL - KO rule");
			return false;
		}
		
		System.out.println("LEGAL - capturing group");
		return true;
	}
	
	private boolean areFriendsAlive(int index, int neighbourIndex, StoneColor color) {
		
		System.out.println("testing " + neighbourIndex);
		
		if (stones.get(neighbourIndex) != null && stones.get(neighbourIndex).getColor() != color) {
			System.out.println(stones.get(neighbourIndex).getColor());
			return false;
		}
		int friendGroup = stones.get(neighbourIndex).getGroup();
		System.out.println("testing group " + friendGroup);
		
		for (Stone s : groups.get(friendGroup)) {
			if (hasLibertyAfterMove(s.getIndex(), index)) {
				System.out.println("LEGAL - group alive");
				return true;
			}
		}
		
//		System.out.println("illegal - suicide move");
		return false;
	}
	
	public boolean isMovelegal(int row, int col, StoneColor color) {
		
		PlayerColor = color;
		int index = row + 1 + col * size;
		
		System.out.print(row + "x" + col + " ");
		
		if (!(index > 0 && index <= size*size)) {
			System.out.println("outside the board");
			return false;
		}
		
		if (stones.get(index) != null) {
			System.out.println("ILLEGAL - occupied");
			return false;
		}
		
		if (hasLiberty(index)) {
			System.out.println("LEGAL - at least one liberty");
			return true;
		}
		
		if (areStonesCaptured(index, index - size, color) ||
			areStonesCaptured(index, index + size, color) ||
			areStonesCaptured(index, index - 1, color) ||
			areStonesCaptured(index, index + 1, color)) {
			return true;
		} else if (areFriendsAlive(index, index - size, color) ||
				areFriendsAlive(index, index + size, color) ||
				areFriendsAlive(index, index - 1, color) ||
				areFriendsAlive(index, index + 1, color)) {
			return true;
		} else {
			System.out.println("ILLEGAL - suicide move");
			return false;
		}
		
//		System.out.println("illegal - none condition true");
//		return false;
	}
	
}
