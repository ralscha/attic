
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class Prg2Servlet extends HttpServlet {

	public void doPost(HttpServletRequest req, 
					   HttpServletResponse res) throws ServletException, IOException {
	
			res.setContentType("application/octet-stream");
			
    		ObjectInputStream ois = new ObjectInputStream(req.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(res.getOutputStream());

			Status status = new Status();
			try {
				List appList = (List)ois.readObject();
				Iterator it = appList.iterator();
				int no = 0;
				while(it.hasNext()) {
					Appointment appoint = (Appointment)it.next();
					no++;
				}
				status.setMessage("OK");
				status.setNo(no);
			} catch(ClassNotFoundException cnfe) {
				status.setMessage(cnfe.toString());				
			}
			
			oos.writeObject(status);
			
			oos.flush();
			oos.close();
			ois.close();
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);	
	}
	
}

    		
    		