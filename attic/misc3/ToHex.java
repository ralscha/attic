
public class ToHex {

	public static String byteArrayToHexString(byte in[]) {
		byte b = 0x00;
		int i = 0;
		if ((in == null) || (in.length <= 0))
			return null;

		String hexNums[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

		StringBuffer out = new StringBuffer(in.length * 2);

		while(i < in.length) {
			b = (byte)(in[i] & 0xF0);
			b = (byte)(b >>> 4);
			b = (byte)(b & 0x0F);
			out.append(hexNums[(int)b]);
			b = (byte)(in[i] & 0x0F);
			out.append(hexNums[(int)b]);
			i++;
		}

		return out.toString();
	}

	public static void main(String[] args) {
		byte byteArray[] = {(byte)0xf1, (byte)0x0d, (byte)0x3c, (byte)0x44};
		System.out.println(byteArrayToHexString(byteArray));				
	}

}