package demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ProtocolUtil {
	
	public static Requset readRequest(InputStream inputStream) throws IOException {
		byte[] encodeByte = new byte[1];
		inputStream.read(encodeByte);
		byte encode = encodeByte[0];
		
		byte[] requestLengthByte = new byte[4];
		inputStream.read(requestLengthByte);
		int length = ByteUtil.bytes2Int(requestLengthByte);
		
		byte[] content = new byte[length];
		inputStream.read(content);
		String requestStr;
		if(encode == Encode.GBK.getValue())
			requestStr = new String(content, "gbk");
		else
			requestStr = new String(content, "utf-8");
		Requset requset = new Requset();
		requset.setCommand(requestStr);
		requset.setCommandLength(length);
		requset.setEncode(encode);
		return requset;
		
	}
	
	
	
	public static Response readResponse(InputStream inputStream) throws IOException {
		byte[] encodeBytes = new byte[1];
		inputStream.read(encodeBytes);
		byte encode = encodeBytes[0];
		
		byte[] responseContentLength = new byte[4];
		inputStream.read(responseContentLength);
		int length = ByteUtil.bytes2Int(responseContentLength);
		
		byte[] responseContent = new byte[length];
		inputStream.read(responseContent);
		
		String responseStr;
		 if (Encode.GBK.getValue() == encode) {
		      responseStr = new String(responseContent, "GBK");
		    } else {
		      responseStr = new String(responseContent, "UTF8");
		    }
		 
		 Response response= new Response();
		 response.setEncode(encode);
		 response.setResponse(responseStr);
		 response.setResponseLength(length);
		 return response;
	}
	
	
	public static void writeResponse(Response response, OutputStream out) throws IOException {
		out.write(response.getEncode());
		
		out.write(ByteUtil.int2Bytes(response.getResponseLength()));
		if (Encode.GBK.getValue() == response.getEncode()) {
		      out.write(response.getResponse().getBytes("GBK"));
		    } else {
		      out.write(response.getResponse().getBytes("UTF8"));
		    }
		out.flush();
		
	}
	
	public static void writeRequest(Requset request, OutputStream outputStream) throws IOException {
		outputStream.write(request.getEncode());
		outputStream.write(ByteUtil.int2Bytes(request.getCommandLength()));
		if (Encode.GBK.getValue() == request.getEncode()) {
			outputStream.write(request.getCommand().getBytes("GBK"));
		    } else {
		    	outputStream.write(request.getCommand().getBytes("UTF8"));
		    }
		outputStream.flush();
	}
	
	
	
	
	

}
