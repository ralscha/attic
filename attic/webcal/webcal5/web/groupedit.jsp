<%@ include file="include/taglibs.jspf"%>


<html>
  <head>
    <title></title>
  </head>

  <body>
	

    
  <misc:initSelectOptions id="userOption" name="userOptions" scope="request" />
  <misc:initSelectOptions id="resourceGroupOption" name="resourceGroupOptions" scope="request" />  
  
	<html:form action="/groupEdit">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

	<forms:form type="edit" caption="groupedit.title" formid="frmGroup">

    <logic:iterate id="entry" indexId="ix" name="GroupForm" property="entry" type="ch.ess.base.web.NameEntry">
     <c:set var="label">#<bean:message key="group.groupName" /> (${entry.language})</c:set>
  
     <html:hidden name="entry" indexed="true" property="locale"/>
     <forms:text label="${label}" size="40" maxlength="255" required="true"  property="entry[${ix}].name"/>
    </logic:iterate>	
    
    <misc:hasFeature feature="event">
      <forms:checkbox  label="event.menu"  property="eventGroup"/>
    </misc:hasFeature>
    
    <misc:hasFeature feature="task">    
      <forms:checkbox  label="task.menu"  property="taskGroup" join="true"/>
    </misc:hasFeature>
    
    <misc:hasFeature feature="document">    
      <forms:checkbox  label="directory.menu"  property="documentGroup" join="true"/>
    </misc:hasFeature>
      
    <misc:hasFeature feature="time">      
      <forms:checkbox  label="time.menu"  property="timeGroup" join="true"/>           
    </misc:hasFeature>
        
    <forms:html label="" valign="top" join="false" width="600">
     <ctrl:tabset id="grouptab" styleId="grouptab" width="500" bgcolor="#DADFE0" runat="client" property="tabset" formElement="true">  
        <ctrl:tab tabid="tab1"  title="user.eMail" tooltip="user.eMail">   
          <forms:form noframe="true" type="edit" formid="frmGroupMail" locale="true">       
          <forms:html>
          		<ctrl:list noframe="true" id="emailList" 
          			 property="emailList"     			 
          			 rows="${sessionScope.noRows}"  
                 title="user.eMail"	              		
                 formElement="true">
          		<ctrl:columntext title="user.eMail" property="email" width="400" maxlength="50" sortable="true"/>
              <ctrl:columncheck title="user.eMail.default" property="default"/>
          		<ctrl:columndelete tooltip="common.delete"/>		
          	  </ctrl:list>
          </forms:html>

          <forms:row>            
        		<forms:text property="addEmail" required="false" size="80" maxlength="255"/>
            <forms:html>        			  
			        <ctrl:button base="buttons.src" name="btnAdd"  src="btnAdd1.gif"  title="button.title.add"/>                
            </forms:html>
          </forms:row>
          </forms:form>
        </ctrl:tab>

				<ctrl:tab	tabid="tab2"	title="user.users"	tooltip="user.users">
              <forms:form noframe="true" type="edit" formid="frmGroupUsers" locale="true">                   
                   <forms:swapselect 
                    property="userIds"              
                    orientation="horizontal"      
                    labelLeft="common.available"      
                    labelRight="common.selected" 
                    runat="client"     
                    size="18"      
                    style="width: 250px;">
                      <base:options name="userOption"/>
                   </forms:swapselect>   
                   
              </forms:form>  
            </ctrl:tab>

				<ctrl:tab	tabid="tab3"	title="group.accessUser"	tooltip="group.accessUser">
              <forms:form noframe="true" type="edit" formid="frmGroupAccessUsers" locale="true">                   
                   <forms:swapselect 
                    property="accessUserIds"              
                    orientation="horizontal"      
                    labelLeft="common.available"      
                    labelRight="common.selected" 
                    runat="client"     
                    size="18"      
                    style="width: 250px;">
                      <base:options name="userOption"/>
                   </forms:swapselect>                      
              </forms:form>                  	
           </ctrl:tab>
           <% /* %>
				<ctrl:tab	tabid="tab4"	title="resourceGroup.resourceGroups"	tooltip="resourceGroup.resourceGroups"> 
              <forms:form noframe="true" type="edit" formid="frmGroupResourceGroup">                   
                   <forms:swapselect 
                    property="resourceGroupIds"              
                    orientation="horizontal"      
                    labelLeft="common.available"      
                    labelRight="common.selected" 
                    runat="client"     
                    size="18"      
                    style="width: 250px;">
                      <base:options name="resourceGroupOption"/>
                   </forms:swapselect>                      
              </forms:form>                	
            </ctrl:tab>        
         <% */ %>
			</ctrl:tabset>
		  		    	
    </forms:html>   
                		                            			
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>			
						
      <c:if test="${GroupForm.deletable}">
			<forms:button onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>   
      </c:if>   
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
          
	</forms:form>
  </html:form>	
  <misc:confirm key="group.delete"/>
  
</body>
</html>