import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import COM.odi.*;
import COM.odi.util.*;
import java.text.*;

public class SWIFTMissingServlet extends HttpServlet {

    private final static SimpleDateFormat lastUpdateFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm:ss.SSS");    

    private Session session;
    private Database db;
    private List missingSWIFTList;
    private Date lastUpdateDate;
    
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        
        try {
            session.join();        
        } catch (ObjectStoreException oe) { 
            DiskLog.log(DiskLog.FATAL, getClass().getName(), "doGet " + oe);            
        }
                        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
        out.println("<HTML><HEAD><TITLE>Fehlende SWIFT Input Messages</TITLE></HEAD>");
        out.println("<BODY><FONT SIZE=\"+1\"><b>Fehlende SWIFT Input Messages </b></FONT>");
        
        
        Transaction tr = Transaction.begin(ObjectStore.READONLY);                
        
        out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"-1\">Last Update: ");
        out.print(lastUpdateFormat.format(lastUpdateDate));
        out.print("</font>");
        
        out.print("<HR>");
        
        if (missingSWIFTList.size() > 0) {
            out.println("SWIFT(s) mit folgender FOSN fehlen:<p>");
            
            for (int i = 0; i < missingSWIFTList.size(); i++)
                out.print("<b>"+missingSWIFTList.get(i)+"</b><br>");

            out.println("<p>");
            out.println("Repetition mit SW41 an Transaktion LC55 (nicht an Drucker!)<br>");
            out.println("SWIFT-Support: "+AppProperties.getProperty("swiftSupportTel"));
        } else {
            out.println("Es fehlt kein SWIFT");
        }
        
        tr.commit(ObjectStore.RETAIN_HOLLOW);
        
        out.print("<HR>");
        out.print("</BODY></HTML>");
        out.close();
    }

    public void init(ServletConfig config)
        throws ServletException {
        super.init(config);

        DiskLog.log(DiskLog.INFO, getClass().getName(), "init");            
        session = DbManager.createSession();                        
        session.join();
        
        if (!DbManager.isInitialized()) {
            DbManager.initialize(false);
        }
        
        db = Database.open(DbManager.DB_NAME, ObjectStore.READONLY);                
        
        Transaction tr = Transaction.begin(ObjectStore.READONLY);                
        missingSWIFTList = (OSVectorList)db.getRoot(DbManager.MISSING_SWIFT_ROOT);

        Long lastUpdate = (Long)db.getRoot(DbManager.LAST_UPDATE_ROOT_STR);
        lastUpdateDate = new Date(lastUpdate.longValue());

        tr.commit(ObjectStore.RETAIN_HOLLOW);
    }


    public void destroy() {
        DiskLog.log(DiskLog.INFO, getClass().getName(), "destroy");            
        session.terminate();
    }

}
