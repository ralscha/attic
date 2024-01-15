<%@ include file="include/taglibs.jspf"%>


<html>
  <head>
    <title></title>
   </head>
  <body>

  
	<html:form action="/categoryEdit">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

	<forms:form type="edit" caption="categoryedit.title" formid="frmCategory" width="600">
            
    <logic:iterate id="entry" indexId="ix" name="CategoryForm" property="entry" type="ch.ess.base.web.NameEntry">
     <c:set var="label">#<bean:message key="category.categoryName" /> (${entry.language})</c:set>
  
    <html:hidden name="entry" indexed="true" property="locale"/>
     <forms:text label="${label}" size="40" maxlength="255" required="true"  property="entry[${ix}].name"/>
    </logic:iterate>							        
		        
    <forms:text label="category.sequence" property="sequence" required="false" size="3" maxlength="3"/>       
    <forms:text label="category.icalKey" property="icalKey" required="false" size="40" maxlength="255"/>		    		  
    <forms:checkbox label="category.defaultCategory" property="defaultCategory" required="false" />    		        
 

    <forms:html label="category.colour" valign="top">
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
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>									
      <c:if test="${CategoryForm.deletable}">
			<forms:button onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>   
      </c:if>   
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  <misc:confirm key="category.delete"/>
</body>
</html>
