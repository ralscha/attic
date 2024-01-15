<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
    
  <misc:initSelectOptions id="monthOption" name="monthOptions" scope="session" />
  <html:form action="/holidayList" focus="value(monthNo)">
	<forms:form type="search" formid="frmListHoliday">
    <forms:html>
    <table>
      <tr>
        <td><bean:message key="time.month.singular"/></td>
        <td>&nbsp;</td>
      </tr>
      <tr>
			  <td>
          <ctrl:select property="value(monthNo)">
            <base:options empty="empty" name="monthOption"/>
          </ctrl:select>        
	      </td> 
        <td>
			    <ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/>		    
        </td>
      </tr>
    </table>
	  </forms:html> 	
	</forms:form>
	</html:form>
	
	<p></p>
	
	<ctrl:list id="holidaylist" 
		   action="/holidayList" 
			 name="listControl" 
			 scope="session"
			 title="holiday.holidays" 
			 rows="${sessionScope.noRows}"  
			 createButton="true"
			 refreshButton="true"
       select="multiple">
	  <ctrl:columntext title="holiday.name" property="name" width="90" maxlength="30" sortable="true"/>       
		<ctrl:columntext title="holiday.dayOfMonth" align="right" property="dayOfMonth" width="90" maxlength="30" sortable="true"/>
	  <ctrl:columntext title="time.month" property="monthNo" width="70" maxlength="30" sortable="true"/>
		<ctrl:columntext title="holiday.fromYear" property="fromYear" width="75" maxlength="30" sortable="true"/>
		<ctrl:columntext title="holiday.toYear" property="toYear" width="75" maxlength="30" sortable="true"/>
    <ctrl:columncheck title="holiday.active" property="checkState" checkAll="true" width="80"/>
    
    <ctrl:columngroup title="common.action" align="center">
	  <ctrl:columnedit tooltip="common.edit"/>
		<ctrl:columndelete tooltip="common.delete" property="deletable" onclick="return confirmRequest('@{bean.map.name}');"/>		
		</ctrl:columngroup>
	</ctrl:list>

  
  <misc:confirm key="holiday.delete"/>
</body>
</html>