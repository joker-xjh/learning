package demo3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class ClientConnection implements Runnable{

	private Socket client;
	private BufferedReader in;
	
	public ClientConnection(Socket client) {
		// TODO Auto-generated constructor stub
		this.client = client;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String code;
			while((code = in.readLine()) != null) {
				switch (code) {
				case "1":
					receiveFile();
					break;
				case "2":
					String fileName;
					while((fileName = in.readLine())!=null)
						sendFile(fileName);
					break;
				default:
					System.out.println("Incorrect command received.");
					break;
				}
				in.close();
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void receiveFile() {
		int byteRead;
		try {
			DataInputStream clientData = new DataInputStream(client.getInputStream());
			String fileName = clientData.readUTF();
			OutputStream outputStream = new FileOutputStream(("receive_from_client"+fileName));
			long size = clientData.readLong();
			byte[] buffer = new byte[1024];
			while(size > 0 && (byteRead = clientData.read(buffer, 0, (int)Math.min(size, buffer.length))) != -1) {
				outputStream.write(buffer, 0, byteRead);
				size -= byteRead;
			}
			outputStream.close();
			clientData.close();
			System.out.println("File "+fileName+" received from client.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.err.println("Client error. Connection closed.");
		}
		
	}
	
	public void sendFile(String fileName) {
		File file = new File(fileName);
		byte[] data = new byte[(int)file.length()];
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedInputStream bufferedReader = new BufferedInputStream(fileInputStream);
			DataInputStream dataInputStream = new DataInputStream(bufferedReader);
			dataInputStream.readFully(data, 0, data.length);
			OutputStream outputStream = client.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeUTF(fileName);
			dataOutputStream.writeLong(data.length);
			dataOutputStream.write(data, 0, data.length);
			dataOutputStream.flush();
			dataInputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("File does not exist!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
