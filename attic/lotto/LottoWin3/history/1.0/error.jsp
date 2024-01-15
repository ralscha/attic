<%@ page language="java" isErrorPage="true"%>
<%@ page import="java.util.*, java.io.*" %>
<%@ page import="javax.mail.*, javax.mail.internet.*" %>
<html><head><title>Lottowin Error</title></head>

<body bgcolor="white">
<p>
Ein Fehler ist aufgetreten<br>
<center><b><%= exception %></b> </center>
</p>

<% try {
     String mailServer = "mail.ess.ch";
     String subject = "Lottowin Error Notification";
     String [] to = { "sr@ess.ch" };
     String from = "Lottowin <webmaster@ess.ch>";
     sendEmail(mailServer, subject, to, from,
               makeErrorReport(request, exception)); %>
     <p>Die zust&auml;ndige Person wurde informiert</p>
<% }
   catch (AddressException e) { %>
     <p>Invalid e-mail address(es) for error notification.</p>
<% }
   catch (MessagingException e) { %>
     <p>Unable to send e-mail for error notification.</p>
<% } %>

</body>
</html>

<%! 
  public String makeErrorReport (HttpServletRequest req, Throwable e) {
    StringBuffer buffer = new StringBuffer();
    reportException(buffer, e);
    reportRequest(buffer, req);
    reportParameters(buffer, req);
    reportHeaders(buffer, req);
    reportCookies(buffer, req);
    return buffer.toString();
  }
  
  public void reportException (StringBuffer buffer, Throwable e) {
    StringWriter writer = new StringWriter();
    e.printStackTrace(new PrintWriter(writer));
    buffer.append(writer.getBuffer());
    buffer.append('\n');
  }

  public void reportRequest (StringBuffer buffer, HttpServletRequest req) {
    buffer.append("Request: ");
    buffer.append(req.getMethod());
    buffer.append(' ');
    buffer.append(HttpUtils.getRequestURL(req));
    String queryString = req.getQueryString();
    if (queryString != null) {
      buffer.append('?');
      buffer.append(queryString);
    }
    buffer.append("\nSession ID: ");
    String sessionId = req.getRequestedSessionId();
    if (sessionId == null) {
      buffer.append("none");
    } else if (req.isRequestedSessionIdValid()) {
      buffer.append(sessionId);
      buffer.append(" (from ");
      if (req.isRequestedSessionIdFromCookie())
        buffer.append("cookie)\n");
      else if (req.isRequestedSessionIdFromURL())
        buffer.append("url)\n");
      else
        buffer.append("unknown)\n");
    } else {
        buffer.append("invalid\n");
    }
  }

  public void reportParameters (StringBuffer buffer, HttpServletRequest req) {
    Enumeration names = req.getParameterNames();
    if (names.hasMoreElements()) {
      buffer.append("Parameters:\n");
      while (names.hasMoreElements()) {
        String name = (String) names.nextElement();
        String[] values = req.getParameterValues(name);
        for (int i = 0; i < values.length; ++i) {
          buffer.append("    ");
          buffer.append(name);
          buffer.append(" = ");
          buffer.append(values[i]);
          buffer.append('\n');
        }
      }
    }
  }

  public void reportHeaders (StringBuffer buffer, HttpServletRequest req) {
    Enumeration names = req.getHeaderNames();
    if (names.hasMoreElements()) {
      buffer.append("Headers:\n");
      while (names.hasMoreElements()) {
        String name = (String) names.nextElement();
        String value = (String) req.getHeader(name);
        buffer.append("    ");
        buffer.append(name);
        buffer.append(": ");
        buffer.append(value);
        buffer.append('\n');
      }
    }
  }

  public void reportCookies (StringBuffer buffer, HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    int l = cookies.length;
    if (l > 0) {
      buffer.append("Cookies:\n");
      for (int i = 0; i < l; ++i) {
        Cookie cookie = cookies[i];
        buffer.append("    ");
        buffer.append(cookie.getName());
        buffer.append(" = ");
	buffer.append(cookie.getValue());
	buffer.append('\n');
      }
    }
  }

  public void sendEmail (String mailServer, String subject,
                         String to[], String from, String messageText) 
    throws AddressException, MessagingException {
    // Create session
    Properties mailProps = new Properties();
    mailProps.put("mail.smtp.host", mailServer);
    //mailProps.put("mail.debug", true);
    Session mailSession = Session.getDefaultInstance(mailProps, null);
    //mailSession.setDebug(true);
    // Construct addresses
    int toCount = to.length;
    InternetAddress[] toAddrs = new InternetAddress[toCount];
    for (int i = 0; i < toCount; ++i) {
        toAddrs[i] = new InternetAddress(to[i]);
    }
    InternetAddress fromAddr = new InternetAddress(from);
    // Create and initialize message
    Message message = new MimeMessage(mailSession);
    message.setFrom(fromAddr);
    message.setRecipients(Message.RecipientType.TO, toAddrs);
    message.setSubject(subject);
    message.setContent(messageText.toString(), "text/plain");
    // Send message
    Transport.send(message);
  } %>
