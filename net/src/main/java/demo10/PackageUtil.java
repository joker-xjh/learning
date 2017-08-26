package demo10;

public class PackageUtil {
	
	public static byte[] intToByteArray(int num) {
		byte[] array = new byte[4];
		array[0] = (byte) ((num>>24) & 0xff);
		array[1] = (byte) ((num >> 16) & 0xff);
		array[2] = (byte) ((num>>8) & 0xff);
		array[3] =  (byte) (num & 0xff);
		return array;
	}
	
	public static int byteArrayToInt(byte[] array) {
		int num = 0;
		for(int i=0; i<array.length; i++) {
			num |= array[i] << (8 * (3-i));
		}
		return num;
	}
	
	
	

}
