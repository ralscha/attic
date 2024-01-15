package snippet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class ModifySpreadsheet {

  public static void main(String[] args) throws FileNotFoundException, IOException {
    // Load the file.
      File file = new File("template/invoice.ods");
      final Sheet sheet = SpreadSheet.createFromFile(file).getSheet(0);
      // Change strings.
      sheet.setValueAt("Filling test", 1, 1);
      sheet.getCellAt("B27").setValue("On site support");
      // Change date.
      sheet.getCellAt("I10").setValue(new Date());
      // Change number.
      sheet.getCellAt("F24").setValue(3);
      // Save to file and open it.
      File outputFile = new File("fillingTest.ods");
      OOUtils.open(sheet.getSpreadSheet().saveAs(outputFile));
  }
}

