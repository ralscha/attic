<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title>
  <#if hasDates>
  <misc:popupCalendarJs />
  </#if>
  </head>
  <body>
	
  <#foreach prop in properties><#if prop.reference>
  <misc:initSelectOptions id="${prop.name}Options" name="${prop.name}Options" scope="session" />
  <#elseif prop.many || prop.manymany>
  <misc:initSelectOptions id="${prop.typOfSet?uncap_first}Options" name="${prop.typOfSet?uncap_first}Options" scope="session" />
  </#if></#foreach>  
    
	<html:form action="/${className?uncap_first}Edit" focus="${headProperty}">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

	<forms:form type="edit" caption="${className?lower_case}edit.title" formid="frm${className}">
    
    <#foreach prop in properties>
      <#if prop.typ == 'Date'>
    <forms:html label="${className?uncap_first}.${prop.name}">
		  <html:text property="${prop.name}" styleId="${prop.name}" size="${prop.size}" maxlength="${prop.maxLength}"/><img src="images/cal.gif" alt="" name="select${prop.name?cap_first}" id="select${prop.name?cap_first}" width="16" height="16" border="0">
		  <#if prop.required>
		  <img title='<bean:message key="fw.form.required" bundle="com.cc.framework.message"/>' width='9' height='13' style='margin-left:3px;' border='0' src='fw/def/image/required.gif'>
		  </#if>
	  </forms:html>	
	    <#elseif prop.typ == 'Boolean'>
    <forms:checkbox label="${className?uncap_first}.${prop.name}" property="${prop.name}"/>	
      <#elseif prop.reference>
	  <forms:select label="${className?uncap_first}.${prop.name}" property="${prop.name}Id" required="${prop.required?string}">
	  	<base:options empty="empty" name="${prop.name}Options" />
	  </forms:select> 
	    <#elseif prop.many>
      <forms:swapselect label="${className?uncap_first}.${prop.name}" valign="top"              
         property="${prop.name}Ids"              
         orientation="horizontal"      
         labelLeft="common.available"      
         labelRight="common.selected" 
         runat="client"  
         size="18"       
         style="width: 250px;">
         <base:options name="${prop.typOfSet?uncap_first}Options"/>
       </forms:swapselect>  	                     
	    <#elseif prop.manymany>
      <forms:swapselect label="${className?uncap_first}.${prop.name}" valign="top"              
         property="${prop.typOfSet?uncap_first}Ids"              
         orientation="horizontal"      
         labelLeft="common.available"      
         labelRight="common.selected" 
         runat="client"  
         size="18"       
         style="width: 250px;">
         <base:options name="${prop.typOfSet?uncap_first}Options"/>
       </forms:swapselect>  	                     

	    <#else>
	  <forms:text label="${className?uncap_first}.${prop.name}" property="${prop.name}" required="${prop.required?string}" size="${prop.size}" maxlength="${prop.maxLength}"/>			  
	    </#if>
    </#foreach>
                    		
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
      <c:if test="${jspExpressionStart}${className}Form.deletable${jspExpressionEnd}">
			<forms:button  onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      </c:if>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  <#foreach prop in properties>
  <#if prop.typ == 'Date'>
  <misc:popupCalendar element="${prop.name}" trigger="select${prop.name?cap_first}" showOthers="true"/>
  </#if>
  </#foreach>  
  
  
  
  <misc:confirm key="${className?uncap_first}.delete"/>
</body>
</html>
