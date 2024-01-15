<%@ include file="include/taglibs.jspf"%>


<html>
  <head><title></title></head>
  <body>
	    
    <menu:crumbs value="crumb1" labellength="40">
	    <menu:crumb	crumbid="crumb1"	title="textResource.textResources"/>
	    <menu:crumb	crumbid="crumb2"	disabled="true"   title="textResource.headline"/>
    </menu:crumbs>
  <p>
		<ctrl:list id="textresourcelist" 
		     action="listTextResource.do" 
			 name="textresources" 
			 scope="session"
			 title="textResource.textResources" 			 
			 rows="${sessionScope.noRows}">
		<ctrl:columntext title="textResource.name" property="name" width="225" maxlength="30" sortable="true"/>		
		<ctrl:columnedit tooltip="common.edit"/>
		
	  </ctrl:list>
	
</body>
</html>
