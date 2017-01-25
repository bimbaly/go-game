package server;

public class App {
	
	private static final int defaultPort = 2222;
	
	public static void main(String[] args) {
		Server server = new Server(defaultPort);
		server.launch();
	}
}
