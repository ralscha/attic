
import java.security.*;
import ViolinStrings.*;
import java.io.*;

public class Test2 {

	public static void main(String args[]) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			//String password = "Hallo" + Test2.class.getName();
			//System.out.println(password);
			
			InputStream is = Test2.class.getResourceAsStream("Test2.class");
			
			byte[] buffer = new byte[256];
			
			StringBuffer sb = new StringBuffer();
         while (true) {
            int bytesRead = is.read(buffer);
				
				for (int i = 0; i < bytesRead; i++) {
					sb.append(Strings.rightJustify(Integer.toHexString(buffer[i]).toUpperCase(), 2, '0'));
				}
			
            if (bytesRead == -1) break;
				md.update(buffer);
         }
			System.out.println(sb.toString());
			is.close();
			
			//byte[] msg = password.toUpperCase().getBytes();
			//md.update(msg);
			byte[] result = md.digest();
			
			
			

					
		} catch (Exception e) {
			System.out.println(e);
		}
	}
} 