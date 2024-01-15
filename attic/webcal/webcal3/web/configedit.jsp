<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
  
    <menu:crumbs value="crumb1" labellength="40">
	    <menu:crumb	crumbid="crumb1"	title="config.headline"/>
    </menu:crumbs>
  <br>
	<html:form action="/config" focus="passwordLen">

	<forms:form type="edit" caption="config.headline" formid="frmConfig" width="470">
        <forms:spin label="config.passwordLen" property="passwordLen" size="5" maxlength="5" required="true"/>		          
        <forms:spin description="time.minute"   label="config.sessionTimeout" property="sessionTimeout" size="5" maxlength="5" required="true"/>		          

    <forms:text  label="config.dateFormat"  property="dateFormat"    required="true" size="15" maxlength="255"/>
    <forms:text  label="config.parseDateFormat"  property="parseDateFormat"    required="true" size="15" maxlength="255"/>    
    <forms:text  label="config.dateTimeFormat"  property="dateTimeFormat"    required="true" size="15" maxlength="255"/>    
    <forms:text  label="config.timeFormat"  property="timeFormat"    required="true" size="15" maxlength="255"/>    
	 		
		<forms:section title="config.mail">
			<forms:text      label="config.mailSmtp"  property="mailSmtp"    required="true" size="40" maxlength="255"/>
			<forms:text      label="config.mailSmtpPort"  property="mailSmtpPort"    required="true" size="5" maxlength="5"/>
			<forms:text      label="config.mailSmtpUser"  property="mailSmtpUser"    required="false" size="40" maxlength="255"/>
			<forms:text      label="config.mailSmtpPassword"  property="mailSmtpPassword"    required="false" size="40" maxlength="255"/>
			<forms:text      label="config.mailSender"  property="mailSender"    required="true" size="40" maxlength="255"/>
		</forms:section>		
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnMailSend"  src="btnMailSend1.gif"  title="button.title.mailsend"/>
			<forms:button   base="buttons.src" name="btnSave" default="true" src="btnSave1.gif"  title="button.title.save"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>  
  </body>  
</html>


	
