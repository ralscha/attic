package ch.ess.testtracker.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.seam.Component;
import org.jboss.seam.servlet.ContextualHttpServletRequest;
import org.jboss.seam.web.ServletContexts;
import ch.ess.testtracker.entity.Principal;

public class UploadTestServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    new ContextualHttpServletRequest(req) {

      @Override
      public void process() throws Exception {
        Session session = (Session)Component.getInstance("hibernateSession");

        Transaction trx = session.beginTransaction();
        List<Principal> principals = session.createCriteria(Principal.class).list();
        for (Principal principal : principals) {
          principal.setEmail("test@test.ch");
          session.update(principal);
          System.out.println(principal.uid());
        }
        trx.commit();
        
//        String msg = ServletContexts.getInstance().getRequest().getParameter("testId");
//        System.out.println(msg);
        
        
        
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Parse the request
        List<FileItem> items = upload.parseRequest(ServletContexts.getInstance().getRequest());
        for (FileItem fileItem : items) {
          if (!fileItem.isFormField()) {
            //System.out.println(fileItem.getString());
            System.out.println(fileItem.getContentType());
            fileItem.delete();
          }
        }
        
      }

    }.run();

  }

}
