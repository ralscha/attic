package lottowin.form;

import lottowin.resource.*;
import lottowin.db.*;
import org.apache.struts.action.*;
import com.objectmatter.bsf.*;
import javax.servlet.http.*;
import org.log4j.*;
import ch.ess.util.pool.*;

public final class CheckForm extends ActionForm {

  private static Category CAT = Category.getInstance(CheckForm.class.getName());

  private String from;
  private String to;
  private boolean onlyWin;

  private String[] labels;
  private String[] values;
  private Draw[] draws;

  public CheckForm() {
    from = null;
    to = null;
    onlyWin = true;

    Database db = null;
    Draw[] dr = null;

    try {
      db = PoolManager.requestDatabase();
      OQuery qry = new OQuery(Draw.class);
      qry.add(2000, "drawyear", OQuery.GREATER_OR_EQUAL);
      qry.addOrder("drawyear", OQuery.DESC);
      qry.addOrder("number", OQuery.DESC);
      qry.setMaxCount(30);
      dr = (Draw[]) qry.list(db);
    } finally {
      PoolManager.returnDatabase(db);
    }

    labels = new String[30];
    values = new String[30];
    draws = new Draw[30];

    for (int i = 29; i >= 0; i--) {
      int dx = 29 - i;
      String drawStr = dr[dx].getNumber() + "/" + dr[dx].getDrawyear();
      draws[i] = dr[dx];
      if (i < 10)
        values[i] = "0"+String.valueOf(i);
      else
        values[i] = String.valueOf(i);

      labels[i] = drawStr + " (" +
                  lottowin.Constants.dateFormat.format(dr[dx].getDrawdate()) + ")";
    }

    from = values[29];
    to = values[29];

    dr = null;

  }

  public boolean getOnlyWin() {
    return onlyWin;
  }

  public void setOnlyWin(boolean flag) {
    onlyWin = flag;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String[] getLabels() {
    return labels;

  }

  public String[] getValues() {
    return values;
  }

  public Draw getDraw(int ix) {
    return draws[ix];
  }

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    onlyWin = false;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer(500);

    buffer.append(super.toString());
    buffer.append(";from = ").append(this.from).append(";");
    buffer.append("to = ").append(this.to).append(";");
    buffer.append("onlyWin = ").append(this.onlyWin).append(";");

    return buffer.toString();
  }

}
