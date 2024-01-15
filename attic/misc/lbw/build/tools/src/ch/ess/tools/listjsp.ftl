<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
  
  <#foreach prop in properties><#if prop.filter><#if prop.reference> 
  <misc:initSelectOptions id="${prop.name}Options" name="${prop.name}Options" scope="session" />
  </#if></#if></#foreach>
  <html:form action="/${className?uncap_first}List" focus="value(<#foreach prop in properties><#if prop.filter>${prop.name}<#break></#if></#foreach>)">
	  <forms:form type="search" formid="frmList${className}">
      <forms:html>
      <table>
        <tr>
		      <#foreach prop in properties><#if prop.filter>
          <td><bean:message key="${className?uncap_first}.${prop.name}"/></td>
          </#if></#foreach>
          <td>&nbsp;</td>
          <#if showClearButton>
          <td>&nbsp;</td>
          </#if>          
        </tr>
		    <tr>		    
	        <#foreach prop in properties><#if prop.filter>
					<#if prop.reference>
					<td>
				  <ctrl:select property="value(${prop.name}Id)">
	          <base:options empty="empty" name="${prop.name}Options"/>
	        </ctrl:select> 				
	        </td>
	 	      <#else>
	        <td><ctrl:text property="value(${prop.name})" size="20" maxlength="255"/></td>
					</#if>
				  </#if></#foreach>        
   	      <td><ctrl:button base="buttons.src" name="btnSearch" src="btnSearch1.gif" title="button.title.search"/></td>
          <#if showClearButton>
          <td><ctrl:button base="buttons.src" name="btnClearSearch" src="btnClearSearch1.gif" title="button.title.clearsearch" /></td>
          </#if>   	      
        </tr>
      </table>
      </forms:html>				
		</forms:form>	  	  
  </html:form>		
	
	<p></p>
	<c:if test="${jspExpressionStart}not empty listControl${jspExpressionEnd}">
	<ctrl:list id="${className?uncap_first}List" 
		   action="/${className?uncap_first}List" 
			 name="listControl" 
			 scope="session"
			 title="${className?uncap_first}.${className?uncap_first}s" 
			 rows="${jspExpressionStart}sessionScope.noRows${jspExpressionEnd}"  
			 createButton="true"
			 refreshButton="true">
		<#foreach prop in listproperties>
		  <#if prop.typ == 'BigDecimal'>
		<ctrl:columntext title="${className?uncap_first}.${prop.name}" property="${prop.name}" width="40" sortable="true" converter="ch.ess.base.web.BigDecimalConverter"/>				  
		  <#elseif prop.typ == 'Date'>
		<ctrl:columntext title="${className?uncap_first}.${prop.name}" property="${prop.name}" width="40" sortable="true" converter="ch.ess.base.web.DateConverter"/>				  
		  <#elseif prop.typ == 'Integer'>	 
		<ctrl:columntext title="${className?uncap_first}.${prop.name}" property="${prop.name}" width="40" sortable="true"/>		
		  <#else>
    <ctrl:columntext title="${className?uncap_first}.${prop.name}" property="${prop.name}" width="225" maxlength="30" sortable="true"/>				  
		  </#if>
		</#foreach>
		<ctrl:columnedit tooltip="common.edit" />
		<#if showCopyColumn>
		<misc:columncopy tooltip="common.copy" />
		</#if>
		<ctrl:columndelete tooltip="common.delete" property="deletable" onclick="return confirmRequest('@{bean.map.${headProperty}}');"/>		
	</ctrl:list>	
  <misc:confirm key="${className?uncap_first}.delete"/>
  </c:if>
</body>
</html>
