package demo8;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public abstract class SocketTransceiver implements Runnable{
	
	protected Socket socket;
	protected DataInputStream in;
	protected DataOutputStream out;
	protected InetAddress addr;
	private boolean runFlag;
	
	public SocketTransceiver(Socket socket) {
		this.socket = socket;
		addr = socket.getInetAddress();
	}
	
	public InetAddress getAddr() {
		return addr;
	}
	
	public void start() {
		runFlag = true;
		new Thread(this).start();
	}
	
	
	public void stop() {
		runFlag = false;
		try {
			in.close();
			socket.shutdownInput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean send(String message) {
		if(out != null) {
			try {
				out.writeUTF(message);
				out.flush();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public abstract void disconnect(InetAddress addr);
	public abstract void onReceive(InetAddress address, String message);
	
	
	

	@Override
	public void run() {
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			runFlag = false;
		}
		
		while(runFlag) {
			try {
				String message = in.readUTF();
				onReceive(addr, message);
			} catch (IOException e) {
				e.printStackTrace();
				runFlag = false;
			}
		}
		
		try {
			in.close();
			out.close();
			socket.close();
			in = null;
			out = null;
			socket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		disconnect(addr);
	}

}
