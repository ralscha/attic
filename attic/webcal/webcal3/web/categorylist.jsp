<%@ include file="include/taglibs.jspf"%>


<html>
  <head><title></title></head>
  <body>

  <menu:crumbs value="crumb1" labellength="40">
	    <menu:crumb	crumbid="crumb1"	title="category.categories"/>
	    <menu:crumb	crumbid="crumb2"	disabled="true"   title="category.headline"/>
  </menu:crumbs>   
  <br> 	   
        	  	  	  		
	<html:form action="/listCategory.do" >
		<forms:form type="search" formid="frmListCategory">
		  <forms:html>
			  <table cellspacing='0' cellpadding='0'>			  
			  <tr>		  		    
		      <td class='searchfl'><bean:message key="category.categoryName"/>:</td>			    
				  <td class='fb'></td>
	      </tr>      
			  <tr>
 			    <td class='fd'><html:text property="value(categoryName)" size="20" maxlength="255"/></td>
		     	 <td class='fb'>
			    <ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/>		    
         </td>
			  </tr>			  
			  </table>
			</forms:html>		  	
		</forms:form>	  	  
  </html:form>		
	
	<p></p>
	
	<ctrl:list id="categorylist" 
		   action="listCategory.do" 
			 name="categorys" 
			 scope="session"
			 title="category.categories" 
			 rows="${sessionScope.noRows}"  
          minRows="${sessionScope.noRows}"
			 createButton="true"
			 refreshButton="true">
	  <ctrl:columntext title="category.categoryName" property="categoryName" width="225" maxlength="30" sortable="true"/>
    
    <ctrl:columnhtml title="category.colour" id="category">
      <div align="center">
      <table cellspacing="0" cellpadding="0"><tr><td>
      <img src='<c:url value="/pic.do?w=25&h=14&c=${category.map.colour}" />' alt="" width="25" height="14" border="0">      
      </td></tr></table>
      </div>
    </ctrl:columnhtml>
  
		<ctrl:columncheck title="category.unknown" property="unknown"/>
		<ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.categoryName}');"/>		
	</ctrl:list>
	
  <misc:confirm key="category.delete"/>
</body>
</html>