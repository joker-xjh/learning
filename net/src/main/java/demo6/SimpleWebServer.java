package demo6;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;


public class SimpleWebServer {
	
	private static final String NEXT_LINE = "\r\n";
	private static final String HTTP_OK = "HTTP/1.1 200 OK";
	private static final String TEXT_HTML = "Content-type: text/html";
	private static final String NOT_FOUND = "HTTP/1.1 404 NOT_FOUND";
	private static final String CONTENT_LENGTH = "Content-Length: ";
	private static final String ERROR_PAGE = "<body>you request is not available</body>";
	private static final String INDEX_PAGE = "/index.html";
	
	private static final int PORT = 9090;
	private static ServerSocket serverSocket;
	
	public  void work() {
		try {
			serverSocket = new ServerSocket(PORT);
			Socket client = serverSocket.accept();
			HTTPRequest request = getRequest(client);
			supportedMethod(request);
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private HTTPRequest getRequest(Socket client) throws IOException {
		HTTPRequest request = null;
		DataInputStream dataInputStream = null;
		dataInputStream = new DataInputStream(client.getInputStream());
		String header = readHeader(dataInputStream);
		request = parseHTTPHeader(header);
		request.setClient(client);
		return request;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String readHeader(DataInputStream dataInputStream) throws IOException {
		String header=null;
		String line = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream));
			header = reader.readLine();
			if(header == null)
				header="";
			header += NEXT_LINE;
			if(header.indexOf("HTTP/") != -1) {
				while((line = reader.readLine())!=null && !"".equals(line))
					header += line+NEXT_LINE;
			}
			else {
				throw new ProtocolException();
			}
		
		return header;
	}
	
	
	
	
	
	
	private HTTPRequest parseHTTPHeader(String header) throws ProtocolException {
		HTTPRequest request = new HTTPRequest();
		StringTokenizer tokenizer = new StringTokenizer(header, NEXT_LINE);
		String curLine = tokenizer.nextToken();
		String[] firstLine = curLine.split(" ");
		request.setMethod(firstLine[0]);
		request.setFile(firstLine[1]);
		if(request.getFile().equals("/"))
			request.setFile(INDEX_PAGE);
		request.setMessage(firstLine[2]);
		while(tokenizer.hasMoreTokens()) {
			curLine = tokenizer.nextToken();
			int slice = curLine.indexOf(":");
			if(slice == -1)
				throw new ProtocolException();
			String name = curLine.substring(0, slice).trim();
			String value = curLine.substring(slice+1).trim();
			request.addHeader(name, value);
		}
		return request;
	}
	
	
	
	private void supportedMethod(HTTPRequest request) throws ProtocolException {
		if("GET".equals(request.getMethod())) {
			serviceGetRequest(request);
		}
		else {
			throw new ProtocolException();
		}
	}
	
	
	
	
	
	
	private void serviceGetRequest(HTTPRequest request) throws ProtocolException{
		if(request.getFile().indexOf("..") != -1)
			throw new ProtocolException();
		try {
			String curPath = new File(".").getCanonicalPath();
			String file = curPath+request.getFile();
			 System.out.println("Requesting file: " + file +" .");
			 FileInputStream fileInputStream = new FileInputStream(file);
			 sendFile(request, fileInputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
		
	
	private void sendErrorResponse(HTTPRequest request) {
		try(DataOutputStream dataOutputStream = new DataOutputStream(request.getClient().getOutputStream())) {
			dataOutputStream.writeBytes(NOT_FOUND+NEXT_LINE);
			dataOutputStream.writeBytes(NEXT_LINE);
			dataOutputStream.writeBytes(ERROR_PAGE);
			dataOutputStream.flush();
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
	
	private void sendFile(HTTPRequest request, FileInputStream fileInputStream) {
		try (DataOutputStream dataOutputStream = new DataOutputStream(request.getClient().getOutputStream())){
			dataOutputStream.writeBytes(HTTP_OK+NEXT_LINE);
			dataOutputStream.writeBytes(TEXT_HTML+NEXT_LINE);
			dataOutputStream.writeBytes(CONTENT_LENGTH+fileInputStream.available()+NEXT_LINE);
			dataOutputStream.writeBytes(NEXT_LINE);
			byte[] buff = new byte[1024];
			int read;
			while((read = fileInputStream.read(buff)) > 0)
				dataOutputStream.write(buff, 0, read);
			dataOutputStream.flush();
			fileInputStream.close();
		} catch (IOException e) {
			// TODO: handle exception
			sendErrorResponse(request);
		}
	}
	
	

	public static void main(String[] args) {

	}

}
