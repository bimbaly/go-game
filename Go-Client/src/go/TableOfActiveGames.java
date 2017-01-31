package go;

import javax.swing.JTable;

public class TableOfActiveGames extends JTable {
	
	static String[] columnHeaders = {"Game ID", "Color", "Size"};
	static String[][] dummyData = {{"Playing", "Ricky", "Julian"}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}, {"Waiting", "Bubbles", ""}};
	
	public TableOfActiveGames() {
		super(dummyData, columnHeaders);

	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
