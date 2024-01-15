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


	
