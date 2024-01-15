<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title>
    </head>
  <body>
	
    
	<html:form action="/werkEdit" focus="name">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

	<forms:form type="edit" caption="werkedit.title" formid="frmWerk">
    
	  <forms:text label="werk.name" property="name" required="true" size="40" maxlength="255"/>			  
                    		
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
      <c:if test="${WerkForm.deletable}">
			<forms:button  onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      </c:if>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  
  
  
  <misc:confirm key="werk.delete"/>
</body>
</html>
