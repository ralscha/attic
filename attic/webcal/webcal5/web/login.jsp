<%@ include file="include/taglibs.jspf"%>


<html>
  <head><title></title></head>
  <body>
	
	<html:form action="/login" focus="userName">
  <html:hidden property="to"/>
	<forms:form type="edit" caption="login.headline" formid="frmLogin" width="350">
		<forms:text      label="login.userName"  property="userName"    required="true"/>
		<forms:password  label="login.password"  property="password"   required="true"/>
		<forms:checkbox  label="login.rememberMe" property="remember" />

		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" default="true" name="btnLogin"  src="btnLogon1.gif"  title="button.title.login"/>
		</forms:buttonsection>
	</forms:form>
    </html:form>	
  

  <p>&nbsp;</p>  
  
  <html:form action="/loginNewPassword">
	<forms:form type="edit" caption="login.newPassword" formid="frmLoginNewPassword" width="350">
		<forms:text      label="login.userName"  property="name"    required="true"/>
		<forms:buttonsection join="false"> 
     		<forms:button   base="buttons.src" default="true" name="btnNewPassword"  src="btnNewPassword1.gif"  title="button.title.newpassword"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	

  <table width="350"><tr><td class="normaltext"><bean:message key="login.newPasswordDescription"/></td></tr></table>
  
</body>
</html>
