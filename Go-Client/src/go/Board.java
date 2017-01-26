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
	
	private void addStone(int row, int col) {
		
		int index = row + 1 + col * size;
		
		Stone stone = new Stone(StoneColor.BLACK);
		
		stones.put(index, stone);
		groups.put(stone.getGroup(), new HashSet<Stone>());
		
		connectWithNeighbours(index);
		updateBoard();
		
	}
	
	private void connectWithNeighbours(int index) {
		
		if (stones.get(index - size).getColor() == PlayerColor) {		//up
			int neighbourGroup = stones.get(index - size).getGroup();
			mergeGroups(neighbourGroup, stones.get(index).getGroup());
		}
		if (stones.get(index + size).getColor() == PlayerColor) {		//down
			int neighbourGroup = stones.get(index + size).getGroup();
			mergeGroups(neighbourGroup, stones.get(index).getGroup());
		}
		if (stones.get(index - 1).getColor() == PlayerColor) {			//left
			int neighbourGroup = stones.get(index - 1).getGroup();
			mergeGroups(neighbourGroup, stones.get(index).getGroup());
		}
		if (stones.get(index + 1).getColor() == PlayerColor) {			//right
			int neighbourGroup = stones.get(index + 1).getGroup();
			mergeGroups(neighbourGroup, stones.get(index).getGroup());
		}
		
	}
	
	private void mergeGroups(int oldGroupIndex, int newGroupIndex) {
		
		groups.get(newGroupIndex).addAll(groups.get(oldGroupIndex));
		groups.remove(oldGroupIndex);
		
	}
	
	private void updateBoard() {

		for (HashSet<Stone> group : groups.values()) {
			for (Stone s : group) {
			    System.out.println(s);
			}
			System.out.println();
		}
		
	}
	
}
