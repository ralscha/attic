package test;

import inetsoft.report.*;
import inetsoft.report.style.*;
import inetsoft.report.painter.*;
import inetsoft.report.io.*;
import inetsoft.report.lens.*;
import inetsoft.report.lens.*;
import inetsoft.report.filter.*;
import inetsoft.report.filter.style.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import inetsoft.report.*;
import inetsoft.report.lens.swing.*;
import com.objectmatter.bsf.*;
import ch.ess.pbroker.db.*;
import ch.ess.pbroker.resource.*;
import ch.ess.util.pool.*;


public class Test {

  public static void main(String[] args) {

      try {

      DefaultTableModel dtm = new DefaultTableModel();	 
      dtm.setColumnIdentifiers(new String[]{"ID", "Kandidat", "Alter", "Pensum", "2", "3", "4", "5", "6", "7"});

      Object[] arr = new Object[10];
      for (int i = 0; i < 10; i++) {
        arr[0] = new Integer(i);
        arr[1] = "Franz Müller";
        arr[2] = new Integer(100);
        arr[3] = new Float(100);
        arr[4] = new Date();
        arr[5] = "Franz Müller";
        arr[6] = "Franz Müller";
        arr[7] = "Franz Müller";
        arr[8] = "Franz Müller";
        arr[9] = "Franz Müller";

        dtm.addRow(arr);
      }

      TableModelLens lens = new TableModelLens(dtm);
      lens.setColAutoSize(true);
      lens.setRowAutoSize(true);
      Font verdanaFont = new Font("Verdana", Font.PLAIN, 10);
      Font verdanaBoldFont = new Font("Verdana", Font.BOLD, 10);
      lens.setFont(verdanaFont);
      lens.setRowFont(0, verdanaBoldFont);

      lens.setColAlignment(0, lens.H_RIGHT);
      lens.setColAlignment(1, lens.H_LEFT);
      lens.setColAlignment(2, lens.H_LEFT);
      lens.setColAlignment(3, lens.H_LEFT);
      lens.setColAlignment(4, lens.H_LEFT);
      lens.setColAlignment(5, lens.H_LEFT);
      lens.setColAlignment(6, lens.H_CENTER);
      lens.setColAlignment(7, lens.H_LEFT);
      lens.setColAlignment(8, lens.H_LEFT);
      lens.setColAlignment(9, lens.H_LEFT);
      
      lens.setFormat(4, new java.text.SimpleDateFormat("dd.MM.yyyy"));
      lens.setFormat(3, new java.text.DecimalFormat("#,##0.00"));


      StyleSheet rep = new StyleSheet();

      rep.setCurrentFont(verdanaBoldFont);
      rep.addText("Kosten: 01.01.2001 - 31.03.2001");
      rep.addNewline(2);
      rep.setMargin(new Margin(0,0,0,0));
      rep.setCurrentTableLayout(StyleSheet.TABLE_FIT_PAGE);
      Grid1 grid = new Grid1(lens);
      rep.addTable(new Grid1(lens));




      FileOutputStream fos = new FileOutputStream("test.xls");
      ExcelGenerator gen = new ExcelGenerator(fos);
      //gen.setPageSize(new Dimension(792,648));
      gen.generate(rep);	      
      fos.close();
      System.exit(1);
      } catch (IOException ioe) {
        System.err.println(ioe);
      }    
  }
}

