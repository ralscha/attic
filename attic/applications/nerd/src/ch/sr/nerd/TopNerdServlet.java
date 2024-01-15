

package ch.sr.nerd;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

public class TopNerdServlet extends HttpServlet {

	private List nerdList;
	private File nerdFile;

	private List nerdOptionList;
	
	private NerdPointsComparator npc = new NerdPointsComparator();
	private NerdNameComparator nnc = new NerdNameComparator();
	
	private synchronized void incPoints(String name) {
		Iterator it = nerdList.iterator();
	
		while (it.hasNext()) {
			Nerd tempNerd = (Nerd)it.next();
			if (tempNerd.getName().equals(name)) {
				tempNerd.incPoints();
				return;
			}
		}
	}
	
	private synchronized void decPoints(String name) {		
		Iterator it = nerdList.iterator();
	
		while (it.hasNext()) {
			Nerd tempNerd = (Nerd)it.next();
			if (tempNerd.getName().equals(name)) {
				if (tempNerd.getPoints() > 0)
					tempNerd.decPoints();
				return;
			}
		}
	}
	
	public void destroy() {
		super.destroy();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		
		// Get the session
		HttpSession session = req.getSession(true);

		// Does the session indicate this user already logged in?
		Object done = session.getAttribute("logon.isDone");  
		if (done != null) 
			log(done.toString());
		else
			log("NULL");
		// marker object
		if (done == null) {
			// No logon.isDone means he hasn't logged in.
			// Save the request URL as the true target and redirect 
			// to the login page.
			//log("REQ:"+HttpUtils.getRequestURL(req).toString());
			
			session.setAttribute("login.target", req.getScheme() + "://" + req.getServerName() + ":"+req.getServerPort()+"/servlet/TopNerd");
			res.sendRedirect(req.getScheme() + "://" + req.getServerName() + ":"+req.getServerPort() + "lohin.html");
			return;
		}
   
		printPage(out, null);	
		out.close();
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		String nerdName = null;
		String action = null;
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
	
		Enumeration values = req.getParameterNames();
		while (values.hasMoreElements()) {
			String name = (String) values.nextElement();
			String value = req.getParameterValues(name)[0];
	
			if (name.equals("Name")) {
				if (!exists(value)) {
					Nerd newNerd = new Nerd();
					newNerd.setName(value);
					nerdList.add(newNerd);
					rebuildNerdOptionList();
				}
			} else if (name.equals("IncPoints")) {
				if (nerdName != null) {
					incPoints(nerdName);
				} else {
					action = "IncPoints";
				}
			} else if (name.equals("DecPoints")) {
				if (nerdName != null) {
					decPoints(nerdName);
				} else {
					action = "DecPoints";
				}	
			} else if (name.equals("ChangePointsName")) {
				if (action != null) {
					if (action.equals("IncPoints"))
						incPoints(value);
					else if (action.equals("DecPoints"))
						decPoints(value);
				} else {
					nerdName = value;
				}
			} else if (name.equals("RemoveNerd")) {
				if (nerdName != null) {
					removeNerd(nerdName);
				} else {
					action = "RemoveNerd";
				}
			} else if (name.equals("RemoveNerdName")) {
				if (action != null) {
					removeNerd(value);
				} else {
					nerdName = value;
				}
			}
					
		}
		
		Collections.sort(nerdList, npc);	
		writeNerds();		
		printPage(out, nerdName);		
		out.close();
	}
	
	private synchronized boolean exists(String name) {
		Iterator it = nerdList.iterator();
	
		while (it.hasNext()) {
			Nerd tempNerd = (Nerd)it.next();
			if (tempNerd.getName().equals(name)) {
				return true;
			}
		}
	
		return false;
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		nerdList = Collections.synchronizedList(new ArrayList());
		nerdOptionList = Collections.synchronizedList(new ArrayList());

		String nerdFileName = config.getInitParameter("nerdFile");
		if (nerdFileName != null) {
			nerdFile = new File(nerdFileName);
			if (nerdFile.exists())
				readNerds();
		}
		rebuildNerdOptionList();	
	}

	private synchronized void printNerdOption(PrintWriter out, String selectName, String lastNerd) {
		StringBuffer sb = new StringBuffer();
		Iterator it = nerdOptionList.iterator();
	
		out.println("<select name=\""+selectName+"\">");
		while (it.hasNext()) {
			sb.setLength(0);
			Nerd nerd = (Nerd) it.next();
			sb.append("<option value=\"");
			sb.append(nerd.getName());
			if (lastNerd == null) {
				sb.append("\">");
			} else {
				if (nerd.getName().equals(lastNerd))
					sb.append("\" selected>");
				else 
					sb.append("\">");
			}
			sb.append(nerd.getName());
			sb.append("</option>");
			out.println(sb.toString());
		}
		out.println("</select>");	
	}

	private synchronized void printPage(PrintWriter out, String lastNerd) {
		out.println("<html><head><title>Nerds</title></head><body>");
		out.println("<div align=\"center\">");
		out.println("<H3><font face=\"Comic Sans MS\">Top Nerds</font></H3>");
		out.println("<table width=\"60%\" cellspacing=\"0\" cellpadding=\"5\" border=\"1\">");
		out.println("<tr>");
		out.println("	<th width=\"10%\"><font face=\"Comic Sans MS\">Pos.</font></th>");
		out.println("	<th width=\"70%\"><font face=\"Comic Sans MS\">Name</font></th>");
		out.println("	<th width=\"20%\"><font face=\"Comic Sans MS\">Points</font></th>");
		out.println("</tr>");
	
		Iterator it = nerdList.iterator();
		int pos = 1;
		int lastPos = 0;
		int lastPoint = -1;
		int wpos;
		
		StringBuffer sb = new StringBuffer();
			
		while (it.hasNext()) {
			sb.setLength(0);
			Nerd nerd = (Nerd) it.next();
			
			sb.append("<tr>\n");	
			if (lastPos != 0) {
				if (lastPoint == nerd.getPoints()) {
					sb.append("<td align=\"CENTER\">");
					
					if (lastPos == 1)
						sb.append("<b>");
					sb.append(lastPos);
					if (lastPos == 1)
						sb.append("</b>");
						
					sb.append("</td>\n");
					lastPoint = nerd.getPoints();
					wpos = lastPos;
				} else {
					sb.append("<td align=\"CENTER\">");
					
					if (pos == 1)
						sb.append("<b>");
					sb.append(pos);
					if (pos == 1)
						sb.append("</b>");
						
					sb.append("</td>\n");
					lastPos = pos;
					wpos = pos;
				}
				lastPoint = nerd.getPoints();
				
			} else {
				pos = 1;
				lastPoint = nerd.getPoints();
				lastPos = 1;
				sb.append("<td align=\"CENTER\">");
				if (pos == 1)
					sb.append("<b>");
				sb.append(pos);
				if (pos == 1)
					sb.append("</b>");
				sb.append("</td>\n");
				wpos = pos;
			} 
					
			sb.append("<td>");
			if (wpos == 1)
				sb.append("<b>");
			sb.append(nerd.getName());
			if (wpos == 1)
				sb.append("</b>");
	
			sb.append("</td>\n");
			sb.append("<td align=\"RIGHT\">");
			if (wpos == 1)
				sb.append("<b>");
			sb.append(nerd.getPoints());
			if (wpos == 1)
				sb.append("</b>");
			
			sb.append("</td>\n");
			sb.append("</tr>\n");
			out.print(sb.toString());
	
			pos++;	
				
		}
	
		out.println("</table><p>&nbsp;");
		out.println("<hr width=\"60%\">");
		
		
		if (!nerdList.isEmpty()) {
			out.println("<b>Points&nbsp;&nbsp;&nbsp;</b>");
			out.println("<FORM action=\"/servlet/TopNerd\" method=post>");		
			printNerdOption(out, "ChangePointsName", lastNerd);
			out.println("<input type=\"Submit\" name=\"IncPoints\" value=\"    +    \">");
			out.println("<input type=\"Submit\" name=\"DecPoints\" value=\"    -    \">");
			out.println("</FORM>");
			out.println("<hr width=\"60%\">");
		}
	
		
		out.println("<FORM action=\"/servlet/TopNerd\" method=post>");
		out.println("<input type=\"Text\" name=\"Name\">");
		out.println("<input type=\"Submit\" name=\"SubmitAddNewNerd\" value=\"Add New Nerd\">");
		out.println("</FORM>");
	
		if (!nerdList.isEmpty()) {
			out.println("<FORM action=\"/servlet/TopNerd\" method=post>");
			printNerdOption(out, "RemoveNerdName", null);
			out.println("<input type=\"Submit\" name=\"RemoveNerd\" value=\"Remove Nerd\">");
			out.println("</FORM>");
	
			out.println("<hr width=\"60%\">");
		}
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");			
	}
	
	private void readNerds() {
		if (nerdFile != null) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(nerdFile));
				String line;
				int pos;
				while((line = reader.readLine()) != null) {
	
					Nerd nerd = new Nerd();
					pos = line.indexOf(";");
													
					nerd.setName(line.substring(0, pos));
					nerd.setPoints(Integer.parseInt(line.substring(pos+1)));
					
					nerdList.add(nerd);
				}
				reader.close();
			} catch (IOException ioe) {
				System.err.println(ioe);
			}
		}
	}

	private synchronized void removeNerd(String name) {	
		ListIterator it = nerdList.listIterator();	
		while (it.hasNext()) {
			Nerd tempNerd = (Nerd)it.next();
			if (tempNerd.getName().equals(name)) {
				it.remove();
				break;
			}
		}
		rebuildNerdOptionList();
	}

	private void rebuildNerdOptionList() {
		nerdOptionList.clear();
		nerdOptionList.addAll(nerdList);
		Collections.sort(nerdOptionList, nnc);
	}

	private synchronized void writeNerds() {	
		if (nerdFile != null) {
			try {
				PrintWriter writer = new PrintWriter(new FileWriter(nerdFile));
				if (writer != null) {
					Iterator it = nerdList.iterator();
					while (it.hasNext()) {
						Nerd nerd = (Nerd) it.next();
						writer.println(nerd.toString());
					}
					writer.close();
				}
			} catch (IOException ioe) {
				System.err.println(ioe);
			}
		}
	}

}