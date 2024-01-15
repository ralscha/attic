import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.*;
import COM.odi.*;
import COM.odi.util.*;

import swift.*;

public class SWIFTtosnServlet extends HttpServlet {

    private SWIFTHeader  shObj;
    private MessageLine  mlObj;

    private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm");    
    private final static SimpleDateFormat dateFormat     = new SimpleDateFormat("dd.MM.yyyy");    

    private Session session;
    private Database db;
    private Map swiftInputMap;
    private Map swiftAdrMap;

    public void doGet (HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        
        String adr;
        
        try {
            session.join();        
        } catch (ObjectStoreException oe) { 
            DiskLog.log(DiskLog.FATAL, getClass().getName(), "doGet " + oe);                        
        }

    	res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String tosn = req.getParameterValues("TOSN")[0];
        if (tosn == null) return;

    	out.print("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
    	out.print("<HTML><HEAD>");
    	out.print("<TITLE>SWIFT Input</TITLE>");
    	out.print("</HEAD><BODY>");    	    	
    	
        Transaction tr = null;
        try {
            tr = Transaction.begin(ObjectStore.READONLY);     
            shObj = (SWIFTHeader)swiftInputMap.get(tosn);            
                                    
            if (shObj != null) {
                SWIFT_MT7 swiftMT = SWIFT_MT7.getSWIFTMT(shObj.getMessageType());        

                out.print("<FONT SIZE=\"+1\"><b>SWIFT Input</b> (TOSN "+tosn+"&nbsp;&nbsp;ID ");
                out.print(shObj.getSessionNumber()+" "+shObj.getSequenceNumber()+")</FONT>");
                
                
                String addrSender = shObj.getAddressSender();
                
                out.print("<table><tr><td colspan=\"3\"><hr></td></tr><TR><TD>Sender</TD><TD><B>");
                out.print(addrSender);
                out.print("</B></td><td>Dispatch date: <b>");
                out.print(dateFormat.format(shObj.getSendDate().getTime()));
                out.print("</b></TD></TR>");
                
                out.print("<TR><td>&nbsp;</td><td colspan=\"2\">");
                adr = (String)swiftAdrMap.get(addrSender.substring(0,8)+addrSender.substring(9));
                if (adr != null)
                    out.print(adr);
                out.print("</td></tr><tr><td colspan=\"3\"><hr></td></tr>");
                                
                
                String addrReceiver = shObj.getAddressReceiver();
                
                out.print("<TR><TD>Receiver&nbsp;</TD><TD><B>");
                out.print(addrReceiver);
                out.print("</B> (");
                out.print(shObj.getProcCenter());
                out.print(")</td><td>Receipt date: <b>");
                out.print(datetimeFormat.format(shObj.getReceiveDate().getTime()));
                out.print("</b></TD></TR><tr><td>&nbsp;</td>");
                out.print("<td colspan=\"2\">");
                
                adr = (String)swiftAdrMap.get(addrReceiver);
                if (adr != null)
                    out.print(adr);
                
                out.print("</td></TR><tr><td colspan=\"3\"><hr></td>");
                out.print("</tr><TR><TD></TD><TD>Priority: <B>");

                
                if (shObj.getPriority().equals("U"))
                    out.print("urgent");
                else
                    out.print("normal");
                
                out.print("</B></TD><td>Possible duplicate: <B>");
                
                if (shObj.getDuplicate().equals("Y"))
                    out.print("yes");
                else
                    out.print("no");
                
                out.print("</B></td></tr><tr><td colspan=\"3\"><hr></td></tr></TABLE><p>");
                
                if (swiftMT != null)
                    out.print("<FONT SIZE=\"+1\">"+swiftMT.getMTName()+" (MT "+shObj.getMessageType()+")</FONT>");
                else
                    out.print("<FONT SIZE=\"+1\">MT "+shObj.getMessageType()+"</FONT>");                    
                out.print("<PRE>");


                StringBuffer line = new StringBuffer();
                String fTag;

                Iterator it = shObj.getMessage().iterator();
                while(it.hasNext()) {
                    mlObj = (MessageLine)it.next();
                    line.setLength(0);

                    fTag = mlObj.getFieldTag();
                                        
                    if (fTag.length() != 0) {
                        line.append("\n   <b>").append(fTag);
                        if (fTag.length() == 2)
                            line.append("   ");
                        else
                            line.append("  ");
                        if (swiftMT != null) {
                            line.append(swiftMT.getTagDescription(fTag)).append("</b>\n");                           
                            line.append("        ");
                            line.append(mlObj.getMsgLine());                                                
                        } else {
                            line.append("</b>").append(mlObj.getMsgLine());                                                                            
                        }
                        
                    } else {
                        line.append("        ");
                        line.append(mlObj.getMsgLine());                        
                    }
                    out.println(line.toString());                    
                }
                out.print("</PRE>");
                
            } else {
                out.print("<FONT SIZE=\"+2\"><B>SWIFT "+tosn+" NOT FOUND</B></FONT>");
            }  
        } finally {
            tr.commit(ObjectStore.RETAIN_HOLLOW);
        }

        
        out.print("</BODY></HTML>");
        out.close();
    }


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        DiskLog.log(DiskLog.INFO, getClass().getName(), "init");            
        
        session = DbManager.createSession();
        session.join();        

        if (!DbManager.isInitialized()) {
            DbManager.initialize(false);
        }
        
        db = Database.open(DbManager.DB_NAME, ObjectStore.READONLY);           

        Transaction tr = Transaction.begin(ObjectStore.READONLY);                                       
        swiftInputMap = (OSTreeMapString)db.getRoot(DbManager.ROOT_STR);
        swiftAdrMap   = (OSTreeMapString)db.getRoot(DbManager.SWIFT_ADR_ROOT);
        tr.commit(ObjectStore.RETAIN_HOLLOW);        
    }

    public void destroy() {
        DiskLog.log(DiskLog.INFO, getClass().getName(), "destroy");            
        session.terminate();
    }

}