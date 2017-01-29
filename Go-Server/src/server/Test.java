package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Test {
	
	public static void main(String[] args) {
		try(Socket socket = new Socket("localhost", 2222)) {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Thread thread = new Thread() {
				public void run() {
					System.out.println("thread has started");
					while(true) {
						try {
							System.out.println(in.readLine());
						} catch (IOException e) {}
					}
				}
			};
			thread.start();
			Scanner sc = new Scanner(System.in);
			while(true) {
				out.println(sc.nextLine());
			}
		} catch (IOException e) {}
	}
}
