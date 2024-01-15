package svgtest;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;

import org.apache.struts.action.*;

public class IndexAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    Calendar today = new GregorianCalendar();

    //    Datumsvariable
    int thisday = today.get(Calendar.DATE);
    int thismonth = today.get(Calendar.MONTH);
    int thisyear = today.get(Calendar.YEAR);
    int nextyear = thisyear + 1;
    ;

    int[] monate = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    String[] monatsnamen =
      new String[] {
        "januar",
        "februar",
        "märz",
        "april",
        "mai",
        "juni",
        "juli",
        "august",
        "september",
        "oktober",
        "november",
        "dezember" };

    DataSource ds = getDataSource(request);

    Connection con = ds.getConnection();

    Statement stmt = con.createStatement();

    response.setContentType("image/svg+xml");

    response.setHeader("extension", "svg");

    ServletOutputStream out = response.getOutputStream();

    out.println("<?xml version=\"1.0\" encoding=\"iso-8859-1\" standalone=\"yes\"?>");
    out.println();
    out.println("<?xml-stylesheet href=\"autoren.css\" type=\"text/css\"?>");
    out.println();

    out.println("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 20010904//EN\"");
    out.println(" \"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">");

    out.print("<svg width=\"100%\" height=\"100%\"");
    out.print(" xmlns=\"http://www.w3.org/2000/svg\"");
    out.print(" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");

    // jahr abfragen/bestimmen
    String yearStr = request.getParameter("thisveryyear");

    int thisveryyear;
    if (request.getParameter("thisveryyear") != null) {
      thisveryyear = Integer.parseInt(request.getParameter("thisveryyear"));
    } else {
      thisveryyear = thisyear;
    }

    //    wie viele aktive autor/inn/en
    String my_aquery = "select count(*) from svgautoren where aktiv=1 ";
    ResultSet rs = stmt.executeQuery(my_aquery);
    int howmanyauts = 0;
    if (rs.next()) {
      howmanyauts = rs.getInt(1);
    }
    rs.close();

    //    welche veranstaltungen
    String my_vquery = "select id, name, dayofyear(anfang), dayofyear(ende), ort ";
    my_vquery += " from svgveranstaltungen where year(anfang) = " + thisveryyear;
    my_vquery += " order by dayofyear(anfang) ";

    //    query fuer die abwesenheitsdaten
    String my_query = "select dayofyear(t.anfang), ";
    my_query += " dayofyear(t.ende), t.grund, ";
    my_query += " t.ort, t.firma, a.kuerzel, ";
    my_query += " a.vname, a.nname";
    my_query += " from svgtermine t, svgautoren a where";
    my_query += " year(t.anfang) = " + thisveryyear;
    my_query += " and t.aut_id = a.autnum";
    my_query += " order by a.kuerzel, month(t.anfang), dayofmonth(t.anfang)";

    //    y-wert fuer autor/in + y-schrittweite
    int auty = 60;
    int stepy = 20;
    //    autorenkuerzel
    String autk = "";
    String oldautk = "";
    //    anfang des rechtecks
    int xrectstart = 20;
    int yrectstart = 15;
    int yrectend = yrectstart + (howmanyauts * 20);

    //    aeusseres svg-element fuellen
    out.println("<rect width=\"100%\" height=\"100%\" style=\"fill: #ccccff; stroke: none;\"/>");
    out.println("<rect x=\"1%\" y=\"1%\" width=\"98%\" height=\"98%\" style=\"fill: #cccccc; stroke: #333366; stroke-width: 1.5;\"/>");
    //    das jahr als text
    out.println("<text x=\"320\" y=\"25\" class=\"jahr\">urlaub und lesungen " + thisveryyear + "</text>");
    out.println();

    // funktion zum zeichnen des rechtecks
    // innerhalb eines geschachtelten svg-Elements
    out.println("<svg width=\"97%\" height=\"97%\">");

    // rahmen

    do_calrect(out, monate, monatsnamen, xrectstart, yrectstart + 25, yrectend + 25, stmt, my_vquery);

    ResultSet rs2 = stmt.executeQuery(my_query);
    // ergebnis auslesen
    while (rs2.next()) {

      int anfangstag = rs2.getInt(1);
      int endtag = rs2.getInt(2);
      String grund = rs2.getString(3);
      String ort = rs2.getString(4);
      String firma = rs2.getString(5);
      String aut_kuerzel = rs2.getString(6);
      String avname = rs2.getString(7);
      String anname = rs2.getString(8);

      //    farbe: urlaub oder lesung
      String mycolour;

      if ("D".equalsIgnoreCase(grund)) {
        mycolour = "#666699";
      } else {
        mycolour = "#336633";
      }

      // abwesenheit ueber die jahresgrenze
      if (endtag < anfangstag) {
        endtag = 365;
      }

      // kuerzel und abwesenheit ausgeben
      autk = aut_kuerzel;
      int auty2 = auty + 5;
      // mindestbreite fuer abwesenheit
      int rwidth;
      if ((endtag - anfangstag) < 1) {
        rwidth = 2;
      } else {
        rwidth = 2 * (endtag - anfangstag);
      }
      // wenn neuer urlauber/lesereisender:
      if (!autk.equals(oldautk)) {
        auty += stepy;
        auty2 += 20;

        // linie vor dem einzeleintrag
        out.println("<line class=\"laengs\" x1=\"22\" y1=\"" + auty2 + "\" x2=\"818\" y2=\"" + auty2 + "\"/>");
        out.println();
        // autorennamen ausgeben
        out.println(
          "<a xlink:href=\"author.do?thisveryauthor="
            + aut_kuerzel
            + "&amp;thisveryyear="
            + thisveryyear
            + "\"><text "
            + " class=\"kuerzel\" id=\""
            + aut_kuerzel
            + "\" x=\"23\""
            + " y=\""
            + auty
            + "\">"
            + anname.toLowerCase()
            + "</text></a>");
        out.println();
        oldautk = autk;
      }

      out.println(
        "<rect x=\""
          + (90 + (2 * anfangstag))
          + "\" y=\""
          + (auty - 12)
          + "\" height=\"14\""
          + " width=\""
          + rwidth
          + "\""
          + " style=\"fill: "
          + mycolour
          + ";\">");
          
      if ("D".equalsIgnoreCase(grund)) {
        out.println(
          "<set attributeName=\"visibility\" xlink:href=\"#"
            + aut_kuerzel
            + anfangstag
            + "\"  begin=\"mouseover\" end=\"mouseout\""
            + " from=\"hidden\" to=\"visible\"/>");
      }
      out.println("</rect>");
      out.println();

      // Lesungsdaten aktivierbar 
      if ("D".equalsIgnoreCase(grund)) {
        out.println(
          "<text class=\"lesung\" x=\"820\" y=\"397\"" + " id=\"" + aut_kuerzel + anfangstag + "\">" + firma + " [" + ort + "]</text>");
        out.println();
      }

    }

    // legende schreiben
    do_legend(out, thisveryyear, thisyear, nextyear, 385, 397);

    out.println("</svg>");
    out.println("</svg>");
    out.close();
    rs2.close();

    stmt.close();
    con.close();

    return null;

  }

  //   rechteck fuer die daten und ein paar linien zeichnen
  private void do_calrect(
    ServletOutputStream out,
    int[] monate,
    String[] monatsnamen,
    int xrectstart,
    int yrectstart,
    int yrectend,
    Statement stmt,
    String query)
    throws SQLException, IOException {

    int firstline = 70;

    // rechteck um die ausgabedaten
    out.println();
    out.println(
      "<rect x=\""
        + xrectstart
        + "\" y=\""
        + yrectstart
        + "\" width=\"800\" height=\""
        + yrectend
        + "\" style=\"fill: rgb(204,204,204);"
        + " stroke: #333366; stroke-width: 1.5;\"/>");
    // waagerechte linien vor den daten
    out.println(
      "<line x1=\""
        + xrectstart
        + "\" y1=\""
        + (yrectstart + 20)
        + "\" x2=\"820\" y2=\""
        + (yrectstart + 20)
        + "\" style=\"fill: none; stroke: #ffffff; stroke-width: 1;\"/>");
    // erste senkrechte linie
    out.println();
    out.println(
      "<line x1=\""
        + (xrectstart + firstline)
        + "\" y1=\""
        + (yrectstart + 20)
        + "\" x2=\""
        + (xrectstart + firstline)
        + "\" y2=\""
        + (yrectend + 5)
        + "\""
        + " style=\"fill: none; stroke: #ffffff; stroke-width: 1;\"/>");

    xrectstart += firstline;
    do_events(out, xrectstart, yrectstart, yrectend, stmt, query);
    // senkrechte linien zwischen den monaten
    for (int i = 0; i < 12; i++) {
      int xrectend = xrectstart + (2 * monate[i]);
      if (i < 11) {
        out.println(
          "<line class=\"laengs\" x1=\""
            + xrectend
            + "\" y1=\""
            + (yrectstart + 20)
            + "\" x2=\""
            + xrectend
            + "\" y2=\""
            + (yrectend + 5)
            + "\"/>");
      }
      // monate ausgeben
      out.println(
        "<text class=\"monat\" x=\""
          + (xrectend - (2 + monate[i]) - 25)
          + "\" y=\""
          + (yrectstart + 15)
          + "\">"
          + monatsnamen[i]
          + "</text>");

      // veranstaltungen aus gesonderter tabelle ausgeben

      //do_events(out, xrectstart, yrectstart, yrectend, stmt, query);

      xrectstart = xrectend;
    } //ende der for-schleife
  } // ende von do_calcrect

  //   legende ausgeben
  private void do_legend(ServletOutputStream out, int thisveryyear, int thisyear, int nextyear, int ya, int yy) throws IOException {

    int refyear;
    String querystr;

    if (thisveryyear == nextyear) {
      querystr = "?thisveryyear=" + thisyear;
      refyear = thisyear;
    } else {
      querystr = "?thisveryyear=" + nextyear;
      refyear = nextyear;
    }

    out.println("<rect x=\"20\" y=\"" + ya + "\" width=\"70\"  height=\"15\"" + " style=\"fill: #336633; stroke: none;\"/>");
    out.println("<text x=\"27\" y=\"" + yy + "\"" + " style=\"fill: white; font-size: 12;\">urlaub etc.</text>");
    out.println("<rect x=\"110\" y=\"" + ya + "\" width=\"70\"  height=\"15\"" + " style=\"fill: #666699; stroke: none;\"/>");
    out.println("<text x=\"115\" y=\"" + yy + "\"" + " style=\"fill: white; font-size: 12;\">lesung etc.</text>");
    out.println(" <rect x=\"200\" y=\"" + ya + "\" width=\"70\"  height=\"15\"" + " style=\"fill: #cccc66; stroke: none;\"/>");
    out.println("<text x=\"210\" y=\"" + yy + "\"" + " style=\"fill: white; font-size: 12;\">messen...</text>");
    out.println(
      "<a xlink:href=\"index.do"
        + querystr
        + "\"><text x=\"295\" y=\""
        + yy
        + "\""
        + " style=\"fill: #000099; font-size: 12;\">"
        + "---&gt; daten für "
        + refyear
        + "</text></a>");
  }

  //   veranstaltungen ausgeben
  private void do_events(ServletOutputStream out, int xstart, int ystart, int yend, Statement stmt, String query)
    throws SQLException, IOException {

    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {

      String id = rs.getString(1);
      String name = rs.getString(2);
      int anfang = rs.getInt(3);
      int ende = rs.getInt(4);
      String ort = rs.getString(5);

      int xhere = xstart + (2 * anfang);
      out.println(
        "<rect x=\""
          + xhere
          + "\" y=\""
          + (ystart + 21)
          + "\" width=\""
          + (2 * (ende - anfang))
          + "\" height=\""
          + (yend - 55)
          + "\" style=\"fill: #cccc66;\">"
          + "<set attributeName=\"visibility\" xlink:href=\"#m"
          + id
          + "\" begin=\"mouseover\" end=\"mouseout\" from=\"hidden\" to=\"visible\"/>"
          + "</rect>");
      out.println("<text class=\"lesung\" x=\"820\" y=\"397\"" + " id=\"m" + id + "\">" + name + " [" + ort + "]</text>");

    }

    rs.close();
  }

}
