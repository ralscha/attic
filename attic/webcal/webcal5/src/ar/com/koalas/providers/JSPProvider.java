/*
 * Created on Jul 8, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package ar.com.koalas.providers;

import javax.servlet.jsp.PageContext;

/**
 * @author Guillermo Meyer
 * Interfaz para los providers que ejecutaran en JSP y necesitan el pageContext
 */
public interface JSPProvider extends Provider {
  public PageContext getPageContext();

  public void setPageContext(PageContext context);
}
