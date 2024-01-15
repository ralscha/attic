<%@ include file="include/taglibs.jspf"%>


<html>
  <head>
    <title></title>
   </head>
  <body>

  <menu:crumbs value="crumb2" labellength="40">
	    <menu:crumb	crumbid="crumb1"	action="/listCategory.do" title="category.categories"/>
	    <menu:crumb	crumbid="crumb2"	title="category.headline"/>
  </menu:crumbs>   
  <br>  
  
	<html:form action="/editCategory">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

  <c:set var="label"><bean:message key="category.headline" /></c:set>	  
	<forms:form type="edit" caption="${label}" formid="frmCategory" width="600" locale="false">
            
    <logic:iterate id="entry" indexId="ix" name="categoryForm" property="entry" type="ch.ess.cal.web.NameEntry">
     <c:set var="label"><bean:message key="category.categoryName" /> (${entry.language})</c:set>
  
    <html:hidden name="entry" indexed="true" property="locale"/>
     <forms:text label="${label}" size="40" maxlength="255" required="true"  property="entry[${ix}].name"/>
    </logic:iterate>							        
		        
    <c:set var="label"><bean:message key="category.icalKey" /></c:set>	              
    <forms:text label="${label}" property="icalKey" required="false" size="40" maxlength="255"/>		    		  

    <c:set var="label"><bean:message key="category.unknown" /></c:set>	    	        
    <forms:checkbox label="${label}" property="unknown" required="false" />    

    <c:set var="label"><bean:message key="category.colour" /></c:set>	  		        	  
    <forms:html label="${label}" valign="top">
    	<table>
    	<c:forTokens items="00,33,66,99,CC,FF" delims="," var="r">
    	  <tr>
    	  <c:forTokens items="00,33,66" delims="," var="g">
    	    <c:forTokens items="00,33,66,99,CC,FF" delims="," var="b">	    
    		  <c:set value="${r}${g}${b}" var="tmpColour"/>  
    	      <td style="background-color:#${tmpColour}"><html:radio property="colour" value="${tmpColour}"/></td>
    	    </c:forTokens>
    	  </c:forTokens>
    	  </tr>
    	</c:forTokens>
    	<c:forTokens items="00,33,66,99,CC,FF" delims="," var="r">
    	  <tr>
    	  <c:forTokens items="99,CC,FF" delims="," var="g">
    	    <c:forTokens items="00,33,66,99,CC,FF" delims="," var="b">	
    		  <c:set value="${r}${g}${b}" var="tmpColour"/>       
    	      <td style="background-color:#${tmpColour}"><html:radio property="colour" value="${tmpColour}"/></td>
    	    </c:forTokens>
    	  </c:forTokens>
    	  </tr>
    	</c:forTokens>	
    	</table>     
    </forms:html>
   	  						
		<forms:buttonsection join="false">
         <c:set var="buttonsbase"><bean:message key="buttons.src" /></c:set>
			<forms:button   base="${buttonsbase}" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="${buttonsbase}" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
			
						
      <c:if test="${not empty categoryForm.modelId}">
			<forms:button onclick="return confirmRequest('');" base="${buttonsbase}" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>   
      </c:if>   
			<forms:button   base="${buttonsbase}" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  <misc:confirm key="category.delete"/>
</body>
</html>