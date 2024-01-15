package ch.ess.common.web.tag;

import javax.servlet.jsp.JspException;

import org.apache.velocity.VelocityContext;

/**
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/12/30 11:10:29 $ 
  * 
  * @jsp.tag name="highlight" body-content="empty"
  */
public class HighlightTag extends VelocityTag {

  private String tableNo, colNo;

  public HighlightTag() {
    init();
  }

  /**
   * @jsp.attribute required="true" rtexprvalue="true"   
   */
  public String getColNo() {
    return colNo;
  }

  /**
   * @jsp.attribute required="true" rtexprvalue="true"   
   */
  public String getTableNo() {
    return tableNo;
  }

  public void setColNo(String string) {
    colNo = string;
  }

  public void setTableNo(String string) {
    tableNo = string;
  }

  /**
   * Process the Highlight tag.
   * 
   * @exception JspException if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {

    
    VelocityContext context = new VelocityContext();
    context.put("colNo", colNo);
    context.put("tableNo", tableNo);

    merge("/ch/ess/common/web/tag/highlightTag.vm", context);

    return SKIP_BODY;
  }


  /**
   * Release all allocated resources.
   */
  public void release() {
    super.release();
    init();
  }

  private void init() {
    colNo = null;
    tableNo = null;    
  }




}