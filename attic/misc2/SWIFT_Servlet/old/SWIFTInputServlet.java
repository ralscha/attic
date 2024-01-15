import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import COM.odi.*;
import COM.odi.util.*;
import java.text.*;

public class SWIFTInputServlet extends HttpServlet {

    private String[] dates;
    private Vector cols;
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");    
    private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm");    
    private final static SimpleDateFormat lastUpdateFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm:ss.SSS");    

    private final static String[] PARMSTR = {"from", "to", "branch", "orderby", "order"};
    private final static String ASCORDER = "ASC";
    private final static String DESCORDER = "DESC";

    private final static String COL_TOSN = "TOSN";
    private final static String COL_MT = "MT";
    private final static String COL_SENDER = "Sender";
    private final static String COL_PRIORITY = "Priority";
    private final static String COL_DUPLICATE = "Duplicate";
    private final static String COL_RECEIVER = "Receiver";
    private final static String COL_RECEIPTDATE = "Receipt Date";
    
    private Session session;
    private Database db;
    private Map siDatesMap, swiftInputMap;
    private Date lastUpdateDate;
    private Branches branches;
    
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        
        try {
            session.join();        
        } catch (ObjectStoreException oe) { 
            DiskLog.log(DiskLog.FATAL, getClass().getName(), "doGet " + oe);            
        }
        
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        printHeader(out, dates[dates.length-1], dates[dates.length-1], 
                    "ALL", (String)cols.elementAt(0), DESCORDER);
                    
        out.print("</BODY>");
        out.print("</HTML>");
        out.close();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Enumeration e;
        String hStr;
        Calendar fromDate = new GregorianCalendar();
        Calendar toDate   = new GregorianCalendar();
        String branch = null;
        String order  = null;
        String by     = null;
        String toDateStr = null;
        String fromDateStr = null;
        
        String name, value;

        Transaction tr = null;
        try {
            RessourceManager.getInstance().getLock();
            try {
                session.join();        
            } catch (ObjectStoreException oe) { 
                DiskLog.log(DiskLog.FATAL, getClass().getName(), "doPost " + oe);                            
            }
                    
            tr = Transaction.begin(ObjectStore.READONLY);                

            Enumeration values = req.getParameterNames();
            while(values.hasMoreElements()) {
                name = (String)values.nextElement();
                value = req.getParameterValues(name)[0];
                
                if (name.equals(PARMSTR[0])) {
                    ParsePosition pos = new ParsePosition(0);
                    fromDateStr = value;
                    Date help = dateFormat.parse(value, pos);
                    fromDate.setTime(help);
                } else if (name.equals(PARMSTR[1])) {
                    ParsePosition pos = new ParsePosition(0);
                    toDateStr = value;
                    Date help = dateFormat.parse(value, pos);
                    toDate.setTime(help);
                }
                
                else if (name.equals(PARMSTR[2]))
                    branch = value;
                else if (name.equals(PARMSTR[3]))
                    order  = value;
                else if (name.equals(PARMSTR[4]))
                    by     = value;                    
            }
           
            if (fromDate.after(toDate)) {
                Calendar h = fromDate;    fromDate = toDate;       toDate = h;
                String   s = fromDateStr; fromDateStr = toDateStr; toDateStr = s;
            }

            res.setContentType("text/html");
            PrintWriter out = res.getWriter();

            printHeader(out, fromDateStr, toDateStr, branch, order, by);
            printTableHeader(out, order, by);

            Object[] headers = searchData(fromDate, toDate, branch, order, by);
            
            if (headers != null) {
                for (int i = 0; i < headers.length; i++) {
                    SWIFTHeader shObj = (SWIFTHeader)headers[i];

                    out.print("<TR>");
                    out.print("<TD><A HREF=\"/Msg?TOSN="+shObj.getTOSN()+"\" TARGET=\"Msg\">"+shObj.getTOSN()+"</A></TD>");
                    out.print("<TD>"+shObj.getMessageType()+"</TD>");
                    out.print("<TD ALIGN=\"LEFT\">"+shObj.getAddressSender()+"</TD>");
                    if (shObj.getPriority().equalsIgnoreCase("U"))
                        out.print("<TD ALIGN=\"CENTER\">urgent</TD>");
                    else
                        out.print("<TD ALIGN=\"CENTER\">normal</TD>");

                    if (shObj.getDuplicate().equalsIgnoreCase("Y"))
                        out.print("<TD ALIGN=\"CENTER\">yes</TD>");
                    else
                        out.print("<TD ALIGN=\"CENTER\">no</TD>");

                    out.print("<TD ALIGN=\"LEFT\">"+shObj.getAddressReceiver()+"</TD>");
                    out.print("<TD>"+shObj.getProcCenter()+"</TD>");

                    out.print("<TD>"+datetimeFormat.format(shObj.getReceiveDate().getTime())+"</TD>");
                    out.print("</TR>"); 
                }
         	} else {
         	    out.print("<b>Nothing found</b>");
            }
            out.print("</TABLE></BODY></HTML>");
            out.close();                    
        } finally {
            tr.commit(ObjectStore.RETAIN_HOLLOW);
            RessourceManager.getInstance().releaseLock();
        }
        
  
    }

    public void printTableHeader(PrintWriter out, String ordercol, String by) {
        
        out.print("<TABLE CELLPADDING=3 WIDTH=0 BORDER=1 ALIGN=\"LEFT\">");
      	out.print("<TR>");
        
        for (int i = 0; i < cols.size(); i++) {
            String hStr = (String)cols.elementAt(i);
            
            if (hStr.equals("Receiver")) 
                out.print("<TH COLSPAN=\"2\" ALIGN=\"LEFT\">");
            else
                out.print("<TH ALIGN=\"LEFT\">");
            
            out.print(hStr);
            
            if (hStr.equals(ordercol)) {
                if (by.equals(ASCORDER)) 
                    out.print("&nbsp;<img src=\"up.gif\" width=13 height=13 border=0>");
                else
                    out.print("&nbsp;<img src=\"down.gif\" width=13 height=13 border=0>");
            }       
            out.print("</TH>");
        }
       	out.print("</TR>");
    }

    public void printHeader(PrintWriter out, String from, String to, String branch,
                            String order, String by) {
        String hStr;
                                        
        out.print("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
        out.print("<HTML>");
        out.print("<HEAD><TITLE>SWIFT Input</TITLE></HEAD>");       
        out.print("<BODY><FONT SIZE=\"+1\"><b>SWIFT Input</b></FONT>");
        
        
        out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"-1\">Last Update: ");
        out.print(lastUpdateFormat.format(lastUpdateDate));
        out.print("</font>");
        
        out.print("<HR>");
        out.print("<FORM ACTION=\"/SWIFTInput\" METHOD=\"POST\">");
        out.print("<table><tr><th align=\"LEFT\">from</th><th align=\"LEFT\">to</th><th align=\"LEFT\">branch</th>");
        out.print("<th colspan=\"2\" align=\"LEFT\">order by</th><th>&nbsp;</th></tr>");

        out.print("<tr>");        
	    out.print("<td><select name=\""+PARMSTR[0]+"\" size=\"1\">");	   
        for (int i = 0; i < dates.length; i++) {
            hStr = dates[i];
            if (hStr.equals(from))
                out.print("<option value=\""+hStr+"\" selected>"+hStr+"</option>");
            else
                out.print("<option value=\""+hStr+"\">"+hStr+"</option>");
        }
        out.print("</select></td>");        
        
        out.print("<td><select name=\""+PARMSTR[1]+"\" size=\"1\">");
        for (int i = 0; i < dates.length; i++) {
            hStr = dates[i];
            if (hStr.equals(to)) 
                out.print("<option value=\""+hStr+"\" selected>"+hStr+"</option>");
            else
                out.print("<option value=\""+hStr+"\">"+hStr+"</option>");
        }
        out.print("</select></td>");
        
        out.print("<td><select name=\""+PARMSTR[2]+"\" size=\"1\">");

        Iterator it = branches.valuesIterator();
        while(it.hasNext()) {
            Branch b = (Branch)it.next();
            hStr = b.getName();
            if (hStr.equals(branch))
                out.print("<option value=\""+b.getHTMLName()+"\" selected>"+b.getHTMLName()+"</option>");
            else
                out.print("<option value=\""+b.getHTMLName()+"\">"+b.getHTMLName()+"</option>");
        }
        out.print("</select></td>");
        
        
        out.print("<td><select name=\""+PARMSTR[3]+"\" size=\"1\">");
        for (int i = 0; i < cols.size(); i++) {
            hStr = (String)cols.elementAt(i);
            if (!hStr.equals(COL_RECEIPTDATE)) {
                if (hStr.equals(order))
                    out.print("<option value=\""+hStr+"\" selected>"+hStr+"</option>");
                else
                    out.print("<option value=\""+hStr+"\">"+hStr+"</option>");
            }
        }
        
        out.print("</select></td><td><select name=\""+PARMSTR[4]+"\" size=\"1\">");        
        if (by.equals(ASCORDER)) {
            out.print("<option value=\""+ASCORDER+"\" selected>ascending</option>");
            out.print("<option value=\""+DESCORDER+"\">descending</option></select>");
        } else {
            out.print("<option value=\""+ASCORDER+"\">ascending</option>");
            out.print("<option value=\""+DESCORDER+"\" selected>descending</option></select>");            
        }
    	out.print("</td><td><INPUT TYPE=\"SUBMIT\" VALUE=\"Show\"></td></tr></table>");

        out.print("</FORM><HR>");
    }


    private Object[] searchData(Calendar fromDate, Calendar toDate, 
                                  String branchStr, String order, String by) {
                
        OSVectorList resultCollection = new OSVectorList();
        Calendar from = new GregorianCalendar();
        from.setTime(fromDate.getTime());
        OSHashSet set;
        
        String fromStr;
        
        Branch branch = branches.getBranch(branchStr);
        if (branch == null) return null;
        
        long start = System.currentTimeMillis();         
        while(from.before(toDate) || from.equals(toDate)) {
            fromStr = dateFormat.format(from.getTime());            
            set = (OSHashSet)siDatesMap.get(fromStr);
            if (set != null) {
                Iterator it = set.iterator();
                while(it.hasNext()) {
                    SWIFTHeader sh = (SWIFTHeader)it.next();
                    if (branch.contains(sh.getProcCenter()))
                        resultCollection.add(sh);
                }
            }        
            from.add(Calendar.DATE, +1);
        }        
        
        DiskLog.log(DiskLog.INFO, getClass().getName(), "searchData  SEARCH " + (System.currentTimeMillis()-start));            

        BinaryPredicate bp = null;
        if (by.equals(ASCORDER)) {
            if (order.equals(COL_TOSN)) bp = new CompareUpTOSN();
            else if (order.equals(COL_MT)) bp = new CompareUpMT();
            else if (order.equals(COL_SENDER)) bp = new CompareUpSender();
            else if (order.equals(COL_PRIORITY)) bp = new CompareUpPriority();
            else if (order.equals(COL_DUPLICATE)) bp = new CompareUpDuplicate();
            else if (order.equals(COL_RECEIVER)) bp = new CompareUpReceiver(); 
        } else {
            if (order.equals(COL_TOSN)) bp = new CompareDownTOSN();
            else if (order.equals(COL_MT)) bp = new CompareDownMT();
            else if (order.equals(COL_SENDER)) bp = new CompareDownSender();
            else if (order.equals(COL_PRIORITY)) bp = new CompareDownPriority();
            else if (order.equals(COL_DUPLICATE)) bp = new CompareDownDuplicate();
            else if (order.equals(COL_RECEIVER)) bp = new CompareDownReceiver();
        }
        
        Object arr[] = new Object[resultCollection.size()];
        arr = resultCollection.toArray(); 
        if (bp != null)
            Sorting.quickSort(arr, 0, arr.length-1, bp);

        return arr;
    }

    private void initDates() {
        Set set = siDatesMap.keySet();        
        
        if (set.size() > 0) {        
            dates = new String[set.size()];
        
            Iterator it = set.iterator();
            int i = 0;
            while(it.hasNext())
                dates[i++] = (String)it.next();

            Sorting.quickSort(dates, 0, dates.length-1, new DDMMYYYString());
        }
    }

    public void init(ServletConfig config)
        throws ServletException {
        super.init(config);

        branches = new Branches();

        DiskLog.log(DiskLog.INFO, getClass().getName(), "init");            
        session = DbManager.createSession();                        
        session.join();
        
        if (!DbManager.isInitialized()) {
            DbManager.initialize(false);
        }
        
        db = Database.open(DbManager.DB_NAME, ObjectStore.READONLY);                
        
        Transaction tr = Transaction.begin(ObjectStore.READONLY);                
        siDatesMap = (OSHashMap)db.getRoot(DbManager.DATE_COLL_ROOT_STR);
        swiftInputMap = (OSTreeMapString)db.getRoot(DbManager.ROOT_STR);
        Long lastUpdate = (Long)db.getRoot(DbManager.LAST_UPDATE_ROOT_STR);
        lastUpdateDate = new Date(lastUpdate.longValue());

        initDates();

        tr.commit(ObjectStore.RETAIN_HOLLOW);
        
        cols = new Vector();
        cols.addElement(COL_TOSN);
        cols.addElement(COL_MT);
        cols.addElement(COL_SENDER);
        cols.addElement(COL_PRIORITY);
        cols.addElement(COL_DUPLICATE);
        cols.addElement(COL_RECEIVER);
        cols.addElement(COL_RECEIPTDATE);
    }


    public void destroy() {
        DiskLog.log(DiskLog.INFO, getClass().getName(), "destroy");            
        session.terminate();
    }

}
