package ch.ess.hgtracker.web.export;

import java.awt.Color;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ch.ess.hgtracker.db.Spieler;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class SpielerPdfAction extends Action {

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

    response.setContentType("application/pdf");
    // ohne zeile 31 wird das pdf innerhalb des browsers geöffnet
    response.addHeader("Content-disposition", "attachment;filename=spieler.pdf");
    List<Spieler> spielerList = (List<Spieler>)request.getSession().getAttribute("spielerList");
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

    // spaltenüberschriften
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

    // liste in die tabelle abfüllen
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

    return null;
  }

}
