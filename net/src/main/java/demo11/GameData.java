package demo11;

public class GameData {
	
	private int processID;
	private Object data;
	
	public GameData(int id, Object data) {
		this.data = data;
		this.processID = id;
	}
	
	public Object getData() {
		return data;
	}
	
	public int getProcessID() {
		return processID;
	}
	

}
