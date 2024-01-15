package ch.ess.hgtracker.web;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.commons.lang.StringUtils;

public class ConvertToEntityTag extends BodyTagSupport {

  public int doAfterBody() throws JspException {
    BodyContent bodyContent = getBodyContent();
    String body = bodyContent.getString();
    JspWriter out = bodyContent.getEnclosingWriter();

    body = replaceHTMLUml(body);
    try {
      out.println(body);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return SKIP_BODY;
  }

  public static String replaceHTMLUml(String string) {
    if (string == null) {
      return string;
    }
    String result = StringUtils.replace(string, "ü", "&uuml;");
    result = StringUtils.replace(result, " ", "&nbsp;");
    result = StringUtils.replace(result, "ä", "&auml;");
    result = StringUtils.replace(result, "ö", "&ouml;");
    result = StringUtils.replace(result, "Ü", "&Uuml;");
    result = StringUtils.replace(result, "Ä", "&Auml;");
    result = StringUtils.replace(result, "Ö", "&Ouml;");
    result = StringUtils.replace(result, "È", "&Egrave;");
    result = StringUtils.replace(result, "è", "&egrave;");
    result = StringUtils.replace(result, "É", "&Eacute;");
    result = StringUtils.replace(result, "é", "&eacute;");
    result = StringUtils.replace(result, "ë", "&euml;");
    result = StringUtils.replace(result, "ñ", "&ntilde;");
    return result;
  }

}