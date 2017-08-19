package demo4;

public interface TCPConnectNotify {
	

    void connectSuccess();

    void connectFailed();

    void newMessageSuccess(Object msg);

    void newMessageFailed();

}
