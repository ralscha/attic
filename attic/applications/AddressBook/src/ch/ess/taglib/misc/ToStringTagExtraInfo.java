/*
 * $Header: c:/cvs/pbroker/src/ch/ess/taglib/misc/ToStringTagExtraInfo.java,v 1.1.1.1 2002/02/26 06:46:55 sr Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/02/26 06:46:55 $
 */

package ch.ess.taglib.misc;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;


/**
 * Class ToStringTagExtraInfo
 *
 * @version $Revision: 1.1.1.1 $ $Date: 2002/02/26 06:46:55 $
 */
public class ToStringTagExtraInfo extends TagExtraInfo {

  public VariableInfo[] getVariableInfo(TagData tagData) {

    String s = tagData.getAttributeString("id");

    if (s == null) {
      return null;
    } else {
      return new VariableInfo[]{ new VariableInfo(s, "java.lang.String", true, VariableInfo.AT_END) };
    }
  }
}
