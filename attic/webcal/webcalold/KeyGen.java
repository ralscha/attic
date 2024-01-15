
import java.security.*;
import java.io.*;

public class KeyGen {


	public static void main(String[] args) {
		if (args.length == 2) {
			try {		
				MessageDigest md = MessageDigest.getInstance("MD5");
				FileInputStream fis = new FileInputStream(args[0]);


				byte[] buffer = new byte[256];
				
		      while (true) {
	            int bytesRead = fis.read(buffer);
	            if (bytesRead == -1) break;
					md.update(buffer, 0, bytesRead);
	         }
				fis.close();
		
				FileOutputStream fos = new FileOutputStream(args[1]);
				fos.write(md.digest());
				fos.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		} else {
			System.out.println("java KeyGen <class> <output>");
		}
		
	}
}

			