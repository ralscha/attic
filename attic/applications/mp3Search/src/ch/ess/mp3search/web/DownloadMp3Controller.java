package ch.ess.mp3search.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ch.ess.mp3search.service.SearchService;

@Controller
public class DownloadMp3Controller {

  private static final Log logger = LogFactory.getLog(SearchService.class);
  
  @RequestMapping({"/downloadMp3.action", "/bin/downloadMp3.action"})
  public void export(@RequestParam(value = "fileName", required = true)String fileName, 
      HttpServletResponse response) throws IOException {
    
    logger.info("PLAYING: " + fileName);
    
    File file = new File(fileName);
    FileInputStream fis = new FileInputStream(file);
        
    response.setContentLength((int)file.length());
    
    OutputStream out = response.getOutputStream();
    IOUtils.copy(fis, out);
    fis.close();
    out.close();

  }

}