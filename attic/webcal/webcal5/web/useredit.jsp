<%@ include file="include/taglibs.jspf"%>

<html>
  <head>
    <title></title>
  </head>
  <body>
	
   <misc:initSelectOptions id="localeOption" name="localeOptions" scope="application" />
   <misc:initSelectOptions id="usergroupOption" name="userGroupOptions" scope="session" />
   <misc:initSelectOptions id="timezoneOption" name="timezoneOptions" scope="application" />
      
   <html:form action="/userEdit">
     <html:hidden property="modelId"/>
     <html:hidden property="version"/>
     <html:hidden property="cookieLogin"/>
     	  
     <forms:form type="edit" caption="useredit.title" formid="frmUser" width="550">
	
      
      <forms:html valign="top" join="false">
        <ctrl:tabset id="usertab" styleId="usertab" width="500" bgcolor="#DADFE0" runat="client" property="tabset" formElement="true">

  	       <ctrl:tab tabid="general" title="user.general" tooltip="user.general">
	         <forms:form type="edit" formid="frmUserGeneral" noframe="true">
        	  <forms:checkbox label="user.enabled" property="enabled"></forms:checkbox>
         	    <forms:text label="login.userName"  property="loginName"     required="true" size="40" maxlength="255"/>
            
              <forms:text label="user.name"   property="name"   required="true" size="40" maxlength="255"/>
              <forms:text label="user.firstName"   property="firstName"   required="true" size="40" maxlength="255"/>
              <forms:text label="user.shortName"  property="shortName"  required="false" size="40" maxlength="30"/>


              <forms:select  label="user.language"  property="localeStr"  required="true">
                <base:options  empty="empty" name="localeOption"/>
              </forms:select>				
			
              <forms:select  label="userconfig.timezone"  property="timezone"  required="true">
                <base:options  empty="empty" name="timezoneOption"/>
              </forms:select>

		        <c:if test="${not UserForm.cookieLogin}">
		          <forms:password label="login.password"  property="loginPassword"   required="false" size="40" maxlength="255"/>
   	          <forms:password join="true" label="user.retypePassword"  property="retypePassword"  required="false"  size="40" maxlength="255"/>
   	          
   	          <c:if test="${not empty UserForm.modelId}">
   	            <forms:html label="" join="true">
                  <ctrl:button base="buttons.src" name="btnPasswordCreate"  src="btnPasswordCreate1.gif"  title="button.title.passwordcreate"/>	
   	            </forms:html>
              </c:if>
            </c:if>
									
            </forms:form>
          </ctrl:tab>

       <ctrl:tab tabid="usergroups" title="usergroup.userGroups" tooltip="usergroup.userGroups">
       <forms:form type="edit" formid="frmUserUserGroups" noframe="true">
       <forms:swapselect label="usergroup.userGroups" valign="top"              
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
    		       <ctrl:list noframe="true" id="emailList" property="emailList" rows="${sessionScope.noRows}" title="user.eMail" formElement="true">
    		         <ctrl:columntext title="user.eMail" property="email" width="350" maxlength="50" sortable="true"/>
                  <ctrl:columncheck title="user.eMail.default" property="default"/>
    		         <ctrl:columndelete tooltip="common.delete"/>		
    	          </ctrl:list>
              </forms:html>
              <forms:group orientation="horizontal">
                
		            <forms:text property="addEmail" required="false" size="60" maxlength="255"/>
  			        <forms:html>
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
         <c:if test="${UserForm.deletable}">
			  <forms:button onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>   
         </c:if>   
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	  </forms:form>  
   </html:form>	
   <misc:confirm key="user.delete"/>
	
  </body>
</html>
