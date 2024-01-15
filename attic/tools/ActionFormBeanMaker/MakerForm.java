import javax.servlet.http.*;
import org.apache.struts.action.*;

public final class MakerForm extends ActionForm  {

  private String text;
  private String password;
  private String hidden;
  private String radio;
  private boolean checkbox;
  private String textArea;
  private String select;
  private String[] selectmulti;

  public MakerForm() {
    reset();
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getHidden() {
    return hidden;
  }

  public void setHidden(String hidden) {
    this.hidden = hidden;
  }

  public String getRadio() {
    return radio;
  }

  public void setRadio(String radio) {
    this.radio = radio;
  }

  public boolean getCheckbox() {
    return checkbox;
  }

  public void setCheckbox(boolean checkbox) {
    this.checkbox = checkbox;
  }

  public String getTextArea() {
    return textArea;
  }

  public void setTextArea(String textArea) {
    this.textArea = textArea;
  }

  public String getSelect() {
    return select;
  }

  public void setSelect(String select) {
    this.select = select;
  }

  public String[] getSelectmulti() {
    return selectmulti;
  }

  public void setSelectmulti(String[] selectmulti) {
    this.selectmulti = selectmulti;
  }

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    reset();
  }

  private void reset() {
    text = null;
    password = null;
    hidden = null;
    radio = null;
    checkbox = false;
    textArea = null;
    select = null;
    selectmulti = null;
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = new ActionErrors();
    //errors.add("joker", new ActionError("joker.no.input"));
    //errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("joker.wrong.input"));
    return errors;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer(500);
    buffer.append(super.toString()).append(";");
    buffer.append("text = ").append(this.text).append(";");
    buffer.append("password = ").append(this.password).append(";");
    buffer.append("hidden = ").append(this.hidden).append(";");
    buffer.append("radio = ").append(this.radio).append(";");
    buffer.append("checkbox = ").append(this.checkbox).append(";");
    buffer.append("textArea = ").append(this.textArea).append(";");
    buffer.append("select = ").append(this.select).append(";");
    for (int i = 0; i < selectmulti.length; i++) {
      buffer.append("selectmulti[").append(i).append("] = ").append(this.selectmulti[i]).append(";");
    }
    return buffer.toString();
  }

}
