<%@ include file="include/taglibs.jspf"%>


<html>
  <head><title></title>  
  	<script language="JavaScript" src="<c:url value='/scripts/overlib_mini.js'/>" type="text/javascript"></script>  
  </head>
  <body>
  <div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>

  <html:form action="/contactList" focus="value(searchText)">
	<forms:form type="search" formid="frmListKurs">

    <forms:html>
    <table>
      <tr>
        <td><bean:message key="contact.searchText"/></td>
        <td><bean:message key="contact.showMode"/></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>        
      </tr>
      <tr>
        <td><ctrl:text property="value(searchText)" size="20" maxlength="255"/></td>
        <td>
          <html:select property="value(showMode)">
            <base:option key="all" label="contact.showMode.all" />
            <base:option key="privateOnly" label="contact.showMode.privateOnly" />
            <base:option key="publicOnly" label="contact.showMode.publicOnly" />
          </html:select>
        </td>
        <td><ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/></td>
        <td>
         <table border="0" cellpadding="4" cellspacing="1" width="100%">
           <tr bgcolor="#ffffff"> 
             <c:forEach items="a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z" var="cat">
               <th<c:if test="${MapForm.valueMap.category == cat}"> bgcolor="#CCCCCC"</c:if>> 
               <a href="<c:url value='contactList.do?value(search)=1&value(category)=${cat}'/>">${fn:toUpperCase(cat)}</a>
               </th>
             </c:forEach>
             <th<c:if test="${not empty MapForm.valueMap.all}"> bgcolor="#CCCCCC"</c:if>>&nbsp;<a href="<c:url value='contactList.do?value(search)=2&value(all)=1'/>"><bean:message key="contact.all"/></a>&nbsp;</th>
           </tr>
         </table>                                      
        </td>
      </tr>
    </table>
    </forms:html>      
	  	
	</forms:form>
	</html:form>

  <c:if test="${not empty listControl}">	
	<p></p>
	
	<ctrl:list id="contactlist" 
		   action="/contactList" 
			 name="listControl" 
			 scope="session"
			 title="contact.contacts" 
			 rows="${sessionScope.noRows}"  
			 createButton="true"
			 refreshButton="true">
       
    <ctrl:columnhtml title="" id="row">
      <div onmouseover="return overlib('${row.map.info}', FGCOLOR, '#FFD700', WIDTH, 110, ABOVE);" onmouseout="return nd();">
      <img src="fw/def/image/msgInput.gif" border="0" width="16" height="16">      
      </div>
    </ctrl:columnhtml>   
		<ctrl:columntext title="contact.firstName" property="firstName" width="200" maxlength="255" sortable="true"/>		
		<ctrl:columntext title="contact.lastName" property="lastName" width="200" maxlength="255" sortable="true"/>		    
		<ctrl:columntext title="contact.companyName" property="companyName" width="200" maxlength="255" sortable="true"/>		    
		<ctrl:columntext title="contact.email" property="email" width="200" maxlength="255" sortable="true"/>		    
            
    <ctrl:columngroup title="common.action" align="center">        
		<ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.firstName}');"/>		
		</ctrl:columngroup>
	</ctrl:list>
	
  <misc:confirm key="contact.delete"/>
  </c:if>
   
  
</body>
</html>
