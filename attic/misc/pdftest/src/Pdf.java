import java.io.FileOutputStream;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author sr
 */
public class Pdf {

  public static void main(String[] args) {
    try {
      Document document = new Document(PageSize.A4.rotate(), 50, 50, 50, 50);
      
      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c:\\table_optimization.pdf"));
      document.open();
      
      
      Table aTable = new Table(3);
      aTable.setPadding(1);
      aTable.setBorderWidth(1);

      aTable.setDefaultHorizontalAlignment(Element.ALIGN_LEFT);
      aTable.setDefaultVerticalAlignment(Element.ALIGN_MIDDLE);
      
      
      Cell cell = new Cell("header1");
      
      cell.setHeader(true); 
      aTable.addCell(cell);
      
      cell = new Cell("header2");
      cell.setHeader(true); 
      aTable.addCell(cell);

      cell = new Cell("header3"); 
      cell.setHeader(true); 
      aTable.addCell(cell);

      
      aTable.addCell("1");
      aTable.addCell("2");
      aTable.addCell("3");
      
      document.add(aTable);
      document.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
