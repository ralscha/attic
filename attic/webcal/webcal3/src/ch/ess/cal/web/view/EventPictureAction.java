package ch.ess.cal.web.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.cal.service.event.impl.EventDistribution;
import ch.ess.cal.service.event.impl.EventDistributionItem;

/** 
 * @struts.action path="/eventPic"
 */
public class EventPictureAction extends Action {

  private final static int BOX_WIDTH = 3;
  private final static int BOX_HEIGHT = 3;

  @Override
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    String widthStr = request.getParameter("w");
    String heightStr = request.getParameter("h");
    String userIdStr = request.getParameter("u");
    String dayStr = request.getParameter("d");

    GroupMonthForm gmf = (GroupMonthForm)request.getSession().getAttribute("groupMonthForm");
    Map<String, Map<String, EventDistribution>> userEvents = gmf.getUserEvents();

    Map<String, EventDistribution> eventsMap = userEvents.get(userIdStr);

    if (eventsMap != null) {
      EventDistribution ed = eventsMap.get(dayStr);

      if (ed != null) {
        int width = Integer.parseInt(widthStr);
        int height = Integer.parseInt(heightStr);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics gr = image.getGraphics();

        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, width, height);

        List<EventDistributionItem> eventCols = ed.getEvents();

        boolean hasAnAllDayEvent = false;

        if (gmf.getGraphicTyp() == 1) {

          int noAm = 0;
          int noPm = 0;

          Set<String> colourAmSet = new HashSet<String>();
          Set<String> colourPmSet = new HashSet<String>();

          for (EventDistributionItem edi : eventCols) {

            int no = 0;
            String[] arr = edi.getArray();
            for (int i = 0; i < arr.length; i++) {
              if (arr[i] != null) {
                if (i <= 11) {
                  colourAmSet.add(arr[i]);
                  noAm++;
                } else {
                  colourPmSet.add(arr[i]);
                  noPm++;
                }
                no++;
              }
            }
            if (no == 24) {
              hasAnAllDayEvent = true;
            }
          }

          if (colourAmSet.size() == 1) {
            gr.setColor(getColor(colourAmSet.iterator().next()));
          } else {
            gr.setColor(Color.BLACK);
          }

          int halfHeight = height / 2;
          if (noAm > 0) {
            if (noAm < 12 * eventCols.size() && !hasAnAllDayEvent) {
              boolean oddrow = false;
              int curX = 0;
              int curY = 0;
              while (curY <= halfHeight) {
                int drawHeight = Math.min(BOX_HEIGHT, halfHeight - curY);
                gr.fillRect(curX, curY, BOX_WIDTH, drawHeight);
                curX = curX + (BOX_WIDTH * 2);
                if (curX > width) {
                  curY = curY + (BOX_HEIGHT);
                  if (oddrow) {
                    curX = 0;
                    oddrow = false;
                  } else {
                    curX = BOX_WIDTH;
                    oddrow = true;
                  }
                }
              }
            } else {
              gr.fillRect(0, 0, width, halfHeight);
            }
          }

          if (noPm > 0) {
            if (colourPmSet.size() == 1) {
              gr.setColor(getColor(colourPmSet.iterator().next()));
            } else {
              gr.setColor(Color.BLACK);
            }

            if (noPm < 12 * eventCols.size() && !hasAnAllDayEvent) {
              boolean oddrow = false;
              int curX = 0;
              int curY = halfHeight;
              while (curY <= height) {
                int drawHeight = Math.min(BOX_HEIGHT, height - curY);
                gr.fillRect(curX, curY, BOX_WIDTH, drawHeight);
                curX = curX + (BOX_WIDTH * 2);
                if (curX > width) {
                  curY = curY + (BOX_HEIGHT);
                  if (oddrow) {
                    curX = 0;
                    oddrow = false;
                  } else {
                    curX = BOX_WIDTH;
                    oddrow = true;
                  }
                }
              }
            } else {
              gr.fillRect(0, halfHeight, width, height);
            }
          }

        } else {

          int widthPerEvent = (width - 2) / eventCols.size();
          int heightPerHour = (height - 2) / 24;

          int col = 0;
          
          for (EventDistributionItem edi : eventCols) {

            String[] arr = edi.getArray();
            for (int i = 0; i < arr.length; i++) {
              if (arr[i] != null) {
                gr.setColor(getColor(arr[i]));
                gr.fillRect((col * widthPerEvent) + 1, (i * heightPerHour) + 1, widthPerEvent, heightPerHour);
              }
            }

            col++;
          }
        }

        OutputStream out = response.getOutputStream();
        response.setContentType("image/png");

        ImageIO.write(image, "png", out);

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
