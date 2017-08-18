package demo2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ListeningThread extends Thread{
	private ServerSocket serverSocket;
	private SocketServer socketServer;
	private boolean isRunning;
	private Vector<ConnectionThread> connectionThreads;
	
	public ListeningThread(SocketServer socketServer, ServerSocket serverSocket) {
		// TODO Auto-generated constructor stub
		isRunning = true;
		this.serverSocket = serverSocket;
		this.socketServer = socketServer;
		connectionThreads = new Vector<>();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunning) {
			if(serverSocket.isClosed()) {
				isRunning = false;
				break;
			}
			try {
				Socket socket = serverSocket.accept();
				ConnectionThread connectionThread = new ConnectionThread(socket,socketServer);
				connectionThreads.addElement(connectionThread);
				connectionThread.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void stopRunning() {
		// TODO Auto-generated method stub
		for(int i=0; i<connectionThreads.size(); i++)
			connectionThreads.elementAt(i).stopRunning();
	}
	
	
	

}
