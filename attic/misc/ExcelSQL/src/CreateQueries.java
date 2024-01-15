import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class CreateQueries {

  public static void main(String[] args) {
    try {
      File outputFile = new File("D:/eclipse/workspace/ExcelSQL/query.sql");
      PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
      Workbook workbook = Workbook.getWorkbook(new File("D:/eclipse/workspace/ExcelSQL/factories.xls")); 
      Sheet sheet = workbook.getSheet(0); 
      
      int noOfRows = sheet.getRows();
      
      for (int i = 1; i <= noOfRows; i++) {
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("update Liegen_Data");
                
        Cell roof = sheet.getCell(1,i);
        Cell garage = sheet.getCell(2,i);
        Cell elevator = sheet.getCell(3,i);
        Cell fire = sheet.getCell(4,i);
        Cell boiler = sheet.getCell(5,i);
        
        if ((roof.getContents() != null) && (roof.getContents().trim().length() > 0)) {
          sb.append(" set RoofFactory = '"+ doubleApostrophe(roof.getContents().trim()) + "'");          
        } else {
          sb.append(" set RoofFactory = null");
        }

        if ((garage.getContents() != null) && (garage.getContents().trim().length() > 0)) {
          sb.append(",GarageFactory = '"+ doubleApostrophe(garage.getContents().trim()) + "'");          
        } else {
          sb.append(",GarageFactory = null");
        }        
        
        if ((elevator.getContents() != null) && (elevator.getContents().trim().length() > 0)) {
          sb.append(",ElevatorFactory = '"+ doubleApostrophe(elevator.getContents().trim()) + "'");          
        } else {
          sb.append(",ElevatorFactory = null");
        }          
        
        if ((fire.getContents() != null) && (fire.getContents().trim().length() > 0)) {
          sb.append(",FireAnnounFactor = '"+ doubleApostrophe(fire.getContents().trim()) + "'");          
        } else {
          sb.append(",FireAnnounFactor = null");
        }   
        
        if ((boiler.getContents() != null) && (boiler.getContents().trim().length() > 0)) {
          sb.append(",BoilerFactory = '"+ doubleApostrophe(boiler.getContents().trim()) + "'");          
        } else {
          sb.append(",BoilerFactory = null");
        }             
        
        Cell rid = sheet.getCell(0,i); 
        sb.append(" where RID = " + rid.getContents());
        
        pw.println(sb.toString());
        pw.println("GO");
        pw.flush();
      }
      
      pw.close();

      
      
      workbook.close();
    } catch (BiffException e) {
      e.printStackTrace();
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 

  }

  public static String doubleApostrophe(String str) {

    if (str == null) {
      return null;
    }

    char[] carray = str.toCharArray();
    StringBuffer sb = new StringBuffer(str.length());

    for (int i = 0; i < carray.length; i++) {
      if (carray[i] != '\'') {
        sb.append(carray[i]);
      } else {
        sb.append("''");
      }
    }

    return sb.toString();
  }
  
}
