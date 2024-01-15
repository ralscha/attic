
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.f1j.swing.JBook;
import com.f1j.util.*;
import com.f1j.ss.*;
import java.util.*;

public class MyCal extends javax.swing.JFrame {
  com.f1j.swing.JBook f1;

  public MyCal() {
    //Locale.setDefault(Locale.US);
    JPanel panel = new JPanel(null);
    f1 = new com.f1j.swing.JBook();

    setTitle("Tutorial 1");
    setSize(575,395);
    setVisible(false);
    getContentPane().setLayout(new BorderLayout(0,0));
    f1.setLayout(null);
    getContentPane().add(BorderLayout.CENTER,panel);
    panel.setBounds(0,0,575,395);
    f1.setBounds(0,0,575,395);
    panel.add(f1);
    
    try {
      callF1API();
    } catch (F1Exception e) {
      e.printStackTrace();
      System.out.println("Formula One Exception Occurred:"+ e.getMessage());
    }


    addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                          System.exit(0);
                        }});
  }



  public void callF1API()throws F1Exception {
    f1.setAutoRecalc(false);
    f1.setEntry(0,8,"01.02.2000");
    f1.setFormula(5,2,"IF(B6+1<=DAY(EOMONTH($I$1,0)),B6+1,-99)");
    f1.setFormula(5,1,"IF(B5+7<=DAY(EOMONTH($I$1,0)),B5+7,-99)");

    CellFormat cf =f1.getCellFormat();
    cf.setValueFormat("mmmm");

    f1.setSelection("I1");
    f1.setCellFormat(cf);
    undefineAll(cf);

    cf.setValueFormat("#;\"\";\"\"");
    cf.setHorizontalAlignment(cf.eHorizontalAlignmentRight);
    cf.setVerticalAlignment(cf.eVerticalAlignmentTop);
    cf.setLeftBorder(cf.eBorderThin);
    cf.setRightBorder(cf.eBorderThin);
    cf.setFontName("San Serif");
    cf.setFontSizeInPoints(12);
    cf.setFontBold(true);
    cf.setFontColor(0x00000000);
    f1.setSelection("B6;C6");
    f1.setCellFormat(cf);

    f1.setSelection("C6:H6");
    f1.editCopyRight();

    RangeRef rDest=f1.formulaToRangeRef("B1:H5");
    RangeRef rSrc =f1.formulaToRangeRef("B6:H6");

    f1.copyRange(rDest.getRow1(),rDest.getCol1(), rDest.getRow2(),rDest.getCol2(),
                  f1, rSrc.getRow1(),rSrc.getCol1(),  rSrc.getRow2(),rSrc.getCol2());

    f1.setSelection(0,1,0,1);
    f1.setFormula("2-WEEKDAY($I$1)");

    for (int i=0;i<5;i++)
      f1.insertRange((i*2)+1,0,(i*2)+1,0,f1.eShiftRows);

    f1.setSelection("B3:H3;B5:H5;B7:H7;B9:H9;B11:H11;B13:H13");
    cf.setTopBorder(cf.eBorderThin);
    f1.setCellFormat(cf);

    f1.setSelection("B2");
    f1.setEntry("=IF(OR(ISERROR(HLOOKUP(DATE(YEAR($I$1),"+
      "MONTH($I$1),B1),Holidays,2,FALSE)),B1<=0),"+
      "\"\",HLOOKUP(DATE(YEAR($I$1),MONTH($I$1),B1),"+
      "Holidays,2,FALSE))");


    undefineAll(cf);
    cf.setFontSizeInPoints(9);
    cf.setFontBold(false);
    cf.setWordWrap(true);
    cf.setHorizontalAlignment(cf.eHorizontalAlignmentLeft);
    f1.setCellFormat(cf);

    f1.copyRange(1,2,1,7,f1,1,1,1,1);
    for (int i=3;i<12;i+=2)
      f1.copyRange(i,1,i,7,f1,1,1,1,7);

    f1.insertSheets(0,12);

    for (int i=0;i<=11;i++){
      f1.setSheet(i);
      f1.copyRange(i,0,0,11,9,
      f1,12,0,0,11,9,f1.eCopyAll);
  System.out.println("01.0"+(i+1)+".2000");

      if (i+1 < 10) 
        f1.setEntry(0,8,"01.0"+(i+1)+".2000");
      else
        f1.setEntry(0,8,"01."+(i+1)+".2000");
      f1.setSheetName(i,f1.getFormattedText(0,8));
      f1.recalc();
      //formatSheet();
    } 

    f1.setAutoRecalc(true);  
  }

  public void undefineAll(CellFormat cf){
    for (short i=0;i<38;i++) {
      try {
        cf.setUndefined(i,true);
      } catch (Exception e) {}
    }
  }

  public static void main(String[] args) {
    try {
      (new MyCal()).setVisible(true);
    } catch (Throwable t) {
      t.printStackTrace();
      System.exit(1);
    } 
  }
}
