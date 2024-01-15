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
    String result = StringUtils.replace(string, "�", "&uuml;");
    result = StringUtils.replace(result, " ", "&nbsp;");
    result = StringUtils.replace(result, "�", "&auml;");
    result = StringUtils.replace(result, "�", "&ouml;");
    result = StringUtils.replace(result, "�", "&Uuml;");
    result = StringUtils.replace(result, "�", "&Auml;");
    result = StringUtils.replace(result, "�", "&Ouml;");
    result = StringUtils.replace(result, "�", "&Egrave;");
    result = StringUtils.replace(result, "�", "&egrave;");
    result = StringUtils.replace(result, "�", "&Eacute;");
    result = StringUtils.replace(result, "�", "&eacute;");
    result = StringUtils.replace(result, "�", "&euml;");
    result = StringUtils.replace(result, "�", "&ntilde;");
    return result;
  }

}