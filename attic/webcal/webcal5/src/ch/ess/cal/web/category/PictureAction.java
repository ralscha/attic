package ch.ess.cal.web.category;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.StrutsAction;


@StrutsAction(path = "/pic")
public class PictureAction extends Action {

  private final static Map<String, byte[]> PIC_CACHE = new HashMap<String, byte[]>();

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    String widthStr = request.getParameter("w");
    String heightStr = request.getParameter("h");
    String colourStr = request.getParameter("c");

    int r = Integer.parseInt(colourStr.substring(0, 2), 16);
    int g = Integer.parseInt(colourStr.substring(2, 4), 16);
    int b = Integer.parseInt(colourStr.substring(4), 16);

    int width = Integer.parseInt(widthStr);
    int height = Integer.parseInt(heightStr);

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    OutputStream out = response.getOutputStream();
    response.setContentType("image/gif");

    byte[] bytes = PIC_CACHE.get(colourStr);
    if (bytes == null) {

      Graphics gr = image.getGraphics();

      Color c = new Color(r, g, b);

      gr.setColor(c);
      gr.fillRect(0, 0, width, height);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      
      ImageIO.write(image, "GIF", baos);
      

      bytes = baos.toByteArray();
      baos.close();
      PIC_CACHE.put(colourStr, bytes);
    }

    out.write(bytes);

    out.flush();
    out.close();

    return null;

  }
}
