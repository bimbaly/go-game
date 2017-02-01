package go;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Client implements ActionListener {
	
	private Connection connection;
	private JButton createBtn, joinBtn, refreshBtn;
	private TableOfActiveGames table;
	
	public void initialize() {
		createBtn = new JButton();
		createBtn.addActionListener(this);
		joinBtn = new JButton();
		joinBtn.addActionListener(this);
		refreshBtn = new JButton();
		table = new TableOfActiveGames();
		refreshBtn.addActionListener(this);
		new ClientWindow(createBtn, joinBtn, refreshBtn, table);
		do {
			ConnectDialog connectionSettings = new ConnectDialog();
			try {
				connection = new Connection(connectionSettings.getIp(), connectionSettings.getPort());
				//connection.send("connect");
			} catch (IOException e) {
				//dialog for invalid data
				JOptionPane.showMessageDialog(null, "Unable to connect", "Connection error", JOptionPane.ERROR_MESSAGE);
			}
		} while (connection == null);
	}
	
	private void refresh() {
		connection.send("refresh");
		String game;
		try {
			while((game = connection.readInput()) != "done") {
				System.out.println(game);
			}
		} catch (IOException e) {
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		if (connection == null) {
//			return;
//		}
		if (e.getSource() == createBtn) {
			NewGameDialog newGameSettings = new NewGameDialog();
			System.out.println("create " + newGameSettings.getSize() + " " + newGameSettings.getColorIndex());
			//new Game(newGameSettings.getSize(), newGameSettings.getColorIndex());
		} else if (e.getSource() == joinBtn) {
			System.out.println("join");
		} else if (e.getSource() == refreshBtn) {
			System.out.println("refresh");
			refresh();
		}
	}
}
