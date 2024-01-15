import java.awt.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.sun.image.codec.jpeg.*;

public class DisplayMyJPGServlet extends HttpServlet {
    com.f1j.swing.JBook m_jBook = new com.f1j.swing.JBook();
    java.awt.image.BufferedImage m_bufferedImage;
    int m_iScreenRes; 

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        java.io.FileInputStream file = null;
        try {             
            m_bufferedImage = new java.awt.image.BufferedImage(657, 323, java.awt.image.BufferedImage.TYPE_INT_RGB);
            file = new java.io.FileInputStream("D:/JavaProjects/scrap/excel/book2.xls");
            m_jBook.read(file);
            m_iScreenRes = ((java.awt.Component)m_jBook).getToolkit().getScreenResolution();
        }
        catch (Throwable e) {
            throw new ServletException(e.getMessage());
        }
        finally {
            try {
                if (file != null)
                    file.close();
            }
            catch (java.io.IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void destroy() {
        m_bufferedImage = null;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) 
                        throws ServletException, java.io.IOException  {
        res.setContentType("image/jpeg");
        ServletOutputStream out = res.getOutputStream() ;


        m_jBook.getLock();

        try {

          String kapital = req.getParameter("kapital");
          String zins    = req.getParameter("zins");
          System.out.println(kapital);
          System.out.println(zins);

          if (!zins.endsWith("%"))
             zins += "%";

          com.f1j.ss.Sheet sheet = m_jBook.getBook().getSheet(0);
          sheet.setEntry(0, 0, kapital);
          sheet.setEntry(1, 0, zins);
          m_jBook.recalc();

          drawImage(out);
          out.close();

        } catch (Throwable e) {
          System.out.println(e.getMessage());
        } finally {
            m_jBook.releaseLock();
        }
    }
      

    private void drawImage(java.io.OutputStream out) {
        java.awt.Graphics2D graphics = (java.awt.Graphics2D)m_bufferedImage.createGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, 657, 323);
        try {
            //size of the chart to be drawn is 6 columns and 23 rows
            int iNoChartCols = 10;
            int iNoChartRows = 19;
            int[] row = {iNoChartRows};
            int[] col = {iNoChartCols};
            m_jBook.setColWidthUnits(m_jBook.eColWidthUnitsTwips);
            m_jBook.setPrintScale(400);
            m_jBook.setPrintGridLines(false);
            int width = 0;
            int height = 0;
            for (int i = 0; i < iNoChartCols; i++ )
                width = width + m_jBook.getColWidth(i);
            for (int j = 0; j < iNoChartRows; j++) 
                height = height + m_jBook.getRowHeight(j);
            //convert width and height from Twips to Pixels
            width = (width * m_iScreenRes)/1440;
            height = (height * m_iScreenRes)/1440;


            m_jBook.draw(graphics,        //handle to the graphics object where the workbook will be drawn into
                        0,          //starting x coordinate of graphics image
                        0,          //starting y coordinate of graphics image
                        width,      //width of the drawn workbook
                        height,     //height of the drawn workbook
                        1,  //beginning row of the workbook to be drawn
                        5,  //beginning column of the workbook to be drawn
                        row,        //number of rows to be drawn
                        col,        //number of columns to be drawn
                        0,          //beginning fixed row of the drawn worksheet
                        0,          //number of rows to be fixed in the drawn worksheet
                        0,          //beginning fixed column of the drawn worksheet
                        0);         //number of columns to be fixed in the drawn worksheet
            
	        //save as JPG
            JPEGImageEncoder jp = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jpparam = jp.getDefaultJPEGEncodeParam(m_bufferedImage);
            jpparam.setQuality((float)1.0, true);
            jp.encode(m_bufferedImage, jpparam);
        }
        catch (Throwable t){
            System.out.println(t.getMessage());
        }
        finally {
            graphics.dispose();
        }
    }
}
