import org.apache.struts.action.ValidatingActionForm;

public final class MakerForm implements ValidatingActionForm  {

  private String textArea;
  private String select;
  private String[] selectmulti;

  public MakerForm() {
    textArea = "";
    select = "";
    selectmulti = new String[0];
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

  public String[] validate() {
  }
}
