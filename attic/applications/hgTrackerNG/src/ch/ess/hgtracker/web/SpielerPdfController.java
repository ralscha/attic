package ch.ess.hgtracker.web;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.db.Spieler;
import ch.ess.hgtracker.service.ClubService;
import ch.ess.hgtracker.service.SpielerService;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Controller
public class SpielerPdfController {
  
  @Autowired
  private ClubService clubService;
  
  @Autowired
  private SpielerService spielerService;
  
    
  @RequestMapping("/exportPdfSpieler.do")
  public void export(@RequestParam("clubId")int clubId, HttpServletResponse response) throws IOException, DocumentException {
    response.setContentType("application/pdf");
  
    Club club = clubService.getClub(clubId);        
    List<Spieler> spielerList = spielerService.getAllOrder(club);    
    
    OutputStream out = response.getOutputStream();

    Document document = new Document(PageSize.A4.rotate(), 5, 5, 50, 5);
    PdfWriter.getInstance(document, out);

    // formatierung
    BaseFont titelFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
    
    Font font = new Font(titelFont, 16, Font.BOLD, Color.BLUE);
    Font spalte = new Font(titelFont, 12, Font.BOLD, Color.BLACK);

    document.open();
    PdfPTable table = new PdfPTable(9);
    int[] breite = {10, 10, 12, 5, 9, 14, 14, 22, 9};
    table.setWidths(breite);

    // Spaltenüberschriften
    table.addCell(new Phrase("Nachname", spalte));
    table.addCell(new Phrase("Vorname", spalte));
    table.addCell(new Phrase("Strasse", spalte));
    table.addCell(new Phrase("PLZ", spalte));
    table.addCell(new Phrase("Ort", spalte));
    table.addCell(new Phrase("Telefon Nr.", spalte));
    table.addCell(new Phrase("Mobile Nr.", spalte));
    table.addCell(new Phrase("E-mail", spalte));
    table.addCell(new Phrase("Jahrgang", spalte));
    table.setTotalWidth(100);

    // Liste in die tabelle abfüllen
    Iterator<Spieler> it = spielerList.iterator();
    while (it.hasNext()) {
      Spieler spieler = it.next();
      table.addCell(spieler.getNachname());
      table.addCell(spieler.getVorname());
      table.addCell(spieler.getStrasse());
      table.addCell(spieler.getPlz());
      table.addCell(spieler.getOrt());
      table.addCell(spieler.getTelNr());
      table.addCell(spieler.getMobileNr());
      table.addCell(spieler.getEmail());
      table.addCell(spieler.getJahrgang().toString());
    }

    document.add(new Paragraph("Spielerliste", font));
    document.add(Chunk.NEWLINE);
    document.add(table);
    document.close();    
  }

}
