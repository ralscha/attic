
package ch.ess.pbroker.model;

import com.objectmatter.bsf.*;
import ch.ess.taglib.table.*;
import ch.ess.pbroker.db.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

public abstract class ModelCreator {
	public abstract JSPTableModel create(Database db, HttpServletRequest  request);	

  protected JSPTableModel createJSPTableModel(HttpServletRequest  request, TableModel model) {
	JSPTableModel tableModel = null;

	HttpSession session = request.getSession();

	if (session != null) {
	  tableModel = (JSPTableModel)session.getAttribute("model");
	  if (tableModel != null) {       
		tableModel.setModel(model);
	  }    
	}

	if (tableModel == null) {
	  tableModel = new JSPTableModel(model);
	  if (session != null)
		session.setAttribute("model", tableModel);
	}

	return tableModel;  
  }    
}