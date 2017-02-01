package go;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MainGUI implements ActionListener, ListSelectionListener {
	
	private boolean debug = true;
	
	private JFrame frame;
	private JButton createBtn, joinBtn, refreshBtn;
	private JLabel infoLbl;
	private TableOfActiveGames table;
	private Connection connection;
	private Thread gameThread;
	
	private int joinSize, joinColorIndex, joinPlayerID;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.frame.setVisible(true);
					window.establishConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Go-Client");
		frame.setSize(380, 300);
		frame.setMinimumSize(new Dimension(320, 240));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BorderLayout(5, 5));
		table = new TableOfActiveGames();
		table.getSelectionModel().addListSelectionListener(this);
		frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		createBtn = new JButton("NEW GAME");
		createBtn.addActionListener(this);
		joinBtn = new JButton("JOIN GAME");
		joinBtn.addActionListener(this);
		joinBtn.setEnabled(false);
		refreshBtn = new JButton("REFRESH");
		refreshBtn.addActionListener(this);
		buttonsPanel.add(createBtn);
		buttonsPanel.add(refreshBtn);
		buttonsPanel.add(joinBtn);
		frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		frame.getContentPane().add(infoLbl = new JLabel(), BorderLayout.NORTH);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(frame, 
			            "Are you sure you wish to leave lobby?", "Exit?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
							connection.close();
			            	System.exit(0);
						}
			}
		});
//		frame.pack();
	}
	
	private void establishConnection() {
		
		if(debug)
			System.out.println("connecting to server...");
		
		do {
			ConnectDialog connectionSettings = new ConnectDialog();
			try {
				connection = new Connection(connectionSettings.getIp(), connectionSettings.getPort());
				
				if(debug)
					System.out.println("success!");
				
				refresh();
			} catch (IOException e) {
				
				if(debug)
					System.out.println("fail, retrying");
				
				JOptionPane.showMessageDialog(null, "Unable to connect", "Connection error", JOptionPane.ERROR_MESSAGE);
			}
		} while (connection == null);
	}
	
	//method to get active games to print it in table
	private void refresh() {
		
		if(debug)
			System.out.println("updating games list...");
		
		table.getTableModel().setRowCount(0);
		connection.send("refresh");
		String input;
		try {
			while(!(input = connection.readInput()).equals("done")) {		// input contains String representation of game, eg. game/ID_OF_PLAYER/SIZE/COLOR   <--------- STRING 
				//System.out.println(input);
				String[] array = input.split("/");
				if(array[0].equals("game")) {
					Object[] row = {array[1], array[3], array[2]};
					table.getTableModel().addRow(row);
//					System.out.println("row added " + array[1] + " " + array[3] + " " + array[2]);
				}
			}
			infoLbl.setText(Integer.toString(table.getTableModel().getRowCount()) + " games available");
			
			if(debug)
				System.out.println("success!");
			
		} catch (IOException e) {
			
			if(debug)
				System.out.println("fail, retrying");
			
			connection.close();
			connection = null;
			establishConnection();
		}
	}
	
	private void create() {
		NewGameDialog newGameSettings = new NewGameDialog();
		if (newGameSettings.isCancelled()) {
			return;
		}
		connection.send("create/" + newGameSettings.getSize() + "/" + newGameSettings.getColorIndex());
		gameThread = new Thread(new Game(newGameSettings.getSize(), newGameSettings.getColorIndex(), connection));
		gameThread.start();
		
		if(debug)
			System.out.println("game/"+ newGameSettings.getSize() + "/" + newGameSettings.getColorIndex());
	}
	
	private void join() {
		
		if(debug)
			System.out.println("joining game " + joinPlayerID);
		
		connection.send("join/" + joinPlayerID);
		String input;
		boolean isBusy = false;
		try {
			do {
				input = connection.readInput();
				if(input.equals("allow"))
					isBusy = false;
				else if(input.equals("busy"))
					isBusy = true;
			} while (!input.equals("allow") || !input.equals("busy"));
			if(isBusy) {
				if(debug)
					System.out.println("game no longer exists!");
				refresh();
			} else  {
				if(debug)
					System.out.println("success!");
				gameThread = new Thread(new Game(joinSize, joinColorIndex, connection));
				gameThread.start();
			}
		} catch (IOException e) {
			connection.close();
			establishConnection();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == createBtn) {
			create();
		} else if (e.getSource() == joinBtn) {
			join();
		} else if (e.getSource() == refreshBtn) {
			refresh();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		
//		System.out.println("row = " + table.getSelectedRow());
		
		if (table.getSelectedRow() == -1) {
			
//			System.out.println("no row is selected");
			joinBtn.setEnabled(false);
			
		} else {
			
			int selectedColorIndex = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString());
			if (selectedColorIndex == 0) {
				joinColorIndex = 1;
			} else if (selectedColorIndex == 1) {
				joinColorIndex = 0;
			}
			joinSize = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 2).toString());
			joinPlayerID = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
			
//			System.out.println("color = " + joinColorIndex);
//			System.out.println("size = " + joinSize);
//			System.out.println("id = " + joinPlayerID);
			
			joinBtn.setEnabled(true);
		}
		
	}
	
}

