package go;

public class MainGUI {
	
	public static final boolean debug = true;
	
	public MainGUI() {
		
//		new Game(9);
		new Client().initialize();
		
	}

	public static void main(String[] args) {

		new MainGUI();
		
	}

}
