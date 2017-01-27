package server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Reader {
	
	private ObjectInputStream input;
	
	public Reader(Socket socket) throws IOException {
		input = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
	}
	
	public String getInput() throws IOException, ClassNotFoundException {
		return (String) input.readObject();
	}
}
