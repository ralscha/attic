import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.f1j.swing.JBook;
import com.f1j.util.*;
import com.f1j.ss.*;
import java.util.*;

public class Cal extends javax.swing.JFrame
{
    com.f1j.swing.JBook f1;

    public Cal()
    {
          Locale.setDefault(Locale.US);
        javax.swing.JPanel JPanel1 = new javax.swing.JPanel(null);
        f1 = new com.f1j.swing.JBook();

        setTitle("Tutorial 1");
        setSize(575,395);
        setVisible(false);

        getContentPane().setLayout(new BorderLayout(0,0));
        f1.setLayout(null);
        getContentPane().add(BorderLayout.CENTER, JPanel1);
        JPanel1.setBounds(0,0,575,395);
        f1.setBounds(0,0,575,395);
        JPanel1.add(f1);
        
        try {
            CallF1API();
        } catch (F1Exception e) {
          e.printStackTrace();
            System.out.println("Formula One Exception Occurred:"+
                                e.getMessage());
        }
        
        addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        } );


    }

    static public void main(String args[])
    {
        try {
            (new Cal()).setVisible(true);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public void CallF1API() throws F1Exception {
        // Turn recalc off until we have completely built our model.  
        // This will greatly improve performance.
        f1.setAutoRecalc(false);
        // Set formulas and values in F1 Cells.  
        // We're building from the bottom row (row=5) up.
        f1.setEntry(  0, 8, "2/1/2000");
        f1.setFormula(5, 2, "IF(B6+1<=DAY(EOMONTH($I$1,0)),"+
                            "B6+1,-99)");
        f1.setFormula(5, 1, "IF(B5+7<=DAY(EOMONTH($I$1,0)),"+
                            "B5+7,-99)");

        // Change the format of I1 to show the month only 
        // (a predefined date format)
        CellFormat cf = f1.getCellFormat();
        cf.setValueFormat("mmmm");
        f1.setSelection("I1");
        f1.setCellFormat(cf);
        
        // Apply Formatting to the Cells.
        // CellFormats are cumulative.  Once a format is 
        // "set" it stays set until unset.  
        // UndefineAll loops through all of the formats and
        // undefines them all. 
        UndefineAll(cf);
        
        // Create a new number format for the day.  
        // For each day > 0 it will display the day.
        // For days = 0 or < 0 it will display nothing.
        cf.setValueFormat("#;\"\";\"\"");
        
        // Specify alignment, font, and borders for the "day" cells.
        cf.setHorizontalAlignment(cf.eHorizontalAlignmentRight);
        cf.setVerticalAlignment(cf.eVerticalAlignmentTop);
        cf.setLeftBorder(cf.eBorderThin);
        cf.setRightBorder(cf.eBorderThin);
        cf.setFontName("San Serif");
        cf.setFontSizeInPoints(12);
        cf.setFontBold(true);
        cf.setFontColor(0x00000000); // 0x00rrggbb
        
        // Create a multiple selection region by listing each cell 
        // individually.
        f1.setSelection("B6,C6");  // individual selected cells 
                                   // applies borders to each cell 
                                   // selected.
        f1.setCellFormat(cf);  // apply formatting to cells B6 and C6
        
        // Copy cell C6 out to the right (D6 - H6)
        f1.setSelection("C6:H6"); 
        f1.editCopyRight();

        // Copy the entire row (B6:H6) up to fill in rows 
        // 1-5 with formulas
        RangeRef rDest = f1.formulaToRangeRef("B1:H5");
        RangeRef rSrc  = f1.formulaToRangeRef("B6:H6");
        f1.copyRange(rDest.getRow1(), rDest.getCol1(), 
                      rDest.getRow2(), rDest.getCol2(),
                      f1, 
                      rSrc.getRow1(), rSrc.getCol1(), 
                      rSrc.getRow2(), rSrc.getCol2()); // default case
                                                       // is copyAll

        // Change B1 to a different formula.
        f1.setSelection(0, 1, 0, 1);
        f1.setFormula("2-WEEKDAY($I$1)");
    
        // Insert the rows for the holiday text in between each of 
        // the previously populated rows
        for (int i=0;i<5;i++)
           f1.insertRange((i*2)+1, 0, (i*2)+1, 0, f1.eShiftRows);
        
        // Put "top" borders on every other row
        f1.setSelection("B3:H3,B5:H5,B7:H7,B9:H9,B11:H11,B13:H13");
        cf.setTopBorder(cf.eBorderThin);
        f1.setCellFormat(cf);  
        
        // Populate each of these new rows with HLOOKUP formulas 
        // to "lookup" whether the day is a holiday and retrieve the 
        // text associated with the holiday
        // Put the formula in the first cell, then copy it to all
        // the others.
        f1.setSelection("B2");
        f1.setEntry("=IF(OR(ISERROR(HLOOKUP(DATE(YEAR($I$1),"+
                    "MONTH($I$1), B1),Holidays, 2,FALSE)), B1<=0), "+
                    "\"\", HLOOKUP(DATE(YEAR($I$1),MONTH($I$1), B1),"+
                    "Holidays, 2,FALSE))");

        // Set some formatting into this cell before copying so all 
        // copied cell get the new format info.
        UndefineAll(cf);
        cf.setFontSizeInPoints(9);
        cf.setFontBold(false);
        cf.setWordWrap(true);
        cf.setHorizontalAlignment(cf.eHorizontalAlignmentLeft);
        f1.setCellFormat(cf);
        // Copy across from B2 to C2-H2
        f1.copyRange(1, 2, 1, 7, f1, 1, 1, 1, 1);
        // Copy each row down
        for (int i=3;i<12;i+=2) 
           f1.copyRange(i,1,i,7,f1,1,1,1,7);

        // Create 12 new sheets - one for each month
        f1.insertSheets(0,12);  // current sheet with data now becomes
                                // sheet number 12
        
        for (int i=0;i<=11;i++) {
            // Copy from sheet 12 (original sheet) all of the cells
            f1.setSheet(i);
            f1.copyRange(i, 0, 0, 11, 9, 
                         f1, 12, 0, 0, 11, 9, f1.eCopyAll);
            // Use F1 to Convert month to "jan, feb..." by retrieving
            // the Formatted text. Then set the name in the sheet tab
            f1.setEntry(0,8,(i+1)+"/1/2000");
            f1.setSheetName(i, f1.getFormattedText(0, 8));
            f1.recalc();  // Since recalc is off, we need to force a
                          // recalc to make F1 update the cells.  
                          // FormatSheet needs access to updated 
                          // values.
            
            // For each new sheet, format column widths, row heights,
            // etc.
            FormatSheet();            
        }


        // Delete "Sheet1"
        f1.deleteSheets(12,1);
        
        // Move the tabs to the top of the control, and turn the edit 
        // bar off.
        f1.setShowTabs(f1.eTabsTop);
        f1.setShowEditBar(false);

        // Put the holidays into the January sheet, and define the 
        // named range referred to by the other cells in the sheet.
        String strHolidays = "New Year's Day\tMartin Luther King Day"+
                             "\tPresident's Day\tSt. Patricks Day\t"+
                             "Good Friday/Easter Monday\tMemorial "+
                             "Day\tIndependence Day\tLabor Day\t"+
                             "Columbus Day\tVeteran's Day\t"+
                             "Thanksgiving Day\tChristmas Day";
        String strDates =    "1/3/2000\t1/17/2000\t2/11/2000\t"+
                             "3/17/2000\t4/21/2000\t5/29/2000\t"+
                             "7/04/2000\t09/04/2000\t10/09/2000\t"+
                             "11/03/2000\t11/23/2000\t12/25/2000";
        f1.setSheet(0);
        // setTabbedText is zero-based; formulas are 1-based
        f1.setTabbedText(12, 0, false, strDates);
        f1.setTabbedText(13, 0, false, strHolidays);
        f1.setDefinedName("Holidays", f1.getSheetName(
                          f1.getSheet())+"!$A$13:$AA$14");  
        
        f1.setAutoRecalc(true);        
        System.out.println("HIER");
        // Save the file to disk in Excel97/2000 format
        try {
            f1.write("C:/Cal.xls", f1.eFileExcel97);
        } catch (Exception e) {
            System.out.println("Formula One exception occurred "+
                               "writing the file: "+e.getMessage());
        }
    }
    
    // All of these API calls affect the currently selected sheet and 
    // need to be done for each sheet in the workbook.
    void FormatSheet() throws F1Exception {

        // Color the weekend days if there are valid dates in them.  
        // We use getFormattedText to determine if there are any 
        // "displayed" numbers in the cell.
        CellFormat cf=f1.getCellFormat();
        UndefineAll(cf);
        cf.setPatternFG(0x00CCCCCC);
        cf.setPattern((short)1);
        
        for (int i=0;i<12;i+=2) {
            if (f1.getFormattedText(i,1).length()!=0) 
                f1.setCellFormat(cf, i, 1, i+1, 1);
            if (f1.getFormattedText(i,7).length()!=0) 
                f1.setCellFormat(cf, i, 7, i+1, 7);
        }

        // Allow the row heights of the date cells to be determined
        // by the size of the font in the cell. Then set the row 
        // height of the holiday cells to 500.
        for (int i=0;i<12;i+=2) {
            f1.setRowHeightAutomatic(i,true);
            f1.setRowHeight(i+1, 500);
        }
            
        // Change the Column Headers to display the names of the 
        // Weekdays
        String weekdays[] = {"Sunday", "Monday", "Tuesday", 
                             "Wednesday", "Thursday", "Friday", 
                             "Saturday" };
        
        for (int i=0;i<weekdays.length;i++) {
            f1.setColText(i+1, weekdays[i]);
        }

        // Change the width of each column so as to fit each cell's 
        // text.  Do this after setting the column header names.
        // Change the size of all columns (need to "select" all rows 
        // to include header in column width determination)
        f1.setColWidthAuto(0, 1, f1.kMaxRow, 8, true);

        // Make all of the columns equal width based on the width of 
        // the "Wednesday" column (column 4)
        f1.setColWidth(1,8, f1.getColWidth(4), false);                  
        // Hide Row Headers
        f1.setShowRowHeading(false);
        f1.setHeaderHeight(400);
            
        // Put limits on the areas of the spreadsheet viewed 
        // and hide Columns A and I
        f1.setColHidden(0, true);
        f1.setColHidden(8, true);
        f1.setMaxCol(8);
        f1.setMinCol(1);
        
        // Delete bottom rows if a short month doesn't use any of the
        // bottom row
        if (f1.getFormattedText(10,1).length()==0) 
            f1.deleteRange(10,0,11,0,f1.eShiftRows);
            
        // Turn off the scroll bars
        f1.setShowHScrollBar(f1.eShowOff);
        f1.setShowVScrollBar(f1.eShowOff);
    
        // Set Sheet properties:  grid lines off, selections off 
        // and the background color.
        f1.setShowGridLines(false);
        f1.setShowSelections(f1.eShowOff);
        Color bgColor=new Color(234,244,186);
        f1.setPaletteEntry(50, bgColor);
        f1.setBackColor(bgColor);
    }

    // This utility method "cleans up" a CellFormat by undefining 
    // all formatting
    public void UndefineAll(CellFormat cf) {
        for (short i=0;i<38;i++) {
            try {
                cf.setUndefined(i, true);
            } catch (Exception e) {}
        }
    }
}
