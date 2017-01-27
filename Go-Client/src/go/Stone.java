package go;

public class Stone {
	
	private static int groupIterator = 0;
	private StoneColor color;
	private int groupIndex;
	private int stoneIndex;
	public static int lastCapturedIndex;
	
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
	
	public static int getLastCapturedIndex() {
		return lastCapturedIndex;
	}

	public static void setLastCapturedIndex(int lastCapturedIndex) {
		Stone.lastCapturedIndex = lastCapturedIndex;
	}

	public String toString() {
		return "[" + stoneIndex + " " + color + " " + groupIndex + "] ";
	}
	
}
