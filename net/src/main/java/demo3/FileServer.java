package demo3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
	
	private static ServerSocket serverSocket;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			serverSocket = new ServerSocket(5555);
			System.out.println("Server start");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.err.println("Port already in use.");
	         System.exit(1);
		}
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				 System.out.println("Accepted connection : " + socket);
				 Thread thread = new Thread(new ClientConnection(socket));
				 thread.start();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

}
