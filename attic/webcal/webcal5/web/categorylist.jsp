<%@ include file="include/taglibs.jspf"%>


<html>
  <head><title></title></head>
  <body>

	          
  <html:form action="/categoryList" focus="value(categoryName)">
	<forms:form type="search" formid="frmListCategory">

      <forms:html>
      <table>
        <tr>
          <td><bean:message key="category.categoryName"/></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><ctrl:text property="value(categoryName)" size="20" maxlength="255"/></td>
          <td><ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/></td>
        </tr>
      </table>
      </forms:html>         
      
      
	</forms:form>
	</html:form>
               	  	  	  			
	<p></p>
	
	<ctrl:list id="categorylist" 
		   action="/categoryList" 
			 name="listControl" 
			 scope="session"
			 title="category.categories" 
			 rows="${sessionScope.noRows}"  
			 createButton="true"
			 refreshButton="true">
	  <ctrl:columntext title="category.categoryName" property="categoryName" width="225" maxlength="30" sortable="true"/>
    
    <ctrl:columnhtml title="category.colour" id="category">
      <div align="center">
      <table cellspacing="0" cellpadding="0"><tr><td>
      <img src='<c:url value="/pic.do?w=25&h=14&c=${category.map.colour}" />' alt="" width="25" height="14" border="0">      
      </td></tr></table>
      </div>
    </ctrl:columnhtml>
  
		<ctrl:columncheck title="category.defaultCategory" property="defaultCategory"/>
		<ctrl:columntext title="category.sequence" property="sequence"/>      
    <ctrl:columngroup title="common.action" align="center">
    <ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" property="deletable" onclick="return confirmRequest('@{bean.map.categoryName}');"/>		
		</ctrl:columngroup>
	</ctrl:list>
	
  <misc:confirm key="category.delete"/>
</body>
</html>
