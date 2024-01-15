package ch.ess.test;

import java.util.*;

import javax.swing.table.*;

import ch.ess.tag.table.*;

public class SortPerforum {

  public static void main(String[] args) {
  
    // This table model will hold our data.
    DefaultTableModel tableModel = new DefaultTableModel();

    // Create the column names.
    Vector columnNames = new Vector();
    columnNames.add("Column1");
    columnNames.add("Column2");
    columnNames.add("Column3");
    columnNames.add("Column4");
    columnNames.add("Column5");

    // We need a Random object for generating random numbers.
    Random random = new Random();

    // Load the data.
    Vector data = new Vector();
    for (int ctr = 0; ctr < 1000; ctr++) {
        Vector row = new Vector();
        row.add("Random:" + (random.nextInt()/100000000));
        row.add(new Integer(ctr));
        row.add(new Integer((random.nextInt()/100000000)));
        row.add("Random:" + (random.nextInt()/100000000));
        row.add("abc");
        data.add(row);
    }

    // Add the data to the table model.
    tableModel.setDataVector(data, columnNames);
    
    TableSorter ts = new TableSorter(tableModel);
    ts.addSortColumn(0, true);
    
    long start = System.currentTimeMillis();
    ts.sort();
    System.out.println((System.currentTimeMillis() - start) + " ms");


        
  }
}
