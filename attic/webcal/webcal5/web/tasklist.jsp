<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
  <link href="<c:url value='/styles/view.css' />" media="all" rel="stylesheet" type="text/css" />	  
	<script language="JavaScript" src="<c:url value='/scripts/overlib_mini.js'/>" type="text/javascript"></script>    
  </head>
  <body>
	<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
	       
  <misc:initSelectOptions id="categoryOption" name="categoryOptions" scope="session" />
  <misc:initSelectOptions id="taskViewOption" name="taskViewOptions" scope="session" />  
          

  <html:form action="/taskList" focus="value(subject)">
	<forms:form type="search" formid="frmListTask">

    <forms:html>
    <table>
      <tr>
        <td><bean:message key="event.subject"/></td>
        <td><bean:message key="category"/></td>
        <td><bean:message key="task.view"/></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>        
      </tr>
      <tr>
        <td><ctrl:text property="value(subject)" size="20" maxlength="255"/></td>      
        <td>
			    <ctrl:select property="value(categoryId)">
            <base:options empty="empty" name="categoryOption"/>
          </ctrl:select> 
        </td>
        <td>
    			<ctrl:select property="value(view)" onchange="document.MapForm.submit()">
            <base:options name="taskViewOption"/>
          </ctrl:select>        
        </td>	  	
        <td><ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/></td>
        <td><ctrl:button base="buttons.src" name="btnClearSearch" src="btnClearSearch1.gif" title="button.title.clearsearch" /></td>
      </tr>
    </table>
    </forms:html>      
      
	</forms:form>
	</html:form>          
	
	<p></p>
  <c:if test="${not empty listControl}">
	<ctrl:list 
		   action="/taskList" 
			 name="listControl" 
			 scope="session">
	</ctrl:list> 	
  <misc:confirm key="task.delete"/>
  </c:if>
</body>
</html>