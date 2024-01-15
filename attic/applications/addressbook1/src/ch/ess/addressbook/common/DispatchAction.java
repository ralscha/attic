package ch.ess.addressbook.common;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.*;
import org.apache.struts.action.*;

import ch.ess.addressbook.resource.*;
import ch.ess.tag.table.*;
import ch.ess.util.attr.*;


/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.1 $ $Date: 2003/03/31 06:38:35 $
 * @author $Author: sr $
 */
public abstract class DispatchAction extends Action {

  private final static String REQUEST_PARAMETER = "action";


  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
                               HttpServletResponse response) throws Exception {

    HttpSession session = request.getSession();
    Session sess = null;
    ActionForward forward = null;

    String parameter = mapping.getParameter();
    if (parameter == null) {
      parameter = request.getParameter(REQUEST_PARAMETER);    
    }
    
    try {
      
      if ("edit".equals(parameter)) {
        sess = HibernateManager.open(request);  
        forward = edit(mapping, form, request, session, sess);
        HibernateManager.commit(sess);
      } else if ("add".equals(parameter)) {
        forward = add(mapping, form, request, session);       
      } else if ("store".equals(parameter)) {
        sess = HibernateManager.open(request);
        String storeAddAction = request.getParameter("storeadd");
        if ((storeAddAction != null) && (!"".equals(storeAddAction.trim()))) {
          forward = storeAndAdd(mapping, form, request, session, sess);
        } else {
          forward = store(mapping, form, request, session, sess);
        }
      } else if ("delete".equals(parameter)) {
        sess = HibernateManager.open(request);
        forward = delete(mapping, form, request, session, sess);
        HibernateManager.commit(sess);          
      }
      
      
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(sess);
      throw e;
    } finally {
      HibernateManager.finallyHandling(sess);
    }       
      

    return forward;
  }


  protected ActionForward storeAndAdd(ActionMapping mapping, ActionForm form, 
                                      HttpServletRequest request, HttpSession session,
                                      Session sess)
                               throws Exception {

    ActionForward forward = store(mapping, form, request, session, sess);

    //if there are no errors goto add    
    ActionErrors errors = (ActionErrors)request.getAttribute(Globals.ERROR_KEY);

    if ((errors == null) || (errors.isEmpty())) {
      form.reset(mapping, request);
      forward = add(mapping, form, request, session);
    }

    return forward;
  }


  protected abstract ActionForward store(ActionMapping mapping, ActionForm form, 
                                         HttpServletRequest request, HttpSession session,
                                         Session sess)
                                  throws Exception;


  protected abstract ActionForward delete(ActionMapping mapping, ActionForm form, 
                                          HttpServletRequest request, HttpSession session, 
                                          Session sess)
                                   throws Exception;


  protected abstract ActionForward edit(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, HttpSession session,
                                        Session sess)
                                 throws Exception;


  protected abstract ActionForward add(ActionMapping mapping, ActionForm form, 
                                       HttpServletRequest request, HttpSession session)
                                throws Exception;




  

  
  protected int getRow(HttpServletRequest request, JSPTableModel tableModel) {

    String rowStr = request.getParameter("row");
    int row = -1;

    if (rowStr != null) {

      try {
        row = Integer.parseInt(rowStr);
      } catch (NumberFormatException nfe) {
      }
    }
    
    if (tableModel != null) {    
      if (row >= 0) {
        tableModel.setAttribute(new Attr("row", row));  
      } else {
        
        Attr attr = tableModel.getAttribute("row");
        if (attr != null) {
          row = attr.getIntValue();
        }
      }      
    }    
    

    return row;
  }
  

  protected Long getId(HttpServletRequest request, String tableModelName, int col) {
    JSPTableModel tableModel = (JSPTableModel) request.getSession().getAttribute(tableModelName);
    int row = getRow(request, tableModel);  
      
    return (Long) tableModel.getValueAt(row, col);
  
  }

  
}