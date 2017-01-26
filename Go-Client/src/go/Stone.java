package go;

public class Stone {
	
	private static int groupIterator = 0;
	private int groupIndex;
	private int stoneIndex;
	private StoneColor color;
	
	public Stone(StoneColor color) {
		
		this.groupIndex = groupIterator++;
		this.color = color;
	}

	
	public StoneColor getColor() {
		return color;
	}


	public int getIndex() {
		return stoneIndex;
	}
	
	
	public int getGroup() {
		return groupIndex;
	}
	
	public void setGroup(int group) {
		this.groupIndex = group;
	}
	


	public String toString() {
		return "[" + stoneIndex + " " + color + "] ";
	}
	
}
