package ch.ess.common.util;


import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ch.ess.addressbook.Constants;


public class ExcelGeneratorHSSF {

  private DefaultTableModel tableModel;
  private Map alignment;
  private Map format;
  private String title;
  private String sheetName;
  
  public ExcelGeneratorHSSF() {

    tableModel = null;
    alignment = new HashMap();
    format = new HashMap();
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setColumnHeader(String[] headers) {

    if (tableModel == null) {
      tableModel = new DefaultTableModel();
    }

    tableModel.setColumnIdentifiers(headers);
  }

  public void setColumnHeader(List headerList) {
    setColumnHeader((String[])headerList.toArray(new String[headerList.size()]));
  }

  public void addRow(Object[] row) {

    if (tableModel == null) {
      tableModel = new DefaultTableModel();
    }

    tableModel.addRow(row);
  }

  public void addRow(List row) {
    addRow(row.toArray());
  }

  public void setTableModel(DefaultTableModel tm) {
    tableModel = tm;
  }

  public void setColAlignment(int col, int alignment) {
    this.alignment.put(new Integer(col), new Integer(alignment));
  }

  public void setColAlignment(short[] alignment) {

    for (int i = 0; i < alignment.length; i++) {
      setColAlignment(i, alignment[i]);
    }
  }

  private short getAlignment(int col) {
    Integer align = (Integer)alignment.get(new Integer(col));
    if (align != null) {
      return align.shortValue();
    } else {
      return 0;
    }
  }


  public void setFormat(int col, String format) {
    this.format.put(new Integer(col), format);
  }

  public void setFormat(String[] format) {

    for (int i = 0; i < format.length; i++) {
      setFormat(i, format[i]);
    }
  }

  private String getFormat(int col) {
    
    String f = (String)format.get(new Integer(col));

    if (f != null) {
      return f;
    } else {
      return null;
    }
  }

  public void setSheetName(String name) {
    this.sheetName = name;
  }

  public void generate(OutputStream out) throws IOException {

    HSSFWorkbook wb = new HSSFWorkbook();            
    HSSFSheet s = wb.createSheet();

    HSSFPrintSetup print = s.getPrintSetup();  
    print.setPaperSize((short)9);  

    
    HSSFRow r = null;
    HSSFCell c = null;
    
    HSSFFont f = wb.createFont();
    f.setFontHeightInPoints((short)10);
    f.setFontName("Verdana");
    f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    
    HSSFFont f2 = wb.createFont();
    f2.setFontHeightInPoints((short)10);
    f2.setFontName("Verdana");    
    
    HSSFCellStyle cs = null;
    
    if (sheetName != null) {
      wb.setSheetName(0, sheetName);
    }
    
    
    s.setGridsPrinted(true);
    
    
    if (title != null) {
      r = s.createRow((short)0);
      
      c = r.createCell((short)0);      
      cs = wb.createCellStyle();
      cs.setAlignment(HSSFCellStyle.ALIGN_LEFT);
      cs.setFont(f);
      c.setCellValue(title);
      c.setCellType(HSSFCell.CELL_TYPE_STRING);
      c.setCellStyle(cs);
    }
    

    //Headers    
    int colCount = tableModel.getColumnCount();
    r = s.createRow((short)2);
    for (int i = 0; i < colCount; i++) {    
      short align = getAlignment(i);
      
      cs = wb.createCellStyle();      
      cs.setAlignment(align);
      cs.setFont(f);

      c = r.createCell((short)i);
      c.setCellValue(tableModel.getColumnName(i));
      c.setCellType(HSSFCell.CELL_TYPE_STRING);
      c.setCellStyle(cs);      

    }
    
    int rowCount = tableModel.getRowCount();

    HSSFCellStyle[] csArray = new HSSFCellStyle[colCount];
    for (int i = 0; i < colCount; i++) {
      csArray[i] = wb.createCellStyle();
      short align = getAlignment(i);           
      csArray[i].setAlignment(align);     
      csArray[i].setFont(f2);     

      String format = getFormat(i);
      if (format != null) {
        csArray[i].setDataFormat(HSSFDataFormat.getBuiltinFormat(format));
      }       
          
    }

    for (int row = 0; row < rowCount; row++) {

      r = s.createRow((short)(3 + row));
      
      for (int i = 0; i < colCount; i++) {
        
        Object val = tableModel.getValueAt(row, i);
        c = r.createCell((short)i);
        c.setCellStyle(csArray[i]);
        

        
        if (val == null) {
          c.setCellValue("");
          c.setCellType(HSSFCell.CELL_TYPE_STRING);          
        } else if (val instanceof String) {
          c.setCellValue((String)val);
          c.setCellType(HSSFCell.CELL_TYPE_STRING);
        } else if (val instanceof Date) {
          Date d = (Date)val;
          c.setCellType(HSSFCell.CELL_TYPE_STRING);
          c.setCellValue(Constants.DEFAULT_DATE_FORMAT.format(d));    
        } else if (val instanceof Calendar) {
          Calendar cal = (Calendar)val;
          c.setCellType(HSSFCell.CELL_TYPE_STRING);
          c.setCellValue(Constants.DEFAULT_DATE_FORMAT.format(cal.getTime()));                
        } else if (val instanceof Float) {
          c.setCellValue(((Float)val).doubleValue());
          c.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        } else if (val instanceof Integer) {
          c.setCellValue(((Integer)val).doubleValue());
          c.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        } else if (val instanceof BigDecimal) {
          c.setCellValue(((BigDecimal)val).doubleValue());
          c.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        } else if (val instanceof Boolean) {
          Boolean b = (Boolean)val;
          c.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
          c.setCellValue(b.booleanValue());  
        } else if (val.toString().length() > 0 && val.toString().substring(0, 1).equals("=")) {
          c.setCellType(HSSFCell.CELL_TYPE_FORMULA);
          c.setCellFormula(val.toString().substring(1));        
        } else {
          c.setCellValue(val.toString());
          c.setCellType(HSSFCell.CELL_TYPE_STRING);          
        }
      }
    }

    wb.write(out);

  }

  
}
