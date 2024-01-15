<%@ include file="include/taglibs.jspf"%>


<html>
  <head><title></title>
  </head>
  <body>
		<script>
		window.onload = function()
		{
			var s = window.location.search.substring(1).split('&');
		    if(!s.length) return;
		    var patt = /[0-9]+$/;
		    for(var i  = 0; i < s.length; i++) {
		        var parts = s[i].split('=');
		        if(patt.exec(unescape(parts[1]))!=null){
		        	if(unescape(parts[1]) != 1){
		        		window.location.hash = "row-"+unescape(parts[1]);
		        		break;
		        	}
		        }
		    }
		}
		
	</script>     
       
  <html:form action="/timeCustomerList" focus="value(customer)">
  <forms:form type="search" formid="frmListTimeCustomer">

    <forms:html>
    <table>
      <tr>
        <td><bean:message key="time.customer"/></td>
        <td><bean:message key="time.project"/></td>
        <td>&nbsp;</td>        
      </tr>
      <tr>
        <td><ctrl:text property="value(customer)" size="20" maxlength="255"/></td>      
        <td><ctrl:text property="value(project)" size="20" maxlength="255"/></td>  
        <td><ctrl:checkbox property="searchWithInactive" /><bean:message key="time.searchWithInactiveCust"/></td>    
        <td><ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/></td>
      </tr>
    </table>
    </forms:html>      
      
  </forms:form>
  </html:form>          
  
  <p></p>       
         

		<util:imagemap name="im_tree">
			<util:imagemapping rule="customer" src="images/timecustomer.png" width="16" height="16" />      
      <util:imagemapping rule="task" src="images/timetask.png" width="16" height="16" />      
		</util:imagemap>			
	
		<ctrl:treelist 
		  	id="timeCustomerList" 
		  	action="/timeCustomerList.do" 
		  	name="listControl" 
		  	scope="session" 
		  	title="time.customer.menu"
			createButton="true" 
			refreshButton="true"
			root="false"			
			linesAtRoot="true" 
			expandMode="multiple" 
			groupselect="false" 
			rows="${sessionScope.noRows}"
			runat="client" 
			checkboxes="true"			
		    printListButton="false"
      		exportListButton="true">
      		<%
            if(request.getParameter("action")!=null){
            	session.setAttribute("timeCustomerPage", timeCustomerList.getCurrentPage());
            }
            //System.out.println("tmpProjectPage: " + session.getAttribute("tmpProjectPage") + " currentpage: " + session.getAttribute(projectList.getName() + ".currentPage"));
            if(session.getAttribute("timeCustomerPage")!=null){
            	timeCustomerList.setCurrentPage( (Integer)session.getAttribute("timeCustomerPage"));
            }
                                        %>
      		
		<ctrl:columntree styleId="row-@{bean.map.id}" title="time.customerProjectTask" property="name"  width="250" maxlength="50" sortable="true" imageProperty="image" imagemap="im_tree"/>
      	<ctrl:columntext title="time.hourRate" property="hourRate" width="80" align="right" sortable="false" converter="ch.ess.base.web.BigDecimalConverter"/>             
			<ctrl:columngroup title="common.action" align="center">
			<ctrl:columnadd tooltip="time.add" property="addable" />
			<ctrl:columnedit tooltip="common.edit" property="editable" />
			<ctrl:columndelete tooltip="common.delete" property="deletable"	onclick="return confirmRequest(' @{bean.map.name}');" />
			</ctrl:columngroup>
   	</ctrl:treelist>			

	
    <misc:confirm key="time.delete"/>
</body>
</html>