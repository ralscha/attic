package svgtest;

import java.sql.*;

import javax.servlet.http.*;
import javax.sql.*;

import org.apache.struts.action.*;

public class TestAction extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
     DataSource ds = getDataSource(request);
     
     Connection con = ds.getConnection();
     
     
     Statement stmt = con.createStatement();
     
     ResultSet rs = stmt.executeQuery("select * from svgautoren");
     
     while (rs.next()) {
       System.out.println(rs.getString("nname"));
     }
     
     rs.close();
     stmt.close();
     con.close();
     
     return mapping.findForward("success");

  }

}
