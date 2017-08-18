package demo2;

public interface MessageHandler {
	void onReceive(Connection connection, String message);

}
