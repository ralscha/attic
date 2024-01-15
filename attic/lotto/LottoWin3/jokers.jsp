<%@ page language="java" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/taglib-error.tld" prefix="error" %>
<%@ taglib uri="/WEB-INF/taglib-table.tld"  prefix="t" %>
<%@ taglib uri="/WEB-INF/taglib-misc.tld"  prefix="misc" %>

<app:checkLogon/>

<jsp:include page="head.html" flush="true"/>
<jsp:include page="bodylogin.jsp" flush="true"/>

<h3>Eingabe Lottozettel: Joker</h3>
<struts:form action="addJoker.do" scope="request" method="post" name="jokerForm" focus="joker" type="lottowin.form.JokerForm">
  <misc:trxToken/>
  <blockquote>
    <table>
      <tr>
        <td ALIGN="center" width="115" bgcolor="#CCCCCC"><i>Joker</i></td>
        <td colspan="2"></td>
      </tr>
      <tr>
        <td ALIGN="center" bgcolor="#CCCCCC"><struts:text property="joker" size="8"/></td>
        <td colspan="2" align="left"><struts:submit value="Abspeichern"/></td>
      </tr>
      <tr><td colspan="3" align="left">
	    <error:errorsPresent>
			<table>
			  <error:listErrors>
			    <tr><td bgcolor="Red"><b><error:errorMessage /></b></td></tr>
			   </error:listErrors>
			 </table>
        </error:errorsPresent>
		<logic:notPresent name="<%= org.apache.struts.action.Action.ERROR_KEY %>">
		<table><tr><td>&nbsp;</td></tr></table></logic:notPresent>
	  </td></tr>
	  <tr><td colspan="3">&nbsp;</td></tr>
	   <t:table name="model" scope="session" maxPageItems="<%= 10 %>">
	     <tr><td align="right"><%@ include file="tableindex.jsp"%></td>
		 <td colspan="2">&nbsp;</td></tr>

		 <% int rows = model.getRowCount();
		    Integer updateid = (Integer)session.getAttribute("update");
			for (int r = 0; r < rows; r++) { %>
      	     <tr>
        	    <td ALIGN="center" <% if (r % 2 == 0) { %>bgcolor="#CCCCCC"<% } else { %>bgcolor="#EEEEEE"<% } %>><table><tr><td><%= model.getValueAt(r, 0) %></td></tr></table></td>
			 <% if ( (updateid == null) || (updateid.intValue() != Integer.parseInt((String)model.getValueAt(r, 1))) ) { %>
	        	<td colspan="2" align="left"><a href="<%= response.encodeURL("updateJoker.do?id="+ model.getValueAt(r, 1) +"&page="+ model.getCurrentPage()) %>">Aendern</a>
				&nbsp;<a href="<%= response.encodeURL("removeJoker.do?id="+ model.getValueAt(r, 1) +"&page="+ model.getCurrentPage()) %>">L&ouml;schen</a></td>			 
			 <%	} else { %>
			    <td colspan="2" align="left">Aenderung pendent</td>
			 <% } %>
      		</tr>
		   <% } %>	   
	  </t:table>
    </table>


    </blockquote>
</struts:form>

<%@ include file="tail.html"%>