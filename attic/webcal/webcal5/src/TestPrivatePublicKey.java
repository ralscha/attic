import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;


public class TestPrivatePublicKey {


  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    try {
            
      KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
      keyPairGen.initialize(1024, new SecureRandom());
      KeyPair myKeyPair = keyPairGen.generateKeyPair();
      
      PrivateKey privateKey = myKeyPair.getPrivate();
      PublicKey publicKey = myKeyPair.getPublic();
      
      String b64privateKey = new String(Base64.encodeBase64(privateKey.getEncoded()), "ASCII");      
      byte[] privateKeyBytes = Base64.decodeBase64(b64privateKey.getBytes());
      
      String b64publicKey = new String(Base64.encodeBase64(publicKey.getEncoded()), "ASCII");      
      byte[] publicKeyBytes = Base64.decodeBase64(b64publicKey.getBytes());
      
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
      PrivateKey privateKey2 = keyFactory.generatePrivate(privateKeySpec);
  
      EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
      PublicKey publicKey2 = keyFactory.generatePublic(publicKeySpec);
      
      
      
      System.out.println(publicKey.equals(publicKey2));
      System.out.println(privateKey.equals(privateKey2));
      
      Cipher rsaEncodeCipher = Cipher.getInstance("RSA");
      rsaEncodeCipher.init(Cipher.ENCRYPT_MODE, privateKey);
      

      Cipher rsaDecodeCipher = Cipher.getInstance("RSA");
      rsaDecodeCipher.init(Cipher.DECRYPT_MODE, publicKey);

      
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      CipherOutputStream cos = new CipherOutputStream(bos, rsaEncodeCipher);
      cos.write("contact;event;task;document;time;usersunlimited".getBytes());
      cos.close();
      
      byte[] k = bos.toByteArray();
      
      
      System.out.println(new String(Base64.encodeBase64(k), "ASCII"));
      
      ByteArrayInputStream bis = new ByteArrayInputStream(k);
      CipherInputStream cis = new CipherInputStream(bis, rsaDecodeCipher);
      
      List<String> lines = IOUtils.readLines(cis);
      for (Iterator<String> it = lines.iterator(); it.hasNext();) {
        System.out.println("ENCRYPTED: " + it.next());        
      }
      
      
      
      
      
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
