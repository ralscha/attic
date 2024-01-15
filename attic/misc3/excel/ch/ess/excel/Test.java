

package ch.ess.excel;

import ch.ess.excel.record.*;
import ch.ess.excel.attribute.*;
import ch.ess.excel.*;
import java.io.*;

public class Test {

  public static void main(String[] args) {
    try {

      ExcelFile ef = new ExcelFile("test.xls");

      ef.setPrintGridLines(false);

      ef.setMargin(Margin.TOP, 1.5);
      ef.setMargin(Margin.LEFT, 1.5);
      ef.setMargin(Margin.RIGHT, 1.5);
      ef.setMargin(Margin.BOTTOM, 1.5);    

      ef.insertHorizPageBreak(10);
      ef.insertHorizPageBreak(20);

      ef.addFont("Arial", 10, FontAttribute.NO_FORMAT); // font0
      ef.addFont("Arial", 10, FontAttribute.BOLD);      // font1
      ef.addFont("Arial", 10, new FontAttributeSet().add(FontAttribute.BOLD).add(FontAttribute.UNDERLINE)); //font2
      ef.addFont("Courier", 12, FontAttribute.ITALIC);  // font3
    
      //Column widths are specified in Excel as 1/256th of a character.
      ef.setColumnWidth(0, 4, 18);

      ef.setHeader("This is the header");
      ef.setFooter("This is the footer");


      CellAttributes attributes = new CellAttributes();
      attributes.setAlignment(Alignment.LEFT);
      attributes.setFontNumber(FontNumber.FONT0);
      attributes.setFormatCode(3);
      ef.writeValue(5, 0, 2000, attributes);

      
      attributes.setAlignment(Alignment.RIGHT);
      attributes.setShaded(true);
      attributes.setFontNumber(FontNumber.FONT1);
      attributes.setBorder(Border.BOTTOM);
      attributes.setFormatCode(4);
      ef.writeValue(6, 0, 12123.456, attributes);
      

      attributes.reset();
      attributes.setAlignment(Alignment.LEFT);
      attributes.setFontNumber(FontNumber.FONT2);
      ef.writeValue(7, 0, "This is a test string", attributes);
          
      attributes.setFontNumber(FontNumber.FONT3);
      attributes.setAlignment(Alignment.LEFT);
      attributes.setLocked(true);
      ef.writeValue(8, 0, "This cell is locked", attributes);
                    
      attributes.reset();
      attributes.setAlignment(Alignment.FILL);
      attributes.setFontNumber(FontNumber.FONT0);
      ef.writeValue(9, 0, "F", attributes);

      ef.writeValue(10, 0, false);
      ef.writeValue(11, 0, CellError.NA);
          
      //ef.setProtectSpreadsheet(true);

      ef.addFormat("0.00");      // 0
      ef.addFormat("0");         // 1
      ef.addFormat("0.00");      // 2
      ef.addFormat("#'##0");     // 3
      ef.addFormat("#'##0.00");  // 4


      ef.close();
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
  
  }
}