package go;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class NewGameDialog {

	public NewGameDialog() {
		
		String[] gameModeList = {"Two Player", "Single Player"};
		String[] boardSizeList = {"9 x 9", "13 x 13", "19 x 19"};
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
			
			System.out.println("Game mode: " + gameMode.getSelectedIndex());
			System.out.println("Board size: " + boardSize.getSelectedIndex());
			System.out.println("Play as: " + playAs.getSelectedIndex());

		} else {
		    System.out.println("Login canceled");
		}
		
	}
	
}
