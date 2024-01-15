<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
  
    
	<html:form action="/appLinkList" focus="value(appLinkName)">
		<forms:form type="search" formid="frmListAppLink" caption="applinklist.title">
      <forms:html>
      <table>
        <tr>
          <td><bean:message key="appLink.name"/></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><ctrl:text property="value(linkName)" size="20" maxlength="255"/></td>
    			<td><ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/></td>
        </tr>
      </table>
      </forms:html>
		</forms:form>	  	  
  </html:form>		
	
	<p></p>
	
	<ctrl:list id="applinklist" 
		   action="/appLinkList" 
			 name="listControl" 
			 scope="session"
			 title="appLink.linkNames" 
			 rows="${sessionScope.noRows}"  
			 createButton="true"
			 refreshButton="true">
		<ctrl:columntext title="appLink.name" property="linkName" width="225" maxlength="30" sortable="true"/>	
		<ctrl:columntext title="appLink.link" property="appLink" width="225" maxlength="150" sortable="false"/>
		<ctrl:columngroup title="common.action" align="center">	
		<ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" property="deletable" onclick="return confirmRequest('@{bean.map.linkName}');"/>		
		</ctrl:columngroup>
	</ctrl:list>
	
  <misc:confirm key="applink.delete"/>
</body>
</html>
