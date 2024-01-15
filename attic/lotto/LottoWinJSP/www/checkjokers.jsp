<%@ page language="java" errorPage="error.jsp"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/ess-misc" prefix="misc" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/ess-table" prefix="t" %>


<jsp:include page="head.html" flush="true"/>
<jsp:include page="bodylogin.jsp" flush="true"/>

<app:checkLogon/>

<h3>Auswertung Jokers</h3>
<html:form action="checkJoker.do" method="post">
  <blockquote>
    <table>
	<tr>
    <td>von Ziehung </td>
  	<td>
	<html:select property="from">
 	  <html:options labelProperty="labels" property="values"/>
	</html:select>
	 </td>
	 <td><html:checkbox property="onlyWin" />nur Gewinne anzeigen</td>
	 </tr>
	 <tr>
     <td>bis Ziehung</td>
  	 <td>
  	  <html:select property="to">
	    <html:options labelProperty="labels" property="values"/>
	  </html:select>
	 </td>
	 <td><input type="submit" name="start" value="Auswertung starten"></td>
	 </tr>	 
	 </table>
   <br>
   	<t:table name="model" scope="session" maxPageItems="<%= 10 %>"> 
   <table>
     <tr><td colspan="6" align="right"><%@ include file="tableindex.jsp"%></td></tr>
	 <tr>
	    <th width="100" align="center" bgcolor="#CCCCCC" class="smallth">Ziehung</th>
        <th width="100" ALIGN="center" bgcolor="#CCCCCC" class="smallth">6 Richtige</th>
        <th width="100" ALIGN="center" bgcolor="#CCCCCC" class="smallth">5 Richtige</th>
        <th width="100" ALIGN="center" bgcolor="#CCCCCC" class="smallth">4 Richtige</th>
        <th width="100" ALIGN="center" bgcolor="#CCCCCC" class="smallth">3 Richtige</th>
        <th width="100" ALIGN="center" bgcolor="#CCCCCC" class="smallth">2 Richtige</th>
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
  
    </table>
	  </t:table>     
	  <t:noTable name="model" scope="session">
	   <% if (request.getParameter("clean") == null) { %>
	   <p>&nbsp;</p>
	     <strong>Keine Gewinne</strong>
	   <% } %>
	  </t:noTable>    

    </blockquote>
</html:form>


<%@ include file="tail.html"%>
