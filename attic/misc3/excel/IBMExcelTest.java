
import com.ibm.excelaccessor.*;
import java.util.*;

public class IBMExcelTest {

  public static void write() {
    ExcelWorkbook workbook = new ExcelWorkbook();
    workbook.setWorkbookName("sample.xls");
    workbook.setReadOnly(false);

    // Zuerst mal ExcelRange ausprobieren
    ExcelRange range = new ExcelRange();
    range.setXlWorkbook(workbook);
    range.setAutoCreateSheet(true);
    range.setWorksheetName("Vertraege");
    range.insertWorksheet();

    range.setRangeName("a1:d3");
    range.initRange();

    range.setValueAsObject("Hello");
    range.insertWorksheet();


    //und jetzt mit ExcelCell
    ExcelCell cell = new ExcelCell();
    cell.setXlWorkbook(workbook);
    cell.setAutoCreateSheet(true);
    cell.setWorksheetName("CellTest");
    cell.insertWorksheet();

    cell.setRangeName("A1");
    cell.initCell();
    cell.setValue(new Integer(1928918));
    cell.setFormat("#'##0.00");
    cell.insertWorksheet();

    Calendar cal = new GregorianCalendar(2000,Calendar.JUNE,23);
    

    cell.setRangeName("B1");
    cell.initCell();
    cell.setValue(cal.getTime());
    cell.setFormat("JJJJ.MM.TT");
    cell.insertWorksheet();

    cell.setRangeName("C1");
    cell.initCell();
    cell.setValue(new Double(93849384.340834));
    cell.setFormat("#'##0.00");
    cell.insertWorksheet();

    workbook.closeWorkbook();
    workbook.closeExcelApplication(); 
  }

  public static void read() {
    ExcelWorkbook workbook = new ExcelWorkbook();
    workbook.setWorkbookName("sample.xls");

    ExcelCell cell = new ExcelCell();
    cell.setXlWorkbook(workbook);
    cell.setWorksheetName("CellTest");
    cell.setRangeName("B1");
    cell.initCell();
    Calendar cal = new GregorianCalendar();
    cal.setTime((Date)cell.getValue());
    System.out.println(cal.get(Calendar.DATE));
    System.out.println(cal.get(Calendar.MONTH)+1);
    System.out.println(cal.get(Calendar.YEAR));

    cell.setRangeName("C1");
    cell.initCell();
    double d = ((Double)cell.getValue()).doubleValue();
    System.out.println(d);

    workbook.closeWorkbook();
    workbook.closeExcelApplication();       
  }

	public static void main(String[] args) {
    write();
    //read();
	}

  public static void dumpWB (ExcelWorkbook workbook) {
    System.err.println("Worksheetes:");
    String[] ws = workbook.getWorksheets();
    if(ws != null)
      for(int i = 0; i < ws.length; i++) 
        System.err.println(ws[i]);
  }

}







