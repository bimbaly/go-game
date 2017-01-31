package go;
import java.awt.FlowLayout;

import javax.swing.*;

public class ConnectDialog {
	
	private String ip, port;

	public ConnectDialog() {
		JTextField ipTxt = new JTextField(10);
		ipTxt.setText("127.0.0.1");
		JTextField portTxt = new JTextField(5);
		portTxt.setText("2222");
		
		JPanel labels = new JPanel();
		labels.setLayout(new FlowLayout(FlowLayout.LEFT));
		labels.add(new JLabel("IP address"));
		labels.add(Box.createHorizontalStrut(50));
		labels.add(new JLabel("Port"));
		  
		JPanel inputs = new JPanel();
		inputs.add(ipTxt);
		inputs.add(portTxt);
		  
		JPanel[] message = {labels, inputs};
		
		int result = JOptionPane.showConfirmDialog(null, message, "Go-Client Connect", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			this.ip = ipTxt.getText();
			this.port = portTxt.getText();
			System.out.println("IP address: " + ip);
			System.out.println("Port: " + port);
		} else {
			System.exit(0);
		}
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return Integer.parseInt(port);
	}
}