package ch.ess.cal.web.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.cal.event.EventDistribution;
import ch.ess.cal.event.EventDistributionItem;

import com.keypoint.PngEncoder;

/** 
* @struts.action path="/calPicUser" scope="request" validate="false"
*/
public class CalendarPictureUserAction extends Action {

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    String widthStr = request.getParameter("w");
    String heightStr = request.getParameter("h");
    String userIdStr = request.getParameter("u");
    String dayStr = request.getParameter("d");

    GroupMonthForm gmf = (GroupMonthForm)request.getSession().getAttribute("groupMonthForm");
    Map userEvents = gmf.getUserEvents();

    Map eventsMap = (Map)userEvents.get(userIdStr);

    if (eventsMap != null) {
      EventDistribution ed = (EventDistribution)eventsMap.get(dayStr);

      if (ed != null) {
        int width = Integer.parseInt(widthStr);
        int height = Integer.parseInt(heightStr);

        Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics gr = image.getGraphics();
        
        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, width, height);
        
        List eventCols = ed.getEvents();
        int widthPerEvent = (width-2) / eventCols.size();
        int heightPerHour = (height-2) / 24;
        
        int col = 0;
        for (Iterator it = eventCols.iterator(); it.hasNext();) {
          EventDistributionItem edi = (EventDistributionItem)it.next();
          
          String[] arr = edi.getArray();
          for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
              gr.setColor(getColor(arr[i]));
              gr.fillRect((col*widthPerEvent)+1, (i*heightPerHour)+1, widthPerEvent, heightPerHour);              
            }            
          }          
          
          col++;
        }
 
        //gr.setColor(Color.BLACK);
        //gr.drawRect(0, 0, width-1, height-1);


        
        OutputStream out = response.getOutputStream();
        response.setContentType("image/png");
        
        
        PngEncoder encoder = new PngEncoder(image);
        out.write(encoder.pngEncode());

        out.flush();
        out.close();
      }
    }

    return null;
  }

  private Color getColor(String cstr) {
    int r = Integer.parseInt(cstr.substring(0, 2), 16);
    int g = Integer.parseInt(cstr.substring(2, 4), 16);
    int b = Integer.parseInt(cstr.substring(4), 16);
    return new Color(r, g, b);
  }
}
