<%@ include file="include/taglibs.jspf"%>




<html>
  <head><title></title></head>
  <body>
		
    <menu:crumbs value="crumb1" labellength="40">
	    <menu:crumb	crumbid="crumb1"	title="user.users"/>
	    <menu:crumb	crumbid="crumb2"	disabled="true"   title="user.headline"/>
    </menu:crumbs>
  <br>
    
    
    <misc:initSelectOptions id="usergroupOption" name="userGroupOptions" scope="session" />
	  
    <html:form action="/listUser.do" focus="value(searchtext)">
	<forms:form type="search" formid="frmListUser">
	    <forms:html>
		  <table cellspacing='0' cellpadding='0'>
		  <tr>		  		    
		    <td class='searchfl'><bean:message key="user.searchText"/>:</td>
   			<td class='searchfl'><bean:message key="userGroup"/>:</td>
			<td class='fb'></td>
          </tr>
		  <tr>
			<td class='fd'><html:text property="value(searchtext)" size="20" maxlength="255"/></td>
			<td class='fd'>
			   <html:select property="value(userGroupId)">
                    <html:option value=" ">&nbsp;</html:option>
                    <html:optionsCollection name="usergroupOption" property="labelValue"/>   
                  </html:select>
			</td>
			<td class='fb'>
			<ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/>		    
         </td>
		  </tr>
		  </table>
		</forms:html>
	  	
	</forms:form>
	</html:form>
	<p></p>
		<ctrl:list id="userlist" 
		     action="listUser.do" 
			 name="users" 
			 scope="session"
			 title="user.users" 
			 width="750" 
          minRows="${sessionScope.noRows}"
			 rows="${sessionScope.noRows}"  
			 createButton="true"
			 refreshButton="true">
		<ctrl:columntext title="login.userName" property="userName" width="150" maxlength="20" sortable="true"/>
		<ctrl:columntext title="user.lastName"  property="name" width="150" maxlength="20" sortable="true"/>
		<ctrl:columntext title="user.firstName" property="firstName" width="150" maxlength="20" sortable="true"/>
		<ctrl:columntext title="userGroup.userGroups" property="userGroup" width="225" maxlength="30" sortable="true"/>
		
		<ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.userName}');"/>
		
	  </ctrl:list>
		<misc:confirm key="user.delete"/>
</body>
</html>
