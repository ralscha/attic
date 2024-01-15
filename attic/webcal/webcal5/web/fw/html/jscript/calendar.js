/*
 * $Header:  $
 * $Revision:  $
 * $Date: $
 *
 * ====================================================================
 *
 * Copyright (c) 2000 - 2005 SCC Informationssysteme GmbH. All rights
 * reserved.
 * Vendor URL : http://www.scc-gmbh.com
 * Product URL: http://www.common-controls.com
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL SCC INFORMATIONSSYSTEME GMBH OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * Note: This file belongs to the Common Controls Presentation
 *       Framework. Permission is given to use this script only
 *       together with the Common Controls Presentation Framework
 *
 * ====================================================================
 */
 
/***********************************************************************
 * Name:
 *        Calendar.js
 *
 * Function:
 *        Provide a DateTime Picker in a web browser.
 *
 * Author:
 *        Gernot Schulz
 *
 * Status:
 *        Version 1, Release 1
 *
 * Environment:
 *        This is a PLATFORM-INDEPENDENT source file. As such it may
 *        contain no dependencies on any specific operating system
 *        environment or hardware platform.
 *
 * Description:
 *        ...
 *
 * TESTED ON:  - InternetExplorer   > 5.0 (Win)
 *             - Netscape Navigator > 7.0 (Win/Linux)
 *             - Safari             > 1.2 (Mac)
 *             - Mozilla            > 1.6 (Win/Linux)
 *
 ***********************************************************************/

LF = '\n';

/*
+ ---------------------------------------------------------------------------------+
| Globals
+ ---------------------------------------------------------------------------------+
*/

// How many characters of the daystring shousl be displayed
DayNameFormat = new Object();
DayNameFormat.FIRSTLETTER    = 0;
DayNameFormat.FIRSTTWOLETTER = 1;
DayNameFormat.FULL           = 2;
DayNameFormat.DEFAULT = DayNameFormat.FIRSTTWOLETTER;

// some languages require setting for lower- or uppercase
DayNameToUppercase = new Object();
DayNameToUppercase.NONE        = 0;
DayNameToUppercase.FIRSTLETTER = 1;
DayNameToUppercase.FULL        = 2;
DayNameToUppercase.DEFAULT     = DayNameToUppercase.FIRSTLETTER;

CalendarTyp  = new Object();
CalendarTyp.POPUP = 0;
CalendarTyp.INLINE = 1;

/*
+ ---------------------------------------------------------------------------------+
| Object....: Calendar
| Function..: The calendar object
| Arguments.: id - A unique id of the calendar object
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 22.05.2004  G.Schulz (SCC)    Initial version
| 20.01.2005  G.Schulz (SCC)    Now the onchange event is fired if the value changes
|                               focus is set to the input field again
+ ---------------------------------------------------------------------------------+
*/
function Calendar(id) {
	this.id                    = id;                                    // id of the Calendar
	this.title                 = '';                                    // title of the Calendar
	this.dtCurrentDate         = new Date();                            // the current date
	this.dtStartDay            = new Date(this.dtCurrentDate);          // start date for painting
	this.dtSelectedDate        = new Date(this.dtCurrentDate);          // if not initialized, set the current date as selected
	this.dateSeperator         = DateSeperator.DEFAULT;                 // the string used as the date seperator
	this.timeSeperator         = TimeSeperator.DEFAULT;                 // the string used as the time seperator
	this.dayNameFormat         = DayNameFormat.DEFAULT;                 // default two letters for wekdays (Mo, .. Su)
	this.dayNameToUppercase    = DayNameToUppercase.DEFAULT;            // default first character (Mo instead of MO or mo)
	this.locale                = 'EN';                                  // default locale
	this.firstDayOfWeek        = 0;                                     // day to start from (normally 0-Su or 1-Mo)
	this.midnightDisplay       = false;                                 // display format 12:00/24:00 or 00:00
	this.formatMask            = 'DD-MMMM-YY HH:mm:ss';                 // default mask if none specified
	this.showTimePicker        = true;                                  // if set to true a time picker is displayed
	this.showDatePicker        = true;                                  // if set to true a date picker is displayed
	this.showDaysOfOtherMonths = true;                                  // show days of othe months. default true
	this.showTodaySelector     = true;
	this.showAMPMString        = false;
	this.buttonStyle           = 0;                                     // Default style of the current painter
	this.preselect             = false									// open calendar with the date specified in the input field
	this.calendarType          = CalendarTyp.POPUP;
	
	// Information about the callee
	this.winOpener             = null;                                  // window wich open this calendar
	this.winLocation           = null;                                  // url including the date/fieldId/format
	this.fieldId               = null;                                  // id of the field the selected value should be passed to
	this.closeOnSelect         = true;                                  // if a date is selected close the popup window
}
function Calendar_getId() {
	return this.id;
}
// -- functions to modify the date
function Calendar_switchMonth(month) {
	this.dtCurrentDate.setMonth(month, 1);				// reset day to 1
	this.dtStartDay = new Date(this.dtCurrentDate);
}
function Calendar_incMonth() {
	this.dtCurrentDate.setMonth(this.dtCurrentDate.getMonth() + 1);
	this.dtStartDay = new Date(this.dtCurrentDate);
}
function Calendar_decMonth() {
	this.dtCurrentDate.setMonth(this.dtCurrentDate.getMonth() - 1);
	this.dtStartDay = new Date(this.dtCurrentDate);
}
function Calendar_incYear() {
	this.dtCurrentDate.setYear(this.dtCurrentDate.getFullYear() + 1);
	this.dtStartDay = new Date(this.dtCurrentDate);
}
function Calendar_decYear() {
	this.dtCurrentDate.setYear(this.dtCurrentDate.getFullYear() - 1);
	this.dtStartDay = new Date(this.dtCurrentDate);
}
// -- functions to modify the time
function Calendar_incHours() {
	this.dtCurrentDate.setHours(this.dtCurrentDate.getHours() + 1);
	this.dtSelectedDate.setHours(this.dtSelectedDate.getHours() + 1);
}
function Calendar_decHours() {
	this.dtCurrentDate.setHours(this.dtCurrentDate.getHours() - 1);
	this.dtSelectedDate.setHours(this.dtSelectedDate.getHours() - 1);
}
function Calendar_incMinutes() {
	this.dtCurrentDate.setMinutes(this.dtCurrentDate.getMinutes() + 1);
	this.dtSelectedDate.setMinutes(this.dtSelectedDate.getMinutes() + 1);
}
function Calendar_decMinutes() {
	this.dtCurrentDate.setMinutes(this.dtCurrentDate.getMinutes() - 1);
	this.dtSelectedDate.setMinutes(this.dtSelectedDate.getMinutes() - 1);
}
function Calendar_incSeconds() {
	this.dtCurrentDate.setSeconds(this.dtCurrentDate.getSeconds() + 1);
	this.dtSelectedDate.setSeconds(this.dtSelectedDate.getSeconds() + 1);
}
function Calendar_decSeconds() {
	this.dtCurrentDate.setSeconds(this.dtCurrentDate.getSeconds() - 1);
	this.dtSelectedDate.setSeconds(this.dtSelectedDate.getSeconds() - 1);
}
function Calendar_incMilliSeconds() {
	// not implemented
}
function Calendar_decMilliSeconds() {
	// not implemented
}
function Calendar_setHours(hours) {
	if (hours >=0 && hours <= 24) {
		this.dtCurrentDate.setHours(hours);
		this.dtSelectedDate.setHours(hours);
	}
}
function Calendar_setMinutes(minutes) {
	if (minutes >=0 && minutes <= 60) {
		this.dtCurrentDate.setMinutes(minutes);
		this.dtSelectedDate.setMinutes(minutes);
	}
}
function Calendar_setSeconds(seconds) {
	if (seconds >=0 && seconds <= 60) {
		this.dtCurrentDate.setSeconds(seconds);
		this.dtSelectedDate.setSeconds(seconds);
	}
}
function Calendar_getStartDay(){
	return this.dtStartDay;
}
function Calendar_setCurrentDate(date) {
	this.dtCurrentDate = new Date(date);
	this.dtStartDay = new Date(this.dtCurrentDate);
}
function Calendar_getCurrentDate() {
	return this.dtCurrentDate;
}
function Calendar_setSelectedDate(date) {
	this.dtSelectedDate = date;
	this.dtStartDay = new Date(this.dtCurrentDate);

	if (this.closeOnSelect || !this.showTimePicker) {
		this.close();
	}
}
function Calendar_getSelectedDate() {
	return this.dtSelectedDate;
}
function Calendar_setPreselect(preselect) {
	this.preselect = preselect;

	if (preselect) {
		var input = null;
		
		if (null == this.winOpener) {
			return;
		}

		if (this.winOpener instanceof PopUp) {
			// if the calendar was configured as popup get the
			// field from the document
			input = document.getElementById(this.fieldId);
		} else {
			// if the calendar opens in a new window
			// get the field from the parent window
			input = this.winOpener.document.getElementById(this.fieldId);
		}
	
		var date = DateFormat.parse(input.value, this.formatMask);
		this.dtCurrentDate = new Date(date);
		this.dtSelectedDate = date;
		this.dtStartDay = new Date(date);
	}
}
function Calendar_isPreselect() {
	return this.preselect;
}
function Calendar_getTitle() {
	return this.title;
}
function Calendar_setTitle(title) {
	this.title = title;
}
function Calendar_setLocale(locale) {
	this.locale = locale;
	DTPRes.setLocale(locale);  // needs work
}
function Calendar_getLocale() {
	return this.locale;
}
function Calendar_setButtonStyle(buttonStyle) {
	this.buttonStyle = buttonStyle;
}
function Calendar_getButtonStyle() {
	return this.buttonStyle;
}
function Calendar_isShowButtons() {
	// if the TimePicker is shown
	// we need the buttons
	return showTimePicker;
}
function Calendar_setFirstDayOfWeek(firstDayOfWeek) {
	this.firstDayOfWeek = firstDayOfWeek;
}
function Calendar_getFirstDayOfWeek() {
	return this.firstDayOfWeek;
}
function Calendar_setShowDaysOfOtherMonths(flag) {
	this.showDaysOfOtherMonths = flag;
}
function Calendar_isShowDaysOfOtherMonths(flag) {
	return this.showDaysOfOtherMonths;
}
function Calendar_setShowTodaySelector(flag) {
	this.showTodaySelector = flag;
}
function Calendar_isShowTodaySelector() {
	return this.showTodaySelector;
}
function Calendar_setWeekDays(arrWeekDays) {
	DTPRes.setWeekdays(this.locale, arrWeekDays);;
}
function Calendar_getWeekDays() {
	var arr = DTPRes.getWeekdays(this.locale);

	if (null != arr) {
		return arr;
	} else {
		alert('No resources defined for DOWS Locale: ' + this.locale);
	}
}
function Calendar_setMonths(arrMonths) {
	DTPRes.setMonths(this.locale, arrMonths);
}
function Calendar_getMonths() {
	var arr = DTPRes.getMonths(this.locale);
	
	if (null != arr) {
		return arr;
	} else {
		alert('No resources defined for Months Locale: ' + this.locale);
	}
}
function Calendar_setAmPmStrings(arrAmPMStrings) {
	DTPRes.setAmPmStrings(this.locale, arrAmPMStrings);
}
function Calendar_getAmPmStrings() {
	var arr = DTPRes.getAmPmStrings(this.locale);
	
	if (null != arr) {
		return arr;
	} else {
		alert('No resources defined for AMPM Locale: ' + this.locale);
	}
}
function Calendar_getAmPmString() {
	var arr = DTPRes.getAmPmStrings(this.locale);
	
	if (this.dtCurrentDate.getHours() >0 & this.dtCurrentDate.getHours() <= 12) {
		return arr[0];
	} else {
		return arr[1];
	}
}
function Calendar_setFormatMask(formatMask) {
	this.formatMask = formatMask;
	this.showAMPMString = DateFormat_hasAMPMString(formatMask);
}
function Calendar_getFormatMask() {
	return this.formatMask;
}
function Calendar_isShowAMPMString() {
	return this.showAMPMString;
}
function Calendar_isMidnightDisplay() {
	return this.midnightDisplay;
}
function Calendar_setMidnightDisplay(flag) {
	this.midnightDisplay = flag;
}
function Calendar_setTimeSeperator(timeSeperator) {
	this.timeSeperator = timeSeperator;
}
function Calendar_getTimeSeperator() {
	return this.timeSeperator;
}
function Calendar_setDateSeperator(dateSeperator) {
	this.dateSeperator = dateSeperator;
}
function Calendar_getDateSeperator() {
	return this.dateSeperator;
}
function Calendar_setDayNameFormat(dayNameFormat) {
	this.dayNameFormat = dayNameFormat;
}
function Calendar_getDayNameFormat() {
	return this.dayNameFormat;
}
function Calendar_setDayNameToUppercase(dayNameToUppercase) {
	this.dayNameToUppercase = dayNameToUppercase;
}
function Calendar_getDayNameToUppercase() {
	return this.dayNameToUppercase;
}
function Calendar_setCloseOnSelect(flag) {
	this.closeOnSelect = flag;
}
function Calendar_getCloseOnSelect() {
	return this.closeOnSelect;
}
function Calendar_setShowTimePicker(flag) {
	this.showTimePicker = flag;
}
function Calendar_isShowTimePicker() {
	return this.showTimePicker;
}
function Calendar_setShowDatePicker(flag) {
	this.showDatePicker = flag;
}
function Calendar_isShowDatePicker() {
	return this.showDatePicker;
}
function Calendar_setOpener(opener) {

	if (opener instanceof PopUp) {
		this.calendarType = CalendarTyp.INLINE;
	} else {
		this.calendarType = CalendarTyp.POPUP;
	}

	this.winOpener = opener;
}
function Calendar_setLocation(location) {
	var param = null;
	this.winLocation = location;

	if (null != location) {
		var params = HTTPUtil.getParameters(location);
		this.setFieldId(HTTPUtil.getParameter('id', params));
		this.setFormatMask(HTTPUtil.getParameter('format', params));
	}
}
function Calendar_setFieldId(fieldId){
	this.fieldId = fieldId;
}
function Calendar_close() {
	var field = null;

	if (null == this.winOpener) {
		return;
	} else if (this.calendarType == CalendarTyp.INLINE) {
		field = document.getElementById(this.fieldId);
	} else if (this.calendarType == CalendarTyp.POPUP) {
		// get the field
		field = this.winOpener.document.getElementById(this.fieldId);
	}

	if (null != field) {
		var newValue = DateFormat.format(this.dtSelectedDate, this.formatMask);

		if (field.value != newValue) {
			field.value = newValue;
			field.focus();
// TODO				input.fireEvent('onchange');
		}
	}
	
	if (this.calendarType == CalendarTyp.INLINE) {
		this.winOpener.close();
		this.winOpener = null;
	} else if (this.calendarType == CalendarTyp.POPUP) {
		window.close();
	}
}
function Calendar_cancel() {
	if (this.calendarType == CalendarTyp.INLINE) {
		this.winOpener.close();
		this.winOpener = null;
	} else if (this.calendarType == CalendarTyp.POPUP) {
		window.close();
	}
}
function Calendar_toString() {
	var out = '';
	out += '******* Calendar *********' + LF
	out += 'Id.............: ' + this.id + LF;
	out += 'Year...........: ' + this.dtCurrentDate.getYear() + LF;
	out += 'Month..........: ' + this.dtCurrentDate.getMonth() + LF;
	out += 'Date...........: ' + this.dtCurrentDate.getDate() + LF;
	out += 'Day............: ' + this.dtCurrentDate.getDay() + LF;
	out += 'Title..........: ' + this.title + LF;
	out += 'Locale.........: ' + this.locale + LF;
	out += 'AM/PM Strings..: ' + DTPRes.getAmPmStrings(this.locale) + LF;
	out += 'FirstDayOfWeek.: ' + this.firstDayOfWeek + LF;
	out += 'DateSeperator..: ' + this.dateSeperator + LF;
	out += 'TimeSeperator..: ' + this.timeSeperator + LF;
	out += 'Weekdays.......: ' + this.getWeekDays() + LF;
	out += 'Months.........: ' + this.getMonths() + LF;
	out += 'Location.......: ' + this.winLocation + LF;
	return out;
}
new Calendar();
Calendar.prototype.getId                    = Calendar_getId;
Calendar.prototype.incYear                  = Calendar_incYear;
Calendar.prototype.decYear                  = Calendar_decYear;
Calendar.prototype.incMonth                 = Calendar_incMonth;
Calendar.prototype.decMonth                 = Calendar_decMonth;
Calendar.prototype.incHours                 = Calendar_incHours;
Calendar.prototype.decHours                 = Calendar_decHours;
Calendar.prototype.incMinutes               = Calendar_incMinutes;
Calendar.prototype.decMinutes               = Calendar_decMinutes;
Calendar.prototype.incSeconds               = Calendar_incSeconds;
Calendar.prototype.decSeconds               = Calendar_decSeconds;
Calendar.prototype.incMilliSeconds          = Calendar_incMilliSeconds;
Calendar.prototype.decMilliSeconds          = Calendar_decMilliSeconds;
Calendar.prototype.setCurrentDate           = Calendar_setCurrentDate;
Calendar.prototype.getCurrentDate           = Calendar_getCurrentDate;
Calendar.prototype.getSelectedDate          = Calendar_getSelectedDate;
Calendar.prototype.setSelectedDate          = Calendar_setSelectedDate;
Calendar.prototype.setPreselect             = Calendar_setPreselect;
Calendar.prototype.isPreselect              = Calendar_isPreselect;
Calendar.prototype.setHours                 = Calendar_setHours;
Calendar.prototype.setMinutes               = Calendar_setMinutes;
Calendar.prototype.setSeconds               = Calendar_setSeconds;
Calendar.prototype.getStartDay              = Calendar_getStartDay;
Calendar.prototype.switchMonth              = Calendar_switchMonth;
Calendar.prototype.setTitle                 = Calendar_setTitle;
Calendar.prototype.getTitle                 = Calendar_getTitle;
Calendar.prototype.setLocale                = Calendar_setLocale;
Calendar.prototype.getLocale                = Calendar_getLocale;
Calendar.prototype.setButtonStyle           = Calendar_setButtonStyle;
Calendar.prototype.getButtonStyle           = Calendar_getButtonStyle;
Calendar.prototype.isShowButtons            = Calendar_isShowButtons;
Calendar.prototype.setFormatMask            = Calendar_setFormatMask;
Calendar.prototype.getFormatMask            = Calendar_getFormatMask;
Calendar.prototype.setMidnightDisplay       = Calendar_setMidnightDisplay;
Calendar.prototype.isMidnightDisplay        = Calendar_isMidnightDisplay;
Calendar.prototype.setShowDaysOfOtherMonths = Calendar_setShowDaysOfOtherMonths
Calendar.prototype.isShowDaysOfOtherMonths  = Calendar_isShowDaysOfOtherMonths
Calendar.prototype.setShowTodaySelector     = Calendar_setShowTodaySelector;
Calendar.prototype.isShowTodaySelector      = Calendar_isShowTodaySelector;
Calendar.prototype.isShowAMPMString         = Calendar_isShowAMPMString;
Calendar.prototype.setWeekDays              = Calendar_setWeekDays;
Calendar.prototype.getWeekDays              = Calendar_getWeekDays;
Calendar.prototype.setMonths                = Calendar_setMonths;
Calendar.prototype.getMonths                = Calendar_getMonths;
Calendar.prototype.setAmPmStrings           = Calendar_setAmPmStrings;
Calendar.prototype.getAmPmStrings           = Calendar_getAmPmStrings;
Calendar.prototype.getAmPmString            = Calendar_getAmPmString;
Calendar.prototype.setTimeSeperator         = Calendar_setTimeSeperator;
Calendar.prototype.getTimeSeperator         = Calendar_getTimeSeperator;
Calendar.prototype.setDateSeperator         = Calendar_setDateSeperator;
Calendar.prototype.getDateSeperator         = Calendar_getDateSeperator;
Calendar.prototype.setCloseOnSelect         = Calendar_setCloseOnSelect;
Calendar.prototype.getCloseOnSelect         = Calendar_getCloseOnSelect;
Calendar.prototype.setFirstDayOfWeek        = Calendar_setFirstDayOfWeek;
Calendar.prototype.getFirstDayOfWeek        = Calendar_getFirstDayOfWeek;
Calendar.prototype.setDayNameFormat         = Calendar_setDayNameFormat;
Calendar.prototype.getDayNameFormat         = Calendar_getDayNameFormat;
Calendar.prototype.setDayNameToUppercase    = Calendar_setDayNameToUppercase;
Calendar.prototype.getDayNameToUppercase    = Calendar_getDayNameToUppercase;
Calendar.prototype.setShowTimePicker        = Calendar_setShowTimePicker;
Calendar.prototype.isShowTimePicker         = Calendar_isShowTimePicker;
Calendar.prototype.setShowDatePicker        = Calendar_setShowDatePicker;
Calendar.prototype.isShowDatePicker         = Calendar_isShowDatePicker;
Calendar.prototype.setOpener                = Calendar_setOpener;
Calendar.prototype.setLocation              = Calendar_setLocation;
Calendar.prototype.setFieldId               = Calendar_setFieldId;
Calendar.prototype.cancel                   = Calendar_cancel;
Calendar.prototype.close                    = Calendar_close;
Calendar.prototype.toString                 = Calendar_toString;


/*
+ ---------------------------------------------------------------------------------+
| Object....: CalendarPainterData(dtpicker, resPath, locale, layout) 
| Function..: 
| Arguments.: dtpicker - Instance of the calendar object
|             resPath  - Path for the image resources
|             locale   - Locale
|             layout   - Integer which selects a special calendar layout
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 22.05.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function CalendarPainterData(dtpicker, resPath, locale, layout, textbuttons) {
	var a = arguments;

	this.dtpicker           = dtpicker;                                       // instance for the calendar control
	this.RESPATH            = resPath;                                        // path for image resources like:'fw/def/image/...'
	this.locale             = (a.length >= 3) ? locale : 'EN';                // Locale
	this.layout             = (a.length >= 4) ? layout : 0;                   // Use the default layout
	this.textButtons        = (a.length >= 5) ? textbuttons : new Array();    // Array with cancel/ok button definition
	this.BTN_LABEL_TODAY    = DTPRes.getButtonLabel(locale, 'Today');         // Button title for the "today" button
	
	// calendar image resources
	this.BTN_PREV           = this.RESPATH + 'btnLeft1.gif';
	this.BTN_NEXT           = this.RESPATH + 'btnRight1.gif';
	this.BTN_SPINUP         = this.RESPATH + 'btnInc1.gif';
	this.BTN_SPINDOWN       = this.RESPATH + 'btnDec1.gif';
	
	// style sheets
	CSS_CALENDAR          = 'cpicker';
	CSS_YM_SWITCH         = 'switch';
	CSS_CPTITLE           = 'cptitle';
	CSS_DOW               = 'dow';
	CSS_DOW_HEADER        = 'dowheader';
	CSS_DATES             = 'dates';
	CSS_CALTABLE          = 'caltable';
	CSS_DATES_SELECTED    = 'dselected';
	CSS_DATES_CURRENT     = 'current';
	CSS_DATES_OTHERMONTH  = 'othermonth';
	CSS_DATES_WEEKEND     = 'weekend';
	CSS_AMPM              = 'ampm';
	CSS_CPTODAY           = 'cptoday';
	CSS_TIMEPICKER        = 'tpicker';
	CSS_TIME_SELECTED     = 'tselected';
	CSS_TEXTBUTTONSECTION = 'tbtnsection';
	
	if (null != this.dtpicker && this.textButtons.length == 0) {
		var btnLabel = '';
		var textButton = null;
	
		btnLabel = DTPRes.getButtonLabel(locale, 'Cancel');
		textButton = new TextButton('btnCancel', btnLabel, 60, resPath);
		this.textButtons[this.textButtons.length] = textButton;
		
		btnLabel = DTPRes.getButtonLabel(locale, 'Ok');
		textButton = new TextButton('btnOk', btnLabel, 60, resPath);
		this.textButtons[this.textButtons.length] = textButton;
	}
}
new CalendarPainterData();


/*
+ ---------------------------------------------------------------------------------+
| Object....: CalendarPainter()
| Function..: Painter Class for the calendar
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 22.05.2004  G.Schulz (SCC)    Initial version
| 25.02.2005  G.Schulz (SCC)    closeOnSelect problem fixed
+ ---------------------------------------------------------------------------------+
*/
function CalendarPainter() {
	// id's for the fields
	FIELDID_HOUR   = 'hour';
	FIELDID_MINUTE = 'minute';
	FIELDID_SECOND = 'second';
	FIELDID_FOCUS  = 'focus';
	FIELDID_AMPM   = 'ampm';
	
	// prefix for the span in which the control is rendered
	DTP_PREFIX     = 'dtp_';
}
function CalendarPainter_render(calData) {
	var dtpicker = calData['dtpicker'];
	var row  = null;
	var cell = null;

	// create Documentfragment
	var doc = null;
	
	doc = document.createElement('Div');

	// create outher table
	var table = document.createElement('Table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.border = 0;
	doc.appendChild(table);

	row = table.insertRow(table.rows.length);		// Safari bug !

	var showDate = DateFormat.isDateMask(dtpicker.getFormatMask());

	// render the calendar
	if (showDate) {	
		cell = row.insertCell(row.cells.length);
		this.renderCalendar(calData, cell);
	}
	
	// render the timepicker
	if (dtpicker.isShowTimePicker()) {
		cell = row.insertCell(row.cells.length);
		cell.vAlign = 'bottom';
		
		if (showDate) {
			cell.style.paddingLeft = 15;
			cell.style.paddingBottom = 15;
		}
		
		var container = document.createElement('Table');
		container.cellSpacing = 0;
		container.cellPadding = 0;
		container.border = 0;
		cell.appendChild(container);

		crow  = this.insertRow(container);
/*		
		if (safari) {
			crow = container.insertRow(-1);	// table.rows.length
		} else {	
			crow = container.insertRow(container.rows.length);
		}
*/
		ccell = crow.insertCell(crow.cells.length);
		var t1 = document.createElement('Table');
		t1.width = 120;

		var c1 = null; 
		if (safari) {
			c1 = t1.insertRow(-1).insertCell(-1);
		} else {	
			c1 = t1.insertRow(t1.rows.length).insertCell(0);
		}

		this.renderTimePicker(calData, c1);
		ccell.appendChild(t1);

		if (safari) {
			crow = container.insertRow(-1);	// table.rows.length
		} else {	
			crow = container.insertRow(container.rows.length);
		}
		ccell = crow.insertCell(crow.cells.length);
		var t2 = document.createElement('Table');

		var c2 = null; 
		if (safari) {
			c2 = t2.insertRow(-1).insertCell(-1);
		} else {	
			c2 = t2.insertRow(t2.rows.length).insertCell(0);
		}
		
		// if close on select is configured no buttons where needed
		if(!dtpicker.getCloseOnSelect()) {
			this.renderTextButtons(calData, c2);
			ccell.appendChild(t2);
		}

	}

	// get the DIV-Element from the form, where the
	// Calendar should be inserted / replaced
	var div = this.getDTPNode(calData);

	// print datetimepicker
	if (div.hasChildNodes()) {
		//div.removeChild(div.firstChild);
		div.appendChild(doc);
	} else {
		div.appendChild(doc);
	}
}
function CalendarPainter_renderTimePicker(calData, node) {
	var dtpicker       = calData['dtpicker'];
	var dtCurrentDate  = dtpicker.getCurrentDate();
	var showAMPMString = DateFormat.hasAMPMString(dtpicker.getFormatMask());
	var showHours      = DateFormat.hasHours(dtpicker.getFormatMask());
	var showMinutes    = DateFormat.hasMinutes(dtpicker.getFormatMask());
	var showSeconds    = DateFormat.hasSeconds(dtpicker.getFormatMask());
	
	if (!showHours & !showMinutes & !showSeconds) {
		return;
	}
	
	var row   = null;
	var cell  = null;
	var input = null;
	var value = null;
	
	// create Table
	var table = document.createElement('Table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.className = CSS_TIMEPICKER;
	node.style.paddingLeft = 5;
	node.appendChild(table);
	row = table.insertRow(table.rows.length);

	// hidden field for selected 
	this.createHidden(row, FIELDID_FOCUS);
	
	// hour
	if (showHours) {
		value = dtCurrentDate.getHours();
		
		if (dtpicker.isShowAMPMString() & value > 12) {
			value -= 12;
		}
		this.createInputCell(calData, row, FIELDID_HOUR, value);
	}
	
	// minute
	if (showMinutes) {
		if (showHours) {
			this.createSeperator(calData, row);
		}
		value = dtCurrentDate.getMinutes();
		this.createInputCell(calData, row, FIELDID_MINUTE, value);
	}

	// second
	if (showSeconds) {
		if (showHours || showMinutes) {
			this.createSeperator(calData, row);
		}
		value = dtCurrentDate.getSeconds();
		this.createInputCell(calData, row, FIELDID_SECOND, value);
	}
	
	// addtional AM/PM Strings
	if (showAMPMString) {
		this.createAmPmTextNode(calData, row);
	}
	
	// spin buttons
	this.createSpinButton(calData, row);
}
function CalendarPainter_createHidden(row, id) {
	var cell = row.insertCell(row.cells.length);

	input = document.createElement('Input');
	input.id = id;
	input.type = 'hidden';
	cell.appendChild(input);
}
function CalendarPainter_createInputCell(calData, row, id, value) {
	var dtpicker  = calData['dtpicker'];
	var cell      = row.insertCell(row.cells.length);
	var cellvalue = (null != value) ? new String(value) : '';
	
	input = document.createElement('Input');
	input.id = id;
	input.size = 2;
	input.maxLength = 2;
	input.value = DateFormat.toLength2(cellvalue);
	input.onfocus = function() {
		var hidden = document.getElementById(FIELDID_FOCUS);
		hidden.value = id;
		CalendarPainter.highlight(id);
	}
	input.onblur = function() {
		this.className = '';
		if (id== FIELDID_HOUR) {
			dtpicker.setHours(this.value);
		} else if (id == FIELDID_MINUTE) {
			dtpicker.setMinutes(this.value);
		} else if (id == FIELDID_SECOND) {
			dtpicker.setSeconds(this.value);
		}
	}
	
	cell.appendChild(input);
}
function CalendarPainter_createSeperator(calData, row) {
	var dtpicker      = calData['dtpicker'];
	var timeSeperator = dtpicker.getTimeSeperator();
	var cell = row.insertCell(row.cells.length);
	
	cell.appendChild(document.createTextNode(timeSeperator));
}
function CalendarPainter_createAmPmTextNode(calData, row) {
	var cell       = row.insertCell(row.cells.length);
	var dtpicker   = calData['dtpicker'];
	var ampmString = dtpicker.getAmPmString();
	
	var span = document.createElement('Span');
	span.className = CSS_AMPM;
	span.id = FIELDID_AMPM;
	span.appendChild(document.createTextNode(ampmString));
	cell.appendChild(span);
}
function CalendarPainter_createSpinButton(calData, row) {
	var dtpicker = calData['dtpicker'];
	var cell     = row.insertCell(row.cells.length);
	var img = null;
	var a   = null;
	
	var table = document.createElement('Table');
	table.cellSpacing = 1;
	table.cellPadding = 0;
	table.border = 0;
	cell.appendChild(table);

	var row1 = null;
	if (safari) {
		row1 = table.insertRow(-1);	// table.rows.length
	} else {	
		row1 = table.insertRow(table.rows.length);
	}
	cell = row1.insertCell(row1.cells.length);
	cell.vAlign = 'bottom';
	a = document.createElement('A');
//	a.href = 'javascript:this.onclick();';
	a.style.cursor = 'pointer';
	a.style.cursor = 'hand';
	a.onclick = function(event) {
		var fieldId = CalendarPainter.getFieldWithFocus();
		
		// select a field if no field has the focus
		if (null == fieldId || fieldId.length == 0) {
			fieldId = FIELDID_HOUR;
		}
		
		var input = document.getElementById(fieldId);
		CalendarPainter.highlight(fieldId);
		
		var value = new Number(input.value);
		
		if (null == value || isNaN(value)) {
			input.value = 0;
		} else {
		
			if ((fieldId == FIELDID_MINUTE) && (value + 1 <= 60)) {
				dtpicker.incMinutes();
				input.value = DateFormat.toLength2(value + 1);
			} else if ((fieldId == FIELDID_SECOND) && (value + 1 <= 60)) {
				dtpicker.incSeconds();
				input.value = DateFormat.toLength2(value + 1);
			} else if (fieldId == FIELDID_HOUR) {
				var max = (!dtpicker.isShowAMPMString()) ? 24 : 12;
			
				if (value + 1> max) {
					if (dtpicker.isShowAMPMString()) {
						var val = dtpicker.getSelectedDate().getHours();
						
						if (val == 12 & value == 12) {
							value = 0;
						} else {
							return;
						}
					} else {
						return;
					}
				}
				
				dtpicker.incHours();
				input.value = DateFormat.toLength2(value + 1);
				
				if (dtpicker.isShowAMPMString()) {
					// check AM/PM Setting
					var node = document.getElementById(FIELDID_AMPM);
					node.innerHTML = '';
					node.appendChild(document.createTextNode(dtpicker.getAmPmString()));
				}
			}
		}
	};
	
	var imgSpinUp = new Icon('imgSpinUp', '', calData['BTN_SPINUP'], 12, 7, '', '') 
	a.appendChild(imgSpinUp.create());
	cell.appendChild(a);
	
	var row2 = null;
	if (safari) {
		row2 = table.insertRow(-1);	// table.rows.length
	} else {	
		row2 = table.insertRow(table.rows.length);
	}

	cell = row2.insertCell(row2.cells.length);
	cell.vAlign = 'top';

	a = document.createElement('A');
//	a.href = 'javascript:this.onclick();';
	a.style.cursor = 'pointer';
	a.style.cursor = 'hand';
	a.onclick = function(event) {
		var fieldId = CalendarPainter.getFieldWithFocus();
		
		// select a field if no field has the focus
		if (null == fieldId || fieldId.length == 0) {
			fieldId = FIELDID_HOUR;
		}
		
		var input = document.getElementById(fieldId);
		CalendarPainter.highlight(fieldId);
		
		var value = new Number(input.value);
		
		if (null == value || isNaN(value)) {
			input.value = 0;
		} else {
			if ((value - 1) >= 0) {
				if (fieldId == FIELDID_HOUR) {
					value --;
					dtpicker.decHours();
					
					if (dtpicker.isShowAMPMString()) {
						var val = dtpicker.getSelectedDate().getHours();
						
						if (val == 12 & value == 0) {
							value = 12;
						}
						
						// check AM/PM Setting
						var node = document.getElementById(FIELDID_AMPM);
						node.innerHTML = '';
						node.appendChild(document.createTextNode(dtpicker.getAmPmString()));
					}
					
					input.value = DateFormat.toLength2(value);
				} else if (fieldId == FIELDID_MINUTE) {
					dtpicker.decMinutes();
					input.value = DateFormat.toLength2(value - 1);
				} else if (fieldId == FIELDID_SECOND) {
					dtpicker.decSeconds();
					input.value = DateFormat.toLength2(value - 1);
				}
			} else {
				input.value = DateFormat.toLength2(0);
			}
		}
	};
	
	var imgSpinDown = new Icon('imgSpinDown', '', calData['BTN_SPINDOWN'], 12, 7, '', '') 
	a.appendChild(imgSpinDown.create());
	cell.appendChild(a);
}
function CalendarPainter_getFieldWithFocus() {
	var hidden = document.getElementById(FIELDID_FOCUS);
	return hidden.value;
}
function CalendarPainter_highlight(fieldId) {
	var fields = [FIELDID_HOUR, FIELDID_MINUTE, FIELDID_SECOND];
	var input = null;

	for (var i=0; i < fields.length; i++) {
		input = document.getElementById(fields[i]);
		
		// check if field exist
		if (null != input) {
			input.className = '';
		}
	}

	input = document.getElementById(fieldId);
	input.className = CSS_TIME_SELECTED;
}
function CalendarPainter_renderTextButtons(calData, node) {
	var dtpicker    = calData['dtpicker'];
	var textButtons = calData['textButtons'];
	var row  = null;
	var cell = null;
	var btnLabel   = '';
	var textButton = null;

	// create Table
	var table = document.createElement('Table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.className = CSS_TEXTBUTTONSECTION;
	node.appendChild(table);
	
	row = table.insertRow(table.rows.length);

	// ok button
	cell = row.insertCell(row.cells.length);
	textButton = textButtons[1];
	textButton.onclick = function(event) {
		dtpicker.close();
	};
	cell.appendChild(textButton.create());

	// Cancel button
	cell = row.insertCell(row.cells.length);
	textButton = textButtons[0];
	textButton.onclick = function(event) {
		dtpicker.cancel();
	};
	cell.appendChild(textButton.create());
}
function CalendarPainter_renderCalendar(calData, node) {
	var dtpicker = calData['dtpicker'];
	var row  = null;
	var cell = null;

	// create Table
	var table = document.createElement('Table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.border = 0;
	table.className = CSS_CALENDAR;
	node.appendChild(table);

	// create year/month selector (first row)
	if (safari) {
		row = table.insertRow(-1);	// table.rows.length
	} else {	
		row = table.insertRow(table.rows.length);
	}

	cell = row.insertCell(row.cells.length);
	this.createSelectors(calData, cell);

	// create title (second row)
	if (safari) {
		row = table.insertRow(-1);	// table.rows.length
	} else {	
		row = table.insertRow(table.rows.length);
	}
	cell = row.insertCell(row.cells.length);
	this.createTitle(calData, cell);

	// create weekdays titles and calendar table (3te row)
	if (safari) {
		row = table.insertRow(-1);	// table.rows.length
	} else {	
		row = table.insertRow(table.rows.length);
	}
	cell = row.insertCell(row.cells.length);
	this.createCalendar(calData, cell);

	// select current date button
	if (dtpicker.isShowTodaySelector()) {
		if (safari) {
			row = table.insertRow(-1);	// table.rows.length
		} else {	
			row = table.insertRow(table.rows.length);
		}
		cell = row.insertCell(row.cells.length);
		this.createTodaySelector(calData, cell);
	}
	
	// create footer
	if (safari) {
		row = table.insertRow(-1);	// table.rows.length
	} else {	
		row = table.insertRow(table.rows.length);
	}
	cell = row.insertCell(row.cells.length);
	this.createFooter(calData, cell);
}
function CalendarPainter_createSelectors(calData, node) {
	var layout  = calData['layout'];
	var table   = null;
	
	switch (layout) {
		case 0:
			table = this.doDefaultLayout(calData);
			break;
		case 1:
			table = this.doCustomLayout1(calData);
			break;
		case 2:
			table = this.doCustomLayout2(calData);
			break;
		default:
			table = this.doDefaultLayout(calData);
	}
	
	// return
	node.appendChild(table);
}
function CalendarPainter_doDefaultLayout(calData) {
	var dtpicker      = calData['dtpicker'];
	var dtCurrentDate = dtpicker.getCurrentDate();
	var textButtons   = calData['textButtons'];
	var row  = null;
	var cell = null;
	var img  = null;
	var a    = null;
	
	// create new table including the option list and year selector
	var table = document.createElement('Table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.className = CSS_YM_SWITCH;
	
	// 1) OptionList month names
	row  = table.insertRow(table.rows.length);
	cell = row.insertCell(row.cells.length);
	cell.appendChild(this.createMonthOptionList(calData));

	// 1) Year selector
	cell = row.insertCell(row.cells.length);
	a = document.createElement('A');
//	a.href='javascript:this.onclick();';
	a.onclick = function() {dtpicker.decYear(); CalendarPainter.render(calData);}
	a.style.cursor = 'pointer';
	a.style.cursor = 'hand';

	var imgPrevYear = textButtons[2];
	a.appendChild(imgPrevYear.create());
	cell.appendChild(a);

	cell = row.insertCell(row.cells.length);
	cell.appendChild(document.createTextNode(dtCurrentDate.getFullYear()));

	cell = row.insertCell(row.cells.length);
	a = document.createElement('A');
//	a.href='javascript:this.onclick();';
	a.onclick = function() {dtpicker.incYear(); CalendarPainter.render(calData);}
	a.style.cursor = 'pointer';
	a.style.cursor = 'hand';

	var imgNextYear = textButtons[3];
	imgNextYear.onclick = function() {dtpicker.incYear(); CalendarPainter.render(calData);}

	a.appendChild(imgNextYear.create());
	cell.appendChild(a);
	
	return table;
}
function CalendarPainter_doCustomLayout1(calData) {
	var dtpicker      = calData['dtpicker'];
	var arrMonths     = dtpicker.getMonths();
	var dtCurrentDate = dtpicker.getCurrentDate();
	var textButtons   = calData['textButtons'];
	var row  = null;
	var cell = null;
	var img  = null;
	var a    = null;

	// create new table including the option list and year selector
	var table = document.createElement('Table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.className = CSS_YM_SWITCH;
	
	// 1) First row including the year
	row  = table.insertRow(table.rows.length);
	cell = row.insertCell(row.cells.length);
	
	a = document.createElement('a');
	a.href = 'javascript:this.onclick();';
	a.onclick = function() {dtpicker.decYear(); CalendarPainter.render(calData);}
	
	var imgPrevYear = textButtons[2];
	a.appendChild(imgPrevYear.create());
	cell.appendChild(a);
	
	cell = row.insertCell(row.cells.length);
	cell.appendChild(document.createTextNode(dtCurrentDate.getFullYear()));

	cell = row.insertCell(row.cells.length);
	a = document.createElement('a');
	a.href = 'javascript:this.onclick();';
	a.onclick = function() {dtpicker.incYear(); CalendarPainter.render(calData);}
	
	var imgNextYear = textButtons[3];
	a.appendChild(imgNextYear.create());
	cell.appendChild(a);

	// 2) Second Row including the month selector
	row  = table.insertRow(table.rows.length);
	cell = row.insertCell(row.cells.length);

	a = document.createElement('a');
	a.href='javascript:this.onclick();';
	a.onclick = function() {
		var month = dtpicker.getCurrentDate().getMonth();
		dtpicker.switchMonth(month - 1);
		CalendarPainter.render(calData);}

	var imgPrevMonth = textButtons[4];
	a.appendChild(imgPrevMonth.create());
	cell.appendChild(a);

	cell = row.insertCell(row.cells.length);
	cell.appendChild(document.createTextNode(arrMonths[dtCurrentDate.getMonth()]));

	cell = row.insertCell(row.cells.length);
	a = document.createElement('a');
	a.href='javascript:this.onclick();';
	a.onclick = function() {
		var month = dtpicker.getCurrentDate().getMonth();
		dtpicker.switchMonth(month + 1);
		CalendarPainter.render(calData);}

	var imgNextMonth = textButtons[5];
	a.appendChild(imgNextMonth.create());
	cell.appendChild(a);
	
	return table;
}
function CalendarPainter_doCustomLayout2(calData) {
	// for later use
}
function CalendarPainter_createMonthOptionList(calData) {
	var dtpicker      = calData['dtpicker'];
	var arrMonths     = dtpicker.getMonths();
	var dtCurrentDate = dtpicker.getCurrentDate();

	var select = document.createElement('select');
	// add options
	for (var i=0; i < arrMonths.length; i++) {
		select.options[select.options.length] = new Option(arrMonths[i], i);
	}
	// set selected month
	select.selectedIndex = dtCurrentDate.getMonth();

	// add onchange handler
	select.onchange = function() {
			// set selected month
			dtpicker.switchMonth(this.selectedIndex);
			// render calendar
			CalendarPainter.render(calData);
		}
	select.id = 'monthSelector';
	
	return select;
}
function CalendarPainter_createTitle(calData, node) {
	var dtpicker = calData['dtpicker'];
	// add an optional title
	node.className = CSS_CPTITLE;
	node.appendChild(document.createTextNode(dtpicker.getTitle()));
}
function CalendarPainter_createCalendar(calData, node) {
	//create Table
	var table = document.createElement('Table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.className = CSS_CALTABLE;
	node.appendChild(table);

	if (safari) {
		row = table.insertRow(-1);	// table.rows.length
	} else {	
		row = table.insertRow(table.rows.length);
	}

	cell = row.insertCell(row.cells.length);
	var dowTable = document.createElement('Table');
	dowTable.cellSpacing = 0;
	dowTable.cellPadding = 0;
	dowTable.className = CSS_DOW_HEADER;
	this.createWeekDays(calData, dowTable);
	cell.appendChild(dowTable);

	if (safari) {
		row = table.insertRow(-1);	// table.rows.length
	} else {	
		row = table.insertRow(table.rows.length);
	}
	cell = row.insertCell(row.cells.length);
	var calTable = document.createElement('Table');
	calTable.cellSpacing = 0;
	calTable.cellPadding = 0;
	this.createCalendarTable(calData, calTable);
	cell.appendChild(calTable);

}
function CalendarPainter_createWeekDays(calData, table) {
	var dtpicker        = calData['dtpicker'];
	var arrWeekdays     = dtpicker.getWeekDays();          //  returns the localized weekday names
	var firstDayOfWeek  = dtpicker.getFirstDayOfWeek();     // specifies if we should start with monday/sunday, ...
	var dayNameFormat   = dtpicker.getDayNameFormat();
	
	// create Row
	var row = table.insertRow(table.rows.length);
	var cell = null;
	
	for (var i=0; i < 7; i++) {
		cell = row.insertCell(row.cells.length);
		cell.className = CSS_DOW;
		var day = arrWeekdays[(firstDayOfWeek + i)%7];
		
		if (dayNameFormat == DayNameFormat.FIRSTLETTER) {
			day = day.charAt();
		} else if (dayNameFormat == DayNameFormat.FIRSTTWOLETTER) {
			day = day.substring(0, 2);
		} else if (dayNameFormat == DayNameFormat.FULL) {
			// no action
		}
		
		cell.appendChild(document.createTextNode(day));
	}
}
function CalendarPainter_createCalendarTable(calData, table) {
	var dtpicker              = calData['dtpicker'];
	var showDaysOfOtherMonths = dtpicker.isShowDaysOfOtherMonths();
	var firstDayOfWeek        = dtpicker.getFirstDayOfWeek();
	var dtCurrentDate         = dtpicker.getCurrentDate();
	var dtSelectedDate        = dtpicker.getSelectedDate();        // the last selected day
	var dtStartDay            = dtpicker.getStartDay();            // day the calendar should start with
	dtStartDay.setDate(1);
	dtStartDay.setDate(1 - (7 + dtStartDay.getDay() - firstDayOfWeek) % 7);
	var dtCurrentDay         = new Date(dtStartDay);              // current day for interation
	var today                = new Date();
	var row  = null;
	var cell = null;

	while (dtCurrentDay.getMonth() == dtCurrentDate.getMonth() || dtCurrentDay.getMonth() == dtStartDay.getMonth()) {

		if (safari) {
			row = table.insertRow(-1);	// table.rows.length
		} else {	
			row = table.insertRow(table.rows.length);
		}
		
		for (var i=0; i < 7; i++) {
		
			var className  = '';
			var otherMonth = false;
			cell = row.insertCell(row.cells.length);
	
			if (dtCurrentDay.getYear() == dtSelectedDate.getYear() &
				dtCurrentDay.getMonth() == dtSelectedDate.getMonth() &
				dtCurrentDay.getDate() == dtSelectedDate.getDate()) {
				// style for the selected date
				className = CSS_DATES_SELECTED;
			} else if (
				dtCurrentDay.getDate() == today.getDate() &
				dtCurrentDay.getMonth() == today.getMonth() &
				dtCurrentDay.getYear() == today.getYear()) {
				// style for the current date
				className = CSS_DATES_CURRENT;
			} else if (dtCurrentDay.getDay() == 0 || dtCurrentDay.getDay() == 6) {
				// style for weekend days
				className = CSS_DATES_WEEKEND;
			} else {
				// style for working day of the current month
				className = CSS_DATES;
			}
			
			if (dtCurrentDay.getMonth() != dtCurrentDate.getMonth()) {
				// style for days of other months
				className = CSS_DATES_OTHERMONTH;
				otherMonth = true;
			}
			
			if (!showDaysOfOtherMonths & otherMonth) {
				// no action
			} else {
				// create the cell which includes the day
				this.createDateAnchor(cell, calData, new Date(dtCurrentDay), className);
			}
			
			// next day
			dtCurrentDay.setDate(dtCurrentDay.getDate() + 1);
		}
	}
}
function CalendarPainter_createDateAnchor(cell, calData, dtCurrentDay, className) {
	var dtpicker = calData['dtpicker'];

	var a = document.createElement('A');
//	a.href = 'javascript:this.onclick();';
	a.onclick = function() {
		// select the date
		dtpicker.setSelectedDate(dtCurrentDay);

		if (dtpicker.getCloseOnSelect()) {
			dtpicker.close();
			return false;
		} else {
			// render the calendar
			CalendarPainter.render(calData);
		}
	};
	a.style.cursor = 'pointer';
	a.style.cursor = 'hand';
	
	a.appendChild(document.createTextNode(dtCurrentDay.getDate()));

	cell.className = className;
	cell.appendChild(a);
}
function CalendarPainter_createTodaySelector(calData, cell) {
	var dtpicker      = calData['dtpicker'];
	var arrMonths     = dtpicker.getMonths();
	var dtCurrentDate = new Date();

	var label = calData['BTN_LABEL_TODAY'];
	label += ': ' + arrMonths[dtCurrentDate.getMonth()];
	label += ' '  + dtCurrentDate.getDate();
	label += ', ' + dtCurrentDate.getFullYear();

	var a = document.createElement('A');
	a.appendChild(document.createTextNode(label));
	//a.href = '#'; //'javascript:this.onclick();';
	a.onclick = function(event) {
			// reset + select current date
			dtpicker.setCurrentDate(new Date());
			dtpicker.setSelectedDate(new Date());

			if (dtpicker.getCloseOnSelect()) {
				dtpicker.close();
				return false;
			} else {
				// render calendar
				CalendarPainter.render(calData);
			}
		}
	a.style.cursor = 'pointer';
	a.style.cursor = 'hand';

	cell.className = CSS_CPTODAY;
	cell.appendChild(a);
}
function CalendarPainter_createFooter(calData, node) {
}
function CalendarPainter_getDTPNode(calData) {
	var dtpicker = calData['dtpicker'];
	var id  = (dtpicker.calendarType == CalendarTyp.INLINE) ? dtpicker.getId() : DTP_PREFIX + dtpicker.getId();
	var div = document.getElementById(id);
	div.innerHTML = '';
	return div;
}
function CalendarPainter_insertRow(table, pos) {
	var row = null;
	
	if (safari) {
		row = table.insertRow(-1);
	} else {	
		row = table.insertRow(table.rows.length);
	}
	
	return row;
}
function CalendarPainter_insertCell(row, pos) {

}

new CalendarPainter();
CalendarPainter.render               = CalendarPainter_render;
CalendarPainter.renderCalendar       = CalendarPainter_renderCalendar;
CalendarPainter.renderTimePicker     = CalendarPainter_renderTimePicker;
CalendarPainter.createSelectors      = CalendarPainter_createSelectors;
CalendarPainter.createTitle          = CalendarPainter_createTitle;
CalendarPainter.createCalendar       = CalendarPainter_createCalendar;
CalendarPainter.createWeekDays       = CalendarPainter_createWeekDays;
CalendarPainter.createCalendarTable  = CalendarPainter_createCalendarTable;
CalendarPainter.createTodaySelector  = CalendarPainter_createTodaySelector;
CalendarPainter.createDateAnchor     = CalendarPainter_createDateAnchor;
CalendarPainter.createFooter         = CalendarPainter_createFooter;
CalendarPainter.getDTPNode           = CalendarPainter_getDTPNode;
CalendarPainter.createInputCell      = CalendarPainter_createInputCell;
CalendarPainter.createHidden         = CalendarPainter_createHidden;
CalendarPainter.createSeperator      = CalendarPainter_createSeperator;
CalendarPainter.createAmPmTextNode   = CalendarPainter_createAmPmTextNode;
CalendarPainter.createSpinButton     = CalendarPainter_createSpinButton;
CalendarPainter.renderTextButtons    = CalendarPainter_renderTextButtons;
CalendarPainter.getFieldWithFocus    = CalendarPainter_getFieldWithFocus;
CalendarPainter.highlight            = CalendarPainter_highlight;
CalendarPainter.createMonthOptionList= CalendarPainter_createMonthOptionList;
CalendarPainter.doDefaultLayout      = CalendarPainter_doDefaultLayout;
CalendarPainter.doCustomLayout1      = CalendarPainter_doCustomLayout1;
CalendarPainter.doCustomLayout2      = CalendarPainter_doCustomLayout2;
CalendarPainter.insertRow            = CalendarPainter_insertRow;
CalendarPainter.insertCell           = CalendarPainter_insertCell;
