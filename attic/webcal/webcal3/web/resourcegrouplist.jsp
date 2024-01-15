<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title></head>
  <body>
	
    <menu:crumbs value="crumb1" labellength="40">
	    <menu:crumb	crumbid="crumb1"	title="resourceGroup.resourceGroups"/>
	    <menu:crumb	crumbid="crumb2"	disabled="true"   title="resourceGroup.headline"/>
    </menu:crumbs>   
<br>        		  	  		
	<html:form action="/listResourceGroup.do"  focus="value(resourceGroupName)">
		<forms:form type="search" formid="frmListResourceGroup">
		  <forms:html>
			  <table cellspacing='0' cellpadding='0'>			  
			  <tr>		  		    
		      <td class='searchfl'><bean:message key="resourceGroup.resourceGroupName"/>:</td>			    
			    <td class='fb'></td>
	      </tr>      
			  <tr>
  			  <td class='fd'><html:text property="value(resourceGroupName)" size="20" maxlength="255"/></td>
			<td class='fb'>
			<ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/>		    
         </td>

			  </tr>		  
			  </table>
			</forms:html>		  	
		</forms:form>	  	  
  </html:form>		
	
	<p></p>
	
	<ctrl:list id="resourcegrouplist" 
		   action="listResourceGroup.do" 
			 name="resourcegroups" 
			 scope="session"
			 title="resourceGroup.resourceGroups" 
			 rows="${sessionScope.noRows}"  
          minRows="${sessionScope.noRows}"
			 createButton="true"
			 refreshButton="true">
    <ctrl:columntext title="resourceGroup.resourceGroupName" property="resourceGroupName" width="225" maxlength="30" sortable="true"/>
	  <ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.resourceGroupName}');"/>		
	</ctrl:list>
	
  <misc:confirm key="resourceGroup.delete"/>
</body>
</html>