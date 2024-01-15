<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title></head>
  <body>	
    <menu:crumbs value="crumb1" labellength="40">
	   <menu:crumb	crumbid="crumb1"	title="userconfig.headline"/>
    </menu:crumbs>
    <br>
    <misc:initSelectOptions id="timeOption" type="ch.ess.base.web.userconfig.TimeOptions" scope="session" />
    <misc:initSelectOptions id="localeOption" name="localeOptions" scope="application" />
    <misc:initSelectOptions id="weekStartOption" type="ch.ess.base.web.userconfig.WeekStartOptions" scope="session" />

    <html:form action="/userConfig">
      <html:hidden property="rememberMeValidUntil"/>

     	<forms:form type="edit" caption="userconfig.headline" formid="frmUserConfig">
      <forms:html valign="top" join="false">  
      <ctrl:tabset name="UserConfigForm" id="grouptab" width="500" bgcolor="#DADFE0" runat="client" property="tabset" formElement="true">

  	     <ctrl:tab tabid="general" title="userconfig.general" tooltip="userconfig.general">
	       <forms:form type="edit" formid="frmUserConfigGeneral" noframe="true" width="460">
		                  
		      <forms:text label="user.name"   property="name"   required="true" size="40" maxlength="255"/>
		      <forms:text label="user.firstName"   property="firstName"   required="true" size="40" maxlength="255"/>
              
		      <forms:text  label="user.eMail"      property="email"        required="true" size="40" maxlength="255"/>
                
            <forms:select  label="user.language"  property="localeStr"  required="true">
              <base:options  empty="empty" name="localeOption"/>
            </forms:select>		

            <forms:select  label="userconfig.firstDayOfWeek"  property="firstDayOfWeek"  required="true">
              <base:options  empty="empty" name="weekStartOption"/>
            </forms:select>		
      
          </forms:form>
        </ctrl:tab>
        
        
  	     <ctrl:tab tabid="password" title="login.password" tooltip="login.password">
    	    <forms:form type="edit" formid="frmUserConfigPassword" noframe="true" width="450">
		      <forms:password      label="userconfig.oldPassword"   property="oldPassword"        size="40" maxlength="255"/>
		      <forms:password      label="userconfig.newPassword"   property="newPassword"        size="40" maxlength="255"/>
		      <forms:password      label="user.retypePassword"      property="retypeNewPassword"  size="40" maxlength="255"/>      
          </forms:form>
        </ctrl:tab>
        
  	     <ctrl:tab tabid="layout"	title="userconfig.layout" tooltip="userconfig.layout">
          <forms:form type="edit" formid="frmUserConfigPassword" noframe="true" width="450">	         		
            <forms:spin label="userconfig.noRows" property="noRows" size="5" maxlength="5" required="true"/>		
          </forms:form>
        </ctrl:tab>
        
  	     <ctrl:tab tabid="rememberme" title="login.rememberMe" tooltip="login.rememberMe">
          <forms:form type="edit" formid="frmUserConfigDeleteCookie" noframe="true" width="450">

            <forms:row>
              <forms:group orientation="horizontal">
	          <forms:spin label="userconfig.loginRemember" property="time" size="5" maxlength="5" required="true"/>		          
	           <forms:select    property="timeUnit"  required="true">
	               <base:options  name="timeOption"/>
	             </forms:select>				
              </forms:group>			
            </forms:row>

            <c:if test="${not empty UserConfigForm.rememberMeValidUntil}">  
	           <forms:plaintext label="userconfig.validUntil"  property="rememberMeValidUntil"/>
		        <forms:buttonsection join="true"> 
			       <forms:button   base="buttons.src" default="true" name="btnDeleteCookie"  src="btnDeleteCookie1.gif"  title="button.title.deletecookie"/>
		        </forms:buttonsection>	  
            </c:if>
	       </forms:form>
        </ctrl:tab>

      </ctrl:tabset>
      </forms:html>      
      <forms:html valign="top" align="right" join="false">  
         <ctrl:button base="buttons.src" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>		                    
		</forms:html>

      </forms:form>  
    </html:form>	
  
  </body>
</html>
