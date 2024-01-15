package ch.ess.hgtracker.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.db.Spieler;
import ch.ess.hgtracker.service.ClubService;
import ch.ess.hgtracker.service.SpielerService;

@Controller
public class SpielerExportController {

  @Autowired
  private ClubService clubService;
  
  @Autowired
  private SpielerService spielerService;
  
  @Transactional(readOnly=true)
  @RequestMapping("/exportSpieler.do")
  public void export(@RequestParam("clubId")int clubId, HttpServletResponse response) throws RowsExceededException, WriteException, IOException {
        
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("extension", "xls");
    
    OutputStream out = response.getOutputStream();
    
    WritableWorkbook workbook = Workbook.createWorkbook(out);
    WritableSheet sheet = workbook.createSheet("Spieler", 0);

    WritableFont arial10fontbold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
    WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
    WritableCellFormat arial10formatbold = new WritableCellFormat(arial10fontbold);
    WritableCellFormat arial10format = new WritableCellFormat(arial10font);
    
    Club club = clubService.getClub(clubId);        
    List<Spieler> spielerList = spielerService.getAllOrder(club);        
    
    sheet.addCell(new Label(0, 0, "Nachname", arial10formatbold));
    sheet.addCell(new Label(1, 0, "Vorname", arial10formatbold));
    sheet.addCell(new Label(2, 0, "Strasse", arial10formatbold));
    sheet.addCell(new Label(3, 0, "PLZ", arial10formatbold));
    sheet.addCell(new Label(4, 0, "Ort", arial10formatbold));
    sheet.addCell(new Label(5, 0, "Telefon Nr.", arial10formatbold));
    sheet.addCell(new Label(6, 0, "Mobile Nr.", arial10formatbold));
    sheet.addCell(new Label(7, 0, "E-Mail", arial10formatbold));
    sheet.addCell(new Label(8, 0, "Jahrgang", arial10formatbold));
         
    sheet.setColumnView(0, 30);
    sheet.setColumnView(1, 30);
    sheet.setColumnView(2, 30);
    sheet.setColumnView(3, 30);
    sheet.setColumnView(4, 30);
    sheet.setColumnView(5, 30);
    sheet.setColumnView(6, 30);
    sheet.setColumnView(7, 30);
    sheet.setColumnView(8, 30);
    
    int row = 1;
    for (Spieler spieler : spielerList) {
      
      sheet.addCell(new Label(0, row, spieler.getNachname(), arial10format));
      sheet.addCell(new Label(1, row, spieler.getVorname(), arial10format));
      sheet.addCell(new Label(2, row, spieler.getStrasse(), arial10format));
      sheet.addCell(new Label(3, row, spieler.getPlz(), arial10format));
      sheet.addCell(new Label(4, row, spieler.getOrt(), arial10format));
      sheet.addCell(new Label(5, row, spieler.getTelNr(), arial10format));
      sheet.addCell(new Label(6, row, spieler.getMobileNr(), arial10format));
      sheet.addCell(new Label(7, row, spieler.getEmail(), arial10format));
      sheet.addCell(new Label(8, row, spieler.getJahrgang().toString(), arial10format));
      
      row++;
    }
    
    workbook.write();
    workbook.close();
    
    out.close();

  }
}