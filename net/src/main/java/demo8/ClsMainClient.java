package demo8;

public class ClsMainClient {

	public static void main(String[] args) {
		
		TcpClient c1 = new TcpClient(1234,"127.0.0.1") {
			
			@Override
			public void onReceive(SocketTransceiver transceiver, String s) {
				System.out.println("Client1 Receive: " + s);
			}
			
			@Override
			public void onDisconnect(SocketTransceiver transceiver) {
				System.out.println("Client1 Disconnect");
			}
			
			@Override
			public void onConnectFailed() {
				System.out.println("Client1 Connect Failed");
			}
			
			@Override
			public void onConnect(SocketTransceiver transceiver) {
				System.out.println("Client1 Connect");
				
			}
		};
		
		TcpClient c2 = new TcpClient(1234,"127.0.0.1") {
			
			@Override
			public void onReceive(SocketTransceiver transceiver, String s) {
				System.out.println("Client2 Receive: " + s);
			}
			
			@Override
			public void onDisconnect(SocketTransceiver transceiver) {
				System.out.println("Client2 Disconnect");
			}
			
			@Override
			public void onConnectFailed() {
				System.out.println("Client2 Connect Failed");
			}
			
			@Override
			public void onConnect(SocketTransceiver transceiver) {
				System.out.println("Client2 Connect");
			}
		};
		
		c1.connect();
		c2.connect();		
		
		while(true) {
			if(c1.isConnected()) {
				c1.getTransceiver().send("hello i am c1");
			}
			else
				break;
			delay();
			if(c2.isConnected())
				c2.getTransceiver().send("hello i am c2");
			else
				break;
			delay();
		}
		
		
	}
	
	static void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
