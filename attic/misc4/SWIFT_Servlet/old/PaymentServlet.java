import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import COM.odi.*;
import COM.odi.util.*;
import COM.odi.util.query.*;
import java.text.*;
import java.math.*;

public class PaymentServlet extends HttpServlet {

    private Session session;
    private Database db;
    private Date lastUpdateDate;
    private List bookingList;
    
    private final static SimpleDateFormat lastUpdateFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm:ss.SSS");    
    private DecimalFormat form;    

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        printHeader(out, "");
        out.println("</BODY>");
        out.println("</HTML>");
        out.close();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        BigDecimal total = new BigDecimal(0.0d);
        BigDecimal zero = new BigDecimal(0.0d);
        
        String gtfNumber = "";
   	            
        Enumeration values = req.getParameterNames();
        while(values.hasMoreElements()) {            
            String name = (String)values.nextElement();
            String value = req.getParameterValues(name)[0];
            if (name.equals("gtfno")) {
                gtfNumber = value;
            }                
        }
       
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
    
        printHeader(out, gtfNumber);                              
        out.println("<table border=\"1\">");
        out.println("<tr>");
        out.println("	<th>Konto</th>");
        out.println("	<th>Auftragsnummer</th>");
        out.println("	<th>Text</th>");
        out.println("	<th>Betrag</th>");
        out.println("	<th>Valuta</th>");
        out.println("</tr>");

        try {
            session.join();        
        } catch (ObjectStoreException oe) { 
            DiskLog.log(DiskLog.FATAL, getClass().getName(), "doPost " + oe);                            
        }

        boolean book = false;
        Transaction tr = Transaction.begin(ObjectStore.READONLY);   
        try {
            Iterator it = bookingList.iterator();
            while(it.hasNext()) {
                Map map = (Map)it.next();
                List li = (List)map.get(gtfNumber);                
                if (li != null) {
                    Iterator itl = li.iterator();
                    while(itl.hasNext()) {
                        Booking b = (Booking)itl.next();
                        
                        out.println("<tr>");
                        out.println("<td>"+b.getAccount()+"</td>");
                        out.println("<td>"+b.getOrderno()+"</td>");
                        out.println("<td>"+b.getText()+"</td>");
                        out.println("<td align=\"RIGHT\">"+form.format(b.getAmount().doubleValue())+"</td>");
                        out.println("<td>"+b.getValueDate()+"</td>");
                        total = total.add(b.getAmount());
                        out.println("</tr>");
                        book = true;
                    }
                }                
            }
            if (book) {
                out.println("<tr>");
                out.println("<td align=\"RIGHT\" colspan=\"3\"><b>TOTAL</b></td>");
                out.println("<td align=\"RIGHT\"><b>"+form.format(total.doubleValue())+"</b></td>");
                out.println("<td>&nbsp;</td>");
                out.println("</tr>");
            }
            
            
        } finally {
            tr.commit(ObjectStore.RETAIN_HOLLOW);
        }

        out.println("</table>");
        out.println("</BODY>");
        out.println("</HTML>");
        out.close();
    }

    public void printHeader(PrintWriter out, String value) {
        
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
        out.println("<HTML><HEAD><TITLE>Zahlungen</TITLE></HEAD>");
        out.println("<BODY><FONT SIZE=\"+1\"><b>Zahlungen</b></FONT>");
        out.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"-1\">Last Update: ");
        out.print(lastUpdateFormat.format(lastUpdateDate));
        out.println("</font>");
        out.println("<HR>");
        out.println("<FORM ACTION=\"/Payments\" METHOD=\"POST\">");

        out.println("<P>Gtf Number&nbsp;&nbsp;<INPUT TYPE=\"TEXT\" NAME=\"gtfno\" VALUE=\""+value+"\" SIZE=\"16\">");
        out.println("<input type=\"Submit\" value=\"Show\">");
        out.println("</form>");
        out.println("<HR>");    
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DiskLog.log(DiskLog.INFO, getClass().getName(), "init");            
        
        session = DbManager.createSession();                        
        session.join();
               
        
        if (!DbManager.isBookingDBInitialized()) {
            DbManager.initialize_bookingdb(false);
        }
        
        db = Database.open(DbManager.BOOKING_DB_NAME, ObjectStore.READONLY);                
        
        Transaction tr = Transaction.begin(ObjectStore.READONLY);                
        bookingList = (OSVectorList)db.getRoot(DbManager.BOOKING_ROOT);
        Long lastUpdate = (Long)db.getRoot(DbManager.LAST_UPDATE_ROOT_STR);
        lastUpdateDate = new Date(lastUpdate.longValue());
        tr.commit(ObjectStore.RETAIN_HOLLOW);

        form = new DecimalFormat("#,##0.00");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator('\'');
        form.setDecimalFormatSymbols(dfs);

        
    }

    public void destroy() {
        DiskLog.log(DiskLog.INFO, getClass().getName(), "destroy");            
        session.terminate();
    }        
}

