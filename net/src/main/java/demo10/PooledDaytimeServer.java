package demo10;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PooledDaytimeServer {
	
	private static final int PORT = 9999;
	private static final int MAX_CONNECTION = 10;
	private static int CONNECTION_COUNTER;

	public static void main(String[] args) {
		
		ExecutorService pool = Executors.newFixedThreadPool(MAX_CONNECTION);
		try (ServerSocket serverSocket = new ServerSocket(PORT)){
			System.out.println("服务器开启成功");
			while(true) {
				try {
					Socket client = serverSocket.accept();
					CONNECTION_COUNTER++;
					synchronized(System.out) {
						System.out.println("接收到"+client.getRemoteSocketAddress().toString()
								+"客户端连接 "
								+ "=========当前已收到"+CONNECTION_COUNTER+"个连接");
					}
					Callable<Void> task = new DaytimeTask(client);
					pool.submit(task);
					
				} catch (IOException e) {
					// TODO: handle exception
				}
			}
			
		} catch (IOException e) {
			// TODO: handle exception
		}
		
	}

}
