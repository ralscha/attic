package ch.ralscha.wsdemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DownloadController {

  @Value("#{appProperties.uploadDirectory}")
  private String uploadDirectory;

  @RequestMapping(value = "/download", method = RequestMethod.GET)
  public void handleUpload(String fileKey, HttpServletResponse response) throws IOException {
    
    String parentName = fileKey.substring(0, 2);
    File srcDir = new File(uploadDirectory, parentName);
    File srcFile = new File(srcDir, fileKey);
    
    InputStream fis = FileUtils.openInputStream(srcFile);
    try {
      IOUtils.copy(fis, response.getOutputStream());
    } finally {
      IOUtils.closeQuietly(response.getOutputStream());
      IOUtils.closeQuietly(fis);
    }
    
  }

}
