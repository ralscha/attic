package ch.ess.langtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.Pattern;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class Test {

  public static void main(String[] args) {
    try {

      String master = "D:\\eclipse\\workspace\\blankwebapp5\\src\\ApplicationResources.properties";
      //String master = "D:\\eclipse\\workspace\\TestTracker\\src\\ApplicationResources_de.properties";
      File masterFile = new File(master);
      
      String masterName = masterFile.getName();
      String masterLanguage = ""; 
      
      String resourceName = masterFile.getName();
      int pos = resourceName.indexOf("_");
      if (pos != -1) {
        masterLanguage = resourceName.substring(pos+1, resourceName.indexOf("."));
        resourceName = resourceName.substring(0, pos);         
      } else {
        masterLanguage = "default";
        resourceName = resourceName.substring(0, resourceName.indexOf("."));
      }
      
      
      List<File> otherFiles = new ArrayList<File>();
      File dir = new File(masterFile.getParent());
      File[] files = dir.listFiles();
      for (int i = 0; i < files.length; i++) {
        if (files[i].getName().startsWith(resourceName) && !masterName.equals(files[i].getName())) {
          otherFiles.add(files[i]);
        }
      }
      

      
      
      FileReader fr = new FileReader(masterFile);
      List<String> keyList = Util.loadKeys(fr);
      fr.close();


      
      WritableWorkbook workbook = Workbook.createWorkbook(new File("c:/test.xls"));
      WritableSheet sheet = workbook.createSheet("Sprachen", 0);
      
      SheetSettings settings = sheet.getSettings();
      settings.setProtected(true);
      settings.setPaperSize(PaperSize.A4);
      settings.setOrientation(PageOrientation.LANDSCAPE);
      settings.setFitHeight(1000);
      settings.setFitWidth(1);
      settings.setVerticalFreeze(1);  
      settings.setHorizontalFreeze(2);
      
      WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
      WritableFont arial10fontbold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
      arial10font.setColour(Colour.BLACK);
      arial10fontbold.setColour(Colour.BLACK);
      
      WritableCellFormat headerStyle = new WritableCellFormat (arial10fontbold);
      headerStyle.setLocked(true);
      headerStyle.setVerticalAlignment(VerticalAlignment.TOP);
      headerStyle.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
      headerStyle.setBorder(Border.RIGHT, BorderLineStyle.THIN);
      headerStyle.setBorder(Border.LEFT, BorderLineStyle.THIN);
      headerStyle.setBorder(Border.TOP, BorderLineStyle.THIN);
      headerStyle.setBackground(Colour.LIGHT_BLUE, Pattern.SOLID);
      
      
      WritableCellFormat keyFormat = new WritableCellFormat (arial10font);
      keyFormat.setLocked(true);
      keyFormat.setWrap(false);
      keyFormat.setVerticalAlignment(VerticalAlignment.TOP);
      keyFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
      keyFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN);
      keyFormat.setBorder(Border.LEFT, BorderLineStyle.THIN);
      
      WritableCellFormat valueStyle = new WritableCellFormat (arial10font);
      valueStyle.setWrap(true);
      valueStyle.setLocked(false);
      valueStyle.setVerticalAlignment(VerticalAlignment.TOP);
      valueStyle.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
      valueStyle.setBorder(Border.RIGHT, BorderLineStyle.THIN);      
      
      WritableCellFormat missingValueStyle = new WritableCellFormat (arial10font);
      missingValueStyle.setWrap(true);
      missingValueStyle.setVerticalAlignment(VerticalAlignment.TOP);
      missingValueStyle.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
      missingValueStyle.setBorder(Border.RIGHT, BorderLineStyle.THIN); 
      missingValueStyle.setLocked(false);
      missingValueStyle.setBackground(Colour.YELLOW2, Pattern.SOLID);
      
      sheet.setColumnView(0, 0);
      sheet.setColumnView(1, 60);
      sheet.setColumnView(2, 60);
      sheet.setColumnView(3, 60);

      
      sheet.addCell(new Label(0, 0, "Key", headerStyle)); 
      sheet.addCell(new Label(1, 0, masterLanguage, headerStyle));
      
      int col = 2;
      for (File file : otherFiles) {
        
        int pos1 = file.getName().indexOf("_");
        int pos2 = file.getName().indexOf(".");
        String lang = file.getName().substring(pos1+1, pos2);
        sheet.addCell(new Label(col, 0, lang, headerStyle));                
        col++;
      }
      
      int row = 1;
      
      Properties masterProps = new Properties();
      FileInputStream fis = new FileInputStream(masterFile);
      masterProps.load(fis);
      fis.close();

      for (String key : keyList) {        
        sheet.addCell(new Label(0, row, key, keyFormat)); 
        sheet.addCell(new Label(1, row, (String)masterProps.get(key), valueStyle));        
        row++;         
      }      
      
      col = 2;
      for (File file : otherFiles) {
        row = 1;
        
        Properties langProps = new Properties();
        fis = new FileInputStream(file);
        langProps.load(fis);
        fis.close();
        
        for (String key : keyList) {        
          sheet.addCell(new Label(col, row, (String)langProps.get(key), valueStyle));        
          row++;
        }
        
        col++;
      }

      
      
      workbook.write();
      workbook.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (WriteException e) {      
      e.printStackTrace();
    }

  }


}
