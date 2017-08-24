package demo7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public abstract class SocketTransceiver implements Runnable{
	
	protected Socket socket;
	protected InetAddress addr;
	protected DataInputStream in;
	protected DataOutputStream out;
	protected boolean runFlag;
	
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
			socket.shutdownInput();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean send(String message) {
		if(out!=null) {
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
				final String message = in.readUTF();
				onReceive(addr, message);
			} catch (IOException e) {
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
	
	public abstract void onReceive(InetAddress addr, String message);
	
	public abstract void disconnect(InetAddress addr);

}
