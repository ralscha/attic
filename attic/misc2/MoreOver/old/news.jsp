<%@ page language="java" import="java.util.*" session="false" info="MoreOver Java News Ticker"%>
<jsp:useBean id="articleReader" scope="application" class="ArticleReader"/>
<%@ taglib uri="/WEB-INF/cache.tld" prefix="cache" %>

<%! 
	private final static String WAP_URL = "http://p.moreover.com/cgi-local/page?index_wap+xml";
    private final static String XML_URL = "http://p.moreover.com/cgi-local/page?index_xml+xml";
	private final static String JAVA_URL = "http://p.moreover.com/cgi-local/page?index_java+xml";
	private final static String LINUX_URL = "http://p.moreover.com/cgi-local/page?index_linux+xml";
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
   <tr><td><a href="http://www2.ess.ch" class="menuoff">Home</a></td></tr>
   </table>
</td>
<td>
<cache:cache key="<%= menu %>" scope="application" refresh="false" time="3600">
<% 
	List articleList = articleReader.getArticleList(url);
	if ((articleList != null) && !articleList.isEmpty()) { 
%>

<table cellspacing="0" cellpadding="0">
<%
		for (int i = 0; i < articleList.size(); i++) {
			Article article = (Article)articleList.get(i);
%>
<tr>
	<td><a class="headline" href="<%= article.getURL() %>"><%= article.getHeadline() %></a></td>
</tr>
<tr><td>
	<table><tr>
	<td class="source"><%= article.getFormattedHarvestTime() %>&nbsp;GMT</td>
	<td><a class="source" href="<%= article.getDocumentURL() %>"><%= article.getSource() %></a></td>
	</tr>
	</table>
	</td>
</tr>
<tr><td height="7" ></td></tr>
<%
		} // for articleList.size
%>

</table>
<%
	} //if !articleList.isEmpty 
%>
</cache:cache>
</td>
</tr>
</table>
</body>
</html>



