package go;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TableOfActiveGames extends JTable {
	
	private DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer();
	private DefaultTableModel tableModel = new DefaultTableModel();
	
	private static String[] columnHeaders = {"ID", "Size", "Color"};
	
	public TableOfActiveGames() {
//		super();
//		setShowHorizontalLines(true);
		setShowGrid(false);
		setDefaultRenderer(Object.class, new customTableCellRenderer());
		setFont(new Font("Tahoma", Font.PLAIN, 14));
		setRowHeight(22);
		setBackground(new Color(84, 77, 71));
		setForeground(new Color(241, 240, 239));
		setFillsViewportHeight(true);
		setSelectionBackground(new Color(48, 44, 40));
		setSelectionForeground(new Color(241, 240, 239));
		tableModel.setColumnIdentifiers(columnHeaders);
		setModel(tableModel);
		
		Object[] x = {"0", "0", "19"};
		Object[] y = {"0", "1", "19"};
		tableModel.addRow(x); 
		tableModel.addRow(y); 
		tableModel.addRow(x); 
		tableModel.addRow(x); 
	}

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public class customTableCellRenderer extends DefaultTableCellRenderer {

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
	        
	        if (column == 1) {
		        if (Integer.parseInt((String) table.getModel().getValueAt(row, 1)) == 0) {
		        	setText("Black");
		        }
		        if (Integer.parseInt((String) table.getModel().getValueAt(row, 1)) == 1) {
		        	setText("White");
		        }
	        }
	        
	        setBorder(noFocusBorder);
	        return this;
	    }

	}
	
}
