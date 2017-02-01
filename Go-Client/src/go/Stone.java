package go;

public class Stone {
	
	private static int groupIterator = 0;
	private StoneColor color;
	private int groupIndex;
	private int stoneIndex;
	public static int lastCapturedIndex;
	private static int lastMoveIndex;
	private static int blackCapturedCounter;
	private static int whiteCapturedCounter;
	

	public Stone(StoneColor color, int index) {
		
		this.color = color;
		this.groupIndex = groupIterator++;
		this.stoneIndex = index;
		Stone.lastMoveIndex = index;
		setLastCapturedIndex(0);	//reset ko
		
	}
	
	public static int getCapturedCount(StoneColor color) {
		
		int allCaptured = 0;
		
		if (color == StoneColor.BLACK)
			allCaptured = blackCapturedCounter;
		if (color == StoneColor.WHITE)
			allCaptured = whiteCapturedCounter;
		
		return allCaptured;
	}



	public static void increaseCapturedCounter(int currentCapturedCount, StoneColor color) {
		if (color == StoneColor.BLACK)
			Stone.blackCapturedCounter += currentCapturedCount;
		if (color == StoneColor.WHITE)
			Stone.whiteCapturedCounter += currentCapturedCount;
	}

	public static int getLastMoveIndex() {
		return lastMoveIndex;
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
