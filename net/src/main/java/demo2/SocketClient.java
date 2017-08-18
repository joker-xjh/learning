package demo2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	
	private Socket socket;
	
	public SocketClient(InetAddress inetAddress,int port) {
		// TODO Auto-generated constructor stub
		try {
			this.socket = new Socket(inetAddress, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void println(String message) {
		try(PrintWriter writer = new PrintWriter(socket.getOutputStream(),true)) {
			writer.println(message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String readLine() {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			String message = reader.readLine();
			return message;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	}
	
	
	
	
	
	
	
	
	
	
	

}
