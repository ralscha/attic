package ch.ess.ex4u.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isomorphic.datasource.DataSource;
import com.isomorphic.js.JSTranslater;
import com.isomorphic.servlet.DataSourceLoader;

public class ItemMasterDS extends DataSourceLoader {


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      DataSource ds = DataSource.fromXML(new InputStreamReader(getServletContext().getResourceAsStream("/ds/" + request.getParameter("dataSource") + ".ds.xml")));
      
      JSTranslater translator = new JSTranslater();
      System.out.println(translator.toJS(ds));
      
      PrintWriter pw = new PrintWriter(new OutputStreamWriter(response.getOutputStream()));
      pw.print(translator.toJS(ds));
      pw.close();
      
      
    } catch (Exception e) {
      
      e.printStackTrace();
    }
//    if ("userDMI".equals(request.getParameter("dataSource"))) {
//    
//    
//    }
    
    super.doGet(request, response);
    
  }
}
  

