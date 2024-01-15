import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

public class TestPDF {

  public static void main(String[] args) {

    try {
      Document document = new Document();
      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c:/temp/test.pdf"));

      document.open();

      Table table = new Table(2);
      table.setPadding(2);
      table.setSpacing(2);

      Cell newHeader = new Cell("header");
      newHeader.setColspan(2);
      newHeader.setHeader(true);
      table.addCell(newHeader);

      for (int i = 0; i < 100; i++) {
        table.addCell(String.valueOf(i));
        table.addCell(String.valueOf(i));



        if (!writer.fitsPage(table)) {
          table.deleteLastRow();
          i--;
          document.add(table);
          document.newPage();
          table = new Table(2);
          table.setPadding(2);
          table.setSpacing(2);
          table.addCell(newHeader);
        }

      }

      document.add(table);
      document.close();
    } catch (DocumentException e) {

      e.printStackTrace();
    } catch (FileNotFoundException e) {

      e.printStackTrace();
    }

  }
}
