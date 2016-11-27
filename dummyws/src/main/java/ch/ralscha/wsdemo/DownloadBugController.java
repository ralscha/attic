package ch.ralscha.wsdemo;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DownloadBugController {

  
  @RequestMapping(value = "/downloadBug", method = RequestMethod.GET)
  public void handleUpload(String fileKey, HttpServletResponse response) throws IOException {
    
    throw new IllegalStateException("this is a bug");  
  }

}
