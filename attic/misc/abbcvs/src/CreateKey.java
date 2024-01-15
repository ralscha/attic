

import java.io.FileWriter;
import java.io.PrintWriter;
import java.security.Security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

/**
 * @author sr
 */
public class CreateKey {

  public static void main(String[] args) {

    Security.addProvider(new BouncyCastleProvider());

    try {

      KeyGenerator keyGen = KeyGenerator.getInstance("AES");            
      SecretKey skey = keyGen.generateKey();
      
      String b64key = new String(Base64.encode(skey.getEncoded()), "ASCII");
      
      PrintWriter pw = new PrintWriter(new FileWriter("src/secret.txt"));
      
      pw.println(b64key);
      pw.close();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}