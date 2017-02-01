package go;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class MainGUI implements ActionListener {
	
	private JFrame frame;
	private JButton createBtn, joinBtn, refreshBtn;
	private TableOfActiveGames table;
	private Connection connection;
	private Thread gameThread;
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
		frame.setSize(480, 480);
		frame.setMinimumSize(new Dimension(320, 240));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BorderLayout(5, 5));
		frame.getContentPane().add(new JScrollPane(table = new TableOfActiveGames()), BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		createBtn = new JButton("NEW GAME");
		createBtn.addActionListener(this);
		joinBtn = new JButton("JOIN GAME");
		joinBtn.addActionListener(this);
		refreshBtn = new JButton("REFRESH");
		refreshBtn.addActionListener(this);
		buttonsPanel.add(createBtn);
		buttonsPanel.add(refreshBtn);
		buttonsPanel.add(joinBtn);
		frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		frame.getContentPane().add(new JLabel("SELECT GAME:"), BorderLayout.NORTH);
		frame.pack();
	}
	
	private void establishConnection() {
//		do {
//			ConnectDialog connectionSettings = new ConnectDialog();
//			try {
//				connection = new Connection(connectionSettings.getIp(), connectionSettings.getPort());
//				System.out.println("connected");
//			} catch (IOException e) {
//				JOptionPane.showMessageDialog(null, "Unable to connect", "Connection error", JOptionPane.ERROR_MESSAGE);
//			}
//		} while (connection == null);
		
		try {																//REVERSE
			connection = new Connection("127.0.0.1", 2222);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Connection refused");
		}
		if (connection == null) {
			System.exit(0);
		}
		
		refresh();
	}
	
	//method to get active games to print it in table
	private void refresh() {
		connection.send("refresh");
		String input;
		try {
			while(!((input = connection.readInput()).equals("done"))) {
				// input contains String representation of game, eg. game/ID_OF_PLAYER/SIZE/COLOR   <--------- STRING 
				System.out.println(input);
			}
		} catch (IOException e) {
			connection.close();
			connection = null;
			establishConnection();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == createBtn) {
//			NewGameDialog newGameSettings = new NewGameDialog();
//			connection.send("create/" + newGameSettings.getSize() + "/" + newGameSettings.getColorIndex());
//			gameThread = new Thread(new Game(newGameSettings.getSize(), newGameSettings.getColorIndex(), connection));
			
			connection.send("create/13/0");																//REVERSE
			gameThread = new Thread(new Game(13, 0, connection));
			
			gameThread.start();
		} else if (e.getSource() == joinBtn) {
			connection.send("join/" + 0); // zawsze dolacza do 
//			gameThread = new Thread(new Game(SIZE, 1, connection));
			gameThread = new Thread(new Game(13, 1, connection));																//REVERSE
			gameThread.start();
		} else if (e.getSource() == refreshBtn) {
			refresh();
			System.out.println("refresh");
		}
	}
	
}

