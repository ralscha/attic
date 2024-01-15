import java.io.*;
import java.net.*;

import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;

class TestPOI {

  public static void main(String args[]) {
    try {
      FileOutputStream out    = new FileOutputStream("c:/temp/test.xls");  // create a new file
      HSSFWorkbook     wb     = new HSSFWorkbook();                           // create a new workbook
      HSSFSheet        s      = wb.createSheet();                             // create a new sheet
      HSSFRow          r      = null;                                         // declare a row object reference
      HSSFCell         c      = null;                                         // declare a cell object reference
      HSSFCellStyle    cs     = wb.createCellStyle();                         // create 3 cell styles
      HSSFCellStyle    cs2    = wb.createCellStyle();        
      HSSFCellStyle    cs3    = wb.createCellStyle();          
      HSSFFont         f      = wb.createFont();                              // create 2 fonts objects
      HSSFFont         f2     = wb.createFont();                              
          
      HSSFPrintSetup print = s.getPrintSetup();  
      print.setPaperSize((short)9);  
          
      f.setFontHeightInPoints((short)12);                                    //set font 1 to 12 point type
      f.setColor(f.COLOR_RED);                                                //make it red
      f.setBoldweight(f.BOLDWEIGHT_BOLD);                                   // make it bold                                                                             //arial is the default font
          
      f2.setFontHeightInPoints((short)10);                                   //set font 2 to 10 point type
      f2.setColor(f.COLOR_NORMAL);                                               //make it the color at palette index 0xf (white)
      f2.setBoldweight(f2.BOLDWEIGHT_BOLD);                                 //make it bold
          
      cs.setFont(f);                                                        //set cell stlye 
      cs.setDataFormat(HSSFDataFormat.getFormat("($#,##0_);[Red]($#,##0)"));//set the cell format see HSSFDataFromat for a full list
          
      cs2.setBorderBottom(cs2.BORDER_THIN);                                 //set a thin border
      cs2.setFillPattern((short)1);                                         //fill w fg fill color
      cs2.setFillForegroundColor((short)0xA);                               // set foreground fill to red
  
      cs2.setFont(f2);                                                      // set the font
      
      wb.setSheetName(0,"HSSF Test");  
      
      r = s.createRow((short)10);
      c = r.createCell((short)10, HSSFCell.CELL_TYPE_NUMERIC);
      c.setCellValue(100.5);
      c.setCellStyle(cs);
      c = r.createCell((short)9, HSSFCell.CELL_TYPE_STRING);
      c.setCellValue("TEST:");
      c.setCellStyle(cs2);
    
                                                                                                //demonstrate adding/naming and deleting a sheet
      /*                                                                                    // create a sheet, set its title then delete it
      s = wb.createSheet();  
      wb.setSheetName(1,"DeletedSheet");
      wb.removeSheetAt(1);
      */
      //end deleted sheet
      
      
      wb.write(out);                                                                   // write the workbook to the output stream
      out.close();                                      
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
  }
}
