<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title></head>
  <body>
    
    <menu:crumbs value="crumb1" labellength="40">
	    <menu:crumb	crumbid="crumb1"	title="group.groups"/>
	    <menu:crumb	crumbid="crumb2"	disabled="true"   title="group.headline"/>
    </menu:crumbs>    
<br>      		  	  		
	<html:form action="/listGroup.do"  focus="value(groupName)">
		<forms:form type="search" formid="frmListGroup">
		  <forms:html>
			  <table cellspacing='0' cellpadding='0'>			  
  			  <tr>		  		    
  		      <td class='searchfl'><bean:message key="group.groupName"/>:</td>			    
   				  <td class='fb'></td>
  	      </tr>      
  			  <tr>
   				  <td class='fd'><html:text property="value(groupName)" size="20" maxlength="255"/></td>
			<td class='fb'>
			<ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/>		    
         </td>

  			  </tr>			  
			  </table>
			</forms:html>		  	
		</forms:form>	  	  
  </html:form>		
	
	<p></p>
	
	<ctrl:list id="grouplist" 
		   action="listGroup.do" 
			 name="groups" 
			 scope="session"
			 title="group.groups" 
			 rows="${sessionScope.noRows}"  
          minRows="${sessionScope.noRows}"
			 createButton="true"
			 refreshButton="true">
	  <ctrl:columntext title="group.groupName" property="groupName" width="225" maxlength="30" sortable="true"/>
		<ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.groupName}');"/>		
	</ctrl:list>
	
  <misc:confirm key="group.delete"/>
</body>
</html>