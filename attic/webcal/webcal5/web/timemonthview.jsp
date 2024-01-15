<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title>

  </head>
  <body>
  
  <misc:initSelectOptions id="timeYearOptions" name="timeYearOptions" scope="session"/>  
  <misc:initSelectOptions id="monthOptions" name="monthOptions" scope="session"/>         
    
  <html:form action="/timeMonthView" focus="week">
    <input type="hidden" value="timeList" name="listType" id="listType"/>
	  <forms:form type="search" formid="frmListTime">
      <forms:html>
       <table>
        <tr style="line-height: 25px;">
        	<td  valign="top"style="border-right: 1px solid grey; padding: 5px; ">
		   	 	<div style="float: left; ">
    	      	<div style="width: 145px; float:left;"><bean:message key="calendar.month"/></div>
        		<div style="float:left;"><bean:message key="calendar.year"/></div>
        		</div>
        		<br />	
        		
        		<input id='btnDecreaseMonthHidden' name='btnDecreaseMonth' type='HIDDEN' />
        		<img alt="" onClick="document.getElementById('btnDecreaseMonthHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
	   				
				<ctrl:select styleId="month" property="month" onchange="document.getElementById('week').value=''">
          			<base:options empty="empty" name="monthOptions"/>
        		</ctrl:select> 				
        		<input id='btnIncreaseMonthHidden' name='btnIncreaseMonth' type='HIDDEN' />
        		<img alt="" onClick="document.getElementById('btnIncreaseMonthHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnRight1.gif">
	   			
	   			<input id='btnDecreaseYearHidden' name='btnDecreaseYear' type='HIDDEN' />
        		<img alt="" style="margin-left: 25px;" onClick="document.getElementById('btnDecreaseYearHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
	   		
	   			<ctrl:select property="year">
          			<base:options empty="empty" name="timeYearOptions"/>
        		</ctrl:select> 		

	   			<input id='btnIncreaseYearHidden' name='btnIncreaseYear' type='HIDDEN' />
        		<img alt="" onClick="document.getElementById('btnIncreaseYearHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnRight1.gif">		
        	</td>
        
        

        	<td valign="top" style="border-right: 1px solid grey; padding: 5px;">
	        	 <sec:granted permission="$timeadmin">	         		          
		         	<bean:message key="time.user"/>
		         	<br />
          			<misc:initSelectOptions id="timeUserOption" name="userOptions" scope="session" permission="time"/>
					<ctrl:select style="width: 150px; margin-top: 4px;" property="userId">
	          			<base:options empty="empty" name="timeUserOption"/>
	        		</ctrl:select> 
	        		<br />
		    	</sec:granted>
		    	
        	</td>
        	
        	
        	<td valign="top" style="border-right: 1px solid grey; padding: 5px;">
        		
						<br />
	          			 <ctrl:checkbox property="searchWithInactive" />
						<bean:message key="time.searchWithInactive" />
						<br />

        	</td>
     	       	
        	
        	<td align="center" style=" padding: 5px;">
        			<br />
        			<ctrl:button style="" base="buttons.src" name="btnSearch" src="btnSearch1.gif" title="button.title.search" />
        	</td>
        </tr>
   		</table>

      </forms:html>				
	</forms:form>	  	  
  </html:form>		
	
	<c:if test="${not empty listControl}">
		<ctrl:treelist 
			  action="/timeMonthView.do" 
			  name="listControl" 
			  scope="session"
			  expandMode="full" >
	   	</ctrl:treelist>
   	</c:if>
				<div class="smalltextbold" >
			<bean:message key="time.grandTotal" />
			&nbsp;${dailySum}
		</div>

</body>
</html>
