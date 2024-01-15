<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title></head>
  <body>	

	<html:form action="/timeCustomerEdit" focus="name">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>
  
  
	<forms:form type="edit" caption="timecustomeredit.title" formid="frmTimeCustomer">
    
    <forms:html valign="top" join="false">
	   <ctrl:tabset
        property="tabset"
				id="tasktab"
				styleId="tasktab"
				width="100%"
				runat="client"
				bgcolor="#DADFE0"
				formElement="true">

		<ctrl:tab	tabid="general"	title="time.general" tooltip="time.general">            
          <forms:form type="edit" formid="frmTimeCustomerGeneral" noframe="true">
		  		<forms:text label="time.customerNumber" property="customerNumber" size="15" maxlength="15"/>	
		  		<forms:text label="time.name" property="name" required="true" size="80" maxlength="255"/>		
				<forms:text label="time.hourRate" property="hourRate" required="false" size="10" maxlength="10"/>      
				<forms:textarea valign="top" label="time.description" property="description" required="false" cols="80" rows="4"/>   
				<forms:checkbox label="time.active" property="active"/>
          </forms:form>
        </ctrl:tab>
        
		<ctrl:tab tabid="userTab" title="user.users" tooltip="user.users">
	
      	 <misc:initSelectOptions id="userOption" name="userOptions" scope="session" permission="time"/>
				  
	       <forms:form noframe="true" type="edit" formid="frmEventUser" width="700">       	                          
		       <forms:swapselect label="user.users" valign="top"              
		         property="userIds"              
		         orientation="horizontal"      
		         labelLeft="common.available"      
		         labelRight="common.selected" 
		         runat="client"     
		         size="18"      
		         style="width: 250px;">
		         <base:options name="userOption"/>
		       </forms:swapselect>             	              
	       </forms:form>         
			  </ctrl:tab>          
        
      </ctrl:tabset>
    </forms:html>
           		
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
      <c:if test="${TimeCustomerForm.deletable}">
			<forms:button  onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      </c:if>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  <misc:confirm key="time.delete"/>
</body>
</html>
