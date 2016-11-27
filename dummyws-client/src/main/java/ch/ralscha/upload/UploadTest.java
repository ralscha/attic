package ch.ralscha.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class UploadTest {

  public static void main(String[] args) throws IOException {
    URL url = new URL("http://localhost:8080/action/upload?param=23");
    URLConnection connection = url.openConnection();
    connection.setDoOutput(true);

    File f = new File("D:\\_todo\\MyBatis-3-Migrations.pdf");
    FileInputStream fis = new FileInputStream(f);
    OutputStream os = connection.getOutputStream();
    IOUtils.copy(fis, os);
    IOUtils.closeQuietly(os);
    IOUtils.closeQuietly(fis);

    InputStream is = connection.getInputStream();

    String fileKey = IOUtils.toString(is);
    IOUtils.closeQuietly(is);
    
    url = new URL("http://localhost:8080/action/download?fileKey="+fileKey);
    connection = url.openConnection();
    connection.setDoInput(true);
    
    is = connection.getInputStream();
    f = new File("d:\\_todo\\test.pdf");
    FileOutputStream fos = new FileOutputStream(f);
    IOUtils.copy(is, fos);
    
    IOUtils.closeQuietly(fos);
    IOUtils.closeQuietly(is);
    
  }

}
