package demo9;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class test3 {

	public static void main(String[] args) {
		
		try {
			DatagramSocket socket = new DatagramSocket(7777);
			String message = "hello god";
			DatagramPacket packet = new DatagramPacket(message.getBytes(), 
					message.getBytes().length, 
					InetAddress.getByName("localhost"), 9999);
			System.out.println("发送数据:"+message);
			socket.send(packet);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
