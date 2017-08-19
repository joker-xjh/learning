package demo4;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;


public class TCPConnector implements Connector{
	
	private final byte[] PACK_LENGTH = new byte[4];
	private int port;
	private String host;
	private Socket client;
	private TCPConnectNotify TCPConnectNotify;
	private boolean needWork;
	
	
	public TCPConnector(int port, String host, TCPConnectNotify tcpConnectNotify) {
		// TODO Auto-generated constructor stub
		this.port = port;
		this.host = host;
		this.TCPConnectNotify = tcpConnectNotify;
		this.needWork = true;
	}

	@Override
	public void connet()  {
		try {
			client = new Socket(host, port);
			client.setKeepAlive(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TCPConnectNotify.connectFailed();
			return;
		}
		if(!client.isConnected()) {
			TCPConnectNotify.connectFailed();
			return;
		}
		TCPConnectNotify.connectSuccess();
		work();
	}

	
	@Override
	public void send(Object object) {
		if(needWork) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				ObjectOutputStream objectOutputStream = new CompactObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject(object);
				objectOutputStream.flush();
				objectOutputStream.close();
				
				int size = byteArrayOutputStream.size();
                byte[] array = ByteBuffer.allocate(4).putInt(size).array();

                ByteArrayOutputStream pack = new ByteArrayOutputStream();
                pack.write(array);
                pack.write(byteArrayOutputStream.toByteArray());

                OutputStream outputStream = client.getOutputStream();
                outputStream.write(pack.toByteArray());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void work() {
        while (needWork) {
            //printStatue();
            checkConnect();
            checkReceiveMsg();
        }
    }

    void checkConnect() {
        if (client == null
                || !client.isConnected()
                || client.isInputShutdown()
                || client.isOutputShutdown()) {
        	connet();
        }
    }
	
	

	@Override
	public void close() throws Exception {
		 closeConnect();
	}
	
	@SuppressWarnings("resource")
	void checkReceiveMsg() {
		DataInputStream dataInputStream = getDataInputStream();
		try {
			dataInputStream.read(PACK_LENGTH);
			ObjectInputStream objectInputStream = new CompactObjectInputStream(dataInputStream, new ClassLoaderClassResolver(this.getClass().getClassLoader()));
			Object object = objectInputStream.readObject();
			 TCPConnectNotify.newMessageSuccess(object);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TCPConnectNotify.newMessageFailed();
			 closeConnect();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TCPConnectNotify.newMessageFailed();
			 closeConnect();
		}
		
	}
	
	
	
	void closeConnect() {
		if(client != null) {
			try {
				client.close();
				client.shutdownOutput();
				client.shutdownInput();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				client = null;
			}
			
		}
	}
	
	
	DataInputStream getDataInputStream() {
		DataInputStream dataInputStream = null;
		try {
			dataInputStream = new DataInputStream(client.getInputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
			closeConnect();
		}
		return dataInputStream;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
