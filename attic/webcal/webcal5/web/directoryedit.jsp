<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title></head>
  <body>	

	<html:form action="/directoryEdit">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>
  
  <misc:initSelectOptions id="directoryOptions" name="directoryOptions" scope="request" exceptDirectory="${DirectoryForm.modelId}" />
  <misc:initSelectOptions id="userOption" name="userOptions" scope="session" permission="document"/>
  <misc:initSelectOptions id="documentGroupOption" name="groupOptions" scope="session" documentGroup="true"/>
  
	<forms:form type="edit" caption="directoryedit.title" formid="frmDirectory">
    
   <forms:html valign="top" join="false">
	   <ctrl:tabset
        property="tabset"
				id="directorytab"
				styleId="directorytab"
				width="100%"
				bgcolor="#DADFE0"
				formElement="true">
    
      <ctrl:tab	tabid="general"	title="directory.general" tooltip="directory.general">   
        <c:if test="${DirectoryForm.hasWritePermission}"> 
        <c:set value="edit" var="displayEditValue"/>
        </c:if>
        <c:if test="${not DirectoryForm.hasWritePermission}"> 
        <c:set value="display" var="displayEditValue"/>
        </c:if>        
        
        <forms:form type="${displayEditValue}" formid="frmDirectoryGeneral" noframe="true">
        
			    <forms:select label="directory.parent" property="parentId">		    
			      <base:options empty="empty" name="directoryOptions"/>
			 		</forms:select> 
			    
				  <forms:text label="directory.name" property="name" required="true" size="80" maxlength="255"/>		

			    <forms:textarea property="description" valign="top" label="directory.description" rows="5" cols="70" maxlength="1000"/>				  
				  
				</forms:form>
		  </ctrl:tab>
      <ctrl:tab	tabid="permission" title="directory.permission" tooltip="directory.permission">    
        <forms:form type="edit" formid="frmDirectoryPermission" noframe="true">

  			<forms:html valign="top" label="user.users">
					<ctrl:list id="userPermissionList" property="userPermissionList" noframe="true"	rows="${sessionScope.noRows}" title="directory.permission" formElement="true">
						<ctrl:columntext title="user.name" property="name" width="100" maxlength="50" sortable="true" />
						<ctrl:columntext title="user.firstName" property="firstName" width="100" sortable="true" />
						<ctrl:columncheckbox title="directory.writePermission"	property="write" width="90" sortable="true" />
						<ctrl:columncheckbox title="directory.readPermission" property="read"	width="90" sortable="true" />
						<c:if test="${DirectoryForm.hasWritePermission}">
							<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.name}');" />
						</c:if>
					</ctrl:list>
					</forms:html>
					<c:if test="${DirectoryForm.hasWritePermission}">
						<forms:row join="true">
							<forms:html label="">
							<table>
								<tr>
									<td>&nbsp;<bean:message key="user" /></td>
									<td>&nbsp;<bean:message key="directory.writePermission" /></td>
									<td>&nbsp;<bean:message key="directory.readPermission" /></td>
									<td></td>
								</tr>
								<tr>
									<td>
									  <ctrl:select property="userId">
   										<base:options empty="empty" name="userOption" />
									  </ctrl:select>
									</td>
									<td><ctrl:checkbox property="userWritePermission" /></td>
									<td><ctrl:checkbox property="userReadPermission" /></td>
									<td><input id='btnAdd' title='<bean:message key="button.title.add"/>' name='btnAdd' src='<bean:message key="buttons.src"/>/btnAdd1.gif' type='image'></td>
								</tr>
							</table>
							</forms:html>
						</forms:row>
					</c:if>   
					
					<c:if test="${documentGroupOption.size > 0}">									
 			    <forms:html valign="top" label="group.groups" join="false">
					<ctrl:list id="groupPermissionList" property="groupPermissionList" noframe="true"	rows="${sessionScope.noRows}" title="directory.permission" formElement="true">
						<ctrl:columntext title="group" property="name" width="200" maxlength="50" sortable="true" />
						<ctrl:columncheckbox title="directory.writePermission"	property="write" width="90" sortable="true" />
						<ctrl:columncheckbox title="directory.readPermission" property="read"	width="90" sortable="true" />
						<c:if test="${DirectoryForm.hasWritePermission}">
							<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.name}');" />
						</c:if>
					</ctrl:list>
					</forms:html>
					<c:if test="${DirectoryForm.hasWritePermission}">
						<forms:row join="true">
							<forms:html label="">
							<table>
								<tr>
									<td>&nbsp;<bean:message key="group" /></td>
									<td>&nbsp;<bean:message key="directory.writePermission" /></td>
									<td>&nbsp;<bean:message key="directory.readPermission" /></td>
									<td></td>
								</tr>
								<tr>
									<td>
									  <ctrl:select property="groupId">
   										<base:options empty="empty" name="documentGroupOption" />
									  </ctrl:select>
									</td>
									<td><ctrl:checkbox property="groupWritePermission" /></td>
									<td><ctrl:checkbox property="groupReadPermission" /></td>
									<td><input id='btnAdd' title='<bean:message key="button.title.add"/>' name='btnAdd' src='<bean:message key="buttons.src"/>/btnAdd1.gif' type='image'></td>
								</tr>
							</table>
							</forms:html>
						</forms:row>
					</c:if>  		
					</c:if>			        
        
        </forms:form>
      </ctrl:tab>
    </ctrl:tabset>
    </forms:html>    		  
		  
           		
		<forms:buttonsection join="false"> 
		  <c:if test="${DirectoryForm.hasWritePermission}">
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
      <c:if test="${DirectoryForm.deletable}">
			<forms:button  onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      </c:if>
      </c:if>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  <misc:confirm key="directory.delete"/>
</body>
</html>
