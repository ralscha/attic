import java.awt.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.sun.image.codec.jpeg.*;

public class DisplayJPGServlet extends HttpServlet {
    com.f1j.swing.JBook m_jBook = new com.f1j.swing.JBook();
    java.awt.image.BufferedImage m_bufferedImage;
    int m_iScreenRes; 

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        java.io.FileInputStream file = null;
        try {             
            m_bufferedImage = new java.awt.image.BufferedImage(500, 400, java.awt.image.BufferedImage.TYPE_INT_RGB);
            file = new java.io.FileInputStream("D:/JavaProjects/scrap/excel/mortgage.vts");
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
        String strPrice = req.getParameter("price");
        String strDownPayment = req.getParameter("down");
        String strClosing = req.getParameter("closing");
        String strYears = req.getParameter("years");
        String strInterest = req.getParameter("interest");
        int iStartRow, iStartCol;
        m_jBook.getLock();
        try {
            calculateValues(strPrice, strDownPayment, strClosing, strYears, strInterest);
            if (strYears.equalsIgnoreCase("30")) {
                iStartCol = 0;
                iStartRow = 59;
            }
            else {
                iStartCol = 0;
                iStartRow = 32;
            } 
            drawImage(out, iStartCol, iStartRow);
            out.close();
        }
        finally {
            m_jBook.releaseLock();
        }
    }
      
    private void calculateValues(String price, String downPayment, String closingCost,
                                 String years, String interest) {
        try {
            com.f1j.ss.Sheet sheet = m_jBook.getBook().getSheet(0);
    		sheet.setEntry(1, 2, price);
    		if (!downPayment.endsWith("%"))
    		    downPayment = downPayment + "%";
    		sheet.setEntry(2, 1, downPayment);
    		if (!closingCost.endsWith("%"))
    		    closingCost = closingCost + "%";
    		sheet.setEntry(4, 1, closingCost);
    		sheet.setEntry(6, 2, years);
    		sheet.setEntry(10, 3, interest);
    		m_jBook.recalc();
        }
        catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }

    private void drawImage(java.io.OutputStream out, int iStartCol, int iStartRow) {
        java.awt.Graphics2D graphics = (java.awt.Graphics2D)m_bufferedImage.createGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, 500, 400);
        try {
            //size of the chart to be drawn is 6 columns and 23 rows
            int iNoChartCols = 6;
            int iNoChartRows = 23;
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
                        iStartRow,  //beginning row of the workbook to be drawn
                        iStartCol,  //beginning column of the workbook to be drawn
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
