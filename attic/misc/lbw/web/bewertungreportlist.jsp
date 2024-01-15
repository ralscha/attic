<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
  
    <misc:initSelectOptions id="quartalOptions" name="quartalOptions" scope="application"/>
    <misc:initSelectOptions id="einstufungOptions" name="einstufungOptions" scope="application"/>
  
    <html:form action="/bewertungReportList" focus="value(nr)">
	  <forms:form type="search" formid="frmListBewertungReport">
      <forms:html>
      <table>
        <tr>
          <td><bean:message key="bewertung.quartal"/></td>
          <td><bean:message key="time.year.singular"/></td>
          <td><bean:message key="bewertung.einstufung"/></td>
          <td>&nbsp;</td>
        </tr>
		    <tr>		    	        
          <td>
            <ctrl:select property="value(quartal)">
              <base:options name="quartalOptions"/>
            </ctrl:select>  
          </td>
          <td><ctrl:spin size="4" maxlength="4" property="value(jahr)"/></td>
          <td>
            <ctrl:select property="value(einstufung)">
              <base:options empty="empty" name="einstufungOptions"/>
            </ctrl:select> 
          </td>
          
          
   	      <td><ctrl:button base="buttons.src" name="btnSearch" src="btnSearch1.gif" title="button.title.search"/></td>
        </tr>
      </table>
      </forms:html>				
		</forms:form>	  	  
    </html:form>  
	
    	<p></p>
    	<c:if test="${not empty listControl}">
        <ctrl:list 
             action="/bewertungReportList" 
             name="listControl" 
             scope="session">
        </ctrl:list>
      </c:if>
  

    
    
    
</body>
</html>
