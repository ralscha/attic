package ch.ess.common.web.tag;

import java.net.MalformedURLException;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.velocity.VelocityContext;

/**
 * @author  Ralph Schaer
 * @version $Revision: 1.6 $ $Date: 2004/05/22 12:24:40 $ 
 * 
 * @jsp.tag name="popupCalendar" body-content="empty"
 */
public class PopupCalendarTag extends VelocityTag {

  private String element = null;
  private Integer elementIndex = null;
  private String form = null;
  private String dateFormat = "dd.mm.yyyy";
  private boolean past = true;

  public PopupCalendarTag() {
    init();
  }

  public String getElement() {
    return element;
  }

  public Integer getElementIndex() {
    return elementIndex;
  }

  public String getForm() {
    return form;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public void setElement(String string) {
    element = string;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public void setElementIndex(Integer integer) {
    elementIndex = integer;
  }

  /**
   * @jsp.attribute required="true" rtexprvalue="true"   
   */
  public void setForm(String string) {
    form = string;
  }

  public String getDateFormat() {
    return dateFormat;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public void setDateFormat(String string) {
    dateFormat = string;
  }

  public boolean isPast() {
    return past;
  }

  /**
   * @jsp.attribute required="false" rtexprvalue="true"   
   */
  public void setPast(boolean b) {
    past = b;
  }

  // --------------------------------------------------------- Public Methods

  /**
   * Process the popupCalendarLocale tag.
   * 
   * @exception JspException
   *              if a JSP exception has occurred
   */
  public int doStartTag() throws JspException {

    String imageURL = "";

    try {
      imageURL = TagUtils.getInstance().computeURL(pageContext, null, null, "/images/down.gif", null, null, null, null,
          true);
    } catch (MalformedURLException mue) {
      throw new JspException(mue);
    }

    String elem = element;
    if (elementIndex != null) {
      elem = "elements[" + elementIndex.toString() + "]";
    }

    VelocityContext context = new VelocityContext();
    context.put("selectdate", getMessage("popupcal.selectDateLink"));
    context.put("dateformat", dateFormat);
    context.put("element", elem);
    context.put("form", form);
    context.put("calendargif", imageURL);
    if (past) {
      context.put("past", "1");
    } else {
      context.put("past", "0");
    }
    context.put("fx", "-1");
    context.put("fy", "-1");

    merge("/ch/ess/common/web/tag/popupcalendarlink.vm", context);

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
    element = null;
    elementIndex = null;
    form = null;
    dateFormat = "dd.mm.yyyy";
    past = true;
  }

}