package ch.ess.sonne.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class ShowPictureServlet extends HttpServlet {
  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    File f = new File(ApplicationConextListener.IMAGE_DIRECTORY);
    String imgFileName = request.getPathInfo();
    File imgFile = new File(f, imgFileName.substring(1));

    response.setContentType("image/jpeg");    
    
    ServletOutputStream sos = response.getOutputStream();
    FileInputStream fis = new FileInputStream(imgFile);
    IOUtils.copy(fis, sos);
    fis.close();
    sos.close();
    
    
    
  }

  
  
}
