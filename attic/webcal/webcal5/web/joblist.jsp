<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title></head>
  <body>
   
		<ctrl:list id="joblist" 
		     action="/jobList"
			 name="listControl" 
			 scope="session"
			 title="job.jobs" 
			 rows="${sessionScope.noRows}"  
			 createButton="false"
			 refreshButton="true">
		<ctrl:columntext title="job.group" property="group" width="100" sortable="true"/>
		<ctrl:columntext title="job.name"  property="name" width="100" sortable="true"/>
		<ctrl:columntext title="job.description" property="description" width="300" sortable="true"/>
		<ctrl:columntext title="job.previousFireTime" property="previousFireTime" sortable="true"/>			
		<ctrl:columntext title="job.nextFireTime" property="nextFireTime" sortable="true"/>				
		<ctrl:columntext title="job.startTime" property="startTime" sortable="true"/>				        	        
				
	  </ctrl:list>

</body>
</html>