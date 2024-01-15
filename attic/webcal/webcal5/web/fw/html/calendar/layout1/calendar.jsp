<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@ page import="com.cc.framework.ui.painter.html.HtmlPainterHelp" %>

<html>
<head>
	<title>DateTime Picker</title>
	<script type="text/javascript" src="../../../global/jscript/formatter.js"></script>
	<script type="text/javascript" src="../../../global/jscript/utility.js"></script>
	<script type="text/javascript" src="../../../global/jscript/environment.js"></script>
	<script type="text/javascript" src="../../../global/jscript/common.js"></script>
	<script type="text/javascript" src="../../jscript/calendar_res.js"></script>
	<script type="text/javascript" src="../../jscript/calendar.js"></script>

	<link href="calendar.css" rel="stylesheet" type="text/css">
</head>

<body background="winbgimg.gif" style="margin-left: 10; margin-top: 10;">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<%-- Do not remove/change the next line --%>
			<span id="dtp_cccal"></span>
		</td>
	</tr>
</table>

<%-- Include the calendar initialization script --%>
<% HtmlPainterHelp.createCalendarScript(pageContext); %>

<script type="text/javascript" >
var layout      = 0;                                   // Default Layout
var locale      = 'EN';                                // Default locale if not specified
var fieldId     = null;                                // Id of the field the calendar is assoziated to
var formatMask  = 'YYYY-MM-DD HH:mm:ss';               // Default mask if none specified;
var resPath     = '';                                  // Path for image resources (Backgrounds, buttons)
var winTitle    = '';

// retrieve the parameters form the url
if (null != window.location) {
	var winlocation = CCUtility.decodeURIComponent(window.location);
	var params      = HTTPUtil.getParameters(winlocation);
	fieldId         = HTTPUtil.getParameter('fieldid', params);
	field           = window.opener.document.getElementById(fieldId);	
	formatMask      = HTTPUtil.getParameter('mask', params);
	var lc          = HTTPUtil.getParameter('locale', params);
	locale          = (null == lc || lc.toUpperCase() == 'NULL') ? 'EN' : lc.toUpperCase();
	document.title  = DTPRes.getWindowTitle(locale);
}

// Initialize variables
var btnLabel    = '';
var btnTooltip  = '';
var btnAlt      = '';
var btnWidth    = 80;
var btnAlt      = '';
var btnCancel   = null;
var btnOk       = null;
var imgPrevYear = null;
var imgNextYear = null;

// --------------------------------------------------------
// Configure the Buttons
// --------------------------------------------------------
var imgSrc    = ['btnBkg1_left.gif', 'btnBkg1_middle.gif', 'btnBkg1_right.gif'];
var imgWidth  = [7, 0, 7];
var imgHeight = 24;

// (1) CANCEL Button
btnLabel    = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.button.cancel.label") %>;
btnTooltip  = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.button.cancel.tooltip") %>;
btnWidth    = <%= HtmlPainterHelp.getLiteral(pageContext, "fw.calendar.button.cancel.width") %>;
btnCancel   = new TextButton('btnCancel', btnLabel, btnWidth, resPath, imgSrc, imgWidth, imgHeight, btnTooltip);

// (2) OK Button
btnLabel    = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.button.ok.label") %>;
btnTooltip  = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.button.ok.tooltip") %>;
btnWidth    = <%= HtmlPainterHelp.getLiteral(pageContext, "fw.calendar.button.ok.width") %>;
btnOk     = new TextButton('btnOk', btnLabel, btnWidth, resPath, imgSrc, imgWidth, imgHeight, btnTooltip);

// (3) Create navigation buttons
btnTooltip  = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.image.prevyear.tooltip") %>;
btnAlt      = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.image.prevyear.alt") %>;
imgPrevYear = new Icon('imgPrevYear', '', 'btnLeft1.gif', 21, 25, btnTooltip, btnAlt);

btnTooltip  = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.image.nextyear.tooltip") %>;
btnAlt      = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.image.nextyear.alt") %>;
imgNextYear = new Icon('imgNextYear', '', 'btnRight1.gif', 21, 25, btnTooltip, btnAlt);

btnTooltip  = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.image.prevmonth.tooltip") %>;
btnAlt      = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.image.prevmonth.alt") %>;
imgPrevMonth = new Icon('imgPrevMonth', '', 'btnLeft1.gif', 21, 25, btnTooltip, btnAlt);

btnTooltip  = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.image.nextmonth.tooltip") %>;
btnAlt      = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.calendar.image.nextmonth.alt") %>;
imgNextMonth = new Icon('imgNextMonth', '', 'btnRight1.gif', 21, 25, btnTooltip, btnAlt);

// (4) Create array including all buttons and images
var buttons = [btnCancel, btnOk, imgPrevYear, imgNextYear, imgPrevMonth, imgNextMonth];

// --------------------------------------------------------
// Configure the calendar
// --------------------------------------------------------
var isTime = DateFormat.isTimeMask(formatMask);

var crtl_calendar = new Calendar('cccal');
crtl_calendar.setLocale(locale);
crtl_calendar.setOpener(window.opener);
crtl_calendar.setDayNameFormat(DayNameFormat.FIRSTTWOLETTER);   // display the first two characters of the dayname
crtl_calendar.setDayNameToUppercase(DayNameToUppercase.FULL);   // convert the first characters of the dayname to uppercase
crtl_calendar.setShowTimePicker(true);
crtl_calendar.setFirstDayOfWeek(1);								// start with monday
crtl_calendar.setShowDaysOfOtherMonths(false);                  // do not display days form the prev or next month
crtl_calendar.setCloseOnSelect(!isTime)                         // 28.07.2005: display ok/cancel button only if a time mask is present
crtl_calendar.setShowTodaySelector(true);                       // display "today" selector
crtl_calendar.setFieldId(fieldId);                              // id of the associated input field
crtl_calendar.setFormatMask(formatMask);                        // set the format mask
crtl_calendar.setPreselect(true);                               // open calendar with the date specified in the input field

// --------------------------------------------------------
// Render the calendar
// --------------------------------------------------------
var caldata = new CalendarPainterData(crtl_calendar, resPath, locale, layout, buttons);
CalendarPainter.render(caldata);
</script>

</body>
</html>