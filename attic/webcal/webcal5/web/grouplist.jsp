<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title></head>
  <body>
    
  <html:form action="/groupList" focus="value(groupName)">
	<forms:form type="search" formid="frmListGroup">

      <forms:html>
      <table>
        <tr>
          <td><bean:message key="group.groupName"/></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><ctrl:text property="value(groupName)" size="20" maxlength="255"/></td>
    			<td><ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/></td>
        </tr>
      </table>
      </forms:html>   
	  	
	</forms:form>
	</html:form>
	
	<p></p>
	
	<ctrl:list id="grouplist" 
		   action="/groupList" 
			 name="listControl" 
			 scope="session"
			 title="group.groups" 
			 rows="${sessionScope.noRows}"  
   		 createButton="true"
			 refreshButton="true">
	  <ctrl:columntext title="group.groupName" property="groupName" width="225" maxlength="30" sortable="true"/>

    <misc:hasFeature feature="event">
      <ctrl:columncheckbox title="event.menu" property="eventGroup"	width="90" sortable="true" />
    </misc:hasFeature>
    
    <misc:hasFeature feature="task">
      <ctrl:columncheckbox title="task.menu" property="taskGroup"	width="90" sortable="true" />
    </misc:hasFeature>
    
    <misc:hasFeature feature="document">
      <ctrl:columncheckbox title="directory.menu" property="documentGroup"	width="90" sortable="true" />        
    </misc:hasFeature>
    
    <misc:hasFeature feature="time">
      <ctrl:columncheckbox title="time.menu" property="timeGroup"	width="110" sortable="true" />     
    </misc:hasFeature>
    
    <ctrl:columngroup title="common.action" align="center">
		<ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" property="deletable" onclick="return confirmRequest('@{bean.map.groupName}');"/>		
		</ctrl:columngroup>
	</ctrl:list>
	
  <misc:confirm key="group.delete"/>
</body>
</html>