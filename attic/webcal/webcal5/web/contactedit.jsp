<%@ include file="include/taglibs.jspf"%>

<html>
  <head>
    <title></title>
    <misc:popupCalendarJs/>
  </head>
  <body>
	
	<html:form action="/contactEdit">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>
  <html:hidden property="value(lat)"/>
  <html:hidden property="value(lng)"/>
  <html:hidden property="value(homeLat)"/>
  <html:hidden property="value(homeLng)"/>

	<forms:form type="edit" caption="contactedit.title" formid="frmContact" width="620">
    <forms:html valign="top" join="false">
	   <ctrl:tabset runat="client" property="tabset" id="eventtab" styleId="eventtab" width="100%" bgcolor="#DADFE0" formElement="true">
		<ctrl:tab	tabid="generalTab"	title="contact.general" tooltip="contact.general">
			<forms:form type="edit" formid="frmEventGeneral" noframe="true">
		      <forms:checkbox label="contact.private" property="privateContact" />
		      <forms:text label="contact.title" property="value(title)" size="60" maxlength="255"/>
		      <forms:text label="contact.firstName" property="value(firstName)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.middleName" property="value(middleName)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.lastName" property="value(lastName)" size="60" maxlength="255"/>
		      <forms:text label="contact.nickName" property="value(nickName)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.addressStreet" property="value(addressStreet)" size="60" maxlength="255"/>
		      <forms:text label="contact.addressPOBox" property="value(addressPOBox)" size="60" maxlength="255"/>
		      <forms:text label="contact.addressState" property="value(addressState)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.addressPostalCode" property="value(addressPostalCode)" size="60" maxlength="255"/>
		      <forms:text label="contact.addressCity" property="value(addressCity)" size="60" maxlength="255"/>
		      <forms:text label="contact.addressCountry" property="value(addressCountry)" size="60" maxlength="255"/>
		      <forms:text label="contact.email" property="value(email)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.homepage" property="value(homepage)" size="60" maxlength="255"/>
          
		      <c:if test="${ContactForm.showMap}">
			      <forms:html align="right">
			        <ctrl:button onclick="window.open('map.jsp?lat=${ContactForm.valueMap['lat']}&lng=${ContactForm.valueMap['lng']}','map', 'left=350,top=200,width=615,height=415,toolbar=0,resizable=0');return false;" base="buttons.src" name="btnMap" src="btnMap1.gif"  title="contact.showMap"/>
			      </forms:html>
		      </c:if>		  
		          
	    	</forms:form>  
	    </ctrl:tab> 
       	<ctrl:tab tabid="companyTab"	title="contact.company" tooltip="contact.company">
       		<forms:form type="edit" formid="frmEventCompany" noframe="true">
		      <forms:text label="contact.companyName" property="value(companyName)" size="60" maxlength="255"/>
		      <forms:text label="contact.businessNumber" property="value(businessNumber)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.officeNumber2" property="value(officeNumber2)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.officeLocation" property="value(officeLocation)" size="60" maxlength="255"/>
		      <forms:text label="contact.departmentName" property="value(departmentName)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.companyMainPhoneNumber" property="value(companyMainPhoneNumber)" size="60" maxlength="255"/>
		      <forms:text label="contact.fax" property="value(fax)" size="60" maxlength="255"/>
		      <forms:text label="contact.mobileNumber" property="value(mobileNumber)" size="60" maxlength="255"/>
	    	</forms:form>
	    </ctrl:tab>
	    <ctrl:tab	tabid="homeTab"	title="contact.home" tooltip="contact.home">   
	    	<forms:form type="edit" formid="frmEventHome" noframe="true">   
		      <forms:text label="contact.homeNumber" property="value(homeNumber)" size="60" maxlength="255"/>
		      <forms:text label="contact.homeNumber2" property="value(homeNumber2)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.homeStreet" property="value(homeStreet)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.homePOBox" property="value(homePOBox)" size="60" maxlength="255"/>
		      <forms:text label="contact.homeState" property="value(homeState)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.homePostalCode" property="value(homePostalCode)" size="60" maxlength="255"/>
		      <forms:text label="contact.homeCity" property="value(homeCity)" size="60" maxlength="255"/>
		      <forms:text label="contact.homeCountry" property="value(homeCountry)" size="60" maxlength="255"/>	
		      <c:if test="${ContactForm.showHomeMap}">
			      <forms:html align="right">
			        <ctrl:button onclick="window.open('map.jsp?lat=${ContactForm.valueMap['homeLat']}&lng=${ContactForm.valueMap['homeLng']}','map', 'left=350,top=200,width=615,height=415,toolbar=0,resizable=0');return false;" base="buttons.src" name="btnMap" src="btnMap1.gif"  title="contact.showMap"/>
			      </forms:html>
		      </c:if>		
	    	</forms:form>
	    </ctrl:tab>  
	    <ctrl:tab	tabid="otherTab"	title="contact.other" tooltip="contact.other"> 
	    	<forms:form type="edit" formid="frmEventOther" noframe="true">  
		      <forms:text label="contact.otherNumber" property="value(otherNumber)" size="60" maxlength="255"/>
		      <forms:text label="contact.otherStreet" property="value(otherStreet)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.otherPOBox" property="value(otherPOBox)" size="60" maxlength="255"/>
		      <forms:text label="contact.otherState" property="value(otherState)" size="60" maxlength="255"/>	    
		      <forms:text label="contact.otherPostalCode" property="value(otherPostalCode)" size="60" maxlength="255"/>
		      <forms:text label="contact.otherCity" property="value(otherCity)" size="60" maxlength="255"/>
		      <forms:text label="contact.otherCountry" property="value(otherCountry)" size="60" maxlength="255"/>	
		      <forms:textarea valign="top" label="contact.comment" property="value(comment)" cols="70" rows="4"/>
		      <forms:text label="contact.birthday" property="value(birthday)" size="10" maxlength="10"/>	
		      <forms:textarea valign="top" label="contact.hobbies" property="value(hobbies)" cols="70" rows="4"/>	
	    	</forms:form>
	    </ctrl:tab>    
	      	      	
      </ctrl:tabset>
   </forms:html>
    	<forms:html valign="top" align="right" join="false">  
         <ctrl:button base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>
         <ctrl:button base="buttons.src" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
         <c:if test="${ContactForm.deletable}">
         	<ctrl:button onclick="return confirmRequest('');" base="buttons.src" name="btnDelete"  src="btnDelete1.gif"  title="button.title.delete"/>
         </c:if>
         <ctrl:button base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>		                    
		</forms:html>
    	
	</forms:form>	
  </html:form>	
  <misc:confirm key="contact.delete"/>
	
</body>
</html>
