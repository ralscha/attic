<%@ page language="java" import="java.io.*,java.net.*,java.util.*,ch.ess.moreover.mapping.*" session="false" info="MoreOver Java News Ticker"%>
<%@ taglib uri="oscache.tld" prefix="cache" %>

<%! 
	private final static String WAP_URL = "http://www.moreover.com/cgi-local/page?o=xml&c=WAP%20and%203G%20news";
    private final static String XML_URL = "http://www.moreover.com/cgi-local/page?o=xml&c=XML%20and%20metadata%20news";
	private final static String JAVA_URL = "http://www.moreover.com/cgi-local/page?c=Java%20news&o=xml";
	private final static String LINUX_URL = "http://www.moreover.com/cgi-local/page?c=Linux%20news&o=xml";
%>	
	
<html>
<head>
<title>Java Newsticker</title>
<link rel="STYLESHEET" type="text/css" href="moreover.css">
</head>
<body bgcolor="White" text="Black" link="Blue" vlink="Purple" alink="Red">

<% String menu = request.getParameter("m"); %>
<% String url = null; %>
<% if (menu == null) menu = "java"; %>
<table>
<tr>
<td align="left" valign="top" bgcolor="Silver">
   <table>
   <% if ("java".equals(menu) || (menu == null)) { %>
   <% url = JAVA_URL; %>
   <tr><td class="menuon">Java</td></tr>
   <% } else { %>
   <tr><td><a class="menuoff" href="news.jsp?m=java">Java</a></td></tr>
   <% } %>
   
   <% if ("xml".equals(menu)) { %>
   <% url = XML_URL; %>   
   <tr><td class="menuon">XML</td></tr>
   <% } else { %>
   <tr><td><a class="menuoff" href="news.jsp?m=xml">XML</a></td></tr>
   <% } %>   

   <% if ("wap".equals(menu)) { %>
   <% url = WAP_URL; %>   
   <tr><td class="menuon">WAP</td></tr>
   <% } else { %>
   <tr><td><a class="menuoff" href="news.jsp?m=wap">WAP</a></td></tr>
   <% } %>
      
   <% if ("linux".equals(menu)) { %>
   <% url = LINUX_URL; %>   
   <tr><td class="menuon">Linux</td></tr>
   <% } else { %>
   <tr><td><a class="menuoff" href="news.jsp?m=linux">Linux</a></td></tr>
   <% } %>
   
   <tr><td height="20"></td></tr>
   <tr><td><a href="http://www.ess.ch/kdb" class="menuoff">Home</a></td></tr>
   </table>
</td>
<td>
<cache:cache key="<%= menu %>" scope="application" time="600">
<%  
      URL u = new URL(url);
      BufferedReader br = new BufferedReader(new InputStreamReader(u.openStream()));
      Moreovernews news = Moreovernews.unmarshal(br);
      br.close();

      Article[] articles = news.getArticle();
      if (articles != null) { 
%>

<table cellspacing="0" cellpadding="0">
<%
		for (int i = 0; i < articles.length; i++) {
			Article article = articles[i];
%>
<tr>
	<td><a href="<%= article.getUrl() %>" target="_blank" class="headline"><%= article.getHeadline_text() %></a></td>
</tr>
<tr><td>
	<table><tr>
	<td class="source"><%= article.getHarvest_time() %>&nbsp;GMT</td>
	<td><a class="source" href="<%= article.getDocument_url() %>"><%= article.getSource() %></a></td>
	</tr>
	</table>
	</td>
</tr>
<tr><td height="7" ></td></tr>
<%
		} // for articles.length
%>

</table>
<%
	} //if articles != null
%>
</cache:cache>
</td>
</tr>
</table>
</body>
</html>



