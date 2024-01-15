<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>

	<html:form action="/config">

	<forms:form type="edit" caption="config.title" formid="frmConfig" width="470">
   
   <forms:html valign="top" join="false">
   <ctrl:tabset id="configedittab" styleId="configedittab" width="500" bgcolor="#DADFE0" runat="client" property="tabset" formElement="true">

     <ctrl:tab tabid="general"	title="config.general" tooltip="config.general">
       <forms:form type="edit" formid="frmConfigGeneral" noframe="true" width="450">	 
       
         <forms:spin label="config.passwordLen" property="passwordLen" size="5" maxlength="5" required="true"/>		          
         <forms:spin description="time.minute"   label="config.sessionTimeout" property="sessionTimeout" size="5" maxlength="5" required="true"/>		          

         <forms:text  label="config.dateFormat"  property="dateFormat"    required="true" size="15" maxlength="255"/>
         <forms:text  label="config.parseDateFormat"  property="parseDateFormat"    required="true" size="15" maxlength="255"/>    
         <forms:text  label="config.dateTimeFormat"  property="dateTimeFormat"    required="true" size="15" maxlength="255"/>    
         <forms:text  label="config.timeFormat"  property="timeFormat"    required="true" size="15" maxlength="255"/>    
         <forms:text  label="config.googleApiKey"  property="googleApiKey"    required="false" size="55" maxlength="255"/>  

       </forms:form>
	 </ctrl:tab>
	 
     <ctrl:tab tabid="attributes"	title="config.attributes" tooltip="config.attributes">               
   	   <misc:initSelectOptions id="appLinkOption" name="appLinkOptions" scope="request" />               
   	   <misc:initSelectOptions id="timeSettingOption" name="timeSettingOptions" scope="request" /> 
       <forms:form type="edit" formid="frmConfigAttributes" noframe="true" width="450">
          <forms:select property="defaultApp" label="attribute.defaultApp" required="true">
            <base:options  empty="empty" name="appLinkOption"/>
          </forms:select>
          <forms:select property="timeSetting" label="attribute.timeSetting" required="true">
            <base:options  empty="empty" name="timeSettingOption"/>
          </forms:select>
          <forms:text  label="attribute.defaultHourRate"  property="defaultHourRate" size="15" maxlength="20" required="true"/>
          <forms:checkbox label="attribute.newCustomer"  property="newCustomer" />
          <forms:checkbox label="attribute.newOrder"  property="newOrder" />
          <forms:checkbox label="attribute.allowActivity"  property="allowActivity" />
          <forms:text  label="attribute.openCustomerTag"  property="openCustomerTag" size="15" maxlength="255"/>    
          <forms:text  label="attribute.closeCustomerTag"  property="closeCustomerTag" size="15" maxlength="255"/>    
       </forms:form>
	 </ctrl:tab>
	  
	 <ctrl:tab tabid="label"	title="config.label" tooltip="config.label">
	     <forms:form type="edit" formid="frmConfigLabel" noframe="true" width="450">	 
	    		<forms:text      label="config.labelCustomerDe"  property="labelCustomerDe"    required="true" size="40" maxlength="255"/>
				<forms:text      label="config.labelCustomerEn"  property="labelCustomerEn"    required="true" size="40" maxlength="255"/>
				<forms:text      label="config.labelProjectDe"  property="labelProjectDe"    required="true" size="40" maxlength="255"/>
				<forms:text      label="config.labelProjectEn"  property="labelProjectEn"    required="true" size="40" maxlength="255"/>    
				<forms:text      label="config.labelTaskDe"  property="labelTaskDe"    required="true" size="40" maxlength="255"/>
				<forms:text      label="config.labelTaskEn"  property="labelTaskEn"    required="true" size="40" maxlength="255"/>
       </forms:form>               
	 </ctrl:tab>
	 
     <ctrl:tab tabid="mail"	title="config.mail" tooltip="config.mail">
	     <forms:form type="edit" formid="frmConfigMail" noframe="true" width="450">	     
				<forms:text      label="config.mailSmtp"  property="mailSmtp"    required="true" size="40" maxlength="255"/>
				<forms:text      label="config.mailSmtpPort"  property="mailSmtpPort"    required="true" size="5" maxlength="5"/>
				<forms:text      label="config.mailSmtpUser"  property="mailSmtpUser"    required="false" size="40" maxlength="255"/>
				<forms:text      label="config.mailSmtpPassword"  property="mailSmtpPassword"    required="false" size="40" maxlength="255"/>
				<forms:text      label="config.mailSender"  property="mailSender"    required="true" size="40" maxlength="255"/>
       </forms:form>               
	 </ctrl:tab>
	 
     <ctrl:tab tabid="info"	title="config.info" tooltip="config.info">
	     <ctrl:plaintext value="Version: 2.12.09"/>               
	 </ctrl:tab>

    </ctrl:tabset>
    </forms:html>       
      	
      
      <forms:html valign="top" align="right" join="false">  
         <ctrl:button base="buttons.src" name="btnMailSend"  src="btnMailSend1.gif"  title="button.title.mailsend"/>		                          
         <ctrl:button base="buttons.src" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>		                    
		</forms:html>      
      
	</forms:form>
  </html:form>  
  </body>  
</html>


	
