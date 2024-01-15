import org.apache.struts.action.ActionForm;

public final class MakerForm implements ActionForm  {

  private String submit = "";
  private String text = "";
  private String password = "";
  private String hidden = "";
  private String radio = "";
  private String[] checkbox = new String[0];
  private String textArea = "";
  private String select = "";

  public String getSubmit() {
    return submit;
  }

  public void setSubmit(String submit) {
    if (submit != null)
      this.submit = submit;
    else
      this.submit = "";
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    if (text != null)
      this.text = text;
    else
      this.text = "";
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    if (password != null)
      this.password = password;
    else
      this.password = "";
  }

  public String getHidden() {
    return hidden;
  }

  public void setHidden(String hidden) {
    if (hidden != null)
      this.hidden = hidden;
    else
      this.hidden = "";
  }

  public String getRadio() {
    return radio;
  }

  public void setRadio(String radio) {
    if (radio != null)
      this.radio = radio;
    else
      this.radio = "";
  }

  public String[] getCheckbox() {
    return checkbox;
  }

  public void setCheckbox(String[] checkbox) {
    if (checkbox != null)
      this.checkbox = checkbox;
    else
      this.checkbox = new String[0];
  }

  public String getTextArea() {
    return textArea;
  }

  public void setTextArea(String textArea) {
    if (textArea != null)
      this.textArea = textArea;
    else
      this.textArea = "";
  }

  public String getSelect() {
    return select;
  }

  public void setSelect(String select) {
    if (select != null)
      this.select = select;
    else
      this.select = "";
  }

}
