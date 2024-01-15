<%@ page language="java" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/taglib-table.tld"  prefix="t" %>

<jsp:include page="head.html" flush="true"/>
<jsp:include page="bodylogin.jsp" flush="true"/>

<app:checkLogon/>

<h3>Auswertung Tips</h3>
<struts:form action="checkTip.do" method="post" name="checkForm" scope="session" type="lottowin.form.CheckForm">
  <blockquote>
    <table>
	<tr>
    <td>von Ziehung </td>
  	<td>
	<struts:select property="from">
 	  <struts:options labelProperty="labels" property="values"/>
	</struts:select>
	 </td>
	 <td><struts:checkbox property="onlyWin" />nur Gewinne anzeigen</td>
	 </tr>
	 <tr>
     <td>bis Ziehung</td>
  	 <td>
  	  <struts:select property="to">
	    <struts:options labelProperty="labels" property="values"/>
	  </struts:select>
	 </td>
	 <td><input type="submit" name="start" value="Auswertung starten"></td>
	 </tr>	 
	 </table>
   <br>
 
   <table>
   	<t:table name="model" scope="session" maxPageItems="<%= 10 %>">
     <tr><td colspan="6" align="right"><%@ include file="tableindex.jsp"%></td></tr>
	 <tr>
	    <th width="100" align="center" bgcolor="#CCCCCC" class="smallth">Ziehung</th>
        <th width="100" ALIGN="center" bgcolor="#CCCCCC" class="smallth">6 Richtige</th>
        <th width="100" ALIGN="center" bgcolor="#CCCCCC" class="smallth">5 Richtige + Zz</th>
        <th width="100" ALIGN="center" bgcolor="#CCCCCC" class="smallth">5 Richtige</th>
        <th width="100" ALIGN="center" bgcolor="#CCCCCC" class="smallth">4 Richtige</th>
        <th width="100" ALIGN="center" bgcolor="#CCCCCC" class="smallth">3 Richtige</th>
      </tr>	
	   
	  <% int rows = model.getRowCount();
	     int cols = model.getColumnCount();
	     for (int r = 0; r < rows; r++) { %>
      	     <tr>
			 <% for (int c = 0; c < cols; c++) { %>
        	    <td ALIGN="center" <% if (r % 2 != 0) { %>bgcolor="#CCCCCC"<% } else { %>bgcolor="#EEEEEE"<% } %>><table><tr><td><%= c > 0 ? "<b>" : "" %><%= model.getValueAt(r, c) %><%= c > 0 ? "</b>" : "" %></td></tr></table></td>
             <% } %>				
      		</tr>
		   <% } %>	   
	  </t:table>   
    </table>
    

    </blockquote>
</struts:form>


<%@ include file="tail.html"%>
