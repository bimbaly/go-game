package go;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Board {
	
	private int size;
//	private StoneColor playerColor, opponentColor;
	
	private Map<Integer, Stone> stones = new HashMap<Integer, Stone>();
	private Map<Integer, HashSet<Stone>> groups = new HashMap<Integer, HashSet<Stone>>();
	
	private boolean debug = false;
	
	public Board(int size, StoneColor playerColor, StoneColor opponentColor) {
		
		this.size = size;
//		this.playerColor = playerColor;
//		this.opponentColor = opponentColor;
		
	}
	
	public Map<Integer, Stone> getStones() {
		return stones;
	}
	
	public Stone getStone(int row, int col) {
		int index = row + 1 + col * size;
		return stones.get(index);
	}
	
	public void addStone(int index, StoneColor color) {
		
		Stone stone = new Stone(color, index);
		
		stones.put(index, stone);
		groups.put(stone.getGroup(), new HashSet<Stone>());
		groups.get(stone.getGroup()).add(stone);
		
		connectWithNeighbours(index, color);
		updateBoard(index);
		
	}
	
private void connectWithNeighbours(int index, StoneColor color) {
		
		if (isOccupied(index - size) && stones.get(index - size).getColor() == color) {		//up
			int neighbourGroup = stones.get(index - size).getGroup();
			mergeGroups(neighbourGroup, stones.get(index).getGroup());
		}
		if (isOccupied(index + size) && stones.get(index + size).getColor() == color) {		//down
			int neighbourGroup = stones.get(index + size).getGroup();
			mergeGroups(neighbourGroup, stones.get(index).getGroup());
		}
		if (isOccupied(index - 1) && stones.get(index - 1).getColor() == color) {			//left
			if (index % size == 1 && (index - 1) % size == 0) {
			} else {
				int neighbourGroup = stones.get(index - 1).getGroup();
				mergeGroups(neighbourGroup, stones.get(index).getGroup());
			}
		}
		if (isOccupied(index + 1) && stones.get(index + 1).getColor() == color) {			//right
			if (index % size == 0 && (index + 1) % size == 1) {
			} else {
				int neighbourGroup = stones.get(index + 1).getGroup();
				mergeGroups(neighbourGroup, stones.get(index).getGroup());
			}
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
				Stone.increaseCapturedCounter(1, s.getColor());
				stones.remove(s.getIndex());
			}
			System.out.println("all black captured " + Stone.getCapturedCount(StoneColor.BLACK));
			System.out.println("all white captured " + Stone.getCapturedCount(StoneColor.WHITE));
			g.clear();
		}
		
	}
	
	private boolean hasLiberty(int index) {
		if (!isOutsideTheBoard(index - size) && !isOccupied(index - size) || 
			!isOutsideTheBoard(index + size) && !isOccupied(index + size) || 
			!isOutsideTheBoard(index - 1) && !isOccupied(index - 1) && (index - 1) % size != 0 || 
			!isOutsideTheBoard(index + 1) && !isOccupied(index + 1) && (index + 1) % size != 1) {
			return true;
		}
		return false;
	}
	
	private boolean hasLibertyAfterMove(int index, int occupiedIndex) {
		if (!isOutsideTheBoard(index - size) && !isOccupied(index - size) && index - size != occupiedIndex || 
			!isOutsideTheBoard(index + size) && !isOccupied(index + size) && index + size != occupiedIndex || 
			!isOutsideTheBoard(index - 1) && !isOccupied(index - 1) && (index - 1) % size != 0 && index - 1 != occupiedIndex || 
			!isOutsideTheBoard(index + 1) && !isOccupied(index + 1) && (index + 1) % size != 1 && index + 1 != occupiedIndex) {
			return true;
		}
		return false;
	}
	
	private Stone getNeighbour(int index, int shift) {
		if (shift == -1 && (index + shift) % size == 0 || shift == 1 && (index + shift) % size == 1) {	//not neighbour
			return null;
		}
		if (!isOutsideTheBoard(index + shift) && isOccupied(index + shift)) {
			return stones.get(index + shift);
		}
		return null;
	}

	private boolean areEnemiesCaptured(int index, int shift, StoneColor color) {
	
		if (getNeighbour(index, shift) == null) {
			return false;
		}
		
		Stone neighbour = getNeighbour(index, shift);
		
		if (neighbour.getColor() == color) {
			return false;
		}
		
		int enemyGroup = neighbour.getGroup();
	
	
		for (Stone s : groups.get(enemyGroup)) {
			if (hasLibertyAfterMove(s.getIndex(), index)) {
				return false;
			}
		}
		
		//KO
		if (groups.get(enemyGroup).size() == 1 && index == Stone.getLastCapturedIndex()) {
//			System.out.println("ILLEGAL - Ko rule");
			return false;
		}
		
		System.out.println("LEGAL - capturing group");
		return true;
	}
	
	private boolean areAlliesAlive(int index, int shift, StoneColor color) {
		
		if (getNeighbour(index, shift) == null) {
			return false;
		}
		
		Stone neighbour = getNeighbour(index, shift);
		
		if (neighbour.getColor() != color) {
			return false;
		}
		
		int allyGroup = neighbour.getGroup();
	
		for (Stone s : groups.get(allyGroup)) {
			if (hasLibertyAfterMove(s.getIndex(), index)) {
				System.out.println("LEGAL - group alive");
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isOutsideTheBoard(int index) {
		if (!(index > 0 && index <= size*size)) {
			return true;
		}
		return false;
	}

	private boolean isOccupied(int index) {
		if (stones.get(index) != null) {
			return true;
		}
		return false;
	}
	
	public boolean isMoveLegal(int index, StoneColor color) {
		
		if (debug)
			System.out.print(index + " ");
		
		
		if (isOutsideTheBoard(index)) {
			if (debug)
				System.out.println("ILLEGAL - outside the board");
			return false;
		}
		
		if (isOccupied(index)) {
			if (debug)
				System.out.println("ILLEGAL - occupied");
			return false;
		}
		
		if (hasLiberty(index)) {
			if (debug)
				System.out.println("LEGAL - at least one liberty");
			return true;
		}
		
		if (areEnemiesCaptured(index, -size, color) ||
			areEnemiesCaptured(index, size, color) ||
			areEnemiesCaptured(index, -1, color) ||
			areEnemiesCaptured(index, 1, color)) {
			return true;
		}
		if (areAlliesAlive(index, -size, color) ||
			areAlliesAlive(index, size, color) ||
			areAlliesAlive(index, -1, color) ||
			areAlliesAlive(index, 1, color)) {
			return true;
		} 
		
		if (debug)
			System.out.println("ILLEGAL - suicide move or ko rule");
		return false;
	}
	
}
