<%@ include file="include/taglibs.jspf"%>
<%@ page import="ch.ess.cal.web.time.*"%>

<html>
<head>
<title></title>
<script language="JavaScript"
	src="<c:url value='/scripts/dayreport.js'/>" type="text/javascript"></script>
<misc:popupCalendarJs />
<style type="text/css">
.dailySum {
	font-family: arial;
	font-weight: bold;
	font-size: 10pt;
	display: inline;
}

.textHeader {
	font-family: arial;
	font-weight: bold;
	font-size: 12pt;
}
</style>


</head>
<body>
	<script>
		window.onload = function()
		{
			totalDaySum();
		}
	</script>
	<misc:initSelectOptions id="timeUserOption" name="userOptions" scope="session" permission="time"/>
	<html:form action="/dayReportList" focus="week" method="POST">
		<input type="hidden" value="timeList" name="listType" id="listType" />
		<forms:form type="search" formid="frmListTime">
			<forms:html>
			<table border="0">
				<tr>
					<td><bean:message key="user" />
					</td>
					<td width="25"></td>
					<td></td>
					<td><bean:message key="header.date" />
					<td></td>
					</td>
					<td width="25"></td>
					<td><bean:message key="header.week" />
					</td>
					<td width="25"></td>
					<td><bean:message key="time.choose" />
					</td>
					<td width="25"></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td valign="middle" class="textHeader">
						<sec:granted permission="$timeadmin">
							   	
							    <ctrl:select property="userId">
	          						<base:options empty="empty" name="timeUserOption"/>
	        					</ctrl:select> 	
						</sec:granted> 
						<sec:notGranted permission="$timeadmin">
							<ctrl:plaintext property="userLoggedIn" />
						</sec:notGranted></td>
					<td></td>
					<td width="">
					
	   			<input id='btnDecreaseDateHidden' name='btnDecreaseDate' type='HIDDEN' />
        		<img alt="" onClick="document.getElementById('btnDecreaseDateHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnLeft1.gif">
	 					
					
					
					</td>
					<td valign="middle" class="textHeader"><ctrl:plaintext
							property="dateDailyReport" /></td>
					<td>
					
					  		
        		<input id='btnIncreaseDateHidden' name='btnIncreaseDate' type='HIDDEN' />
        		<img alt=""  onClick="document.getElementById('btnIncreaseDateHidden').value='clicked'; CCUtility.submitEnclosingForm(this);" src="fw/def/image/buttons/btnRight1.gif">
	 
					
					</td>
					<td></td>
					<td valign="middle" class="textHeader"><ctrl:plaintext
							property="dateActualWeek" /></td>
					<td></td>

					<td valign="top"><ctrl:text styleId="from"
							style="font-family:arial;font-weight:bold;font-size:12pt;"
							property="from" size="10" maxlength="10" /><img
						src="images/cal.gif" alt="" name="selectFrom" id="selectFrom"
						width="16" height="16" border="0">
					</td>

					<td></td>
					<td valign="top"><ctrl:button base="buttons.src"
							name="btnSearch" src="btnSearch1.gif" title="button.title.search" />
					</td>
					<td valign="top"><ctrl:button base="buttons.src"
							name="btnSave" src="btnSave1.gif" title="button.title.save" /></td>
				</tr>
			</table>
			</forms:html>
		</forms:form>

		<div style="margin-top: 25px;">
			<div class="dailySum" style="margin-left: 480px;">Total:</div>
			<div class="dailySum" style="margin-left: 20px;" id="totalDay">
				<ctrl:plaintext property="dailySum" />
			</div>
		</div>

		<misc:popupCalendar element="from" trigger="selectFrom"
			showOthers="true" />

		<c:if test="${not empty listControl}">

			<ctrl:list id="listControl" action="/dayReportListAction"
				name="listControl" scope="session" title="dayreport.title"
				createButton="false" refreshButton="false" formElement="true">
				<ctrl:columntext title="dayreport.KdNr" property="customerNumber"
					width="50" sortable="true" />
				<ctrl:columntext title="dayreport.Customer" property="customer"
					width="150" sortable="true" />
				<ctrl:columntext title="dayreport.ProjNr" property="projectNumber"
					width="50" sortable="true" />
				<ctrl:columntext title="dayreport.Project" property="project"
					width="100" sortable="true" />
				<ctrl:columntext title="dayreport.Task" property="task" width="100"
					sortable="true" />
				<ctrl:columntext title="dayreport.WorkHour" property="workinhour"
					size="5" editable="true" onfocus="setYellow(this)"
					onblur="calcDaySum(this, this.value);setWhite(this)" />
				<ctrl:columntext title="dayreport.Activity" property="activity"
					width="100" editable="true" onfocus="setYellow(this)"
					onblur="setWhite(this)" />
				<ctrl:columntext title="dayreport.Comment" property="comment"
					width="100" editable="true" onfocus="setYellow(this)"
					onblur="setWhite(this)" />
				<ctrl:columntext title="dayreport.Amount" property="amount"
					size="12" editable="true" onfocus="setYellow(this)"
					onblur="checkAmount(this, this.value);setWhite(this);" />
				<ctrl:columntext title="dayreport.ChargesStyle"
					property="chargesStyle" width="100" editable="true"
					onfocus="setYellow(this)" onblur="setWhite(this)" />
			</ctrl:list>

		</c:if>

	</html:form>
</body>
</html>
