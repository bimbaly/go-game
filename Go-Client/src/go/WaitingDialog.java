package go;

import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class WaitingDialog {
	
	private Connection connection;
	private JOptionPane cancel;
	private	Object[] options = {"cancel"};

	public WaitingDialog(Connection connection) {
		this.connection = connection;
		cancel = new JOptionPane("waiting for oponent...", 
				JOptionPane.PLAIN_MESSAGE, 
				JOptionPane.DEFAULT_OPTION, 
				null, options, options[0]);
	}
	
	public void startDialog() {
//		String input;
//		try {
//			do {
//				input = connection.readInput();
//			} while (!input.equals("found") || cancel.getValue().equals(options[0]));
//			return true;
//			//gameThread = new Thread(new Game(newGameSettings.getSize(), newGameSettings.getColorIndex(), connection));
//			//gameThread.start();
//		} catch (IOException e) {
//			connection.close();
//			return false;
//			//establishConnection();
//		}
	}
	
	public JDialog getDialog() {
		return this.cancel.createDialog(null, "loading");
	}
	
	public boolean isShowing() {
		return cancel.isShowing();
	}
}
