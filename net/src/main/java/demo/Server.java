package demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(3456);
		
		while(true) {
			Socket client = serverSocket.accept();
			InputStream inputStream = client.getInputStream();
			OutputStream outputStream = client.getOutputStream();
			@SuppressWarnings("unused")
			Requset request = ProtocolUtil.readRequest(inputStream);
			Response response = new Response();
			response.setEncode(Encode.UTF8.getValue());
			response.setResponse("bye bye");
			response.setResponseLength(response.getResponse().length());
			ProtocolUtil.writeResponse(response, outputStream);
			client.shutdownOutput();
		}
	}

}
