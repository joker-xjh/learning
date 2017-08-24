package demo5;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	
	private static String WEB_ROOT = "";

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8888);
			while(true) {
				Socket client = serverSocket.accept();
				System.out.println(client.getInetAddress()+"is connected");
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream(),"utf-8"));
				DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
				
				String http = reader.readLine();
				if(http == null)
					continue;
				String[] tokens = http.split(" ");
				if(!"GET".equals(tokens[0])) {
					String message = "This server can only understand GET method\r\n";
					dataOutputStream.writeBytes("HTTP/1.1 400 Bad Request\r\n");
					dataOutputStream.writeBytes("Content-length: "+message.length()+"\r\n\r\n");
					dataOutputStream.writeBytes(message);
					client.close();
					continue;
				}
				
				while(!"".equals(http)) {
					http = reader.readLine();
				}
				
				System.out.println("GET "+tokens[1]);
				
				String fileName = WEB_ROOT + tokens[1];
				File file = new File(fileName);
				if(!file.exists()) {
					String message = "cannot find "+tokens[1]+" on this server\r\n";
					dataOutputStream.writeBytes("HTTP/1.1 404 Bot Found\r\n");
					dataOutputStream.writeBytes("Content-length: "+message.length()+"\r\n\r\n");
					dataOutputStream.writeBytes(message);
					client.close();
					continue;
				}
				if(!file.canRead()) {
					String message = "You have no permission to access "+tokens[1]+"\r\n";
					dataOutputStream.writeBytes("HTTP/1.1 403 Forbidden\r\n");
					dataOutputStream.writeBytes("Content-length: "+message.length()+"\r\n");
					dataOutputStream.writeBytes(message);
					client.close();
					continue;
				}
				
				dataOutputStream.writeBytes("HTTP/1.1 200 OK\r\n");
				dataOutputStream.writeBytes("Content-length: "+file.length()+"\r\n");
				
				if(fileName.endsWith(".html")) {
					dataOutputStream.writeBytes("Content-type: text/html\r\n");
				}
				if(fileName.endsWith(".jpg")) {
					dataOutputStream.writeBytes("Content-type: image/jpeg\r\n");
				}
				
				dataOutputStream.writeBytes("\r\n");
				
				byte[] buff = new byte[1024];
				FileInputStream fileInputStream = new FileInputStream(file);
				int read = 0;
				while((read = fileInputStream.read(buff))>0) {
					dataOutputStream.write(buff, 0, read);
				}
				dataOutputStream.flush();
				fileInputStream.close();
				client.close();
			}
			
			
			
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Something bad happened");
		}
	}

}
