import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class FileIncludeServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
            
            String head, file, tail;
            String name, value;
            
            
            head = getInitParameter("Head");
            file = getInitParameter("File");
            tail = getInitParameter("Tail");
            
            res.setContentType("text/html");
            PrintWriter out = res.getWriter();
            
            try {
                BufferedReader br = new BufferedReader(new FileReader(head));
                writeFile(out, br);
                br.close();
                br = new BufferedReader(new FileReader(file));
                writeFile(out, br);
                br.close();
                br = new BufferedReader(new FileReader(tail));
                writeFile(out, br);
                br.close();
            } catch (Exception e) {
                out.print("<HTML><HEAD><TITLE>Panic</TITLE></HEAD>");       
                out.print("<BODY>");
                out.print(e);
                out.println("</BODY></HTML>");
            }            
            out.close();            
    }

    private void writeFile(PrintWriter out, BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            out.println(line);
        }
    }
    
    
    public void init(ServletConfig config)
        throws ServletException {
        super.init(config);

    }


    public void destroy() {
    }

}
