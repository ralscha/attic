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
      
   <html:form action="/editUser" focus="userName">
     <html:hidden property="modelId"/>
     <html:hidden property="version"/>
     <html:hidden property="cookieLogin"/>
     	  
     <forms:form type="edit" caption="user.headline" formid="frmUser" width="660">
	
	   <forms:text label="login.userName"  property="userName"     required="true" size="40" maxlength="255"/>
      
      <forms:html label="" valign="top" join="false">
        <ctrl:tabset name="UserForm" id="usertab" width="500" bgcolor="#DADFE0" runat="client" property="tabset" formElement="true">

  	       <ctrl:tab tabid="general" title="user.general" tooltip="user.general">
	         <forms:form type="edit" formid="frmUserGeneral" noframe="true">
            
		        <forms:text label="user.name"   property="name"   required="true" size="40" maxlength="255"/>
              <forms:text label="user.firstName"   property="firstName"   required="true" size="40" maxlength="255"/>

   		     <forms:text label="user.eMail"  property="email"  required="true" size="40" maxlength="255"/>    						
               
		        <c:if test="${not UserForm.cookieLogin}">
		          <forms:password    label="login.password"  property="password"   required="true" size="40" maxlength="255"/>
   	          <forms:password    label="user.retypePassword"  property="retypePassword"  required="true"  size="40" maxlength="255"/>
              </c:if>

              <forms:select  label="user.language"  property="localeStr"  required="true">
                <base:options  empty="empty" name="localeOption"/>
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
                    
        </ctrl:tabset>
      </forms:html>      
                                      
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
         <c:if test="${not empty UserForm.modelId}">
			  <forms:button onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>   
         </c:if>   
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	  </forms:form>  
   </html:form>	
   <misc:confirm key="user.delete"/>
	
  </body>
</html>
