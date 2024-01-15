/*
 * Created on Jul 10, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package ar.com.koalas.providers;

import javax.servlet.jsp.PageContext;

/**
 * @author Guillermo Meyer
 */
public abstract class JSPProviderImpl extends ProviderImpl implements JSPProvider {
  private PageContext pageContext = null;

  /**
   * @return
   */
  public PageContext getPageContext() {
    return pageContext;
  }

  /**
   * @param context
   */
  public void setPageContext(PageContext context) {
    pageContext = context;
  }

}