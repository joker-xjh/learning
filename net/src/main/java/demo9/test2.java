package demo9;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class test2 {

	public static void main(String[] args) {
		byte[] buff = new byte[1024];
		try {
			DatagramSocket socket = new DatagramSocket(9999);
			DatagramPacket packet = new DatagramPacket(buff, buff.length);
			System.out.println("等待接收数据");
			socket.receive(packet);
			String data = new String(packet.getData(),0,packet.getData().length);
			String abc = data +"efgergergghtrhtrh";
			System.out.println("abc:"+abc);
			System.out.println("before:"+data);
			
			data += " from ";
//		            +packet.getAddress().getHostAddress()
//		            +":"+packet.getPort();
			System.out.println("after:"+data);
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
