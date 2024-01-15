package ch.ess.base.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.ErrorData;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cc.framework.security.SecurityUtil;

public class ErrorPrinter {

  private final static String SERVLET_EXCPTION = "javax.servlet.error.exception";
  private final static String STRUTS_EXCPTION = "org.apache.struts.action.EXCEPTION";

  private ErrorPrinter() {
    //singleton
  }

  public static String logError(final PageContext pageContext, final Throwable exception) {
    Log logger = LogFactory.getLog(ErrorPrinter.class);
    String errorMsg = getErrorString(pageContext, exception);
    logger.error(errorMsg);
    return errorMsg;
  }

  public static String getErrorString(final PageContext pageContext, final Throwable exception) {
    ServletRequest request = pageContext.getRequest();
    ErrorData errorData = null;
    try {
      errorData = pageContext.getErrorData();
    } catch (Exception e) {
      errorData = null;
    }

    StringWriter writer = new StringWriter(500);
    PrintWriter pw = new PrintWriter(writer);

    if (pageContext.findAttribute(STRUTS_EXCPTION) != null) {
      ((Throwable)pageContext.findAttribute(STRUTS_EXCPTION)).printStackTrace(pw);
    } else if ((errorData != null) && (errorData.getThrowable() != null)) {
      errorData.getThrowable().printStackTrace(pw);
    } else if (exception != null) {
      exception.printStackTrace(pw);
    } else if ((Exception)request.getAttribute(SERVLET_EXCPTION) != null) {
      ((Exception)request.getAttribute(SERVLET_EXCPTION)).printStackTrace(pw);
    }

    String result = writer.toString();
    result += "\n\n";
    result += getAdditionalData(pageContext);

    return result;
  }

  @SuppressWarnings("unchecked")
  private static String getAdditionalData(final PageContext pageContext) {
    ErrorData errorData = null;
    try {
      errorData = pageContext.getErrorData();
    } catch (Exception e) {
      errorData = null;
    }
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();

    StringBuilder sb = new StringBuilder(500);

    if (errorData != null) {
      sb.append("SERVLETNAME: ").append(errorData.getServletName());
      sb.append("\n");
      sb.append("STATUSCODE: ").append(errorData.getStatusCode());
      sb.append("\n\n");
    }
    sb.append("REQUEST:\n");
    if (errorData != null) {
      sb.append("URI: ");
      sb.append(errorData.getRequestURI());
    }

    sb.append("\nAttributes:\n");

    Enumeration<String> names = request.getAttributeNames();
    while (names.hasMoreElements()) {
      String key = names.nextElement();
      sb.append(key).append(" = ").append(request.getAttribute(key));
      sb.append("\n");
    }

    HttpSession session = request.getSession();

    sb.append("\nSESSION:\n");

    sb.append("ID: ");
    String sessionId = request.getRequestedSessionId();
    if (sessionId == null) {
      sb.append("none");
    } else if (request.isRequestedSessionIdValid()) {
      sb.append(sessionId);
      sb.append(" (from ");
      if (request.isRequestedSessionIdFromCookie()) {
        sb.append("cookie)\n");
      } else if (request.isRequestedSessionIdFromURL()) {
        sb.append("url)\n");
      } else {
        sb.append("unknown)\n");
      }
    } else {
      sb.append("invalid\n");
    }

    sb.append("\nAttributes:\n");

    names = session.getAttributeNames();
    while (names.hasMoreElements()) {
      String key = names.nextElement();
      sb.append(key).append(" = ").append(session.getAttribute(key));
      sb.append("\n");
    }

    sb.append("\nPARAMETERS:\n");
    names = request.getParameterNames();
    if (names.hasMoreElements()) {
      while (names.hasMoreElements()) {
        String name = names.nextElement();
        String[] values = request.getParameterValues(name);
        for (String element : values) {
          sb.append(name).append(" = ").append(element);
          sb.append("\n");
        }
      }
    }

    sb.append("\nHEADERS:\n");

    names = request.getHeaderNames();
    if (names != null) {
      if (names.hasMoreElements()) {
        while (names.hasMoreElements()) {
          String name = names.nextElement();
          String value = request.getHeader(name);
          sb.append(name).append(" = ").append(value);
          sb.append("\n");
        }
      }
    }

    sb.append("\nCOOKIES:\n");

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      int l = cookies.length;
      if (l > 0) {
        for (int i = 0; i < l; ++i) {
          Cookie cookie = cookies[i];
          sb.append(cookie.getName()).append(" = ").append(cookie.getValue());
          sb.append("\n");
        }
      }
    }

    sb.append("\nUSER:\n");
    UserPrincipal principal = (UserPrincipal)SecurityUtil.getPrincipal(request);
    if (principal != null) {
      sb.append(principal.getUserId());
    }

    return sb.toString();

  }
}
