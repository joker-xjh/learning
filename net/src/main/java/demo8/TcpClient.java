package demo8;

import java.net.InetAddress;
import java.net.Socket;

public abstract class TcpClient implements Runnable{
	
	private int port;
	private String host;
	private boolean connect = false;
	private SocketTransceiver transceiver;
	
	public TcpClient(int port, String host) {
		this.host = host;
		this.port = port;
	}
	
	public void connect() {
		new Thread(this).start();
	}
	
	
	public boolean isConnected() {
		return connect;
	}
	
	public SocketTransceiver getTransceiver() {
		return connect ? transceiver : null;
	}
	
	public void disconnect() {
		if(transceiver != null) {
			transceiver.stop();
			transceiver = null;
		}
	}
	
	

	@Override
	public void run() {
		try {
			Socket socket = new Socket(host, port);
			transceiver = new SocketTransceiver(socket) {
				
				@Override
				public void onReceive(InetAddress address, String message) {
					TcpClient.this.onReceive(transceiver, message);
				}
				
				@Override
				public void disconnect(InetAddress addr) {
					TcpClient.this.onDisconnect(this);
					
				}
			};
			transceiver.start();
			connect = true;
			this.onConnect(transceiver);
		} catch (Exception e) {
			e.printStackTrace();
			onConnectFailed();
		}
	}
	
	public abstract void onReceive(SocketTransceiver transceiver, String s);
	public abstract void onDisconnect(SocketTransceiver transceiver);
	public abstract void onConnectFailed();
	public abstract void onConnect(SocketTransceiver transceiver);

}
