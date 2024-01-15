package ar.com.koalas.providers.taglibs.comboselect;

import javax.servlet.jsp.JspException;

import ar.com.koalas.providers.taglibs.struts.SelectTag;

/**
 * @author Guillermo Meyer
 * Es similar a SelectTag pero es solo una referencia para trabajar con varias combinaciones de selects
 */
public class SelectRefTag extends SelectTag {
  @Override
  public int doStartTag() throws JspException {
    this.manageComboSelect();
    return SKIP_BODY;
  }
}
