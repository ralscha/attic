package ch.ess.hgtracker.web.export;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ch.ess.hgtracker.db.Spieler;

public class SpielerExcelAction extends Action {

  @SuppressWarnings("unchecked")
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    if (response.containsHeader("Cache-Control")) {
      response.setHeader("Cache-Control", "public");
    }
    if (response.containsHeader("Expires")) {
      response.setHeader("Expires", "");
    }
    if (response.containsHeader("Pragma")) {
      response.setHeader("Pragma", "");
    }

    response.setContentType("application/vnd.ms-excel");
    // ohne zeile 31 wird das excel innerhalb des browsers geöffnet
    response.addHeader("Content-disposition", "attachment;filename=spieler.xls");
    List<Spieler> spielerList = (List<Spieler>)request.getSession().getAttribute("spielerList");
    OutputStream out = response.getOutputStream();
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("Spieler");

    // sheet formatieren
    Region r = new Region(1, (short)0, 1, (short)8);
    sheet.addMergedRegion(r);

    // formatieren der spalten breite tiel.1
    int[] max = new int[9];
    Arrays.fill(max, 0);

    // querformat
    HSSFPrintSetup print = sheet.getPrintSetup();
    print.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
    print.setLandscape(true);

    // titel formatierung
    HSSFFont titelFont = workbook.createFont();
    titelFont.setColor(HSSFColor.DARK_BLUE.index);
    titelFont.setFontName("Verdana");
    titelFont.setFontHeightInPoints((short)16);
    titelFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    HSSFCellStyle titelstyle = workbook.createCellStyle();
    titelstyle.setFont(titelFont);

    // spaltenüberschriften formatierung
    HSSFFont spaltenFont = workbook.createFont();
    spaltenFont.setColor(HSSFColor.BLACK.index);
    spaltenFont.setFontName("Verdana");
    spaltenFont.setFontHeightInPoints((short)10);
    spaltenFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    HSSFCellStyle spaltenstyle = workbook.createCellStyle();
    spaltenstyle.setFont(spaltenFont);

    HSSFRow row = sheet.createRow(1);

    //titel
    HSSFCell cell = row.createCell((short)0);
    cell.setCellStyle(titelstyle);
    cell.setCellValue("Spielerliste");

    // spaltenüberschriften
    row = sheet.createRow(3);
    cell = row.createCell((short)0);
    cell.setCellStyle(spaltenstyle);
    cell.setCellValue("Nachname");
    max[0] = 10;
    cell = row.createCell((short)1);
    cell.setCellStyle(spaltenstyle);
    cell.setCellValue("Vorname");
    max[1] = 8;
    cell = row.createCell((short)2);
    cell.setCellStyle(spaltenstyle);
    cell.setCellValue("Strasse");
    cell = row.createCell((short)3);
    cell.setCellStyle(spaltenstyle);
    cell.setCellValue("PLZ");
    cell = row.createCell((short)4);
    cell.setCellStyle(spaltenstyle);
    cell.setCellValue("Ort");
    cell = row.createCell((short)5);
    cell.setCellStyle(spaltenstyle);
    cell.setCellValue("Telefon Nr.");
    cell = row.createCell((short)6);
    cell.setCellStyle(spaltenstyle);
    cell.setCellValue("Mobile Nr.");
    cell = row.createCell((short)7);
    cell.setCellStyle(spaltenstyle);
    cell.setCellValue("E-mail");
    cell = row.createCell((short)8);
    cell.setCellStyle(spaltenstyle);
    cell.setCellValue("Jahrgang");

    // daten
    int zeile = 4;
    Iterator<Spieler> it = spielerList.iterator();
    while (it.hasNext()) {
      Spieler spieler = it.next();
      row = sheet.createRow(zeile);

      cell = row.createCell((short)0);
      cell.setCellValue(spieler.getNachname());
      max[0] = Math.max(spieler.getNachname().length(), max[0]);

      cell = row.createCell((short)1);
      cell.setCellValue(spieler.getVorname());
      max[1] = Math.max(spieler.getVorname().length(), max[1]);

      cell = row.createCell((short)2);
      cell.setCellValue(spieler.getStrasse());
      max[2] = Math.max(spieler.getStrasse().length(), max[2]);

      cell = row.createCell((short)3);
      cell.setCellValue(spieler.getPlz());
      max[3] = Math.max(spieler.getPlz().length(), max[3]);

      cell = row.createCell((short)4);
      cell.setCellValue(spieler.getOrt());
      max[4] = Math.max(spieler.getOrt().length(), max[4]);

      cell = row.createCell((short)5);
      cell.setCellValue(spieler.getTelNr());
      if (spieler.getTelNr() != null) {
        max[5] = Math.max(spieler.getTelNr().length(), max[5]);
      }

      cell = row.createCell((short)6);
      cell.setCellValue(spieler.getMobileNr());
      if (spieler.getMobileNr() != null) {
        max[6] = Math.max(spieler.getMobileNr().length(), max[6]);
      }

      cell = row.createCell((short)7);
      cell.setCellValue(spieler.getEmail());
      if (spieler.getEmail() != null) {
        max[7] = Math.max(spieler.getEmail().length(), max[7]);
      }

      cell = row.createCell((short)8);
      cell.setCellValue(spieler.getJahrgang().intValue());
      max[8] = 9;
      zeile++;
    }

    // formatieren der spalten breite tiel.2
    for (int i = 0; i < max.length; i++) {
      sheet.setColumnWidth((short)i, (short)((max[i] + 2) * 256));
    }

    workbook.write(out);
    out.close();
    return null;
  }

}
