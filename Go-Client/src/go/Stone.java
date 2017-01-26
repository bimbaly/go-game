package go;

public class Stone {
	
	private static int groupIterator = 0;
	private StoneColor color;
	private int groupIndex;
	private int stoneIndex;
	
	public Stone(StoneColor color, int index) {
		
		this.color = color;
		this.groupIndex = groupIterator++;
		this.stoneIndex = index;
		
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
		return "[" + stoneIndex + " " + color + " " + groupIndex + "] ";
	}
	
}
