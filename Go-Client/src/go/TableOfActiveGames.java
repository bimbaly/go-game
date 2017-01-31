package go;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class TableOfActiveGames extends JTable {
	
	private DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer();
	
	static String[] columnHeaders = {"Game ID", "Color", "Size"};
	static String[][] dummyData = {{"0", "Black", "19 x 19"}, {"1", "Black", "19 x 19"}, {"2", "Black", "19 x 19"}, {"3", "Black", "13 x 13"}, {"4", "Black", "13 x 13"}, {"5", "White", "13 x 13"}, {"6", "White", "13 x 13"}, {"7", "White", "9 x 9"}, {"8", "White", "9 x 9"}, {"9", "White", "9 x 9"}, {"10", "White", "9 x 9"}};
	
	public TableOfActiveGames() {
		super(dummyData, columnHeaders);
		setShowVerticalLines(false);
//		setDefaultRenderer(getColumnClass(2), new customTableCellRenderer());
		getColumnModel().getColumn(2).setCellRenderer(customRenderer);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public class customTableCellRenderer extends DefaultTableCellRenderer {

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
	        setBorder(noFocusBorder);
	        setHorizontalAlignment(CENTER);
	        return this;
	    }

	}
	
}
