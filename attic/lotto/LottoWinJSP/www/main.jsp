<%@ page language="java" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/ess-misc" prefix="misc" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/ess-table" prefix="t" %>

<app:checkLogon/>
<jsp:include page="head.html" flush="true"/>
<jsp:include page="bodylogin.jsp" flush="true"/>

<% String name = "";
   ch.sr.lottowin.db.User user = (ch.sr.lottowin.db.User)session.getAttribute(ch.sr.lottowin.Constants.USER_KEY);
   if (user != null) {
     name = user.getName();
   }
%>

<h3>Wilkommen zu Lottowin</h3>
<blockquote>
Hallo <%= name %>
<p>&nbsp;</p>
<p>&nbsp;</p>
Unter <strong>Eingabe</strong> Tips und Jokers eingeben
<p>&nbsp;</p>
Unter <strong>Auswertungen</strong> nachsehen ob du was gewonnen hast
<p>&nbsp;</p>
<strong>Abmelden</strong> bringt dich wieder raus
</blockquote>

<%@ include file="tail.html"%>
