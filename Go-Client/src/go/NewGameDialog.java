package go;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class NewGameDialog {

	private String[] gameModeList = {"Two Player", "Single Player"};
	private String[] boardSizeList = {"9 x 9", "13 x 13", "19 x 19"};
	private int[] boardSizeValue = {9, 13, 19};
	
	private int mode, size, colorIndex;
	private boolean isCancelled;
	
	public NewGameDialog() {
		
		String[] playAsList = {"Black", "White"};
		
		JComboBox<String> gameMode = new JComboBox<String>(gameModeList);
		JComboBox<String> boardSize = new JComboBox<String>(boardSizeList);
		JComboBox<String> playAs = new JComboBox<String>(playAsList);
		Object[] message = {
		    "Game mode:", gameMode,
		    "Board size:", boardSize,
		    "Play as:", playAs
		};
	
		
		int option = JOptionPane.showConfirmDialog(null, message, "New Game Setup", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			
			mode = gameMode.getSelectedIndex();
			size = boardSizeValue[boardSize.getSelectedIndex()];
			colorIndex = playAs.getSelectedIndex();
			
//			System.out.println("Game mode: " + gameMode.getSelectedIndex());
//			System.out.println("Board size: " + boardSize.getSelectedIndex());
//			System.out.println("Play as: " + playAs.getSelectedIndex());

		} else {
			isCancelled = true;
//		    System.out.println("New game canceled");
		}
		
	}

	public boolean isCancelled() {
		return isCancelled;
	}
	
	public int getMode() {
		return mode;
	}

	public int getSize() {
		return size;
	}

	public int getColorIndex() {
		return colorIndex;
	}
	
}
