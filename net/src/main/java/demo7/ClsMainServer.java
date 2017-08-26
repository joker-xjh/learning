package demo7;

public class ClsMainServer {

	public static void main(String[] args) {
		int port = 1234;
		TcpServer server = new TcpServer(port) {
			
			@Override
			public void serverStop() {
				// TODO Auto-generated method stub
				System.out.println("--------Server Stopped--------");
			}
			
			@Override
			public void onReceive(SocketTransceiver sokcet, String message) {
				// TODO Auto-generated method stub
				printInfo(sokcet,message);
			}
			
			@Override
			public void onConnect(SocketTransceiver client) {
				// TODO Auto-generated method stub
				printInfo(client, "Connect");
				
			}
			
			@Override
			public void disconnect(SocketTransceiver sokcet) {
				// TODO Auto-generated method stub
				printInfo(sokcet, "disconnect");
			}
			
			@Override
			public void connectFail() {
				// TODO Auto-generated method stub
				System.out.println("Client Connect Failed");
			}
		};
		
		System.out.println("--------Server Started--------");
		server.start();
		
	}
	
	static void printInfo(SocketTransceiver st, String msg) {
		System.out.println("Client " + st.getAddr().getHostAddress());
		System.out.println("  " + msg);
	}

}
