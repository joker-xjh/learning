package demo6;

import java.io.DataInputStream;
import java.net.Socket;

public class HTTPRequest {

	private String version;
	private String method;
	private String file;
	private Socket client;
	private NameValue[] headers;
	private DataInputStream dataInputStream;
	private String message;
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HTTPRequest() {
		headers = new NameValue[0];
	}
	
	public void addHeader(String name, String value) {
		NameValue nameValue = new NameValue(name, value);
		NameValue[] temp = new NameValue[headers.length+1];
		for(int i=0;i<headers.length;i++)
			temp[i] = headers[i];
		temp[headers.length] = nameValue;
		headers = temp;
	}
	
	@Override
	public String toString() {
		String line = method +" "+file+" "+message+"\n";
		for(int i=0;i<headers.length;i++)
			line += headers[i].getName()+": "+headers[i].getValue()+"\n";
		return line;
	}
	
	
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public Socket getClient() {
		return client;
	}
	public void setClient(Socket client) {
		this.client = client;
	}
	public NameValue[] getHeaders() {
		return headers;
	}
	public void setHeaders(NameValue[] headers) {
		this.headers = headers;
	}
	public DataInputStream getDataInputStream() {
		return dataInputStream;
	}
	public void setDataInputStream(DataInputStream dataInputStream) {
		this.dataInputStream = dataInputStream;
	}
	
	
	
}
