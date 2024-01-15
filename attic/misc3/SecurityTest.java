
import javax.crypto.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.spec.*;
import org.bouncycastle.util.encoders.*;
import common.io.*;

import java.io.*;
import java.util.*;

public class SecurityTest {
	
	private final static String PASSWORD = "THE PASSWORD";
	
	public void encode() {
		
		try {		
		   SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithSHA1AndDES");
		   PBEKeySpec pks = new PBEKeySpec(PASSWORD.toCharArray());
		   		   
		   SecretKey key = keyFactory.generateSecret(pks);
		   
		   
		   	byte[] salt = new byte[64]; /* ??? */
			new SecureRandom().nextBytes(salt);	
			PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 2);
	  
		   Cipher cipher = Cipher.getInstance("PBEWithSHA1AndDES");
		   cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			
			FileInputStream fis = new FileInputStream("test.txt");
      FileOutputStream fos = new FileOutputStream("test.encoded");
			CipherOutputStream cos = new CipherOutputStream(fos, cipher);
			
			
		
			fos.write(salt, 0, salt.length);
			
			
			byte[] buffer = new byte[1024];
			int len;			
			while((len = fis.read(buffer)) != -1) {	
				cos.write(buffer, 0, len);
			}
			cos.close();
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}

	public void decode() {
		
		try {	
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithSHA1AndDES");
		   PBEKeySpec pks = new PBEKeySpec(PASSWORD.toCharArray());
		   		   
		   SecretKey key = keyFactory.generateSecret(pks);
			
			Cipher cipher = Cipher.getInstance("PBEWithSHA1AndDES");
		
			int len;
			FileInputStream fis = new FileInputStream("test.encoded");
			
			byte[] salt = new byte[64];
			len = fis.read(salt, 0, 64);
			PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 2);

			cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			
			CipherInputStream cis = new CipherInputStream(fis, cipher);
			FileOutputStream fos = new FileOutputStream("test.decoded");
			
			
			
			byte[] buffer = new byte[1024];
			//len = cis.read(buffer, 0, cipher.getBlockSize());	
			while((len = cis.read(buffer)) != -1) {			
				fos.write(buffer, 0, len);
			}
			cis.close();
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	

	public void hashTest() {
    try {
	    byte buffer[] = "Ralph Schaer".getBytes();
    
      MessageDigest algorithm = MessageDigest.getInstance ("MD5");
      algorithm.reset();
      algorithm.update(buffer);

      byte digest[] = algorithm.digest();

      StringBuffer hexString = new StringBuffer();
      for (int i=0;i<digest.length;i++) {
        hexString.append(hexDigitScramble(digest[i]));
        hexString.append(" ");
      }
      System.out.println (hexString.toString());
    } catch (NoSuchAlgorithmException nsa) {
      System.err.println(nsa);
    }

  	
	}

  static private String hexDigit(byte x) {
    StringBuffer sb = new StringBuffer();
    char c;
    // First nibble
    c = (char) ((x >> 4) & 0xf);
    if (c > 9) {
      c = (char) ((c - 10) + 'a');
    } else {
      c = (char) (c + '0');
    }
    sb.append (c);
    // Second nibble
    c = (char) (x & 0xf);
    if (c > 9) {
      c = (char)((c - 10) + 'a');
    } else {
      c = (char)(c + '0');
    }
    sb.append (c);
    return sb.toString();
  }

  static private String hexDigitScramble(byte x) {
    StringBuffer sb = new StringBuffer();
    char c;
    // Second nibble
    c = (char) (x & 0xf);
    if (c > 9) {
      c = (char)((c - 10) + 'G');
    } else {
      c = (char)(c + '0');
    }
    sb.append (c);
    // First nibble
    c = (char) ((x >> 4) & 0xf);
    if (c > 9) {
      c = (char) ((c - 10) + 'A');
    } else {
      c = (char) (c + '0');
    }
    sb.append (c);

    return sb.toString();
  }

  public void generateRijndaelKey() {

    try
    {
      SecureRandom sr = new SecureRandom();
	    KeyGenerator keyGen = KeyGenerator.getInstance("Rijndael");
	    keyGen.init(256, sr);
	    SecretKey key = keyGen.generateKey();

      System.out.println("Algorithm: " + key.getAlgorithm());
      System.out.println("Format   : " + key.getFormat());
      byte[] b = key.getEncoded();
      System.out.println(b.length);


      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("r.key"));
      oos.writeObject(b);
      oos.close();

      Cipher cipher = Cipher.getInstance("Rijndael/ECB/PKCS5Padding");
		  cipher.init(Cipher.ENCRYPT_MODE, key);

      ZipOutputFile zof = new ZipOutputFile("test.zip.encode", cipher);
			zof.addFile(new File("test.txt"));
      zof.close();

    }
    catch (Exception e)
    {
	    System.err.println(e);
    }
  
  }

	public void decodeRijndael() {

  	try {	

      String keyDatFile = "r.key";

      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(keyDatFile));
      byte[] keyBytes = (byte[])ois.readObject();
      ois.close();

      SecretKey key = new SecretKeySpec(keyBytes, "Rijndael"); 

			int len;
			FileInputStream fis = new FileInputStream("test.zip.encode");

			Cipher cipher = Cipher.getInstance("Rijndael/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);

			
			CipherInputStream cis = new CipherInputStream(fis, cipher);
			FileOutputStream fos = new FileOutputStream("test.zip");
						
			byte[] buffer = new byte[1024];
			while((len = cis.read(buffer)) != -1) {			
				fos.write(buffer, 0, len);
			}
			cis.close();
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}


	public static void main(String[] args) {
    //new SecurityTest().hashTest();
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    new SecurityTest().generateRijndaelKey();
    new SecurityTest().decodeRijndael();

/*
    System.out.println("Encode");
		new SecurityTest().encode();
    System.out.println("Decode");
		new SecurityTest().decode();
*/		
	}
}