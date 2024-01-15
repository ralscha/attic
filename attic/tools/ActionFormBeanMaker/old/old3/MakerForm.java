import org.apache.struts.action.ValidatingActionForm;

public final class MakerForm implements ValidatingActionForm  {

  private String text;
  private String password;
  private String hidden;
  private String textArea;
  private String select;
  private String[] selectmulti;
  private String radio;
  private boolean checkbox;

  public MakerForm() {
    text = "";
    password = "";
    hidden = "";
    textArea = "";
    select = "";
    selectmulti = new String[0];
    radio = "";
    checkbox = false;
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

  public String[] getSelectmulti() {
    return selectmulti;
  }

  public void setSelectmulti(String[] selectmulti) {
    if (selectmulti != null)
      this.selectmulti = selectmulti;
    else
      this.selectmulti = new String[0];
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

  public boolean getCheckbox() {
    return checkbox;
  }

  public void setCheckbox(boolean checkbox) {
    this.checkbox = checkbox;
  }

  public String[] validate() {
  }
}
