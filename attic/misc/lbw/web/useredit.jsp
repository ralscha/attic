<%@ include file="include/taglibs.jspf"%>

<html>
  <head>
    <title></title>
  </head>
  <body>
	      
   <misc:initSelectOptions id="localeOption" name="localeOptions" scope="application" />
   <misc:initSelectOptions id="usergroupOption" name="userGroupOptions" scope="session" />
   <misc:initSelectOptions id="werkOptions" name="werkOptions" scope="request"/>
          
   <html:form action="/userEdit">
     <html:hidden property="modelId"/>
     <html:hidden property="version"/>
     <html:hidden property="cookieLogin"/>
     	  
     <forms:form type="edit" caption="useredit.title" formid="frmUser" width="550">
	
      
      <forms:html valign="top" join="false">
        <ctrl:tabset id="usertab" styleId="usertab" width="500" bgcolor="#DADFE0" runat="client" property="tabset" formElement="true">

  	       <ctrl:tab tabid="general" title="user.general" tooltip="user.general">
	         <forms:form type="edit" formid="frmUserGeneral" noframe="true">
        	  <forms:text label="login.userName"  property="userName"     required="true" size="40" maxlength="255"/>
            
		        <forms:text label="user.name"   property="name"   required="true" size="40" maxlength="255"/>
            <forms:text label="user.firstName"   property="firstName"   required="true" size="40" maxlength="255"/>

   		      <forms:text label="user.eMail"  property="email"  required="true" size="40" maxlength="255"/>    						
               
		        <c:if test="${not UserForm.cookieLogin}">
		          <forms:password label="login.password"  property="password"   required="true" size="40" maxlength="255"/>
   	          <forms:password label="user.retypePassword"  property="retypePassword"  required="true"  size="40" maxlength="255"/>
              </c:if>

              <forms:select  label="user.language"  property="localeStr"  required="true">
                <base:options  empty="empty" name="localeOption"/>
              </forms:select>				
			
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
           

       <ctrl:tab tabid="werke" title="bewertung.bewertungen" tooltip="bewertung.bewertungen">
       <forms:form type="edit" formid="frmUserWerke" noframe="true">
       
       <forms:checkbox label="bewertung.preis" property="kriteriumPreis"/>
       <forms:checkbox label="bewertung.service" property="kriteriumService"/>
       <forms:checkbox label="bewertung.teilequalitaet" property="kriteriumTeilequalitaet"/>
       <forms:checkbox label="bewertung.sap" property="kriteriumSap"/>
       
       <forms:swapselect label="werk.werks" valign="top"              
         property="werkIds"              
         orientation="horizontal"      
         labelLeft="common.available"      
         labelRight="common.selected" 
         runat="client"          
         style="width: 170px;">
         <base:options name="werkOptions"/>

       </forms:swapselect>  
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
