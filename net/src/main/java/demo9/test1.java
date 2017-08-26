package demo9;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class test1 {

	public static void main(String[] args) {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			InetAddress remote = InetAddress.getByName("www.baidu.com");
			System.out.println("本机的ip地址:"+localhost.getHostAddress());
			System.out.println("百度的ip地址:"+remote.getHostAddress());
			System.out.println("3秒是否到达:"+remote.isReachable(3000));
			System.out.println("百度的主机名:"+remote.getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
