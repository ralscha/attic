<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title></head>
  <body>
		        
  <misc:initSelectOptions id="usergroupOption" name="userGroupOptions" scope="session"/>
  <html:form action="/userList" focus="value(searchtext)">
	  <forms:form type="search" formid="frmListUser">
    
      <forms:html>
      <table>
        <tr>
          <td><bean:message key="login.userName"/></td>
          <td><bean:message key="usergroup"/></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><ctrl:text property="value(searchtext)" size="20" maxlength="255"/></td>
          <td>
            <ctrl:select property="value(userGroupId)">
              <base:options empty="empty" name="usergroupOption"/>
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
		<ctrl:list id="userlist" 
       action="/userList" 
			 name="listControl" 
			 scope="session"
			 title="user.users" 
			 width="750" 
			 rows="${sessionScope.noRows}"  
			 createButton="true"
			 refreshButton="true"
       printListButton="true"
       exportListButton="true">
		<ctrl:columntext title="login.userName" property="userName" width="150" maxlength="20" sortable="true"/>
		<ctrl:columntext title="user.firstName" property="firstName" maxlength="20" width="150" sortable="true"/>
		<ctrl:columntext title="user.name"  property="name" width="150" maxlength="20" sortable="true"/>

		<ctrl:columntext title="usergroup.userGroups" property="userGroup" width="225" maxlength="30" sortable="true"/>
		
		<ctrl:columnedit tooltip="common.edit"/>
		<misc:columncopy tooltip="common.copy"/>
		<ctrl:columndelete tooltip="common.delete" property="deletable" onclick="return confirmRequest('@{bean.map.userName}');"/>
		
	  </ctrl:list>
		<misc:confirm key="user.delete"/>
		</c:if>
</body>
</html>
