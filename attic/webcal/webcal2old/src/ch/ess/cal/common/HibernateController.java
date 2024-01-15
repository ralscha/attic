package ch.ess.cal.common;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.tiles.*;

import ch.ess.cal.resource.*;

public abstract class HibernateController implements Controller {

  public void perform(
    ComponentContext tileContext,
    HttpServletRequest request,
    HttpServletResponse response,
    ServletContext servletContext)
    throws ServletException, IOException {

    Session sess = null;
    Transaction tx = null;
    try {
      sess = HibernateManager.open(request);
      tx = sess.beginTransaction();

      perform(tileContext, request, response, servletContext, sess);

      tx.commit();
    } catch (HibernateException e) {
      HibernateManager.exceptionHandling(tx);
      throw new ServletException(e);
    } finally {
      HibernateManager.finallyHandling(sess);
    }

  }

  public abstract void perform(
    ComponentContext tileContext,
    HttpServletRequest request,
    HttpServletResponse response,
    ServletContext servletContext,
    Session sess)
    throws ServletException, IOException;

}
