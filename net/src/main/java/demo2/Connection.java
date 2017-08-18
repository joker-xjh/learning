package demo2;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
	
	private Socket socket;
	
	public Connection(Socket socket) {
		this.socket = socket;
	}
	
	public void println(String message) {
		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true)){
			writer.println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
		}
	}

}
