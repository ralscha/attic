<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title></head>
  <body>    
  <%
     request.setAttribute("userPermissionsAdapter", new ch.ess.base.web.PrincipalAdapter(request)); 
  %>
     
     <dropmenu:useMenuDisplayer name="MenuInfo" 
         bundle="org.apache.struts.action.MESSAGE"
         locale="org.apache.struts.action.LOCALE"
         permissions="userPermissionsAdapter">
          <dropmenu:displayMenu name="m_mainmenu"/>
        </dropmenu:useMenuDisplayer>
  </body>
</html>
