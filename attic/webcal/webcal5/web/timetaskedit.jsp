<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title></head>
  <body>	

	<html:form action="/timeTaskEdit" focus="name">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>
  
	<forms:form type="edit" caption="timetaskedit.title" formid="frmTimeTask">
        
    <misc:initSelectOptions id="timeCustomerProjetsOptions" name="timeCustomerProjetsOptions" scope="request" projectId="${TimeTaskForm.projectId}" />        

    <forms:plaintext label="time.customer" property="customer"/>

    <forms:select label="time.project" property="projectId" required="true">       
      <base:options name="timeCustomerProjetsOptions"/>
    </forms:select> 
        
        
	  <forms:text label="time.name" property="name" required="true" size="80" maxlength="255"/>	
    <forms:text label="time.hourRate" property="hourRate" required="false" size="10" maxlength="10"/>  
    <forms:text label="time.description" property="description" required="false" size="80" maxlength="255"/>   
    <forms:checkbox label="time.active" property="active"/>
           		
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
      <c:if test="${TimeTaskForm.deletable}">
			<forms:button  onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      </c:if>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  <misc:confirm key="time.delete"/>
</body>
</html>
