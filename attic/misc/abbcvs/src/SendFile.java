

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.Security;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

/**
 * @author sr
 */
public class SendFile {

  public static void main(String[] args) {

    Security.addProvider(new BouncyCastleProvider());
    FTPClient ftp = null;
    try {

      Properties props = null;
      props = new Properties();
      InputStream is = SendFile.class.getResourceAsStream("/app.properties");
      props.load(is);
      is.close();

      String server = props.getProperty("ftp.server");
      String username = props.getProperty("ftp.user");
      String password = props.getProperty("ftp.password");
      String directory = props.getProperty("remote.dir");
      String fileName = props.getProperty("local.file");
      String remoteFileName = props.getProperty("remote.file");

      boolean passiveMode = Boolean.valueOf(props.getProperty("passive.mode")).booleanValue();
      System.out.println("PASSIVE MODE: " + passiveMode);
      
      String line = null;

      is = SendFile.class.getResourceAsStream("secret.txt");
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      line = br.readLine();
      br.close();

      SecretKeySpec skeySpec = new SecretKeySpec(Base64.decode(line), "AES");

      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

      ftp = new FTPClient();
      
      
      ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(new OutputStreamWriter(System.out))));

      ftp.connect(server);
      ftp.login(username, password);

      ftp.setFileType(FTP.BINARY_FILE_TYPE);

      ftp.makeDirectory(directory);
      ftp.changeWorkingDirectory(directory);
      
      if (passiveMode) {
        ftp.enterRemotePassiveMode();
        ftp.enterLocalPassiveMode();
      }
      
      ftp.deleteFile(remoteFileName);
      
      File file = new File(fileName);
      FileInputStream fis = new FileInputStream(file);

      OutputStream outStream = new CipherOutputStream(ftp.storeFileStream(remoteFileName), cipher);
      
      
      CopyUtils.copy(fis, outStream);

      fis.close();
      outStream.close();

      //      FTPFile[] files = ftp.listFiles(directory);
      //
      //      if (files != null) {
      //        for (int i = 0; i < files.length; i++) {
      //          System.out.println(files[i].getName());
      //          ftp.deleteFile(files[i].getName());
      //        }
      //      }
      //      

      ftp.quit();
      ftp.disconnect();
      
      System.exit(0);

    } catch (Exception e) {
      if (ftp != null && ftp.isConnected()) {
        try {
          ftp.disconnect();
        } catch (IOException f) {
          // do nothing
        }
      }
      System.err.println("Could not connect to server.");
      e.printStackTrace();
      System.exit(1);
    }

  }
}