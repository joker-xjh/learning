package demo7;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public abstract class TcpServer implements Runnable{
	
	private int port;
	private boolean runFlag;
	private List<SocketTransceiver> clients = new ArrayList<>();
	
	public TcpServer(int port) {
		this.port = port;
	}
	
	public void start() {
		runFlag = true;
		new Thread(this).start();
	}
	
	public void stop() {
		runFlag = false;
	}
	
	
	
	

	@Override
	public void run() {
		try {
			final ServerSocket serverSocket = new ServerSocket(port);
			while(runFlag) {
				try {
					final Socket client = serverSocket.accept();
					startClient(client);
				} catch (IOException e) {
					e.printStackTrace();
					
				}
				
			}
			
			for(SocketTransceiver socketTransceiver:clients)
				socketTransceiver.stop();
			clients.clear();
			serverSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		serverStop();
	}
	
	private void startClient(final Socket socket) {
		SocketTransceiver socketTransceiver = new SocketTransceiver(socket) {
			
			@Override
			public void onReceive(InetAddress addr, String message) {
				TcpServer.this.onReceive(this, message);
			}
			
			@Override
			public void disconnect(InetAddress addr) {
				clients.remove(this);
				TcpServer.this.disconnect(this);
			}
		};
		socketTransceiver.start();
		clients.add(socketTransceiver);
		onConnect(socketTransceiver);
	}
	
	
	
	public abstract void connectFail();
	
	public abstract void onConnect(SocketTransceiver client);
	
	public abstract void onReceive(SocketTransceiver sokcet, String message);
	
	public abstract void disconnect(SocketTransceiver sokcet);
	
	public abstract void serverStop();
	
	
	
	
	

}
