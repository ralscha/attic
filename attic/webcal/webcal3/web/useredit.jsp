<%@ include file="include/taglibs.jspf"%>

<html>
  <head>
    <title></title>
  </head>
  <body>
	
    <menu:crumbs value="crumb2" labellength="40">
	   <menu:crumb	crumbid="crumb1"	action="/listUser.do" title="user.users"/>
	   <menu:crumb	crumbid="crumb2"	title="user.headline"/>
    </menu:crumbs>
    <br>
   
   <misc:initSelectOptions id="localeOption" name="localeOptions" scope="application" />
   <misc:initSelectOptions id="usergroupOption" name="userGroupOptions" scope="session" />
   <misc:initSelectOptions id="timezoneOption" type="ch.ess.cal.web.userconfig.TimezoneOptions" scope="application" />
  
   <html:form action="/editUser" focus="userName">
     <html:hidden property="modelId"/>
     <html:hidden property="version"/>
     <html:hidden property="cookieLogin"/>
     	  
     <forms:form type="edit" caption="user.headline" formid="frmUser" width="660">
	
	   <forms:text label="login.userName"  property="userName"     required="true" size="40" maxlength="255"/>
      
      <forms:html label="" valign="top" join="false">
        <ctrl:tabset name="userForm" id="usertab" width="500" bgcolor="#DADFE0" runat="client" property="tabset" formElement="true">

  	       <ctrl:tab tabid="general" title="user.general" tooltip="user.general">
	         <forms:form type="edit" formid="frmUserGeneral" noframe="true">
      
		        <forms:text label="user.lastName"   property="lastName"   required="true" size="40" maxlength="255"/>
		        <forms:text label="user.firstName"  property="firstName"  required="true" size="40" maxlength="255"/>
		        <forms:text label="user.shortName"  property="shortName"  required="false" size="40" maxlength="30"/>
    						
		        <c:if test="${not userForm.cookieLogin}">
		          <forms:password    label="login.password"  property="password"   required="true" size="40" maxlength="255"/>
   	          <forms:password    label="user.retypePassword"  property="retypePassword"  required="true"  size="40" maxlength="255"/>
              </c:if>

              <forms:select  label="user.language"  property="localeStr"  required="true">
                <base:options  empty="empty" name="localeOption"/>
              </forms:select>				

              <forms:select  label="userconfig.timezone"  property="timezone"  required="true">
                <base:options  empty="empty" name="timezoneOption"/>
              </forms:select>				
            </forms:form>
          </ctrl:tab>

  	       <ctrl:tab tabid="usergroups" title="userGroup.userGroups" tooltip="userGroup.userGroups">
            <forms:form type="edit" formid="frmUserUserGroups" noframe="true">
       <forms:swapselect label="userGroup.userGroups" valign="top"              
         property="userGroupIds"              
         orientation="horizontal"      
         labelLeft="common.available"      
         labelRight="common.selected" 
         runat="client"          
         style="width: 170px;">
         <base:options name="usergroupOption"/>

       </forms:swapselect>	
            </forms:form>
          </ctrl:tab>
          
  	       <ctrl:tab tabid="email" title="user.eMail" tooltip="user.eMail">
	         <forms:form type="edit" formid="frmUserEMail" noframe="true">
              <forms:html>
    		       <ctrl:list id="emailList" property="emailList" rows="${sessionScope.noRows}" title="user.eMail" formElement="true">
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
          
        </ctrl:tabset>
      </forms:html>      
                                      
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
         <c:if test="${not empty userForm.modelId}">
			  <forms:button onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>   
         </c:if>   
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	  </forms:form>  
   </html:form>	
   <misc:confirm key="user.delete"/>
	
  </body>
</html>
