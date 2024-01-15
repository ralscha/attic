<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
	

	<misc:initSelectOptions id="permissionOption" name="permissionOptions" scope="session" />
	
	<html:form action="/appLinkEdit" focus="linkName">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

	<forms:form type="edit" caption="applinkedit.title" formid="frmUserGroup">

	  <forms:text label="appLink.name" property="linkName" required="true" size="40" maxlength="255"/>
	  <forms:text label="appLink.link" property="appLink" required="true" size="255" maxlength="255"/>		
       
		
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
      <c:if test="${UserGroupForm.deletable}">
			<forms:button  onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      </c:if>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  <misc:confirm key="usergroup.delete"/>
</body>
</html>
