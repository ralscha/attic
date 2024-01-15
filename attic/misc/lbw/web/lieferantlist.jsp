<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
  
    <html:form action="/lieferantList" focus="value(nr)">
	  <forms:form type="search" formid="frmListLieferant">
      <forms:html>
      <table>
        <tr>
          <td><bean:message key="lieferant.nr"/></td>
          <td><bean:message key="lieferant.name"/></td>
          <td>&nbsp;</td>
        </tr>
		    <tr>		    
	        <td><ctrl:text property="value(nr)" size="20" maxlength="255"/></td>
	        <td><ctrl:text property="value(name)" size="20" maxlength="255"/></td>
   	      <td><ctrl:button base="buttons.src" name="btnSearch" src="btnSearch1.gif" title="button.title.search"/></td>
        </tr>
      </table>
      </forms:html>				
		</forms:form>	  	  
  </html:form>		
	
	<p></p>
	<c:if test="${not empty listControl}">
	<ctrl:list id="lieferantList" 
		   action="/lieferantList" 
			 name="listControl" 
			 scope="session"
			 title="lieferant.lieferants" 
			 rows="${sessionScope.noRows}"  
			 createButton="true"
			 refreshButton="true">
    <ctrl:columntext title="lieferant.nr" property="nr" width="100" maxlength="30" sortable="true"/>				  
    <ctrl:columntext title="lieferant.kurz" property="kurz" width="200" maxlength="30" sortable="true"/>				  
    <ctrl:columntext title="lieferant.name" property="name" width="300" maxlength="30" sortable="true"/>				  
    <ctrl:columntext title="lieferant.strasse" property="strasse" width="225" maxlength="30" sortable="true"/>				  
    <ctrl:columntext title="lieferant.plz" property="plz" width="225" maxlength="30" sortable="true"/>				  
    <ctrl:columntext title="lieferant.ort" property="ort" width="225" maxlength="30" sortable="true"/>				  
		<ctrl:columnedit tooltip="common.edit" />
		<misc:columncopy tooltip="common.copy" />
		<ctrl:columndelete tooltip="common.delete" property="deletable" onclick="return confirmRequest('@{bean.map.nr}');"/>		
	</ctrl:list>	
  <misc:confirm key="lieferant.delete"/>
  </c:if>
</body>
</html>
