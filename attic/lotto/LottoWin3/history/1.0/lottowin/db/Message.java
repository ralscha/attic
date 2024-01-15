
package lottowin.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;

/**
 * Class Message.
 */
public class Message {

  /* attributes */
  private String language;
  private String lkey;
  private String msg;


  /** Creates a new Message */
  public Message() {
  }


  /** Accessor for attribute language */
  public String getLanguage() {
    return language;
  }

  /** Accessor for attribute lkey */
  public String getLkey() {
    return lkey;
  }

  /** Accessor for attribute msg */
  public String getMsg() {
    return msg;
  }


  /** Mutator for attribute language */
  public void setLanguage(String newLanguage) {
    language = newLanguage;
  }

  /** Mutator for attribute lkey */
  public void setLkey(String newLkey) {
    lkey = newLkey;
  }

  /** Mutator for attribute msg */
  public void setMsg(String newMsg) {
    msg = newMsg;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer(500);
    buffer.append("language = ").append(this.language).append(";");
    buffer.append("lkey = ").append(this.lkey).append(";");
    buffer.append("msg = ").append(this.msg).append(";");
    return buffer.toString();
  }

}



