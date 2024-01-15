import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

import ch.ess.base.Constants;

public class TestLicense {
  public static void main(String[] args) {
    String license = "pJKrMiIa+TYwRQ6pL2fZ5au2Vxw8bYpk1UEZsbLqEdtXJl71md+z14gtJjAlDq6rICm052rSx8/Eiv2rvPU2V8ex4BbNDR3io7MPmoM0ebxEU14eaJspeIPNFV40FjQfjTmrRZO5CsAc02CiqH81YCV/vlZVLw1XLpDjxYr5Enw=";

    try {
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(Constants.PUBLIC_KEY.getBytes()));
      PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            
      Cipher cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.DECRYPT_MODE, publicKey);
      

      ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(license.getBytes()));
      BufferedReader br = new BufferedReader(new InputStreamReader(new CipherInputStream(bis, cipher)));
      
      System.out.println(br.readLine());
      br.close();
      
      
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    }

    
    
    
    
  }
}
