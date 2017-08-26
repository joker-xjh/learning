package demo10;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;

public class DaytimeTask implements Callable<Void>{
	
	private Socket client;
	
	public DaytimeTask(Socket client) {
		this.client = client;
	}
	

	@Override
	public Void call() throws Exception {
		
		BufferedInputStream inputStream = null;
		BufferedOutputStream outputStream = null;
		try {
			inputStream = new BufferedInputStream(client.getInputStream());
			outputStream = new BufferedOutputStream(client.getOutputStream());
			
			while(true) {
				byte[] length_array = new byte[4];
				inputStream.read(length_array);
				int length = PackageUtil.byteArrayToInt(length_array);
				byte[] data = new byte[length];
				inputStream.read(data);
				String message = new String(data);
				synchronized (System.out) {
					System.out.println(client.getRemoteSocketAddress().toString()+":"+message);
				}
				String sendData="收到连接==="+new Date().toString();
				byte[] sendData_array = sendData.getBytes();
				byte[] data_length = PackageUtil.intToByteArray(sendData_array.length);
				outputStream.write(data_length);
				outputStream.flush();
				outputStream.write(sendData_array);
				outputStream.flush();
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			if(inputStream != null)
				inputStream.close();
			if(outputStream != null)
				outputStream.close();
		}
		
		return null;
	}

}
