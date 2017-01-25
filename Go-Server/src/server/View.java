package server;

public class View {
	
	public View() {
		
	}
	
	public synchronized void setLog(String log) {
		System.out.println(log);
	}
}
