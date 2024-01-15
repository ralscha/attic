package ch.ess.mp3search.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ch.ess.mp3search.service.SearchService;

@Controller
public class DownloadMp3ZipController {

  private static final Log logger = LogFactory.getLog(SearchService.class);
  
  @RequestMapping({"/downloadMp3Zip.action", "/bin/downloadMp3Zip.action"})
  public void export(@RequestParam(value = "selectedFiles", required = true)String fileNamesRequest, 
      HttpServletResponse response) throws IOException {

    
    String[] fileNames = fileNamesRequest.split(",");
    
    File tmpFile = File.createTempFile("mp3", "zip");
    tmpFile.deleteOnExit();    
    FileOutputStream fos = new FileOutputStream(tmpFile);
    ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(fos));
    zip.setMethod(ZipOutputStream.DEFLATED);
    zip.setLevel(Deflater.NO_COMPRESSION);
    
    
    for (String fileName : fileNames) {
      
      logger.info("DOWNLOADING: " + fileName);
      
      File file = new File(fileName);
      FileInputStream fis = new FileInputStream(file);
      
      ZipEntry entry = new ZipEntry(file.getName());
      entry.setTime(new Date().getTime());
      zip.putNextEntry(entry);            
      zip.write(IOUtils.toByteArray(fis));
      zip.closeEntry();     
      
      fis.close();
    }
                    
    zip.close();
    fos.close();
    
    response.setContentLength((int)tmpFile.length());
    OutputStream out = response.getOutputStream();
    FileInputStream fis = new FileInputStream(tmpFile);
    
    IOUtils.copy(fis, out);
    fis.close();
    out.close();
    
    tmpFile.delete();    
        
  }

}