package demo2;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {
	
	private ServerSocket serverSocket;
	private ListeningThread listeningThread;
	private MessageHandler messageHandler;
	
	public SocketServer(int port, MessageHandler messageHandler) {
		// TODO Auto-generated constructor stub
		this.messageHandler = messageHandler;
		try {
			serverSocket = new ServerSocket(port);
			 listeningThread = new ListeningThread(this, serverSocket);
			 listeningThread.start();
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void close() {
		if(serverSocket!=null && !serverSocket.isClosed()) {
			 listeningThread.stopRunning();
			 try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}
	
	
	
	
	
	
	
	

}
