import javax.servlet.http.*;
import org.apache.struts.action.*;

public final class PersPersonalienForm extends ActionForm  {

  private String notiz;
  private String anrede;

  public PersPersonalienForm() {
    reset();
  }

  public String getNotiz() {
    return notiz;
  }

  public void setNotiz(String notiz) {
    this.notiz = notiz;
  }

  public String getAnrede() {
    return anrede;
  }

  public void setAnrede(String anrede) {
    this.anrede = anrede;
  }

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    reset();
  }

  private void reset() {
    notiz = null;
    anrede = null;
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
    buffer.append("notiz = ").append(this.notiz).append(";");
    buffer.append("anrede = ").append(this.anrede).append(";");
    return buffer.toString();
  }

}
