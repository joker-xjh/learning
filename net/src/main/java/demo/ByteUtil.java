package demo;

public class ByteUtil {
	
	public static int bytes2Int(byte[] bytes) {
		int res = 0;
		res |= ((bytes[0] << 24) & 0xff000000);
		res |= (bytes[1] << 16) & 0xff0000;
		res |= (bytes[2] << 8 ) & 0xff00;
		res |= bytes[3] & 0xff;
		return res;
	}
	
	public static byte[] int2Bytes(int num) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) ((num >> 24) & 0xff);
		bytes[1] = (byte) ((num >> 16) & 0xff);
		bytes[2] = (byte) ((num >> 8) & 0xff);
		bytes[3] = (byte) (num  & 0xff);
		return bytes;
	}

}
