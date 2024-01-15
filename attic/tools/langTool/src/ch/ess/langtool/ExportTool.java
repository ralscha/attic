package ch.ess.langtool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.bushe.swing.event.EventBus;

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


public class ExportTool {

  public static void exportExcel(String masterFileName, String exportFileName, boolean sort, boolean onlyEmpty) {
    try {

      EventBus.publish("log", "Masterfile: " + masterFileName);
      
      File masterFile = new File(masterFileName);

      String masterName = masterFile.getName();
      String masterLanguage = "";

      String resourceName = masterFile.getName();
      int pos = resourceName.indexOf("_");
      if (pos != -1) {
        masterLanguage = resourceName.substring(pos + 1, resourceName.indexOf("."));
        resourceName = resourceName.substring(0, pos);
      } else {
        masterLanguage = "default";
        resourceName = resourceName.substring(0, resourceName.indexOf("."));
      }
      
      EventBus.publish("log", "Masterlanguage: " + masterLanguage);

      List<File> otherFiles = new ArrayList<File>();
      File dir = new File(masterFile.getParent());
      File[] files = dir.listFiles();
      for (int i = 0; i < files.length; i++) {
        if (files[i].getName().startsWith(resourceName) 
            && !masterName.equals(files[i].getName())
            && files[i].getName().endsWith(".properties")) {
          otherFiles.add(files[i]);
          
          EventBus.publish("log", "Languagefiles: " + files[i].getName());
        }
      }

      FileReader fr = new FileReader(masterFile);
      List<String> keyList = Util.loadKeys(fr);
      fr.close();
      
      if (sort) {
        Collections.sort(keyList);
      }

      WritableWorkbook workbook = Workbook.createWorkbook(new File(exportFileName));
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

      WritableCellFormat headerStyle = new WritableCellFormat(arial10fontbold);
      headerStyle.setLocked(true);
      headerStyle.setVerticalAlignment(VerticalAlignment.TOP);
      headerStyle.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
      headerStyle.setBorder(Border.RIGHT, BorderLineStyle.THIN);
      headerStyle.setBorder(Border.LEFT, BorderLineStyle.THIN);
      headerStyle.setBorder(Border.TOP, BorderLineStyle.THIN);
      headerStyle.setBackground(Colour.LIGHT_BLUE, Pattern.SOLID);

      WritableCellFormat keyFormat = new WritableCellFormat(arial10font);
      keyFormat.setLocked(true);
      keyFormat.setWrap(false);
      keyFormat.setVerticalAlignment(VerticalAlignment.TOP);
      keyFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
      keyFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN);
      keyFormat.setBorder(Border.LEFT, BorderLineStyle.THIN);
      
//      WritableCellFormat masterValueFormat = new WritableCellFormat(arial10font);
//      masterValueFormat.setWrap(true);
//      masterValueFormat.setLocked(true);
//      masterValueFormat.setVerticalAlignment(VerticalAlignment.TOP);
//      masterValueFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
//      masterValueFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN);  

      WritableCellFormat valueFormat = new WritableCellFormat(arial10font);
      valueFormat.setWrap(true);
      valueFormat.setLocked(false);
      valueFormat.setVerticalAlignment(VerticalAlignment.TOP);
      valueFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
      valueFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN);

      WritableCellFormat missingValueFormat = new WritableCellFormat(arial10font);
      missingValueFormat.setWrap(true);
      missingValueFormat.setVerticalAlignment(VerticalAlignment.TOP);
      missingValueFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
      missingValueFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN);
      missingValueFormat.setLocked(false);
      missingValueFormat.setBackground(Colour.YELLOW2, Pattern.SOLID);

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
        String lang = file.getName().substring(pos1 + 1, pos2);
        sheet.addCell(new Label(col, 0, lang, headerStyle));
        col++;
      }

      int row = 1;

      Properties masterProps = new Properties();
      FileInputStream fis = new FileInputStream(masterFile);
      masterProps.load(fis);
      fis.close();



      Set<String> showKeysSet = new HashSet<String>();
      if (onlyEmpty) {
        for (File file : otherFiles) {
          Properties langProps = new Properties();
          fis = new FileInputStream(file);
          langProps.load(fis);
          fis.close();
          
          for (String key : keyList) {
            String value = (String)langProps.get(key);
            if (value == null || value.trim().equals("")) {
              showKeysSet.add(key);
            }
          }
        }
      }
      
      
      for (String key : keyList) {
        if (!onlyEmpty || showKeysSet.contains(key)) {
          sheet.addCell(new Label(0, row, key, keyFormat));
          sheet.addCell(new Label(1, row, (String)masterProps.get(key), valueFormat));
          row++;
        }
      }
      
      
      col = 2;
      for (File file : otherFiles) {
        row = 1;

        Properties langProps = new Properties();
        fis = new FileInputStream(file);
        langProps.load(fis);
        fis.close();

        for (String key : keyList) {
          if (!onlyEmpty || showKeysSet.contains(key)) {
            String value = (String)langProps.get(key);
            if (value == null) {
              sheet.addCell(new Label(col, row, "", missingValueFormat));
            } else {
              sheet.addCell(new Label(col, row, value, valueFormat));
            }
            row++;
          }
        }

        col++;
      }

      workbook.write();
      workbook.close();
      
      EventBus.publish("log", "EXPORTED: " + exportFileName);

    } catch (FileNotFoundException e) {
      EventBus.publish("log", "\n" + e.toString());
      e.printStackTrace();
    } catch (IOException e) {
      EventBus.publish("log", "\n" + e.toString());
      e.printStackTrace();
    } catch (WriteException e) {
      EventBus.publish("log", "\n" + e.toString());
      e.printStackTrace();
    }
  }

}
