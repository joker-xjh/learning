package demo;

public enum Encode {
	UTF8((byte) 0), GBK((byte) 1);

	  private byte value;

	  Encode(byte value) {
	    this.value = value;
	  }

	  public byte getValue() {
	    return value;
	  }
}
