package ch.ess.langtool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.bushe.swing.event.EventBus;

public class ImportTool {
  public static void importResourceFile(String masterFileName, String excelFileName) {
  
    try {
      
      File masterFile = new File(masterFileName);

      String resourceName = masterFile.getName();
      int pos = resourceName.indexOf("_");
      if (pos != -1) {
        resourceName = resourceName.substring(0, pos);
      } else {
        resourceName = resourceName.substring(0, resourceName.indexOf("."));
      }

      EventBus.publish("log", "Resourcename: " + resourceName);
      
      FileInputStream excel = new FileInputStream(excelFileName);

      Workbook workbook = Workbook.getWorkbook(excel);
      Sheet sheet = workbook.getSheet(0);

      int cols = sheet.getColumns();
      int rows = sheet.getRows();
      
      List<String> masterFileContent = new ArrayList<String>();
      FileInputStream fis = new FileInputStream(masterFile);
      BufferedReader br = new BufferedReader(new InputStreamReader(fis, "8859_1"));
      String l;
      
      while ((l = br.readLine()) != null) {
        masterFileContent.add(l);
      }
      br.close();
      fis.close();
      
      
      Map<String,String> langMap = new HashMap<String,String>();
      
      for (int i = 1; i < cols; i++) {
              
        for (int row = 1; row < rows; row++) {
          String key = sheet.getCell(0, row).getContents();
          String value = sheet.getCell(i, row).getContents();
          langMap.put(key, value);
        }
        
        
        Cell headerCell = sheet.getCell(i, 0);
        String lang = headerCell.getContents();
        
        
        
        String fileName;
        if ("default".equals(lang)) {
          fileName = resourceName + ".properties";
        } else {
          fileName = resourceName + "_" + lang + ".properties";
        }
        
        EventBus.publish("log", "Import: " + fileName);
        
        File outputFile = new File(masterFile.getParentFile(), fileName);
        
        Properties oldLangProps = new Properties();
        if (outputFile.exists()) {          
          fis = new FileInputStream(outputFile);
          oldLangProps.load(fis);
          fis.close();
        }
        
        FileOutputStream fos = new FileOutputStream(outputFile);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "8859_1")));

        for (String line : masterFileContent) {
          
          int eqs = line.indexOf('=');
          if (eqs > 0) {
            String key = line.substring(0, eqs).trim();
            String val = langMap.get(key); 
            if (val != null && !val.trim().equals("")) {
              val = Util.saveConvert(val, false, true);
            } else {
              if (i == 1) {
                val = line.substring(eqs+1);
              } else {
                String origVal = (String)oldLangProps.get(key);
                if (origVal != null) {
                  val = origVal;
                } else {
                  val = "";
                }
              }
            }

            if (val != null && !val.trim().equals("")) {
              String outLine = key + "=" + val;
              pw.println(outLine);
            }
            
            oldLangProps.remove(key);
            
          } else {
            pw.println(line);
          }

        }
        
        Set<Map.Entry<Object,Object>> oldEntries = oldLangProps.entrySet();
        for (Map.Entry<Object,Object> entry : oldEntries) {
          pw.print(entry.getKey());
          pw.print("=");
          pw.println(Util.saveConvert((String)entry.getValue(), false, true));
          
        }
        
        
        pw.close();
        fos.close();
        
      }

      excel.close();

      EventBus.publish("log", "The End");
      
    } catch (FileNotFoundException e) {
      EventBus.publish("log", "\n" + e.toString());
      e.printStackTrace();
    } catch (IOException e) {
      EventBus.publish("log", "\n" + e.toString());
      e.printStackTrace();
    } catch (BiffException e) {
      EventBus.publish("log", "\n" + e.toString());
      e.printStackTrace();
    }

  }
}
