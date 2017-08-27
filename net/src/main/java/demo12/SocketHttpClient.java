package demo12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class SocketHttpClient {
	
	public void start(String host, int port) {
		Socket client = new Socket();
		try {
			client.setSoTimeout(5000);
			InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(host), port);
			client.connect(address);
			PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
			writer.println(HttpUtil.compositeRequest(host));
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String line;
			while( (line =reader.readLine()) != null ) {
				System.out.println(line);
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
