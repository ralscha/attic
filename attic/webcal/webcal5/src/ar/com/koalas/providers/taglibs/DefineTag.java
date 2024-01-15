/* ====================================================================
 * Copyright (c) 2004 Guillermo Meyer.                                    
 * Project: Providers                                                      
 * (#)DefineTag.java                  
 *                                         
 * Permission is granted to copy, distribute and/or modify this document  
 * under the terms of the GNU Free Documentation License, Version 1.1 or  
 * any later version published by the Free Software Foundation; with no   
 * Invariant Sections, with no Front-Cover Texts.                     
 * A copy of the license is included in the section entitled 
 * "GNU Free Documentation License".                                 
 * ====================================================================
 */
package ar.com.koalas.providers.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import ar.com.koalas.providers.Provider;
import ar.com.koalas.providers.ProviderFactory;

/**
 * This tag defines a new bean in a given scope and sets there the collection returned from the provider
 *
 * @author Guillermo Meyer
 */
public class DefineTag extends TagSupport {
  //~ Instance fields ----------------------------------------------------------------------------

  protected String id = null;
  protected String toScope = null;
  protected String provider = null;

  //~ Methods ------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param id DOCUMENT ME!
   */
  @Override
  public void setId(String id) {
    this.id = id;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @Override
  public String getId() {
    return (this.id);
  }

  /**
   * DOCUMENT ME!
   *
   * @param toScope DOCUMENT ME!
   */
  public void setToScope(String toScope) {
    this.toScope = toScope;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getToScope() {
    return (this.toScope);
  }

  /**
   * Retrieve the required property and expose it as a scripting variable.
   *
   * @exception JspException if a JSP exception has occurred
   */
  @Override
  public int doEndTag() throws JspException {

    Object value = null;
    try {
      ProviderFactory factory = ProviderFactory.getInstance();
      Provider prov = factory.getProvider(this.getProvider());
      if (prov == null)
        throw new NullPointerException("provider not found: " + this.getProvider());
      value = prov.getCollection();
    } catch (Exception e) {
      throw new JspException("Error en el provider " + this.getProvider() + ". en DefineTag: " + e.getMessage());
    }

    int targetScope = PageContext.PAGE_SCOPE;
    if (toScope != null) {
      targetScope = this.getScope(toScope);
    }

    pageContext.setAttribute(id, value, targetScope);

    // Continue processing this page
    return (EVAL_PAGE);
  }


  @Override
  public int doStartTag() {
    return (SKIP_BODY);
  }

  /**
   * Release all allocated resources.
   */
  @Override
  public void release() {
    super.release();
    id = null;
    toScope = "page";
  }

  private int getScope(String toScope1) {
    if ("request".equalsIgnoreCase(this.toScope)) {
      return PageContext.SESSION_SCOPE;
    }
    if ("session".equalsIgnoreCase(this.toScope)) {
      return PageContext.REQUEST_SCOPE;
    }
    if ("application".equalsIgnoreCase(this.toScope)) {
      return PageContext.APPLICATION_SCOPE;
    }

    return PageContext.PAGE_SCOPE;
  }

  /**
   * @return
   */
  public String getProvider() {
    return provider;
  }

  /**
   * @param string
   */
  public void setProvider(String string) {
    provider = string;
  }

}
