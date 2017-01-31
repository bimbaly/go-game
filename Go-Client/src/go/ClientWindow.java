package go;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ClientWindow extends JFrame implements ActionListener {
	
	JButton createBtn, joinBtn, refreshBtn;
	
	public ClientWindow() {
		super("Go-Client (not connected)");
		setSize(480, 480);
		setMinimumSize(new Dimension(320, 240));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(5, 5));
		
		getContentPane().add(new JScrollPane(new TableOfActiveGames()), BorderLayout.CENTER);
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
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		getContentPane().add(new JLabel("SELECT GAME:"), BorderLayout.NORTH);
		
		pack();
		setVisible(true);
		ConnectDialog connectionSettings = new ConnectDialog();
		setTitle("Go-Client ("+connectionSettings.getIp()+":"+connectionSettings.getPort()+")");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == createBtn) {
			NewGameDialog gameSettings = new NewGameDialog();
		} else if (e.getSource() == joinBtn) {
			System.out.println("join");
		} else if (e.getSource() == refreshBtn) {
			System.out.println("refresh");
		}
		
	}

}
