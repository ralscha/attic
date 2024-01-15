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

TimeSeperator = new Object();
TimeSeperator.COLON    = ':';
TimeSeperator.COMMA    = ',';
TimeSeperator.DEFAULT  = TimeSeperator.COLON;

DateSeperator = new Object();
DateSeperator.SLASH    = '/';
DateSeperator.DOT      = '.';
DateSeperator.DASH     = '-';
DateSeperator.DEFAULT  = DateSeperator.DOT;

/*
+ ---------------------------------------------------------------------------------+
| Object....: Formatter for DateTime fields
| Function..: Formatter
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 01.11.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function Formatter() {
}
function Formatter_formatDateField(obj, pattern) {
	var value    = '';
	var date     = null;
	var isObject = false;

	// check arguments
	if (null == obj || null == pattern ) {
		return;
	}

	// check argument
    if (typeof obj == 'string') {
		value = obj;
	} else if (typeof obj == 'object') {
		value = obj.value;
		isObject = true;
	} else {
		return;
	}
	
	// only format the field if it contains a value
	if ('' == value) {
		return;
	}

	// check for expressions like +D, +W, +M, +Y
	// ToDo

	// otherwise parse the value
	if (date == null) {
		date = DateFormat.parse(value, pattern);

		if (null == date && isObject) {
			obj.focus();
			return;
		} else if (null == date) {
			return;
		}

		if (null == date || isNaN(date)) {
			date = new Date();
		}
	}

	// get the formatted string
	value = DateFormat.formatDate(date, pattern);

	// handle the result	
	if (isObject) {
		// if the value comes from an object set the value
		obj.value = value;
	} else {
		// otherwise we only return the string
		return value;
	}
}
function Formatter_formatTimeField(obj, pattern) {
	var value    = '';
	var date     = null;
	var isObject = false;

	// check arguments
	if (null == obj || null == pattern ) {
		return;
	}

	// check argument
    if (typeof obj == 'string') {
		value = obj;
	} else if (typeof obj == 'object') {
		value = obj.value;
		isObject = true;
	} else {
		return;
	}
	
	// only format the field if it contains a value
	if ('' == value) {
		return;
	}

	// check for expressions like +H, +M
	// ToDo
	
	// otherwise parse the value
	if (date == null) {
		date = DateFormat.parse(value, pattern);

		if (null == date || isNaN(date)) {
			date = new Date();
		}
	}

	// get the formatted string
	value =  DateFormat.formatTime(date, pattern);

	// handle the result	
	if (isObject) {
		// if the value comes from an object set the value
		obj.value = value;
	} else {
		// otherwise we only return the string
		return value;
	}
}
new Formatter();
Formatter.formatDateField = Formatter_formatDateField;
Formatter.formatTimeField = Formatter_formatTimeField;


/*
+ ---------------------------------------------------------------------------------+
| Object....: DateFormat
| Function..: Helper Object used to format or parse Date Strings
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 22.05.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function DateFormat() {
}
function DateFormat_parse(value, formatMask) {

	// check arguments
	if (null == value || '' == value || null == formatMask || '' == formatMask) {
		return new Date();
	}

	// Create a object which holds the parsed values
	var DATE = new Object();
	DATE.YEAR = 0;
	DATE.MONTHS = 0;
	DATE.DAYS = 0;
	DATE.HOURS = 0;
	DATE.MINUTES = 0;
	DATE.SECONDS = 0;
	DATE.AMPM = '';

	var SPACE = ' ';
	var fStrings = new Array();
	var vStrings = new Array();

	// split mask and value
	var fElements = formatMask.split(SPACE);
	var vElements = value.split(SPACE);

	// check mask and value
	if (null == fElements || null == vElements) {
		return new Date();
	}

	if (fElements.length != vElements.length) {
		return new Date();
	}

	// Now parse the string
	for (var i=0; i < fElements.length; i++) {
		this.parseDateTime(vElements[i], fElements[i], DATE);
	}

	// check if AM/PM was specified
	if (DATE.AMPM == 'PM') {
		DATE.HOURS += 10;
	}

	if (DATE.YEAR == 0 && DATE.MONTHS == 0 && DATE.DAYS == 0) {
		var today   = new Date();
		DATE.YEAR   = today.getYear();
		DATE.MONTHS = today.getMonth();
		DATE.DAYS   = today.getDate();
	}

	// creat a date object based on the parsed values
	var newDate = new Date(DATE.YEAR,DATE.MONTHS,DATE.DAYS,DATE.HOURS,DATE.MINUTES,DATE.SECONDS);
	
	if (!this.isValid(DATE, newDate)) {
		var rtc = this.showErrorMessage(value, formatMask);
		return rtc ? new Date() : null;
		
	} else {
		return newDate;
	}
}

function DateFormat_parseDateTime(value, submask, date) {
	var seperator = null;
	var masks  = new Array();
	var values = new Array();
	var isDate = this.isDateMask(submask);
	var isTime = this.isTimeMask(submask);

	if (isDate) {
		seperator = this.getDateSeperator(submask)
	} else if (isTime) {
		seperator = this.getTimeSeperator(submask)
	} else {
		// no seperator used 
	}

	if (null == seperator) {
		masks[masks.length] = submask;
		values[values.length] = value;
	} else {
		masks  = submask.split(seperator);
		values = value.split(seperator);
	}

	// check if mask can be matched
	if (values.length != masks.length) {
		// return the currrent date
		return this.newDate(date);
	}
	
	for (var i=0; i < masks.length; i++) {
		if (masks[i].toUpperCase() == 'YY' || masks[i].toUpperCase() == 'YYYY') {
			var val = this.parseSaveInt(values[i]);
			if (isNaN(val)) return this.newDate(date);
			date.YEAR = (val > 10) ? val : 2000 + val;
		} else if (masks[i] == 'M' || masks[i] == 'MM') {
			var val = this.parseSaveInt(values[i]);
			if (isNaN(val) || val > 12 || val < 1) return this.newDate(date);	
			date.MONTHS = val - 1;
		} else if (masks[i] == 'MMMM') {
			// not supported
		} else if (masks[i].toUpperCase() == 'D' || masks[i].toUpperCase() == 'DD') {
			var val = this.parseSaveInt(values[i]);
			if (isNaN(val) || val > 31 || val < 1) return this.newDate(date);	
			date.DAYS = val;
		} else if (masks[i] == 'DDDD') {
		
		} else if (masks[i].toUpperCase() == 'H' || masks[i].toUpperCase() == 'HH') {
			var val = this.parseSaveInt(values[i]);
			if (isNaN(val) || val > 24 || val < 0) return this.newDate(date);	
			date.HOURS = val;
		} else if (masks[i] == 'm' || masks[i] == 'mm') {
			var val = this.parseSaveInt(values[i]);
			if (isNaN(val) || val > 60 || val < 0) return this.newDate(date);	
			date.MINUTES = val;
		} else if (masks[i].toUpperCase() == 'S' || masks[i].toUpperCase() == 'SS') {
			var val = this.parseSaveInt(values[i]);
			if (isNaN(val) || val > 60 || val < 0) return this.newDate(date);	
			date.SECONDS = val;
		} else if (masks[i].toUpperCase() == 'TT') {
			date.AMPM = values[i].toUpperCase();
		} else {
			// mask not supported/skipped
		}
	}
}
function DateFormat_format(value, formatMask) {
	var SPACE = ' ';
	var fStrings = new Array();

	// split mask
	var elements = formatMask.split(SPACE);  // TRIM?
	
	for (var i=0; i < elements.length; i++) {
		fStrings[i] =  this.formatDateTime(value, elements[i]);
	}

	return fStrings.join(SPACE);
}
function DateFormat_formatDateTime(value, formatMask) {
	
	if (this.isDateMask(formatMask)) {
		return this.formatDate(value, formatMask);
	} else if (this.isTimeMask(formatMask)) {
		return this.formatTime(value, formatMask);
	} else {
		// If can not be passed return
		// Example: hh:mm 'hour'
		return formatMask;
	}
}
function DateFormat_formatDate(value, formatMask) {
	var arrDate = new Array();
	
	// get sperators
	var dateSeperator = this.getDateSeperator(formatMask);
	
	if (null != dateSeperator) {
		var arrFormat = formatMask.split(dateSeperator);
		
		for (var i=0; i < arrFormat.length; i++) {
			arrDate[i] = this.formatDateElement(value, arrFormat[i].toUpperCase());
		}
		
		return arrDate.join(dateSeperator);
	} else {
		return this.formatDateElement(value, formatMask);
	}
}
function DateFormat_formatDateElement(value, submask) {
	var mask = submask.toUpperCase();
	
	if (mask == 'YY') {
		return (new String(value.getFullYear())).substring(2,4);
	} else if (mask == 'YYYY') {
		return value.getFullYear();
	} else if (mask == 'M') {
		return value.getMonth();
	} else if (mask == 'MM') {
		var month = value.getMonth() + 1;
		return (month >= 10) ? month : '0' + month;
	} else if (mask == 'MMMM') {
		var ARR_MONTHS = DTPRes.getMonths();
		return ARR_MONTHS[value.getMonth()];
	} else if (mask == 'D') {
		return value.getDate();
	} else if (mask == 'DD') {
		return (value.getDate() >= 10) ? value.getDate() : '0' + value.getDate();
	} else if (mask == 'DDDD') {
		var ARR_DOWS = DTPRes.getWeekdays();
		return ARR_DOWS[value.getDay()];;
	} else {
		// If the mask can not be parsed return the value.
		return submask;
	}
}
function DateFormat_formatTime(value, formatMask) {

	var arrTime = new Array();
	
	// get sperators
	var timeSeperator = this.getTimeSeperator(formatMask);
	
	if (null != timeSeperator) {
		var arrFormat = formatMask.split(timeSeperator);

		for (var i=0; i < arrFormat.length; i++) {
			arrTime[i] = this.formatTimeElement(value, arrFormat[i].toUpperCase());
		}
		
		return arrTime.join(timeSeperator);
	} else {
		return this.formatTimeElement(value, formatMask);
	}
}
function DateFormat_formatTimeElement(value, submask) {
	var mask = submask.toUpperCase();

	if (mask == 'H') {
		// check for Am/PM setting
		
		return value.getHours();
	} else if (mask == 'HH') {
	
		return DateFormat.toLength2(value.getHours());
		
	} else if (mask == 'M') {
		return value.getMinutes();
	} else if (mask == 'MM') {
		return  DateFormat.toLength2(value.getMinutes());
	} else if (mask == 'S') {
		return value.getSeconds();
	} else if (mask == 'SS') {
		return DateFormat.toLength2(value.getSeconds());
	} else if (mask == 'MS') {
		return value.getMilliSeconds();
	} else if (mask == 'TT' || submask == 'tt') {
		if (value.getHours() >= 0 && value.getHours() <= 12) {
			return 'AM';
		} else {
			return 'PM';
		}
	} else {
		// If the mask can not be passed return the value.
		return submask;
	}
}
function DateFormat_getDateSeperator(formatMask) {
	if (formatMask.indexOf(DateSeperator.SLASH) != -1) {
		return DateSeperator.SLASH;
	} else if (formatMask.indexOf(DateSeperator.DOT) != -1) {
		return DateSeperator.DOT;
	} else if (formatMask.indexOf(DateSeperator.DASH) != -1) {
		return DateSeperator.DASH;
	} else {
		return null;
	}
}
function DateFormat_getTimeSeperator(format) {
	if (format.indexOf(TimeSeperator.COLON) != -1) {
		return TimeSeperator.COLON;
	} else if (format.indexOf(TimeSeperator.COMMA) != -1) {
		return TimeSeperator.COMMA;
	} else {
		return null;
	}
}
function DateFormat_isDateMask(formatMask) {
	var mask = formatMask.toUpperCase();
	
	if (null != this.getDateSeperator(formatMask)
		|| mask == 'DDDD'
		|| mask == 'YYYY' || mask == 'YY'
		|| formatMask == 'MM' || formatMask == 'M'
		|| mask == 'DD' || mask == 'D') {
		return true;
	} else {
		return false;
	}
}
function DateFormat_isTimeMask(formatMask) {
	var mask = formatMask.toUpperCase();
	
	if (null != this.getTimeSeperator(formatMask)
		|| mask == 'HH' || mask == 'H'
		|| formatMask == 'mm' || formatMask == 'm'
		|| mask == 'SS' || mask == 'S'
		|| mask == 'TT') {
		return true;
	} else {
		return false;
	}
}
function DateFormat_hasHours(formatMask) {
	var SPACE = ' ';
	var timeseperator = this.getTimeSeperator(formatMask);
	
	// split mask
	var elements = formatMask.split(SPACE);
	
	for (var i=0; i < elements.length; i++) {
		if (elements[i].toUpperCase() == 'HH' || elements[i].toUpperCase() == 'H') {
			return true;
		} else if (null != timeseperator) {
			var tokens = elements[i].toUpperCase().split(timeseperator);
			
			if (null != tokens) {
				for (var j=0; j < tokens.length; j++) {
					if (tokens[j] == 'HH' || tokens[j] == 'H') {
						return true;
					}
				}
			}
		}
	}
	
	return false;
}
function DateFormat_hasMinutes(formatMask) {
	var SPACE = ' ';
	var timeseperator = this.getTimeSeperator(formatMask);
	
	// split mask
	var elements = formatMask.split(SPACE);
	
	for (var i=0; i < elements.length; i++) {
		if (elements[i].toUpperCase() == 'MM' || elements[i].toUpperCase() == 'M') {
			return true;
		} else if (null != timeseperator) {
			var tokens = elements[i].toUpperCase().split(timeseperator);
			
			if (null != tokens) {
				for (var j=0; j < tokens.length; j++) {
					if (tokens[j] == 'MM' || tokens[j] == 'M') {
						return true;
					}
				}
			}
		}
	}
	
	return false;
}
function DateFormat_hasSeconds(formatMask) {
	var SPACE = ' ';
	var timeseperator = this.getTimeSeperator(formatMask);
	
	// split mask
	var elements = formatMask.split(SPACE);
	
	for (var i=0; i < elements.length; i++) {
		if (elements[i].toUpperCase() == 'SS' || elements[i].toUpperCase() == 'S') {
			return true;
		} else if (null != timeseperator) {
			var tokens = elements[i].toUpperCase().split(timeseperator);
			
			if (null != tokens) {
				for (var j=0; j < tokens.length; j++) {
					if (tokens[j] == 'SS' || tokens[j] == 'S') {
						return true;
					}
				}
			}
		}
	}
	
	return false;
}
function DateFormat_hasAMPMString(formatMask) {
	var SPACE = ' ';
	
	// split mask
	var elements = formatMask.split(SPACE);
	
	for (var i=0; i < elements.length; i++) {
		if (elements[i].toUpperCase() == 'TT') {
			return true;
		}
	}
	
	return false;
}
function DateFormat_isTime(formatMask) {
	var hasHour    = DateFormat.hasHours(formatMask);
	var hasMinute  = DateFormat.hasMinutes(formatMask);
	var hasSeconds = DateFormat.hasSeconds(formatMask);
	
	if (hasHour || hasMinute || hasSeconds) {
		return true;
	}
}

function DateFormat_toLength2(value) {
	var val = new String(value);

	if (val.length < 2) {
		return ('0' + val);
	} else {
		return val;
	}
}
function DateFormat_parseSaveInt(value) {
	value = '' + value;
	
	if (value.charAt(0) == '0' && value.length == 2) {
		return parseInt(value.charAt(1));
	} else {
		return parseInt(value);
	}
}
function DateFormat_isValid(parsedDate, newDate) {
	// arg1: parsed date
	// the new date created/based on the parsed date

//	alert(parsedDate.DAYS + " <--> " + newDate.getDate());
//	alert(parsedDate.MONTHS + " <--> " + newDate.getMonth());
//	alert(parsedDate.YEAR + " <--> " + newDate.getFullYear());

	if (this.parseSaveInt(parsedDate.DAYS) != this.parseSaveInt(newDate.getDate())) {
		return false;
	}
	
	if (this.parseSaveInt(parsedDate.MONTHS) != this.parseSaveInt(newDate.getMonth())) {
		return false;
	}

	if (this.parseSaveInt(parsedDate.YEAR) != this.parseSaveInt(newDate.getFullYear())) {
		return false;
	}
	
	return true;
}
function DateFormat_showErrorMessage(value, formatMask) {
	// overwrite this method within the jsp page to display
	// an error message if an invalid date was entered by the user
	// Example: DateFormat.showErrorMessage = myfunction();
	// or : DateFormat_showErrorMessage(value, formatMask) { .... }
	//alert('Invalid Date: ' +  value);
	return false;
}
function DateFormat_newDate(date) {
		var today    = new Date();
		date.YEAR    = today.getYear();
		date.MONTHS  = today.getMonth();
		date.DAYS    = today.getDate();
		date.HOURS   = today.getHours();
		date.MINUTES = today.getMinutes();
		date.SECONDS = today.getSeconds();
}
new DateFormat();
DateFormat.parse             = DateFormat_parse;
DateFormat.parseDateTime     = DateFormat_parseDateTime;
DateFormat.format            = DateFormat_format;
DateFormat.formatDateTime    = DateFormat_formatDateTime;
DateFormat.formatDate        = DateFormat_formatDate;
DateFormat.formatDateElement = DateFormat_formatDateElement;
DateFormat.formatTime        = DateFormat_formatTime;
DateFormat.formatTimeElement = DateFormat_formatTimeElement;
DateFormat.getDateSeperator  = DateFormat_getDateSeperator;
DateFormat.getTimeSeperator  = DateFormat_getTimeSeperator;
DateFormat.isDateMask        = DateFormat_isDateMask;
DateFormat.isTimeMask        = DateFormat_isTimeMask;
DateFormat.hasHours          = DateFormat_hasHours;
DateFormat.hasMinutes        = DateFormat_hasMinutes;
DateFormat.hasSeconds        = DateFormat_hasSeconds;
DateFormat.hasAMPMString     = DateFormat_hasAMPMString;
DateFormat.isTime            = DateFormat_isTime;
DateFormat.toLength2         = DateFormat_toLength2;
DateFormat.parseSaveInt      = DateFormat_parseSaveInt;
DateFormat.isValid           = DateFormat_isValid;
DateFormat.showErrorMessage  = DateFormat_showErrorMessage;
DateFormat.newDate           = DateFormat_newDate;

