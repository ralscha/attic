package ch.sr.lotto.producer;

import java.io.*;
import java.util.*;

import ch.sr.lotto.db.*;
import ch.sr.lotto.statistic.*;
import com.objectmatter.bsf.*;

public class ZiehungenProducer extends Producer {

  //Properties
  //jahr     int   JJJJ (wenn fehlt, alle Jahre)
  //asc      true/false
  //withDate true/false
  //joker    true/false
  //extrajoker true/false

  public void producePage(Database db, Properties props, AusspielungenStatistiken as) {

    try {
      boolean asc = true;
      int year = -1;
      boolean withDate = true;
      boolean joker = false;
      boolean extrajoker = false;

      if (props != null) {
        Boolean ascBoolean = (Boolean) props.get("asc");
        if (ascBoolean != null) {
          asc = ascBoolean.booleanValue();
        }
        Integer yearInteger = (Integer) props.get("year");
        if (yearInteger != null) {
          year = yearInteger.intValue();
        }
        Boolean wdBoolean = (Boolean) props.get("withDate");
        if (wdBoolean != null) {
          withDate = wdBoolean.booleanValue();
        }
        Boolean jokerBoolean = (Boolean) props.get("joker");
        if (jokerBoolean != null) {
          joker = jokerBoolean.booleanValue();
        }
        Boolean extrajokerBoolean = (Boolean) props.get("extrajoker");
        if (extrajokerBoolean != null) {
          extrajoker = extrajokerBoolean.booleanValue();
        }        
      }

      OQuery query = new OQuery();

      if (year > 0) {
        query.add(year, "year");
      }

      if (asc) {
        query.addOrder("year", OQuery.ASC);
        query.addOrder("no", OQuery.ASC);
      } else {
        query.addOrder("year", OQuery.DESC);
        query.addOrder("no", OQuery.DESC);
      }

      Draw[] draws = (Draw[]) db.get(Draw.class, query);

      if (year > 0) {
        PrintWriter pw = writeHTMLHeader(year);
        writeHead(pw, draws[0], withDate, joker, extrajoker);

        for (int i = 0; i < draws.length; i++) {
          writeZiehung(pw, draws[i], withDate, joker, extrajoker);
        }

        writeTail(pw);
        pw.close();
      } else {

        Draw draw = draws[0];
        int mjahr = draw.getYear().intValue();

        PrintWriter pw = writeHTMLHeader(mjahr);
        writeHead(pw, draw, withDate, joker, extrajoker);
        writeZiehung(pw, draw, withDate, joker, extrajoker);

        for (int i = 1; i < draws.length; i++) {
          draw = draws[i];
          if (draw.getYear().intValue() != mjahr) {
            writeTail(pw);
            pw.close();

            mjahr = draw.getYear().intValue();
            pw = writeHTMLHeader(mjahr);
            writeHead(pw, draw, withDate, joker, extrajoker);
          }
          writeZiehung(pw, draw, withDate, joker, extrajoker);
        }
        
        writeTail(pw);
        pw.close();
      }

    } catch (Exception fe) {
      System.err.println(fe);
    }
  }

  private PrintWriter writeHTMLHeader(int year) throws IOException, FileNotFoundException {
    PrintWriter pw =
      new PrintWriter(new BufferedWriter(new FileWriter(lottoPath + "Z" + year + ".shtml")));

    pw.println("<html>");
    pw.println("<head>");
    pw.print("<title>Schweizer Zahlenlotto: Ziehungen ");
    pw.print(year);
    pw.println("</title>");
    pw.println("<link rel=stylesheet type=\"text/css\" href=\"include/lotto.css\">");
    pw.println("</head>");
    pw.println("<!--#include file=\"include/body.shtml\"-->");
    return pw;
  }

  private void writeHead(PrintWriter pw, Draw zie, boolean withDate, boolean joker, boolean extrajoker) {
    pw.println("<br>");
    pw.println("<p class=\"subTitle\">Ziehungen ");
    pw.print(zie.getYear());
    pw.println("</p>");
    pw.println("<br>");
    pw.println("<table border=\"1\" class=\"tableContents\">");

    pw.println("<tr>");
    pw.println("<th align=\"RIGHT\" class=\"tableHeader\">Nr.</th>");
    pw.println("<th></th>");

    if (withDate) {
      pw.println("<th align=\"center\" class=\"tableHeader\">Datum</th>");
      pw.println("<th></th>");
    }
    pw.println("<th colspan=\"6\" class=\"tableHeader\">Gewinnzahlen</th>");
    pw.println("<th></th>");
    pw.println("<th align=\"RIGHT\" class=\"tableHeader\">Zz</th>");

    if (joker) {
      pw.println("<th></th>");
      pw.println("<th align=\"center\" class=\"tableHeader\">Joker</th>");
    }
    if (extrajoker) {
      pw.println("<th></th>");
      pw.println("<th align=\"center\" class=\"tableHeader\">ExtraJoker</th>");
    
    }

    pw.println("</tr>");
  }

  private void writeTail(PrintWriter pw) {
    pw.println("</table>");
    pw.println("<!--#include file=\"include/tail.shtml\"-->");
  }

  private void writeZiehung(PrintWriter pw, Draw zie, boolean withDate, boolean joker, boolean extrajoker) {
    pw.println("<tr>");

    pw.print("<th align=\"right\" class=\"textb\">");
    pw.print(zie.getNo());
    pw.println("</th>");
    pw.println("<td></td>");

    if (withDate) {

      pw.print("<TD align=\"center\" class=\"text\">");

      if (zie.isWednesday())
        pw.print("Mi,&nbsp;");
      else
        pw.print("Sa,&nbsp;");

      pw.print(dateFormat.format(zie.getDrawdate()));
      pw.println("</TD>");
      pw.println("<td></td>");
    }

    int zahlen[] = zie.getNumbers();
    for (int i = 0; i < zahlen.length; i++) {
      pw.print("<td align=\"right\" class=\"text\">");
      pw.print(zahlen[i]);
      pw.println("</td>");
    }
    pw.println("<td></td>");

    pw.print("<td align=\"right\" class=\"tc1\">");
    pw.print(zie.getAddnum());
    pw.println("</td>");

    if (joker) {
      pw.println("<td></td>");
      pw.print("<td align=\"center\" class=\"text\">");
      pw.print(zie.getJoker());
      pw.println("</td>");
    }
    if (extrajoker) {
      pw.println("<td></td>");
      pw.print("<td align=\"center\" class=\"text\">");
      String ej = zie.getExtrajoker();
      if (ej != null) {
        pw.print(ej);
      } else {
        pw.print("&nbsp;");
      }
      pw.println("</td>");
    }    

    pw.print("</tr>");
  }

}