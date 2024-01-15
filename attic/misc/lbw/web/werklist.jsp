<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
  
    <html:form action="/werkList" focus="value(name)">
	  <forms:form type="search" formid="frmListWerk">
      <forms:html>
      <table>
        <tr>
          <td><bean:message key="werk.name"/></td>
          <td>&nbsp;</td>
        </tr>
		    <tr>		    
	        <td><ctrl:text property="value(name)" size="20" maxlength="255"/></td>
   	      <td><ctrl:button base="buttons.src" name="btnSearch" src="btnSearch1.gif" title="button.title.search"/></td>
        </tr>
      </table>
      </forms:html>				
		</forms:form>	  	  
  </html:form>		
	
	<p></p>
	<c:if test="${not empty listControl}">
	<ctrl:list id="werkList" 
		   action="/werkList" 
			 name="listControl" 
			 scope="session"
			 title="werk.werks" 
			 rows="${sessionScope.noRows}"  
			 createButton="true"
			 refreshButton="true">
    <ctrl:columntext title="werk.name" property="name" width="225" maxlength="30" sortable="true"/>				  
		<ctrl:columnedit tooltip="common.edit" />
		<ctrl:columndelete tooltip="common.delete" property="deletable" onclick="return confirmRequest('@{bean.map.name}');"/>		
	</ctrl:list>	
  <misc:confirm key="werk.delete"/>
  </c:if>
</body>
</html>
