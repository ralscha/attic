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
     
     <table class="inputform">
     <tr>
       <td><misc:label property="userName" key="login.userName"/>&nbsp;</td>
       <td><html:text tabindex="10" property="userName" size="20" maxlength="255"/></td>
     </tr>
     <tr>
       <td><misc:label property="password" key="login.password"/>&nbsp;</td>
       <td><html:password tabindex="20" property="password" size="20" maxlength="255"/></td>
     </tr>
     <tr>
       <td><misc:label property="remember" key="login.rememberMe"/>&nbsp;</td>
       <td><html:checkbox tabindex="30" property="remember"/></td>
     </tr>
     </table>          
     <p>&nbsp;</p>
     <html:submit property="submit"><bean:message key="button.title.login"/></html:submit>&nbsp;
     
  </html:form>	

  <p>&nbsp;</p>  
  
  <html:form action="/loginNewPassword">
     <table class="inputform">  
     <tr>
       <td><misc:label property="userName" key="login.userName"/>&nbsp;</td>
       <td><html:text tabindex="10" property="name" size="20" maxlength="255"/></td>
     </tr>
     </table>          
     <p>&nbsp;</p>
     <html:submit property="submit"><bean:message key="button.title.newpassword"/></html:submit>&nbsp;          
  </html:form>	

  <table width="350"><tr><td class="normaltext"><bean:message key="login.newPasswordDescription"/></td></tr></table>
  
</body>
</html>
