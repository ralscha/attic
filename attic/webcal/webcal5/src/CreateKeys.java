import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;


public class CreateKeys {


  public static void main(String[] args) {
    
    try {
      KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
      keyPairGen.initialize(1024, new SecureRandom());
      KeyPair myKeyPair = keyPairGen.generateKeyPair();
      
      PrivateKey privateKey = myKeyPair.getPrivate();
      PublicKey publicKey = myKeyPair.getPublic();
      
      String b64privateKey = new String(Base64.encodeBase64(privateKey.getEncoded()), "ASCII");      
      String b64publicKey = new String(Base64.encodeBase64(publicKey.getEncoded()), "ASCII");      

      File f = new File("D:/eclipse/workspace/webcal5/private.key");
      PrintWriter pw = new PrintWriter(f);
      pw.println(b64privateKey);
      pw.close();
      
      System.out.println("private final static String PUBLIC_KEY = \""+b64publicKey+"\";");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    
  }

}
