package server;

public class App {
	
	private static final int DEFAULT_PORT = 2222;
	
	public static void main(String[] args) {
		Server server = new Server(DEFAULT_PORT);
		server.launch();
	}
}
