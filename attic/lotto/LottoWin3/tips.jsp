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


<h3>Eingabe Lottozettel: Tips</h3>
<struts:form action="addTip.do" scope="request" method="post" name="tipForm" focus="z1" type="lottowin.form.TipForm">
  <misc:trxToken/>
  <blockquote>
    <table width="638">
      <tr>
        <td ALIGN="center" width="62" bgcolor="#CCCCCC"><i>1. Zahl</i></td>
        <td ALIGN="center" width="62" bgcolor="#CCCCCC"><i>2. Zahl</i></td>
        <td ALIGN="center" width="62" bgcolor="#CCCCCC"><i>3. Zahl</i></td>
        <td ALIGN="center" width="62" bgcolor="#CCCCCC"><i>4. Zahl</i></td>
        <td ALIGN="center" width="62" bgcolor="#CCCCCC"><i>5. Zahl</i></td>
        <td ALIGN="center" width="62" bgcolor="#CCCCCC"><i>6. Zahl</i></td>
        <td colspan="2"></td>
      </tr>
      <tr>
        <td ALIGN="center" bgcolor="#CCCCCC"><struts:text property="z1" size="4"/></td>
        <td ALIGN="center" bgcolor="#CCCCCC"><struts:text property="z2" size="4"/></td>
        <td ALIGN="center" bgcolor="#CCCCCC"><struts:text property="z3" size="4"/></td>
        <td ALIGN="center" bgcolor="#CCCCCC"><struts:text property="z4" size="4"/></td>
        <td ALIGN="center" bgcolor="#CCCCCC"><struts:text property="z5" size="4"/></td>
        <td ALIGN="center" bgcolor="#CCCCCC"><struts:text property="z6" size="4"/></td>
        <td width="266" colspan="2" align="left"><struts:submit value="Abspeichern"/></td>
      </tr>
      <tr><td colspan="8" align="left">
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
	  <tr><td colspan="8">&nbsp;</td></tr>
	   <t:table name="model" scope="session" maxPageItems="<%= 10 %>">
	     <tr><td colspan="6" align="right"><%@ include file="tableindex.jsp"%></td>
		 <td colspan="2">&nbsp;</td></tr>

		 <% int rows = model.getRowCount();
		    Integer updateid = (Integer)session.getAttribute("update");
			for (int r = 0; r < rows; r++) { %>
      	     <tr>
	         <% for (int c = 0; c < 6; c++) { %>
        	    <td ALIGN="right" <% if (r % 2 == 0) { %>bgcolor="#CCCCCC"<% } else { %>bgcolor="#EEEEEE"<% } %>><table><tr><td align="right"><%= model.getValueAt(r, c) %></td><td>&nbsp;</td></tr></table></td>
	         <% } %>
			 <% if ( (updateid == null) || (updateid.intValue() != Integer.parseInt((String)model.getValueAt(r, 6))) ) { %>
	        	<td colspan="2" align="left"><a href="<%= response.encodeURL("updateTip.do?id="+ model.getValueAt(r, 6) +"&page="+ model.getCurrentPage()) %>">Aendern</a>
				&nbsp;<a href="<%= response.encodeURL("removeTip.do?id="+ model.getValueAt(r, 6) +"&page="+ model.getCurrentPage()) %>">L&ouml;schen</a></td>			 
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
