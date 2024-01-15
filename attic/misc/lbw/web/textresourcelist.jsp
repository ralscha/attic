<%@ include file="include/taglibs.jspf"%>


<html>
  <head><title></title></head>
  <body>
	    

		<ctrl:list id="textresourcelist" 
		   action="/textResourceList" 
			 name="listControl" 
			 scope="session"
			 title="textresource.textResources" 			 
			 rows="${sessionScope.noRows}">
		<ctrl:columntext title="textresource.name" property="name" width="225" maxlength="30" sortable="true"/>		
		<ctrl:columnedit tooltip="common.edit"/>
		
	  </ctrl:list>
	
</body>
</html>
