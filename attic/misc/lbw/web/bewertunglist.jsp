<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
  
    <misc:initSelectOptions id="quartalOptions" name="quartalOptions" scope="application"/>
    <misc:initSelectOptions id="userWerkOptions" name="userWerkOptions" scope="request"/>
  
    <html:form action="/bewertungList" focus="value(nr)">
	  <forms:form type="search" formid="frmListBewertung">
      <forms:html>
      <table>
        <tr>
          <td><bean:message key="lieferant.nr"/></td>
          <td><bean:message key="lieferant.name"/></td>
          <td><bean:message key="werk"/></td>
          <td><bean:message key="bewertung.quartal"/></td>
          <td><bean:message key="time.year.singular"/></td>
          <td>&nbsp;</td>
        </tr>
		    <tr>		    
	        <td><ctrl:text property="value(nr)" size="20" maxlength="255" onkeypress="if (event.keyCode == 13) {document.forms[0].submit(); return false;}"/></td>
	        <td><ctrl:text property="value(name)" size="20" maxlength="255" onkeypress="if (event.keyCode == 13) {document.forms[0].submit(); return false;}"/></td>
          
          <td>
            <ctrl:select property="value(werkId)" onchange="document.forms[0].submit()">
              <base:options name="userWerkOptions"/>
            </ctrl:select>  
          </td>
          
          <td>
            <ctrl:select property="value(quartal)" onchange="document.forms[0].submit()">
              <base:options name="quartalOptions"/>
            </ctrl:select>  
          </td>
          <td><ctrl:spin size="4" maxlength="4" property="value(jahr)"/></td>
          
   	      <td><ctrl:button base="buttons.src" name="btnSearch" src="btnSearch1.gif" title="button.title.search"/></td>
        </tr>
      </table>
      </forms:html>				
		</forms:form>	  	  
	
	
    	<p></p>
    	<c:if test="${not empty listControl}">
    	<ctrl:list id="bewertungList" 
    		   action="/bewertungList" 
    			 name="listControl" 
    			 scope="session"
    			 title="bewertung.bewertungen" 
    			 rows="${sessionScope.noRows}"  
    			 createButton="false"
    			 refreshButton="true"
           formElement="true">
        <ctrl:columntext title="lieferant" property="lieferant" width="225" maxlength="30" sortable="true"/>			  
        <c:if test="${preisAnzeige}">
        <ctrl:columntext title="bewertung.preis" editable="true" align="center" property="preis" width="90" sortable="true" size="5"/>                  
        </c:if>
        <c:if test="${serviceAnzeige}">
        <ctrl:columntext title="bewertung.service" editable="true" align="center" property="service" width="90" sortable="true" size="5"/>                  
        </c:if>
        <c:if test="${teilequalitaetAnzeige}">
        <ctrl:columntext title="bewertung.teilequalitaet" editable="true" align="center" property="teilequalitaet" width="90" sortable="true" size="5"/>                                  
        </c:if>
        <c:if test="${sapAnzeige}">
        <ctrl:columngroup title="bewertung.sap" align="center">
        <ctrl:columntext title="bewertung.mengentreue" editable="true" align="center" property="mengentreue" width="90" sortable="true" size="5"/>                                  
        <ctrl:columntext title="bewertung.liefertreue" editable="true" align="center" property="liefertreue" width="90" sortable="true" size="5"/>                                                  
        </ctrl:columngroup>
        </c:if>
    	</ctrl:list>
      <br>
      <ctrl:button base="buttons.src" name="btnSave" src="btnSave1.gif"/>	
      </c:if>
  
    </html:form>  
    
    
    
</body>
</html>
