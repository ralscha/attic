<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
  <%@ include file="include/helpJavascript.jspf"%>
  </head>
  <body>	
    
    <misc:initSelectOptions id="timezoneOption" name="timezoneOptions" scope="application" />
    <misc:initSelectOptions id="timeOption" name="timeOptions" scope="session" />
    <misc:initSelectOptions id="localeOption" name="localeOptions" scope="application" />
    <misc:initSelectOptions id="weekStartOption" name="weekStartOptions" scope="session" />
    <misc:initSelectOptions id="taskViewOption" name="taskViewOptions" scope="session" />
    <misc:initSelectOptions id="groupOption" name="groupUserAccessOptions" scope="session" />        
  
    <html:form action="/userConfig">
      <html:hidden property="rememberMeValidUntil"/>

     	<forms:form type="edit" caption="userconfig.title" formid="frmUserConfig">
      <forms:html valign="top" join="false">  
      <ctrl:tabset id="grouptab" styleId="grouptab" width="500" bgcolor="#DADFE0" runat="client" property="tabset" formElement="true">

  	     <ctrl:tab tabid="general" title="userconfig.general" tooltip="userconfig.general">
	       <forms:form type="edit" formid="frmUserConfigGeneral" noframe="true" width="460">		                  
       	  <forms:plaintext label="login.userName"  property="loginName"/>	                  
               <forms:text label="user.name"   property="name"   required="true" size="40" maxlength="255"/>
 	       <forms:text label="user.firstName"   property="firstName"   required="true" size="40" maxlength="255"/>
	       <forms:text      label="user.shortName"  property="shortName"    required="false" size="40" maxlength="30"/>
                
            <forms:select  label="user.language"  property="localeStr"  required="true">
              <base:options  empty="empty" name="localeOption"/>
            </forms:select>		

            <forms:select  label="userconfig.firstDayOfWeek"  property="firstDayOfWeek"  required="true">
              <base:options  empty="empty" name="weekStartOption"/>
            </forms:select>		
      
   	    <forms:text label="userconfig.minDaysInFirstWeek"  property="minDaysInFirstWeek"  required="true" size="3" maxlength="3"/>
       
            <forms:select  label="userconfig.timezone"  property="timezone"  required="true">
              <base:options  empty="empty" name="timezoneOption"/>
            </forms:select>		
          </forms:form>
        </ctrl:tab>
        
  	     <ctrl:tab tabid="email" title="userconfig.email" tooltip="userconfig.email">
	       <forms:form type="edit" formid="frmUserConfigEMail" noframe="true" width="460">
            <forms:html>
    		     <ctrl:list id="emailList" property="emailList"  rows="${sessionScope.noRows}" title="user.eMail" formElement="true">
    		       <ctrl:columntext title="user.eMail" property="email" width="350" maxlength="50" sortable="true"/>
                <ctrl:columncheck title="user.eMail.default" property="default"/>
    		       <ctrl:columndelete tooltip="common.delete"/>		
    	        </ctrl:list>
            </forms:html>
            <forms:group orientation="horizontal">
              <forms:html>
		          <forms:text property="addEmail" required="false" size="40" maxlength="255"/>
			       <ctrl:button base="buttons.src" name="btnAdd"  src="btnAdd1.gif"  title="button.title.add"/>		                    
              </forms:html>
            </forms:group>
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
          <forms:form type="edit" formid="frmUserConfigLayout" noframe="true" width="450">	         		
            <forms:spin label="userconfig.noRows" property="noRows" size="5" maxlength="5" required="true"/>		
            <forms:checkbox label="userconfig.highlightWeekends" property="highlightWeekends"/>
            <forms:checkbox label="userconfig.showQuickAdd" property="showQuickAdd"/>            
            <forms:spin label="userconfig.overviewWidth" property="overviewWidth" size="5" maxlength="5" required="true"/>		
            <forms:spin label="userconfig.overviewHeight" property="overviewHeight" size="5" maxlength="5" required="true"/>		                        
            
            <c:if test="${groupOption.size > 1}">
            <forms:select label="userconfig.defaultGroup" property="groupId">
              <base:options name="groupOption"/>
            </forms:select>
            </c:if>
            
            <sec:granted permission="$task">
						<forms:select label="userconfig.taskDefaultView" property="taskDefaultView">
			        <base:options name="taskViewOption"/>
			      </forms:select>
			      </sec:granted>             
          </forms:form>
        </ctrl:tab>
        
        <sec:granted permission="$time">
  	    <ctrl:tab tabid="timetracking"	title="userconfig.time" tooltip="userconfig.time">
          <forms:form type="edit" formid="frmUserConfigTimeTracking" noframe="true" width="450">	         		
            <forms:text label="userconfig.timefactor"   property="timefactor" required="false" size="10" maxlength="10" help="userconfig.timefactor.help"/>
          </forms:form>
        </ctrl:tab>        
        </sec:granted>
        
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
