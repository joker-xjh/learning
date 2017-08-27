package demo12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolApplication {

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(5);
		for(final String host:HttpUtil.HOSTS) {
			Thread t = new Thread() {
				@Override
				public void run() {
					new SocketHttpClient().start(host, 80);
				}
			};
			service.submit(t);
		}
		service.shutdown();

	}

}
