package demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		
		Requset requset = new Requset();
		requset.setCommand("hello");
		requset.setCommandLength(requset.getCommand().length());
		requset.setEncode(Encode.UTF8.getValue());
		
		Socket socket = new Socket("127.0.0.1", 3456);
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream = socket.getOutputStream();
		ProtocolUtil.writeRequest(requset, outputStream);
		
		Response response = ProtocolUtil.readResponse(inputStream);
		socket.close();
		System.out.println(response.getResponse());
	}

}
