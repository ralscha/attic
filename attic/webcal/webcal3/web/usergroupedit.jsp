<%@ include file="include/taglibs.jspf"%>



<html>
  <head>
    <title></title>
  </head>
  <body>
	
    <menu:crumbs value="crumb2" labellength="40">
	    <menu:crumb	crumbid="crumb1"	action="/listUserGroup.do" title="userGroup.userGroups"/>
	    <menu:crumb	crumbid="crumb2"	title="userGroup.headline"/>
    </menu:crumbs>
  <br>
    
	<misc:initSelectOptions id="permissionOption" name="permissionOptions" scope="session" />
	
	<html:form action="/editUserGroup" focus="groupName">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

	<forms:form type="edit" caption="userGroup.headline" formid="frmUserGroup" width="460">

	  <forms:text label="userGroup.groupName" property="groupName" required="true" size="40" maxlength="255"/>		
       
       <forms:swapselect label="userGroup.permissions" valign="top"              
         property="permissionIds"              
         orientation="horizontal"      
         labelLeft="common.available"      
         labelRight="common.selected" 
         runat="client"  
         size="18"       
         style="width: 250px;">
         <base:options name="permissionOption"/>

       </forms:swapselect>           
    
    		
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
      <c:if test="${not empty userGroupForm.modelId}">
			<forms:button  onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      </c:if>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  <misc:confirm key="userGroup.delete"/>
</body>
</html>