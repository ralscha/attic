package ch.ralscha.wsdemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UploadController {

  @Value("#{appProperties.uploadDirectory}")
  private String uploadDirectory;

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  @ResponseBody
  public String handleUpload(InputStream is) throws IOException {

    try {
      File file = File.createTempFile("upload", "doc");
      FileUtils.copyInputStreamToFile(is, file);
      IOUtils.closeQuietly(is);

      InputStream fis = FileUtils.openInputStream(file);
      String hex = DigestUtils.sha256Hex(fis);
      IOUtils.closeQuietly(fis);

      String parentName = hex.substring(0, 2);
      File destDir = new File(uploadDirectory, parentName);
      FileUtils.forceMkdir(destDir);
      File destFile = new File(destDir, hex);

      if (!destFile.exists()) {
        FileUtils.moveFile(file, destFile);
      } else {
        file.delete();
      }

      return hex;
    } finally {
      IOUtils.closeQuietly(is);
    }
  }

}
