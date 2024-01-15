package ch.ess.cal.web.view;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import ch.ess.cal.CalUtil;
import ch.ess.cal.model.Event;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class WeekPdfExport {

  private Calendar from;
  private List<String>[] events;
  private Locale locale;  
  private List<Event>[] eventsArrayCurrentMonth;
  private List<Event>[] eventsArrayNextMonth;

  public WeekPdfExport(Calendar from, List<String>[] events, Locale locale, 
      List<Event>[] eventsArrayCurrentMonth, List<Event>[] eventsArrayNextMonth) {    
    this.from = from;
    this.events = events;
    this.locale = locale;
    
    this.eventsArrayCurrentMonth = eventsArrayCurrentMonth; 
    this.eventsArrayNextMonth = eventsArrayNextMonth;    
  }

  public byte[] createPdf() throws DocumentException, IOException {
    Document document = new Document();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = PdfWriter.getInstance(document, baos);
    writer.setFullCompression();

    document.open();

    PdfContentByte cb = writer.getDirectContent();

    cb.rectangle(30, 732, 540, 89);
    cb.setGrayFill(0.9f);
    cb.fill();

    cb.rectangle(30, 718, 540, 12);
    cb.setGrayFill(0.9f);
    cb.fill();

    cb.rectangle(30, 492, 540, 12);
    cb.setGrayFill(0.9f);
    cb.fill();

    cb.rectangle(30, 264, 540, 12);
    cb.setGrayFill(0.9f);
    cb.fill();

    cb.rectangle(300, 139, 270, 12);
    cb.setGrayFill(0.9f);
    cb.fill();
    cb.setGrayFill(0f);

    
    
    cb.beginText();

    BaseFont bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
    BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

    cb.setFontAndSize(bfBold, 20);
    cb.setTextMatrix(43, 792);
    DateFormat dfFrom = new SimpleDateFormat("dd. MMMM", locale);
    cb.showText(dfFrom.format(from.getTime()) + " - ");
    cb.setTextMatrix(43, 763);
    DateFormat dfTo = new SimpleDateFormat("dd. MMMM yyyy", locale);
    Calendar toCal = (Calendar)from.clone();
    toCal.add(Calendar.DAY_OF_MONTH, 6);
    cb.showText(dfTo.format(toCal.getTime()));
    cb.endText();
    
    // Kalender
    Calendar cal = (Calendar)from.clone();
    createMiniCalendar(cb, bf, bfBold, cal, true);
    cal.add(Calendar.MONTH, 1);
    createMiniCalendar(cb, bf, bfBold, cal, false);

    
    cb.beginText();
    
    Calendar startCal = (Calendar)from.clone();
    DateFormat dfStart = new SimpleDateFormat("EEEE, dd. MMMM", locale);
    cb.setFontAndSize(bf, 9);
    cb.setTextMatrix(43, 721);
    cb.showText(dfStart.format(startCal.getTime()));
    startCal.add(Calendar.DAY_OF_MONTH, 1);

    cb.setTextMatrix(43, 495);
    cb.showText(dfStart.format(startCal.getTime()));
    startCal.add(Calendar.DAY_OF_MONTH, 1);

    cb.setTextMatrix(43, 267);
    cb.showText(dfStart.format(startCal.getTime()));
    startCal.add(Calendar.DAY_OF_MONTH, 1);

    cb.setTextMatrix(313, 721);
    cb.showText(dfStart.format(startCal.getTime()));
    startCal.add(Calendar.DAY_OF_MONTH, 1);

    cb.setTextMatrix(313, 495);
    cb.showText(dfStart.format(startCal.getTime()));
    startCal.add(Calendar.DAY_OF_MONTH, 1);

    cb.setTextMatrix(313, 267);
    cb.showText(dfStart.format(startCal.getTime()));
    startCal.add(Calendar.DAY_OF_MONTH, 1);

    cb.setTextMatrix(313, 142);
    cb.showText(dfStart.format(startCal.getTime()));
    startCal.add(Calendar.DAY_OF_MONTH, 1);

    // Termine
    insertEvents(cb, events[0], 43, 708);
    insertEvents(cb, events[1], 43, 482);
    insertEvents(cb, events[2], 43, 254);
    insertEvents(cb, events[3], 313, 708);
    insertEvents(cb, events[4], 313, 482);
    insertEvents(cb, events[5], 313, 254);
    insertEvents(cb, events[6], 313, 129);

    cb.setFontAndSize(bf, 7);
    Date today = new Date();
    DateFormat dfToday = new SimpleDateFormat("dd.MM.yyyy HH:mm", locale);
    cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, dfToday.format(today), 570, 22, 0);

    cb.endText();

    // Titeltabelle
    cb.setLineWidth(1f);
    cb.moveTo(30, 821);
    cb.lineTo(30, 732);

    cb.setLineWidth(1f);
    cb.moveTo(30, 821);
    cb.lineTo(570, 821);

    cb.setLineWidth(1f);
    cb.moveTo(30, 732);
    cb.lineTo(570, 732);

    cb.setLineWidth(1f);
    cb.moveTo(570, 821);
    cb.lineTo(570, 732);

    // Datentabelle
    cb.setLineWidth(1f);
    cb.moveTo(30, 730);
    cb.lineTo(30, 31);

    cb.setLineWidth(1f);
    cb.moveTo(30, 730);
    cb.lineTo(570, 730);

    cb.setLineWidth(1f);
    cb.moveTo(30, 31);
    cb.lineTo(570, 31);

    cb.setLineWidth(1f);
    cb.moveTo(570, 730);
    cb.lineTo(570, 31);

    cb.stroke();

    // Linien innen
    cb.setLineWidth(0.5f);
    cb.moveTo(300, 730);
    cb.lineTo(300, 31);

    cb.moveTo(30, 718);
    cb.lineTo(570, 718);

    cb.moveTo(30, 504);
    cb.lineTo(570, 504);

    cb.moveTo(30, 492);
    cb.lineTo(570, 492);

    cb.moveTo(30, 276);
    cb.lineTo(570, 276);

    cb.moveTo(30, 264);
    cb.lineTo(570, 264);

    cb.moveTo(300, 151);
    cb.lineTo(570, 151);

    cb.moveTo(300, 139);
    cb.lineTo(570, 139);

    cb.stroke();

    document.close();
    return baos.toByteArray();
  }

  private void insertEvents(PdfContentByte cb, List<String> dayEvents, int col, int row) {
    if (dayEvents != null) {
      for (String event : dayEvents) {
        cb.setTextMatrix(col, row);
        cb.showText(event);
        row = row - 12;
      }
    }
  }

  private void createMiniCalendar(PdfContentByte cb, BaseFont bf, BaseFont boldBf, Calendar cal, boolean first) {
    cb.beginText();
    cb.setFontAndSize(bf, 7);
   
    int zeilen = 795;
    int col = 320;
    if (!first) {
      col = 450;
    }
    
    DateFormatSymbols dfs = new DateFormatSymbols(locale);
    String[] weekDayNames = dfs.getShortWeekdays();
    for (int i = 0; i < weekDayNames.length; i++) {
      if (StringUtils.isNotBlank(weekDayNames[i])) {
        weekDayNames[i] = weekDayNames[i].substring(0,1);
      }      
    }
    
    cb.setTextMatrix(col, zeilen);
    DateFormat df = new SimpleDateFormat("MMMM yyyy", locale);
    cb.showTextAligned(PdfContentByte.ALIGN_CENTER, df.format(cal.getTime()), col + 44, zeilen + 10, 0);
    cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, weekDayNames[Calendar.MONDAY] + " ", col + 12, zeilen, 0);
    cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, weekDayNames[Calendar.TUESDAY] + " ", col + 24, zeilen, 0);
    cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, weekDayNames[Calendar.WEDNESDAY] + " ", col + 36, zeilen, 0);
    cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, weekDayNames[Calendar.THURSDAY] + " ", col + 48, zeilen, 0);
    cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, weekDayNames[Calendar.FRIDAY] + " ", col + 60, zeilen, 0);
    cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, weekDayNames[Calendar.SATURDAY] + " ", col + 72, zeilen, 0);
    cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, weekDayNames[Calendar.SUNDAY] + " ", col + 84, zeilen, 0);

    int maxday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

    Calendar calstart = (Calendar)cal.clone();
    calstart.set(Calendar.DAY_OF_MONTH, 1);
    int tag = calstart.get(Calendar.DAY_OF_WEEK);
    int leer;
    if (tag == Calendar.SUNDAY) {
      leer = 6;
    } else {
      leer = tag - 2;
    }
    int geschrieben = 0;
    for (int i = 0; i < leer; i++) {
      col = col + 12;
      geschrieben++;
    }
    int day = 1;
    zeilen = zeilen - 10;
    
    cb.setColorFill(Color.GRAY);
    Calendar weekCal = new GregorianCalendar(calstart.get(Calendar.YEAR), calstart.get(Calendar.MONTH), day);
    int weekCol = 0;
    if (first) {
      weekCol = 320;
    } else {
      weekCol = 450;
    }
    cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, String.valueOf(weekCal.get(Calendar.WEEK_OF_YEAR)), weekCol-5, zeilen, 0);
    cb.setColorFill(Color.BLACK);
    
    while (day <= maxday) {

      
      if (first) {
        List<Event> dayEvents = eventsArrayCurrentMonth[day];
        if ((dayEvents != null) && (!dayEvents.isEmpty())) {
          cb.setFontAndSize(boldBf, 7);
        } else {
          cb.setFontAndSize(bf, 7);  
        }
      } else {
        List<Event> dayEvents = eventsArrayNextMonth[day];
        if ((dayEvents != null) && (!dayEvents.isEmpty())) {
          cb.setFontAndSize(boldBf, 7);
        } else {
          cb.setFontAndSize(bf, 7);  
        }
      }
      
      
      if (day >= 10) {
        cb.setTextMatrix(col, zeilen);
        col = col + 12;
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, String.valueOf(day) + " ", col, zeilen, 0);        
      } else {
        cb.setTextMatrix(col, zeilen);
        col = col + 12;
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, " " + String.valueOf(day) + " ", col, zeilen, 0);
      }
      geschrieben++;
      day++;
      if (geschrieben == 7) {
        if (first) {
          col = 320;
        } else {
          col = 450;
        }
        zeilen = zeilen - 10;
        geschrieben = 0;
        
        cb.setColorFill(Color.GRAY);
        weekCal = new GregorianCalendar(calstart.get(Calendar.YEAR), calstart.get(Calendar.MONTH), day);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, String.valueOf(weekCal.get(Calendar.WEEK_OF_YEAR)), col-5, zeilen, 0);
        cb.setColorFill(Color.BLACK);
      }
    }
    cb.endText();
    
    
    if (first) {
      col = 320;
    } else {
      col = 450;
    }

    int weekCount = CalUtil.countWeeks(cal);
       
    cb.setLineWidth(0.5f);
    cb.moveTo(col, 793);
    cb.lineTo(col+84, 793);
    cb.moveTo(col, 793);
    cb.lineTo(col, 793 - ( weekCount * 10));
    
    cb.stroke();
  }
}
