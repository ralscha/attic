import java.security.*;
import java.io.*;

public class Birth7 {

    public static void main(String args[]) {

        String test = "gtf";
        /*
        ByteArrayOutputStream baos;
        DataOutputStream dos;
        
        ByteArrayInputStream bais;
        DataInputStream dis;
        */
        
        Md5 md5 = new Md5 (test) ;
        md5.processString() ;
        System.out.println (md5.getStringDigest()) ;
        /*
        
        try { 
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeChars(test);
            
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(baos.toByteArray());
            byte b[] = md.digest();
            System.out.println(md);
            
            for (int i = 0; i < b.length; i++) {
                System.out.print(Integer.toHexString(b[i])); 
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }*/
        
    }

}