package demo12;

public class MultiThreadApplication {

	public static void main(String[] args) {
		
		for(final String host:HttpUtil.HOSTS) {
			Thread thread = new Thread() {
				@Override
				public void run() {
					new SocketHttpClient().start(host, 80);
				}
			};
			thread.start();
		}

	}

}
