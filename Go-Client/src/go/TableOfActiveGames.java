package go;

import javax.swing.JTable;

public class TableOfActiveGames extends JTable {
	
	static String[] columnHeaders = {"Status", "Player 1", "Player 2"};
	static String[][] dummyData = {{"Playing", "Ricky", "Julian"}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}};
	
	public TableOfActiveGames() {
		super(dummyData, columnHeaders);

	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
