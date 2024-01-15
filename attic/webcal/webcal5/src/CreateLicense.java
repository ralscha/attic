import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;


//features: contact event task document time usersunlimited

public class CreateLicense {


  public static void main(String[] args) {
    try {
            
      //String features = "contact;event;task;document;time";
      //String features = "contact;event;task;document;time;usersunlimited";
    	String features = "event;contact;task;document;time;usersunlimited";
      //String features = "event;usersunlimited";
      //String features = "contact;event;task;document;time";
      
      File f = new File("C:/Users/egli/workplatz/webcal5/private.key");
      BufferedReader br = new BufferedReader(new FileReader(f));
      String privateKeyString = br.readLine();
      br.close();
            
      byte[] privateKeyBytes = Base64.decodeBase64(privateKeyString.getBytes());
            
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
      PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
  
      
      Cipher cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.ENCRYPT_MODE, privateKey);

      
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      CipherOutputStream cos = new CipherOutputStream(bos, cipher);
      cos.write(features.getBytes());
      cos.close();
      
      byte[] k = bos.toByteArray();
      System.out.println(new String(Base64.encodeBase64(k), "ASCII"));
      
    } catch (NoSuchAlgorithmException e) {
      
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (InvalidKeySpecException e) {      
      e.printStackTrace();
    } catch (IOException e) {     
      e.printStackTrace();
    }
    

  }

}
