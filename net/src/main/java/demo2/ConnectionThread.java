package demo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectionThread extends Thread{

	private Socket socket;
	private Connection connection;
	private boolean isRunning;
	private SocketServer socketServer;
	
	public ConnectionThread(Socket socket, SocketServer socketServer) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		connection = new Connection(socket);
		isRunning = true;
		this.socketServer = socketServer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunning) {
			if(socket.isClosed()) {
				isRunning = false;
				break;
			}
			
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
				 String message = reader.readLine();
				 if(message!=null)
					 socketServer.getMessageHandler().onReceive(connection, message);
					 
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	}
	
	
	public void stopRunning() {
		isRunning = false;
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}
