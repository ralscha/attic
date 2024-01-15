
import javax.crypto.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.spec.*;
import java.io.*;
import org.bouncycastle.util.encoders.*;

public class KeyTool {

  static {
	  Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
  }


  public static void generateRijndaelKey(String fileName) {

    try
    {
      SecureRandom sr = new SecureRandom();
	    KeyGenerator keyGen = KeyGenerator.getInstance("Rijndael");
	    keyGen.init(256, sr);
	    SecretKey key = keyGen.generateKey();

      System.out.println("Algorithm: " + key.getAlgorithm());
      System.out.println("Format   : " + key.getFormat());
      byte[] b = key.getEncoded();


      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
      oos.writeObject(b);
      oos.close();

    } catch (Exception e) {
	    e.printStackTrace();
    }
  
  }

	public static Cipher getCipher(String fileName) {

  	try {	

      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
      byte[] keyBytes = (byte[])ois.readObject();
      ois.close();

      SecretKey key = new SecretKeySpec(keyBytes, "Rijndael"); 

			Cipher cipher = Cipher.getInstance("Rijndael/ECB/PKCS5Padding");
      return cipher;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    return null;
			
	}


	public static void main(String[] args) {
    if (args.length == 1) {
      KeyTool.generateRijndaelKey(args[0]);
    } else {
      System.out.println("java KeyTool <fileName>");
	  }

	}

}