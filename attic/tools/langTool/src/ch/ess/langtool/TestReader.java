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
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class TestReader {

  public static void main(String[] args) {

    try {

      String master = "c:\\ApplicationResources.properties";
      File masterFile = new File(master);

      String resourceName = masterFile.getName();
      int pos = resourceName.indexOf("_");
      if (pos != -1) {
        resourceName = resourceName.substring(0, pos);
      } else {
        resourceName = resourceName.substring(0, resourceName.indexOf("."));
      }

      FileInputStream excel = new FileInputStream("C:\\test.xls");

      Workbook workbook = Workbook.getWorkbook(excel);
      Sheet sheet = workbook.getSheet(0);

      int cols = sheet.getColumns();
      int rows = sheet.getRows();
      
//      String masterLang = sheet.getCell(1, 0).getContents();

//      if ("default".equals(masterLang)) {
//        master = resourceName + ".properties";
//      } else {
//        master = resourceName + "_" + masterLang + ".properties";
//      }

      
      Map<String,String> langMap = new HashMap<String,String>();
      
      for (int i = 2; i < cols; i++) {
              
        for (int row = 1; row < rows; row++) {
          String key = sheet.getCell(0, row).getContents();
          String value = sheet.getCell(i, row).getContents();
          langMap.put(key, value);
        }
        
        
        Cell headerCell = sheet.getCell(i, 0);
        String lang = headerCell.getContents();
        
        FileInputStream fis = new FileInputStream(masterFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis, "8859_1"));
        String line;
        
        String fileName = resourceName + "_" + lang + ".properties";
        File outputFile = new File(masterFile.getParentFile(), fileName);
        FileOutputStream fos = new FileOutputStream(outputFile);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "8859_1")));

        while ((line = br.readLine()) != null) {

          int eqs = line.indexOf('=');
          if (eqs > 0) {
            String key = line.substring(0, eqs).trim();
            String val = langMap.get(key); 

            String outLine = key + "=" + (val == null ? "" : val);
            pw.println(outLine);

          } else {
            pw.println(line);
          }

        }
        pw.close();
        fos.close();

        br.close();
        fis.close();
      }

      excel.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (BiffException e) {
      e.printStackTrace();
    }

  }

}
