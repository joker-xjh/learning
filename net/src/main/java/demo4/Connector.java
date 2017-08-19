package demo4;

public interface Connector {
	void connet() throws Exception;
	void send(Object object);
	void close() throws Exception;

}
