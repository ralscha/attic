import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import COM.odi.*;
import COM.odi.util.*;
import COM.odi.util.query.*;
import java.text.*;

public class StateServlet extends HttpServlet {

    private StateDescriptionMap descriptionMap;
    private Session session;
    private Database db;
    private Set stateSet;
    private Date lastUpdateDate;
    private Branches branches;
    private OSVectorList colourList;
    private Query gtfNumberQuery;
    private CompareState compareState;
    
    private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm");    
    private final static SimpleDateFormat lastUpdateFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm:ss.SSS");    

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        printHeader(out, "", "all", "ALL");
        out.println("</BODY>");
        out.println("</HTML>");
        out.close();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {


        int gtfNumber = 0;
        String submit = null;
        String value, name;
        String branchStr,colour;
        String gtfNumberStr = "";
        State stateObj;
   	    int colourValue = 0;
   	    
   	    branchStr = colour = null;        
   	            
        Enumeration values = req.getParameterNames();
        while(values.hasMoreElements()) {            
            name = (String)values.nextElement();
            value = req.getParameterValues(name)[0];
            if (name.equals("gtfno")) {
                try {
                    gtfNumber = Integer.parseInt(value);
                    gtfNumberStr = value;
                } catch(NumberFormatException nfe) {
                    gtfNumber = 0;
                    gtfNumberStr = "";
                }
            } else if (name.equals("branches")) {
                branchStr = value;
            } else if (name.equals("colour"))
                
                colour = value;
                colourValue = 0;
                
                for (int i = 0; i < colourList.size(); i++) {
                    if (colour.equals(colourList.elementAt(i))) {
                        colourValue = i+1;
                        break;
                    }
                }
                
        }
       
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
    
        printHeader(out, gtfNumberStr, colour, branchStr);                              
                      
        out.println("<P>");

        out.println("<table border=\"1\">");
     	out.println("<TR>");
		out.println("<TH>Gtf Nr</TH>");
		out.println("<TH>Proc Center</TH>");
		out.println("<TH>Typ</TH>");
		out.println("<TH>Status</TH>");
    	out.println("<TH>Datum</TH>");
		out.println("<TH>Beschreibung</TH>");
    	out.println("</TR>");


        try {
            session.join();        
        } catch (ObjectStoreException oe) { 
            DiskLog.log(DiskLog.FATAL, getClass().getName(), "doPost " + oe);                            
        }
        
        Branch branch = branches.getBranch(branchStr);
        Transaction tr = Transaction.begin(ObjectStore.READONLY);   
        try {
            
            Collection queryResult;
            
            if (gtfNumber != 0) {
                FreeVariableBindings freeVB = new FreeVariableBindings();
                
                freeVB.put("gtfNumber", new Integer(gtfNumber));
                queryResult = gtfNumberQuery.select(stateSet, freeVB);                
                                
            } else {
                queryResult = stateSet;
            }
            
            
            Object arr[] = new Object[queryResult.size()];
            arr = queryResult.toArray(); 
            Sorting.quickSort(arr, 0, arr.length-1, compareState);
            
            
            for (int i = 0; i < arr.length; i++) {

                stateObj = (State)arr[i];
            
                if (branch.contains(stateObj.getProcCenter())) {                                
                    StateDescription sd = descriptionMap.getStateDescription(stateObj.getStateNum());
                    
                    if ((colourValue == 4) || (colourValue == sd.getColour())) {
                
                        switch(sd.getColour()) {
                            case 1 : out.println("<TR bgcolor=\"Lime\">"); break;
                            case 2 : out.println("<TR bgcolor=\"Yellow\">"); break;
                            case 3 : out.println("<TR bgcolor=\"Red\">"); break;
                            default:  out.println("<TR>");
                        }

                        out.println("<TD>"+stateObj.getGtfNumber()+"</TD>");
                        out.println("<TD>"+stateObj.getProcCenter()+"</TD>");
                        if (stateObj.getMessageType().equalsIgnoreCase("S"))
                    		out.println("<TD>SWIFT</TD>");
                    	else
            	            out.println("<TD>TELEX</TD>");

                        out.println("<TD>"+stateObj.getStateNum()+" "+stateObj.getStateTran()+"</TD>");
                        out.println("<TD>"+datetimeFormat.format(stateObj.getStateDate().getTime())+"</TD>");
        		        out.println("<TD>"+sd.getDescription()+"</TD>");
                       	out.println("</TR>");
                    }
                }
            }
        } finally {
            tr.commit(ObjectStore.RETAIN_HOLLOW);
        }

        out.println("</TABLE>");
        out.println("</BODY>");
        out.println("</HTML>");
        out.close();
    }

    public void printHeader(PrintWriter out, String value, String colour, String branch) {
        
        out.print("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">");
        out.print("<HTML>");
        out.print("<HEAD><TITLE>SWIFT/Telex Status</TITLE></HEAD>");       
        out.print("<BODY><FONT SIZE=\"+1\"><b>SWIFT/Telex Status</b></FONT>");
        
        
        out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"-1\">Last Update: ");
        out.print(lastUpdateFormat.format(lastUpdateDate));
        out.print("</font>");
        
        out.print("<HR>");

        out.println("<FORM ACTION=\"/State\" METHOD=\"POST\">");
        out.println("<P>Gtf Number&nbsp;&nbsp;<INPUT TYPE=\"TEXT\" NAME=\"gtfno\" VALUE=\""+value+"\" SIZE=\"7\">");
        
        out.println("&nbsp;&nbsp;");
        
        out.println("<select name=\"branches\" size=\"1\">");
        Iterator it = branches.valuesIterator();
        while(it.hasNext()) {
            Branch b = (Branch)it.next();
            String hStr = b.getName();
            if (hStr.equals(branch))
                out.print("<option value=\""+b.getHTMLName()+"\" selected>"+b.getHTMLName()+"</option>");
            else
                out.print("<option value=\""+b.getHTMLName()+"\">"+b.getHTMLName()+"</option>");
        }
        out.print("</select>");
        out.println("&nbsp;&nbsp;");

        for (int i = 0; i < colourList.size(); i++) {
            String hs = (String)colourList.elementAt(i);
            if (hs.equals(colour))
                out.println("<input type=\"Radio\" name=\"colour\" value=\""+hs+"\" checked>"+hs);
            else    
                out.println("<input type=\"Radio\" name=\"colour\" value=\""+hs+"\">"+hs);                
        }
       
        out.println("&nbsp;&nbsp;");
        out.println("<INPUT TYPE=\"SUBMIT\" NAME=\"Submit\" VALUE=\"Show\">");

        out.println("</FORM>");
        out.println("<HR>");
        
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DiskLog.log(DiskLog.INFO, getClass().getName(), "init");            
        
        compareState = new CompareState();
        
        colourList = new OSVectorList(4);
        colourList.add("green");
        colourList.add("yellow");
        colourList.add("red");
        colourList.add("all");
        
        descriptionMap = new StateDescriptionMap();
        branches = new Branches();

        session = DbManager.createSession();                        
        session.join();
        
        
        FreeVariables freeV = new FreeVariables();
        freeV.put("gtfNumber", int.class);
        gtfNumberQuery = new Query(State.class, "getGtfNumber() == gtfNumber", freeV);

        
        if (!DbManager.isInitialized()) {
            DbManager.initialize(false);
        }
        
        db = Database.open(DbManager.DB_NAME, ObjectStore.READONLY);                
        
        Transaction tr = Transaction.begin(ObjectStore.READONLY);                
        stateSet = (OSHashSet)db.getRoot(DbManager.STATE_ROOT);
        Long lastUpdate = (Long)db.getRoot(DbManager.LAST_UPDATE_ROOT_STR);
        lastUpdateDate = new Date(lastUpdate.longValue());
        tr.commit(ObjectStore.RETAIN_HOLLOW);
        
    }

    public void destroy() {
        DiskLog.log(DiskLog.INFO, getClass().getName(), "destroy");            
        session.terminate();
    }        
}

