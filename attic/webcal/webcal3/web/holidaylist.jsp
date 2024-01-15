<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title><bean:message key="application.title"/>: <bean:message key="holiday.menu"/></title></head>
  <body>

    <menu:crumbs value="crumb1" labellength="40">
	    <menu:crumb	crumbid="crumb1"	title="holiday.holidays"/>
	    <menu:crumb	crumbid="crumb2"	disabled="true" title="holiday.headline"/>
    </menu:crumbs>
  <br>
    
  <misc:initSelectOptions id="monthOption" type="ch.ess.cal.web.holiday.MonthOptions" scope="session" />
	      		  	  	  	  	  	  		
	<html:form action="/listHoliday.do">
		<forms:form type="search" formid="frmListHoliday">
		  <forms:html>
			  <table cellspacing='0' cellpadding='0'>			  
			  <tr>		  		    
		      <td class='searchfl'><bean:message key="time.month"/>:</td>			    			    	        	        	        	        	        	        	        	        	        	        	        	        	        	        				  
          <td class='fb'></td>
	      </tr>      
			  <tr>
				  <td class='fd'>
				   <html:select property="value(monthNo)">
             <html:option value=" ">&nbsp;</html:option>
             <html:optionsCollection name="monthOption" property="labelValue"/>   
           </html:select>          
          </td>				  		      		      	        		      	        		      	        		      	        		      	        		      	        		      				  
			<td class='fb'>
			<ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/>		    
         </td>
			  </tr>			  
			  </table>
			</forms:html>		  	
		</forms:form>	  	  
  </html:form>		
	
	<p></p>
	
	<ctrl:list id="holidaylist" 
		   action="listHoliday.do" 
			 name="holidays" 
			 scope="session"
			 title="holiday.holidays" 
			 rows="${sessionScope.noRows}"  
          minRows="${sessionScope.noRows}"
			 createButton="true"
			 refreshButton="true"
       select="multiple">
	  <ctrl:columntext title="holiday.name" property="name" width="90" maxlength="30" sortable="true"/>       
		<ctrl:columntext title="holiday.dayOfMonth" align="right" property="dayOfMonth" width="90" maxlength="30" sortable="true"/>
	  <ctrl:columntext title="time.month" property="monthNo" width="70" maxlength="30" sortable="true"/>
		<ctrl:columntext title="holiday.fromYear" property="fromYear" width="75" maxlength="30" sortable="true"/>
		<ctrl:columntext title="holiday.toYear" property="toYear" width="75" maxlength="30" sortable="true"/>
    <ctrl:columncheck title="holiday.active" property="checkState" checkAll="true" width="80"/>
	  <ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.name}');"/>		
	</ctrl:list>

  
  <misc:confirm key="holiday.delete"/>
</body>
</html>