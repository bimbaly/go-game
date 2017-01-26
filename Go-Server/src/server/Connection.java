package server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Connection {
	
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public Connection(Socket socket) throws IOException {
		this.socket = socket;
		input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		output = new ObjectOutputStream(socket.getOutputStream());
			
	}
	
	public String read() throws IOException, ClassNotFoundException {
		return (String) input.readObject();
	}
	
	public void send(String msg) throws IOException {
		output.writeObject(msg);
		output.flush();
	}
	
	public String[] readArray() throws ClassNotFoundException, IOException {
		return (String[]) input.readObject();		
	}
	
	public void sendArray(String[] array) throws IOException {
		output.writeObject(array);
		output.flush();
	}
	
	public void close() throws IOException {
		input.close();
		output.close();
		socket.close();
	}
	
	public boolean isClosed() {
		if (socket.isOutputShutdown() && socket.isInputShutdown() && socket.isClosed())
			return false;
		else 
			return true;
	}
}
