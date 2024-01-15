

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.CopyUtils;
import org.bouncycastle.util.encoders.Base64;

/**
 * @author sr
 */
public class Decrypt {

public static void main(String[] args) {
    
    try {
      
      if (args.length == 2) {
      
        InputStream keyis = Decrypt.class.getResourceAsStream("secret.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(keyis));
        String line = br.readLine();
        br.close();
        
        SecretKeySpec skeySpec = new SecretKeySpec(Base64.decode(line), "AES");
        
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        
        InputStream is = new CipherInputStream(new BufferedInputStream(new FileInputStream(args[0])), cipher);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(args[1]));
        
        CopyUtils.copy(is, os);
     
        is.close();
        os.close();
            
      } else {
        System.out.println("java Decrypt <encryptedFile> <decryptedFile>");
      }
      
      
    } catch (Exception e) {
      e.printStackTrace();
    }

  }}