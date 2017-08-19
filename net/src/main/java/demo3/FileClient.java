package demo3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileClient {
	
	private static Socket socket;
	private static String fileName;
	private static BufferedReader reader;
	private static PrintWriter writer;
	
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 5555);
			reader = new BufferedReader(new InputStreamReader(System.in));
			writer = new PrintWriter(socket.getOutputStream(),true);
			String code = selectAction();
			switch (code) {
			case "1":
				writer.println("1");
				sendFile();
				break;
			case "2":
				writer.println("2");
				System.err.print("Enter file name: ");
                fileName = reader.readLine();
                writer.println(fileName);
                receiveFile(fileName);
				break;
			default:
				break;
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Cannot connect to the server, try again later.");
            System.exit(1);
		}
		
	}
	
	 public static void sendFile() {
	        try {
	            System.err.print("Enter file name: ");
	            fileName = reader.readLine();

	            File myFile = new File(fileName);
	            byte[] mybytearray = new byte[(int) myFile.length()];

	            FileInputStream fis = new FileInputStream(myFile);
	            BufferedInputStream bis = new BufferedInputStream(fis);

	            DataInputStream dis = new DataInputStream(bis);
	            dis.readFully(mybytearray, 0, mybytearray.length);

	            OutputStream os = socket.getOutputStream();

	            DataOutputStream dos = new DataOutputStream(os);
	            dos.writeUTF(myFile.getName());
	            dos.writeLong(mybytearray.length);
	            dos.write(mybytearray, 0, mybytearray.length);
	            dos.flush();
	            dis.close();
	            System.out.println("File "+fileName+" sent to Server.");
	        } catch (Exception e) {
	            System.err.println("File does not exist!");
	        }
	    }

	    public static void receiveFile(String fileName) {
	        try {
	            int bytesRead;
	            InputStream in = socket.getInputStream();

	            DataInputStream clientData = new DataInputStream(in);

	            fileName = clientData.readUTF();
	            OutputStream output = new FileOutputStream(("received_from_server_" + fileName));
	            long size = clientData.readLong();
	            byte[] buffer = new byte[1024];
	            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
	                output.write(buffer, 0, bytesRead);
	                size -= bytesRead;
	            }

	            output.close();
	            in.close();

	            System.out.println("File "+fileName+" received from Server.");
	        } catch (IOException ex) {
	            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	
	public static String selectAction() throws IOException {
        System.out.println("1. Send file.");
        System.out.println("2. Recieve file.");
        System.out.print("\nMake selection: ");

        return reader.readLine();
    }

}
