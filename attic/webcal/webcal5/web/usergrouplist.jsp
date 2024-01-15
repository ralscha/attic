<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
  
    
	<html:form action="/userGroupList" focus="value(userGroupName)">
		<forms:form type="search" formid="frmListUserGroup">
      <forms:html>
      <table>
        <tr>
          <td><bean:message key="usergroup.groupName"/></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><ctrl:text property="value(userGroupName)" size="20" maxlength="255"/></td>
    			<td><ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/></td>
        </tr>
      </table>
      </forms:html>
		</forms:form>	  	  
  </html:form>		
	
	<p></p>
	
	<ctrl:list id="usergrouplist" 
		   action="/userGroupList" 
			 name="listControl" 
			 scope="session"
			 title="usergroup.userGroups" 
			 rows="${sessionScope.noRows}"  
			 createButton="true"
			 refreshButton="true">
		<ctrl:columntext title="usergroup.groupName" property="groupName" width="225" maxlength="30" sortable="true"/>	
		<ctrl:columngroup title="common.action" align="center">	
		<ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" property="deletable" onclick="return confirmRequest('@{bean.map.groupName}');"/>		
		</ctrl:columngroup>
	</ctrl:list>
	
  <misc:confirm key="usergroup.delete"/>
</body>
</html>
