
import com.objectplanet.chart.*;
import java.awt.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Acme.JPM.Encoders.*;
import java.util.*;

/**
 * This class generates a chart and makes a gif image of it.
 *
 * @author Bjorn J. Kvande.
 */
public class GifServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, java.io.IOException {


    res.setContentType("image/gif");

    res.setHeader( "Pragma", "no-cache" );
    res.setHeader( "Cache-Control", "no-cache" );
    res.setHeader( "Cache-Control", "no-store" );
    res.setDateHeader( "Expires", 0 );

    HttpSession session = req.getSession(true);
    
    Enumeration e = session.getAttributeNames();
    while (e.hasMoreElements()) {
      log((String)e.nextElement());
    }

    InputData input = (InputData)session.getAttribute("input");

    if (input != null) {

      // create the image
      Frame frame = new Frame();
      frame.addNotify();
      Image image = frame.createImage(input.getWidth(), input.getHeight());
      Graphics g = image.getGraphics();

      //create the chart

      double[] values = input.getValues();

      if (input.getType().equals(InputData.BAR)) {
        BarChart chart = new BarChart(values.length);
        
        chart.setSize(input.getWidth(), input.getHeight());
  
        chart.setSampleValues(0, values);
        chart.setRange(getMax(values));
        
        chart.setTitle("Chart generated as a GIF");
        chart.setValueLinesOn(true);
        chart.setBackground(Color.white);
        chart.set3DModeOn(input.is3D());
        chart.paint(g);

      } else if (input.getType().equals(InputData.PIE)) {
        PieChart chart = new PieChart(values.length);
        
        chart.setSize(input.getWidth(), input.getHeight());
  
        chart.setSampleValues(0, values);
        chart.setTitle("Chart generated as a GIF");

        chart.setBackground(Color.white);
        chart.set3DModeOn(input.is3D());
        chart.paint(g);

      } else {
        LineChart chart = new LineChart(1, values.length, getMax(values)+20);
        
        chart.setSize(input.getWidth(), input.getHeight());
  
        chart.setSampleValues(0, values);
        
        chart.setTitle("Chart generated as a GIF");
        chart.setValueLinesOn(true);
        chart.setBackground(Color.white);
        chart.set3DModeOn(input.is3D());
        chart.paint(g);

      }


      // produce the gif image
      ServletOutputStream out = (ServletOutputStream) res.getOutputStream();
      GifEncoder gif = new GifEncoder (image, out);
      gif.encode();
      out.flush();
      
      //reset the checkbox state
      input.reset();
    }
  }

  private double getMax(double[] values) {
    double max = 0;
    for (int i = 0; i < values.length; i++) {
      max = Math.max(max, values[i]);
    }
    return max;
  }

}
