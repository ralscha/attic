<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
  
    <menu:crumbs value="crumb1" labellength="40">
	    <menu:crumb	crumbid="crumb1"	title="userGroup.userGroups"/>
	    <menu:crumb	crumbid="crumb2"	disabled="true"   title="userGroup.headline"/>
    </menu:crumbs>
  <br>
    
	<html:form action="/listUserGroup.do" focus="value(userGroupName)">
		<forms:form type="search" formid="frmListUserGroup">
		  <forms:html>
			  <table cellspacing='0' cellpadding='0'>
			  <tr>		  		    
			    <td class='searchfl'><bean:message key="userGroup.groupName"/>:</td>
				  <td class='fb'></td>
	      </tr>
			  <tr>
				  <td class='fd'><html:text property="value(userGroupName)" size="20" maxlength="255"/></td>
			<td class='fb'>
			<ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/>		    
         </td>

			  </tr>
			  </table>
			</forms:html>		  	
		</forms:form>	  	  
  </html:form>		
	
	<p></p>
	
	<ctrl:list id="usergrouplist" 
		   action="listUserGroup.do" 
			 name="usergroups" 
			 scope="session"
			 title="userGroup.userGroups" 
			 rows="${sessionScope.noRows}"  
          minRows="${sessionScope.noRows}"
			 createButton="true"
			 refreshButton="true">
		<ctrl:columntext title="userGroup.groupName" property="groupName" width="225" maxlength="30" sortable="true"/>		
		<ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.groupName}');"/>		
	</ctrl:list>
	
  <misc:confirm key="userGroup.delete"/>
</body>
</html>
