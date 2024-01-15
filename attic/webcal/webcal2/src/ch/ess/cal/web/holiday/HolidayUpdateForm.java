package ch.ess.cal.web.holiday;

import org.apache.struts.action.ActionForm;

      
/** 
  * @author  Ralph Schaer
  * @version 1.0, 28.09.2003
  * @struts.form name="holidayUpdateForm"
  */
public class HolidayUpdateForm extends ActionForm {
  
  private String show;
  private String hide;
  private String save;
  private Long[] check;
  private Long[] activeId;
  private Long[] notactiveId;

  public Long[] getCheck() {
    return check;
  }

  public String getHide() {
    return hide;
  }

  public String getSave() {
    return save;
  }

  public String getShow() {
    return show;
  }

  public void setCheck(Long[] strings) {
    check = strings;
  }

  public void setHide(String string) {
    hide = string;
  }

  public void setSave(String string) {
    save = string;
  }

  public void setShow(String string) {
    show = string;
  }

  public Long[] getActiveId() {
    return activeId;
  }

  public void setActiveId(Long[] strings) {
    activeId = strings;
  }

  public Long[] getNotactiveId() {
    return notactiveId;
  }

  public void setNotactiveId(Long[] longs) {
    notactiveId = longs;
  }

}
