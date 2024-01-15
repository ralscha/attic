package svgtest;

import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;

import org.apache.struts.action.*;

public class AuthorAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    DataSource ds = getDataSource(request);

    Connection con = ds.getConnection();

    Statement stmt = con.createStatement();

    response.setContentType("image/svg+xml");
    
    
    response.setHeader("extension", "svg");
    //response.setHeader("Content-disposition", "attachment;filename=author.svg");
              
    
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


    Calendar today = new GregorianCalendar();    
    int thisveryyear = today.get(Calendar.YEAR);
    
    String yearStr = request.getParameter("thisveryyear"); 
    if (yearStr != null) {
      thisveryyear = Integer.parseInt(yearStr);
    }

    String thisveryauthor = request.getParameter("thisveryauthor");
    if (thisveryauthor == null) {
       thisveryauthor = "connel";
     }

    //query fuer die abwesenheitsdaten
    String my_query = "select dayofmonth(t.anfang), month(t.anfang), year(t.anfang), ";
    my_query += " dayofmonth(t.ende), month(t.ende), year(t.ende), t.grund, ";
    my_query += " t.ort, t.firma, ";
    my_query += " a.vname, a.nname";
    my_query += " from svgtermine t, svgautoren a where";
    my_query += " year(t.anfang) = '" + thisveryyear + "'";
    my_query += " and t.aut_id = a.autnum";
    my_query += " and a.kuerzel = '" + thisveryauthor + "'";
    my_query += " order by month(t.anfang), dayofmonth(t.anfang)";

    //  aeusseres svg-element fuellen
    out.print("<rect width=\"100%\" height=\"100%\"");
    out.print(" style=\"fill: #ccccff; stroke: none;\"/>");
    out.print("<rect x=\"1%\" y=\"1%\" width=\"98%\" height=\"98%\"");
    out.print(" style=\"fill: #cccccc; stroke: #333366; stroke-width: 1.5;\"/>");
    //  das jahr als text
    out.print("<text x=\"320\" y=\"25\" class=\"jahr\">");
    out.print("urlaub und lesungen " + thisveryyear + "</text>");
    out.println();

    ResultSet rs = stmt.executeQuery(my_query);

    int autterm_y = 136;

    while (rs.next()) {

      String anfangDay = rs.getString(1);
      String anfangMonth = rs.getString(2);
      String anfangYear = rs.getString(3);
      String endeDay = rs.getString(4);
      String endeMonth = rs.getString(5);
      String endeYear = rs.getString(6);
      String grund = rs.getString(7);
      String ort = rs.getString(8);
      String firma = rs.getString(9);
      String vname = rs.getString(10);
      String nname = rs.getString(11);

      if (autterm_y == 136) {

        out.print("<text x=\"190\" y=\"112\" fill=\"#336699\">" + vname + " " + nname + "</text>");
        out.print("<line x1=\"190\" y1=\"118\" x2=\"600\" y2=\"118\"");
        out.println(" style=\"stroke-width: 1; stroke: white;\"/>");
      }
      out.print("<text x=\"290\" y=\"" + autterm_y);
      out.print("\" style=\"fill: #333399;\">");
      out.println(anfangDay + ". " + anfangMonth + ". " + anfangYear + "</text>");
      out.println("<text x=\"360\" y=\"" + autterm_y + "\" style=\"fill: #333399;\">bis</text>");
      out.print("<text x=\"390\" y=\"" + autterm_y + "\" style=\"fill: #333399;\">");
      out.println(endeDay + ". " + endeMonth + ". " + endeYear + "</text>");
      if ("U".equalsIgnoreCase(grund)) {
        out.print("<text x=\"480\" y=\"" + autterm_y + "\">[Urlaub]");
      } else {
        out.print("<text x=\"480\" y=\"" + autterm_y + "\">[" + firma + " in " + ort + "]");
      }
      out.println("</text>");
      out.println();

      autterm_y += 18;
    }

    out.println("</svg>");
    out.close();
    rs.close();
    stmt.close();
    con.close();

    return null;

  }

}
