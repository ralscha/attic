package ch.ess.task.web.task;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.keypoint.PngEncoder;

/** 
* @struts.action path="/completePic" scope="request" validate="false"
*/

public class CompletePictureAction extends Action {

  private final static Map PIC_CACHE = new HashMap();

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    String widthStr = request.getParameter("w");
    String heightStr = request.getParameter("h");
    String completeStr = request.getParameter("c");
    
    int width = Integer.parseInt(widthStr);
    int height = Integer.parseInt(heightStr);
    int complete = Integer.parseInt(completeStr);

    Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    OutputStream out = response.getOutputStream();
    response.setContentType("image/png");
        
    
    byte[] bytes = (byte[])PIC_CACHE.get(completeStr);
    if (bytes == null) {
    
      Graphics gr = image.getGraphics();
      
      int wg = width * complete / 100;
      int wr = width - wg;
      
      gr.setColor(Color.GREEN);
      gr.fillRect(0, 0, wg, height);
      
      gr.setColor(Color.RED);
      gr.fillRect(wg, 0, wr, height);
  
      PngEncoder encoder = new PngEncoder(image);
      bytes = encoder.pngEncode();
      PIC_CACHE.put(completeStr, bytes);
    }
    
    out.write(bytes);

    out.flush();
    out.close();

    return null;

  }
}
