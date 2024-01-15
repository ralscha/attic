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


/////////////////////////////////////////////////////////////
// Global Variables used by the CC-Framework
/////////////////////////////////////////////////////////////


var LF = '\n';
var NBSP = '';

var RunAt = new Object();
RunAt.SERVER = 0;
RunAt.CLIENT = 1;

var SelectMode = new Object();
SelectMode.NONE = 0;
SelectMode.SINGLE = 1;
SelectMode.MULTIPLE = 2;

var NodeType = new Object();
NodeType.NODE  = 1;
NodeType.GROUP = 2;

var ExpandMode = new Object();
ExpandMode.SINGLE   = 0;
ExpandMode.MULTIPLE = 1;
ExpandMode.FULL     = 2;

var NodeState = new Object();
NodeState.NONE      = 0;
NodeState.EXPAND    = 1;
NodeState.COLLAPSE  = 2;
NodeState.EXPANDEX  = 3;

var NodeFilter = new Object();
NodeFilter.ROOT     = 2;
NodeFilter.GROUP    = 3;
NodeFilter.NODE     = 4;

var CheckState = new Object();
CheckState.NONE          = -2;
CheckState.UNSELECTABLE  = -1;
CheckState.UNCHECKED     = 0;
CheckState.CHECKED       = 1;
CheckState.UNDEFINED     = 2;

var Orientation = new Object();
Orientation.VERTICAL   = 1;
Orientation.HORIZONTAL = 2;

// TODO remove
var ClientHandler = new Object();
ClientHandler.ONCHANGE = 1;
ClientHandler.ONCLICK  = 2;
ClientHandler.ONFOCUS  = 3;

ClientEvent = new Object();
ClientEvent.ONCLICK        = 'onClick';
ClientEvent.ONCONTEXTMENU  = 'onContextMenu';
ClientEvent.ONFOCUS        = 'onFocus';
ClientEvent.ONMOUSEOVER    = 'onMouseOver';
ClientHandler.ONCHANGE     = 'onChange';

/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Opens a PopUp Window which shows a datetime picker.
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 22.04.2004  G.Schulz (SCC)    Inital version
| 28.06.2005  G.Schulz (SCC)    Reduce the width if no time mask is present
+ ---------------------------------------------------------------------------------+
*/
var calendar = null;

function popupCalendar(fieldId, locale, formatMask, width, height, template) {
	var obj    = document.getElementById(fieldId);
	var id     = obj.id;
	var value  = (null != obj.value) ? obj.value : '';
	var target = '';

	if (null == width || '' == width) {
		width = 350;
	}
	if (null == height || '' == height) {
		height = 250;
	}
	
	var isTime = DateFormat.isTimeMask(formatMask);
	var isDate = DateFormat.isDateMask(formatMask);
	
	if (!isTime) {
		width = 215;
	} else if (!isDate) {
		width = 250;
		height = 110;
	}
	
	target += template;
	target += '?datetime=' + value;
	target += '&fieldid=' + id;
	target += '&locale=' + locale.toUpperCase();
	target += '&mask=' + formatMask;

	// get the coordinate to display the new window
	var left = (screen.width - width) / 2;
	var top  = (screen.height - height) / 2;

	// Check if the window is already opend
	if (null == calendar || calendar.closed) {
		calendar = window.open(target, '', 'top=' + top + ',left=' + left + ',width=' + width + ',height=' + height + ',status=no,toolbar=no,location=no,resizable=no,scrollbars=no,menubar=no');
	} else {
		calendar.focus();
	}
}


/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Opens a Inline Popup Calendar Window
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 24.08.2005  G.Schulz (SCC)    Inital version
+ ---------------------------------------------------------------------------------+
*/
function createCalendarButtons(resPath, locale) {

	var imgSrc    = ['btnBkg1_left.gif', 'btnBkg1_middle.gif', 'btnBkg1_right.gif'];
	var imgWidth  = [7, 0, 7];
	var imgHeight = 24;

	// (1) CANCEL Button
	var btnLabel    = DTPRes.getButtonLabel(locale,'Cancel');
	var btnTooltip  = DTPRes.getButtonLabel(locale,'Cancel');
	var btnWidth    = 90;
	var btnCancel   = new TextButton('btnCancel', btnLabel, btnWidth, resPath, imgSrc, imgWidth, imgHeight, btnTooltip);

	// (2) OK Button
	btnLabel    = DTPRes.getButtonLabel(locale,'OK');
	btnTooltip  = DTPRes.getButtonLabel(locale,'OK');
	btnWidth    = 90;
	var btnOk   = new TextButton('btnOk', btnLabel, btnWidth, resPath, imgSrc, imgWidth, imgHeight, btnTooltip);

	// (3) Previous Year
	btnTooltip  = DTPRes.getButtonLabel(locale,'PrevYearLabel');
	btnAlt      = DTPRes.getButtonLabel(locale,'PrevYearAlt');
	imgPrevYear = new Icon('imgPrevYear', resPath, 'btnLeft1.gif', 21, 25, btnTooltip, btnAlt);

	// (4) Next Year	
	btnTooltip  = DTPRes.getButtonLabel(locale,'NextYearLabel');
	btnAlt      = DTPRes.getButtonLabel(locale,'NextYearAlt');
	imgNextYear = new Icon('imgNextYear', resPath, 'btnRight1.gif', 21, 25, btnTooltip, btnAlt);

	// (5) Previous Month
	btnTooltip  = DTPRes.getButtonLabel(locale,'PrevMonthLabel');
	btnAlt      = DTPRes.getButtonLabel(locale,'PrevMonthAlt');
	imgPrevMonth = new Icon('imgPrevMonth', resPath, 'btnLeft1.gif', 21, 25, btnTooltip, btnAlt);

	// (6) Next Month	
	btnTooltip  = DTPRes.getButtonLabel(locale,'NextMonthLabel');
	btnAlt      = DTPRes.getButtonLabel(locale,'NextMonthAlt');
	imgNextMonth = new Icon('imgNextMonth', resPath, 'btnRight1.gif', 21, 25, btnTooltip, btnAlt);
	
	// (2) Create array including all buttons and images
	var buttons = [btnCancel, btnOk, imgPrevYear, imgNextYear, imgPrevMonth, imgNextMonth];

	return buttons;
}

function showInlineCalendar(obj, fieldId, locale, formatMask, width, height, resPath) {

	// default width/height
	var width  = 0;
	var height = 0;

	// Currently only the default template is supported
	var resPath = 'fw/html/calendar/layout1/'; // = (null == resPath) ? 'fw/html/calendar/layout1/' : resPath;

	// get the localized button resources
	var buttons = createCalendarButtons(resPath, locale);

	var isTime = DateFormat.isTimeMask(formatMask);
	var isDate = DateFormat.isDateMask(formatMask);
	
	if (isDate && isTime) {
		width  = 390;
		height = 190 + 20;
	} else if (isTime) {
		width = 180;
		height = (ie || opera || safari) ? 70 : 85;
	} else if (isDate) {
		width  = 190;
		height = 190 + 20;
	}

	// create and open an inline popup
	var popup =  new PopUp(fieldId, obj, width, height);
	popup.open();

	// Configure the calendar
	var crtl_calendar = new Calendar(popup.div.id);
	crtl_calendar.setOpener(popup);
	crtl_calendar.setDayNameFormat(DayNameFormat.FIRSTTWOLETTER);   // display the first two characters of the dayname
	crtl_calendar.setDayNameToUppercase(DayNameToUppercase.FULL);   // convert the first characters of the dayname to uppercase
	crtl_calendar.setShowTimePicker(isTime);
	crtl_calendar.setFirstDayOfWeek(1);                             // start with monday
	crtl_calendar.setShowDaysOfOtherMonths(false);                  // do not display days form the prev or next month
	crtl_calendar.setCloseOnSelect(!isTime)                         // 28.07.2005: display ok/cancel button only if a time mask is present
	crtl_calendar.setShowTodaySelector(true);                       // display "today" selector
	crtl_calendar.setFieldId(fieldId);                              // id of the associated input field
	crtl_calendar.setFormatMask(formatMask);                        // set the format mask
	crtl_calendar.setPreselect(true);                               // open calendar with the date specified in the input field
	
	// set up the painter informations
	var caldata = new CalendarPainterData(crtl_calendar, resPath, locale, 0, buttons);
	
	// render the component
	CalendarPainter.render(caldata);
}


/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Opens a PopUp Window which shows a color picker.
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 22.04.2004  G.Schulz (SCC)    Inital version
+ ---------------------------------------------------------------------------------+
*/
var colorpicker = null;

function popupCPicker(target, fieldId, locale, palette) {
	var obj    = document.getElementById(fieldId);
	var id     = obj.id;
	var value  = (null != obj.value) ? obj.value : '';

	target += '?fieldid=' + id;
	target += '&locale='  + locale.toUpperCase();
	target += '&value='   + value;
	target += '&palette=' + palette;

	// get the coordinate to display the new window
	var left = (screen.width - 255) / 2;
	var top  = (screen.height - 250) / 2;

	// Check if the window is already opend
	if (null == colorpicker || colorpicker.closed) {
		colorpicker = window.open(target, '', 'top=' + top + ',left=' + left + ',width=255,height=250,status=no,toolbar=no,location=no,resizable=no,scrollbars=no,menubar=no');
	} else {
		colorpicker.focus();
	}
}

/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Opens a PopUp Window to edit the textarea within a seperate window.
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 21.10.2004  G.Schulz (SCC)    Inital version
| 15.11.2004  G.Schulz (SCC)    New readonly attribute
+ ---------------------------------------------------------------------------------+
*/
var textpopup = null;

function popupTextPopup(fieldId, locale, maxlength, readonly, width, height, rows, template) {
	var target    = '';
	var obj       = document.getElementById(fieldId);
	var id        = obj.id;
	var winWidth  = (null == width || isNaN(width)) ? 350 : width;
	var winHeight = (null == height || isNaN(height)) ? 150 : height;

	target += template;
	target += '?fieldid=' + id;
	target += '&maxlength=' + maxlength;
	target += '&locale='  + locale.toUpperCase();
	target += '&height='  + winHeight;
	target += '&readonly=' + readonly;
	
	// get the coordinate to display the new window
	var left = (screen.width - winWidth) / 2;
	var top  = (screen.height - winHeight) / 2;

	// Check if the window is already opend
	if (null == textpopup || textpopup.closed) {
		var options = 'top=' + top + ',left=' + left + ',width=' + winWidth + ',height=' + winHeight + ',status=no,toolbar=no,location=no,resizable=no,scrollbars=no,menubar=no'
		textpopup = window.open(target, '', options);
	} else {
		textpopup.focus();
	}
}


/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Provides some helper functions for a textarea
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 12.06.2004  G.Schulz (SCC)    Initial version
| 01.11.2004  G.Schulz (SCC)    dirty flag added
| 13.02.2005  G.Schulz (SCC)    Input not longer accepted if maximum no. of characters is exeeded
|
+ ---------------------------------------------------------------------------------+
*/
function Textarea(id, maxlength, message, nobreak, disabled, readonly) {
	var a = arguments;
	this.id                = id;
	this.obj               = document.getElementById(id);
	this.maxlength         = (a.length >= 1) ? a[1] : null;
	this.limited           = (maxlength != null) ? true : false;
	this.infoLine          = (a.length >= 2) ? a[2] : 'Characters remaining: <b>{0}</b>/{1}';    // Text to display for remaining characters
	this.nobreak           = (a.length >= 3) ? a[3] : false;
	this.disabled          = (a.length >= 4) ? a[4] : false;
	this.readonly          = (a.length >= 5) ? a[5] : false;
	this.infoLineNode      = null;                                                               // span which contains the text
	this.textWarningtAt    = 0;                                                                  // default 10 characters remaining
	this.CSS_INFOLINE      = 'ltail';
	this.dirty             = false;
	this.allowOverflow     = false;
	
	// add eventhandler
	if (null != this.obj && this.limited) {
		
		// check if the textare is readonly or disabled
		// In both cases no message is displayed
		if (!this.disabled && !this.readonly) {
			this.createInfoLine();
			this.setUpHandler();
			this.checkLimit();
		}
	}
}
function Textarea_getId() {
	return this.id;
}
function Textarea_getMaxLength() {
	return this.maxlength;
}
function Textarea_setInfoLine(infoLine) {
	this.infoLine = infoLine;
}
function Textarea_setUpHandler() {
	var _textarea = this;

	// check if the textare is readonly or disabled
	// In both cases no message is displayed and
	// no handlers where needed
	if (this.isReadonly() || this.isDisabled()) {
		return;
	}
	
	this.obj.onchange = function(event) {
		_textarea.dirty = true;
		_textarea.checkLimit();
	}
	this.obj.onkeyup = function(event) {
		_textarea.checkLimit();
	}
	this.obj.onkeypress = function(event) {
		var acceptKey = _textarea.checkLimit();

		if (!acceptKey && ie) {
			return false;
		} else {
			return true;
		}
	}
	this.obj.onpaste = function(event) {
		_textarea.checkLimit();
	}
}
function Textarea_checkLimit() {

	if (0 == this.maxlength) {
		// maxlength must not be checked
		return true;
	}

	var remaining = parseInt(this.maxlength) - parseInt(this.obj.value.length);

	if (!this.allowOverflow && remaining == 0){
		this.updateInfoLine(0, 0);
		return false;
	}
	if (!this.allowOverflow && remaining < 0) {
		this.obj.value = this.obj.value.substr(0, this.maxlength);
		this.updateInfoLine(0, this.textWarningtAt);
	} else {
		this.updateInfoLine(remaining, this.textWarningtAt);
	}
	
	return true;
}
function Textarea_updateInfoLine(remaining, warningtAt) {
	var warning = (remaining < warningtAt) ? true : false;

	var	out = this.infoLine;
	
	if (this.infoLine.indexOf('{0}') != -1) {
		out = out.replace('{0}', remaining);
	}
	if (this.infoLine.indexOf('{1}') != -1) {
		out = out.replace('{1}', this.maxlength);
	}
	
	if (warning) {
		out = out.fontcolor('red');
	}

	if (!this.nobreak) {
		out = '<br>' + out;
	}

	this.infoLineNode.innerHTML = out;
}
function Textarea_createInfoLine() {
	var text = document.createTextNode(this.infoLine);
	var span = document.createElement('Span');
	span.appendChild(text);
	span.className = this.CSS_INFOLINE;
	this.obj.parentNode.appendChild(span);
	
	this.infoLineNode = span;
}
function Textarea_encodeHTML(s) {
	s = s.replace(/\&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\n/g, "<BR>");

	while (/\s\s/.test(s)) {
		s = s.replace(/\s\s/, "&nbsp; ");
	}
	
	return s.replace(/\s/g, " ");
}
function Textarea_isDirty() {
	return this.dirty;
}
function Textarea_insertTag(textareaId, tag) {
	var ta = document.getElementById(textareaId);
	var tagOpen = '[' + tag.toLowerCase() + ']';
	var tagClose = '[/' + tag.toLowerCase() + ']';
	
	if (ie) {
		var selected = document.selection.createRange().text;

		if (selected) {
            var addSpace = false;
            if (selected.charAt(selected.length-1) == ' ') {
                selected = selected.substring(0, selected.length-1);
                addSpace = true;
            }
            document.selection.createRange().text = tagOpen + selected + tagClose + ((addSpace) ? ' ': '');
        } else {
            ta.value += tagOpen + tagClose;
        }
    } else {
        ta.value += tagOpen + tagClose;
    }
    
    ta.focus();
    return;
}
function Textarea_isReadonly() {
	return this.readonly;
/*
	if (null == this.obj) {
		return false;
	} else {
		if (ie) {
			return this.obj.getAttribute('readonly');
		} else {
			return this.obj.hasAttribute('readonly');
		}
	}
*/
}
function Textarea_isDisabled() {
	return this.disabled;
/*
	if (null == this.obj) {
		return false;
	} else {
		if (ie) {
			return this.obj.getAttribute('disabled');
		} else {
			return this.obj.hasAttribute('disabled');
		}
	}
*/
}
function Textarea_setAllowOverflow(allowOverflow) {
	this.allowOverflow = allowOverflow;
}
function Textarea_isAllowOverflow(allowOverflow) {
	return this.allowOverflow;
}
function Textarea_toString() {
	var out = '';
	out += '******** TextArea ******* ' + LF;
	out += 'Id.........: ' + this.id + LF;
	out += 'MaxLenght..: ' + this.maxlength + LF;
	out += 'Limited....: ' + this.limited + LF;
	out += 'InfoLine...: ' + this.infoLine + LF;
	out += 'Dirty......: ' + this.dirty;
	out += 'Overflow...: ' + this.allowOverflow;
	return out;
}
new Textarea();
Textarea.prototype.setUpHandler     = Textarea_setUpHandler;
Textarea.prototype.checkLimit       = Textarea_checkLimit;
Textarea.prototype.createInfoLine   = Textarea_createInfoLine;
Textarea.prototype.updateInfoLine   = Textarea_updateInfoLine;
Textarea.prototype.isDirty          = Textarea_isDirty;
Textarea.prototype.isReadonly       = Textarea_isReadonly
Textarea.prototype.isDisabled       = Textarea_isDisabled;
Textarea.prototype.setAllowOverflow = Textarea_setAllowOverflow;
Textarea.prototype.isAllowOverflow  = Textarea_isAllowOverflow;
Textarea.encodeHTML                 = Textarea_encodeHTML;
Textarea.insertTag                  = Textarea_insertTag;
Textarea.prototype.toString         = Textarea_toString;



/*
+ ---------------------------------------------------------------------------------+
| Object....: ImageMapping
| Function..: 
| Arguments.: 
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 05.01.2005  G.Schulz (SCC)    Initial version
+ ---------------------------------------------------------------------------------+
*/
function ImageMapping(rule, src, width, height, tooltip, alt, resource) {
	var a = arguments;

	this.rule     = rule;                              // RegExpression teh image must match
	this.src      = src;                               // image source
	this.width    = width;                             // image width
	this.height   = height;                            // image height
	this.tooltip  = (a.length >= 5) ? tooltip : '';    // additional tooltip
	this.alt      = (a.length >= 6) ? alt : '';        // alt attribute
	this.resource = (a.length >= 7) ? resource : '';
}
function ImageMapping_getSource() {
	return this.src;
}
function ImageMapping_getWidth() {
	return this.width;
}
function ImageMapping_getHeight() {
	return this.height;
}
function ImageMapping_getTooltip() {
	return this.tooltip;
}
function ImageMapping_getAlt() {
	return this.alt;
}
function ImageMapping_toString() {
	var out = '';
	out += '********* ImageMapping ***********' + LF
	out += 'Rule...........: ' + this.rule + LF;
	out += 'Source.........: ' + this.src + LF;
	out += 'Width..........: ' + this.width + LF;
	out += 'Height.........: ' + this.height + LF;
	out += 'Tooltip........: ' + this.tooltip + LF;
	out += 'Alt............: ' + this.alt + LF;
	out += 'Resource.......: ' + this.resource + LF;
	return out;
}
new ImageMapping();
ImageMapping.prototype.getSource  = ImageMapping_getSource;
ImageMapping.prototype.getWidth   = ImageMapping_getWidth;
ImageMapping.prototype.getHeight  = ImageMapping_getHeight;
ImageMapping.prototype.getTooltip = ImageMapping_getTooltip;
ImageMapping.prototype.getAlt     = ImageMapping_getAlt;
ImageMapping.prototype.toString   = ImageMapping_toString;



/*
+ ---------------------------------------------------------------------------------+
| Object....: ImageMap
| Function..: Collection for ImageMapping objects
| Arguments.: id, runAt
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 05.01.2005  G.Schulz (SCC)    Initial version
+ ---------------------------------------------------------------------------------+
*/
function ImageMap(id, runAt, base) {
	var a = arguments;
	
	this.arrImageMappings = new Array();                            // Collection
	this.id     = id;                                               // the id for this image map
	this.runAt  = (a.length >= 2) ? runAt : RunAt.SERVER;           // indicates if the control should work with or without roundtrips
	this.base   = (a.length >= 3) ? base : '';                      // The base directory for all the images
}
function ImageMap_addImageMapping(mapping) {
	if (mapping instanceof ImageMapping) {
		this.arrImageMappings[this.arrImageMappings.length] = mapping;
	}
}
function ImageMap_getImageMapping(rule) {
	for (var i=0; i <= this.arrImageMappings.length; i++) {
		var imRule = this.arrImageMappings[i]['rule'];
		
		if (imRule == rule) {
			return this.arrImageMappings[i];
		}
	}

	return null;
}
function ImageMap_getImageMappings() {
	// return the collection
	return this.arrImageMappings;
}
function ImageMap_toString() {
	var out = '';
	out += '********* ImageMap ***********' + LF
	out += 'Id.............: ' + this.id + LF;
	out += 'RunAt..........: ' + this.runAt + LF;
	out += 'Base...........: ' + this.base + LF;
	return out;
}
new ImageMap();
ImageMap.prototype.toString           = ImageMap_toString;
ImageMap.prototype.addImageMapping    = ImageMap_addImageMapping;
ImageMap.prototype.getImageMapping    = ImageMap_getImageMapping;
ImageMap.prototype.getImageMappings   = ImageMap_getImageMappings;



/*
+ ---------------------------------------------------------------------------------+
| Object....: Icon
| Function..: An Image object
| Arguments.: id, resPath, src, width, height, tooltip, alt
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 17.08.2004  G.Schulz (SCC)    Initial version
| 22.11.2004  G.Schulz (SCC)    Hide ALT + TITLE if null
+ ---------------------------------------------------------------------------------+
*/
function Icon(id, resPath, src, width, height, tooltip, alt) {
	this.id        = id;             // unique button id
	this.width     = width;          // button width
	this.height    = height;         // button height
	this.src       = src;            // src-Attribute
	this.onclick   = Icon_onclick;
	this.resPath   = resPath;
	this.tooltip   = tooltip;        // Tooltip
	this.alt       = alt;
	this.border    = 0;
}
function Icon_onclick() {
	return;
}
function Icon_create() {
	var img    = document.createElement('Img');
	img.src    = (null != this.resPath) ? this.resPath + this.src : this.src;
	img.title  = (null != this.tooltip) ? this.tooltip : '';
	img.alt    = (null != this.alt) ? this.alt : '';
	img.border = 0;
	img.width  = this.width;
	img.height = this.height;
	img.setAttribute('vspace', 0);
	img.border = this.border;
	return img;
}
function Icon_toString() {
	var out = '';
	out += '******* Icon *********' + LF
	out += 'Id.............: ' + this.id + LF;
	out += 'ResPath........: ' + this.resPath + LF;
	out += 'Source.........: ' + this.src + LF;
	out += 'Width..........: ' + this.width + LF;
	out += 'Height.........: ' + this.height + LF;
	out += 'Tooltip........: ' + this.tooltip + LF;
	out += 'Alt............: ' + this.alt + LF;
	out += 'Border.........: ' + this.border + LF;
	return out;
}
new Icon();
Icon.prototype.onclick    = Icon_onclick;       // used to assign an onclick handler
Icon.prototype.toString   = Icon_toString;      // toString
Icon.prototype.create     = Icon_create;        // creates a html img object

/*
+ ---------------------------------------------------------------------------------+
| Object....: TextButton
| Function..: Provides a TextButton object
| Arguments.: id, label, resPath, imgSrc, imgWidth, height, tooltip
| 
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 22.05.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function TextButton(id, label, width, resPath, imgSrc, imgWidth, height, tooltip) {
	this.id        = id;             // unique button id
	this.label     = label;          // the button label
	this.width     = width;          //
	this.imgWidth  = imgWidth;       // array including width of the bgimages (left, middle, right)
	this.imgSrc    = imgSrc;         // array including the image resources (left, middle, right)
	this.height    = height;         // button hight
	this.onclick   = TextButton_onclick;
	this.resPath   = resPath;
	this.tooltip   = tooltip;

	if (arguments.length <= 4) {
		// register some default images
		this.imgSrc   = ['btnBkg1_left.gif', 'btnBkg1_middle.gif', 'btnBkg1_right.gif'];
		this.imgWidth = [7, 0, 7];
		this.height   = 24;
		this.tooltip  = label;
	}
}
function TextButton_create() {
	var row   = null;
	var cell  = null;
	var img   = null;
	var span  = null;

	// ensure table width > 0
	var btnTotalWidth = TextButton.getWidth(this.imgWidth);
	var width = ((this.width - btnTotalWidth) < 0) ? this.width : (this.width - btnTotalWidth);

	// create Table
	var table = document.createElement('Table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.width = width;
	table.className = 'tbtn';
	table.onclick = this.onclick;
	
	row = table.insertRow(table.rows.length);
	row.setAttribute('valign', 'middle');
	
	// left
	cell = row.insertCell(row.cells.length);
	img = document.createElement('Img');
	img.width  = this.imgWidth[0];
	img.height = this.height;
	img.src    = this.resPath + this.imgSrc[0];
	img.border = 0;
	cell.appendChild(img);
	
	// middle
	cell = row.insertCell(row.cells.length);
	cell.width = '100%';
	cell.setAttribute('background-position', 'right');
	cell.setAttribute('word-wrap', true);
	cell.setAttribute('background', this.resPath + this.imgSrc[1]);
	
	span = document.createElement('Span');
	span.appendChild(document.createTextNode(this.label));
	span.title  = this.tooltip;
	cell.appendChild(span);
	
	// right
	cell = row.insertCell(row.cells.length);
	img = document.createElement('Img');
	img.width  = this.imgWidth[2];
	img.height = this.height;
	img.src    = this.resPath + this.imgSrc[2];
	img.border = 0;
	cell.appendChild(img);

	return table;
}
function TextButton_onclick() {
	return;
}
function TextButton_getWidth(widths) {
	var total = 0;
	
	for(var i=0; i < widths.length; i++) {
		var val = parseInt(widths[i]);
		
		if (!isNaN(val)) {
			total =+ val;
		}
	}
	return total;
}
function TextButton_toString() {
	var out = '';
	out += '******* TextButton *********' + LF
	out += 'Id.............: ' + this.id + LF;
	out += 'Label..........: ' + this.label + LF;
	out += 'BGImages.......: ' + this.imgSrc + LF;
	out += 'Tooltip........: ' + this.tooltip + LF;
	out += 'Alt............: ' + this.alt + LF;
	out += 'Width..........: ' + this.width + LF;
	out += 'Height.........: ' + this.height + LF;
	
	return out;
}
new TextButton();
TextButton.prototype.create     = TextButton_create;
TextButton.prototype.onclick    = TextButton_onclick;
TextButton.prototype.toString   = TextButton_toString;
TextButton.getWidth             = TextButton_getWidth;


/*
+ ---------------------------------------------------------------------------------+
| Object...: SwapSelect
| Function.: 
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 29.12.2004  G.Schulz          Inital version
| 03.03.2005  G.Schulz          onChange Handler Support added
+ ---------------------------------------------------------------------------------+
*/
function SwapSelect(id, runat, hiddenName, disabled) {
	var a = arguments;
	
	this.id            = id;                                               // the id for the control
	this.runAt         = (a.length >= 2) ? runat : RunAt.SERVER;           // indicates if the control should work with or without roundtrips
	this.hiddenName    = (a.length >= 3) ? hiddenName : id;                // Name of the hidden field
	this.orientation   = Orientation.VERTICAL;                             // select elementes are displayed vertical or horizontal
	this.keepSortOrder = true;                                             // move item at the end (flase) or use the original position
	this.keepSelection = true;                                             // keep selection for moved options 
	this.disabled      = (a.length >= 4) ? disabled : false;
	this.PREFIX        = 'swc_';
	this.BTNPREFIX     = 'btnswc_';
	this.ROOT          = 'swc_span_'+ id;
	this.clientHandler = new Array();
	
	if (null != id) {
		this.optionsSrc = document.getElementById('swcl_' + id);
		this.optionsSel = document.getElementById('swcr_' + id);
		this.optionsSrc.swc_type = 'L';
		this.optionsSel.swc_type = 'R';
		
		if (this.runAt == RunAt.CLIENT && !this.disabled) {
			// assign the client handlers		
			this.setupEventHandler();
		}
	}
}
function SwapSelect_setRunAt(runat) {
	this.runAt = runat;
}
function SwapSelect_getRunAt() {
	return this.runAt;
}
function SwapSelect_setOrientation(orientation) {
	this.orientation = orientation;
}
function SwapSelect_getOrientation() {
	return this.orientation;
}
function SwapSelect_setDisabled(disabled) {
	// set attribute
	this.disabled = disabled;

	if (this.optionsSrc == null || this.optionsSel == null) {
		return;
	}

	if (this.disabled) {
		// disable control
		this.optionsSrc.disabled = true;
		this.optionsSel.disabled = true;
		this.removeEventHandler();
	} else {
		// enable control
		this.optionsSrc.disabled = false;
		this.optionsSel.disabled = false;
		this.setupEventHandler();
	}
}
function SwapSelect_isDisabled() {
	return this.disabled;
}
function SwapSelect_addClientHandler(type, script) {
	this.clientHandler[type] = script;
}
function SwapSelect_getClientHandler(type) {
	return this.clientHandler[type];
}
function SwapSelect_setupEventHandler() {
	// get the buttons
	var objAddAll    = document.getElementById(this.BTNPREFIX + this.id + '_AddAll');
	var objAdd       = document.getElementById(this.BTNPREFIX + this.id + '_Add');
	var objRemove    = document.getElementById(this.BTNPREFIX + this.id + '_Remove');
	var objRemoveAll = document.getElementById(this.BTNPREFIX + this.id + '_RemoveAll');

	var ctrl = this;

	objAddAll.onclick = function() {
							var changed = ctrl.doMove(ctrl.optionsSrc, ctrl.optionsSel, true);
							ctrl.updateHiddenFields(ctrl.optionsSel);
							
							if (changed) {
								var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
								return CCUtility.executeUserScript(userfct);
							} else {
								return false;
							}
						}
		
	objAdd.onclick    = function() {
							var changed = ctrl.doMove(ctrl.optionsSrc, ctrl.optionsSel, false);
							ctrl.updateHiddenFields(ctrl.optionsSel);

							if (changed) {
								var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
								return CCUtility.executeUserScript(userfct);
							} else {
								return false;
							}
						}
	
	objRemove.onclick   = function() {
							var changed = ctrl.doMove(ctrl.optionsSel, ctrl.optionsSrc, false);
							ctrl.updateHiddenFields(ctrl.optionsSel);

							if (changed) {
								var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
								return CCUtility.executeUserScript(userfct);
							} else {
								return false;
							}
						}
	
	objRemoveAll.onclick = function() {
							var changed = ctrl.doMove(ctrl.optionsSel, ctrl.optionsSrc, true);
							ctrl.updateHiddenFields(ctrl.optionsSel);

							if (changed) {
								var userfct = ctrl.getClientHandler(ClientHandler.ONCHANGE);
								return CCUtility.executeUserScript(userfct);
							} else {
								return false;
							}
						}
							
	this.optionsSrc.onclick = function() {
							ctrl.unselectOptionList(ctrl.optionsSel);
						}

	this.optionsSel.onclick = function() {	
							ctrl.unselectOptionList(ctrl.optionsSrc);
						}
	
}
function SwapSelect_removeEventHandler() {
	var objAddAll    = document.getElementById(this.BTNPREFIX + this.id + '_AddAll');
	var objAdd       = document.getElementById(this.BTNPREFIX + this.id + '_Add');
	var objRemove    = document.getElementById(this.BTNPREFIX + this.id + '_Remove');
	var objRemoveAll = document.getElementById(this.BTNPREFIX + this.id + '_RemoveAll');

	objAddAll.onclick = function(){return false;};
	objAdd.onclick    = function(){return false;};
	objRemove.onclick = function(){return false;};
	objRemoveAll.onclick = function(){return false;};

}
function SwapSelect_unselectOptionList(optionList) {

	// unselect option elements
	for(var i=0; i < optionList.options.length; i++) {
		optionList.options[i].selected = false;
	}
}
function SwapSelect_doMove(source, target, moveall) {
	// flag indicates an option has been moved
	var flagChanged = false;
	
	if (null == source || null == target) {
		return flagChanged;
	}

	if (this.keepSelection) {
		this.unselectOptionList(target);
	}

	if(moveall) {
		// copy all options
		var opt = source.options;

		for(var i=0; i < opt.length; i++) {
			var newOption = new Option(opt[i].text, opt[i].value, false, false);
			newOption.swc_type  = opt[i].getAttribute('swc_type');
			newOption.selected  = this.keepSelection;
			
			target.options[target.options.length] = newOption;
			
			flagChanged = true;
		}
		
		// delete all options from the source element
		source.options.length=0;
		
		if (this.keepSortOrder) {
			this.sort(target);
		}
		
	} else {
		// copy only the selected options
		while (source.selectedIndex != -1) {
			var opt = source.options[source.selectedIndex];
			
			if (opt.selected) {
				// delete the selected option from the source element
				source.options[source.selectedIndex] = null;
				
				// insert the selected option into the target element
				var newOption =  new Option(opt.text, opt.value, false, false);
				newOption.swc_index  = opt.getAttribute('swc_index');
				newOption.selected  = this.keepSelection;
				target.options[target.options.length] = newOption;
				
				if (this.keepSortOrder) {
					this.sort(target);
				}
				
				flagChanged = true;
			}
		}
	}
	
	return flagChanged;
}
function SwapSelect_sort(optionList) {

	if (null == optionList) {
		return;
	}

	var arr  = new Array();
	
	// add options to array
	for(var i=0; i < optionList.options.length; i++) {
			arr[arr.length] = optionList.options[i];
	}
	
	// sort the array
	arr.sort(SwapSelect_typeOrder);
	
	// reset select element
	optionList.options.length=0;
	
	// add sorted options
	for(var i=0; i < arr.length; i++) {
		var newOption = new Option(arr[i].text, arr[i].value, false, false);
		newOption.swc_index = arr[i].getAttribute('swc_index');
		newOption.selected  = arr[i].selected;
		optionList.options[optionList.options.length] = newOption;
	}
	
}
function SwapSelect_typeOrder(a, b) {
	var va = parseInt(a.getAttribute('swc_index'));
	var vb = parseInt(b.getAttribute('swc_index'));
	
	if (va > vb) {
		return 1;
	} else if (va < vb) {
		return -1;
	} else {
		return 0;
	}
}
function SwapSelect_getOptionList(side) {
	if (side.toUpperCase() == 'L') {
		return this.optionsSrc;
	} else if (side.toUpperCase() == 'R') {
		return this.optionsSel;
	} else {
		return null;
	}
}
function SwapSelect_setKeepSortOrder(keepSortOrder) {
	this.keepSortOrder = keepSortOrder;
}
function SwapSelect_isKeepSortOrder() {
	return this.keepSortOrder;
}
function SwapSelect_setKeepSelection(keepSelection) {
	this.keepSelection = keepSelection;
}
function SwapSelect_isKeepSelection() {
	return this.keepSelection;
}
function SwapSelect_updateHiddenFields(optionList) {
	// This function creates the required hidden fields
	// which are used to synchronize the selected items
	// after a server roundtrip.

	var span = document.getElementById(this.ROOT);

	// remove existing hidden fields
	var inputfields = span.getElementsByTagName('input');
	var length = inputfields.length;
	
	for (var i = length - 1; i >= 0; i--) {
		var node = inputfields[i];
		
		if (node.type == 'hidden' && node.name == this.hiddenName) {
			//alert(i + ") " + inputfields[i].value);
			var parent = node.parentNode;
			parent.removeChild(node);
		}
	}

	// add the new hidden fields for the selected options
	if (optionList.options.length != 0) {
		for (var i=0; i < optionList.options.length; i++) {
			var option = optionList.options[i];
			var hidden = this.createHidden(option.value);
			span.appendChild(hidden);
		}
	} else {
		// we must create a dummy hidden field otherwise
		// the list will not be reseted because the parameter
		// is missing in the http request
		var hidden = this.createHidden('');
		span.appendChild(hidden);
	}
}
function SwapSelect_createHidden(value) {
	var input   = document.createElement('input');
	input.type  = 'hidden';
	input.name  = this.hiddenName;
	input.value = value;
	return input;
}
function SwapSelect_toString() {
	var out = '';
	out += '******* SwapSelect *********' + LF
	out += 'Id..............: ' + this.id + LF;
	out += 'Orientation.....: ' + this.orientation + LF;
	out += 'Keep SortOrder..: ' + this.keepSortOrder + LF;
	out += 'Keep Selection..: ' + this.keepSelection + LF;
	out += 'Disabled........: ' + this.disabled + LF;
	out += 'RunAt...........: ' + this.runAt + LF;
	return out;
}
new SwapSelect();
SwapSelect.prototype.setRunAt           = SwapSelect_setRunAt;
SwapSelect.prototype.getRunAt           = SwapSelect_getRunAt;
SwapSelect.prototype.setOrientation     = SwapSelect_setOrientation;
SwapSelect.prototype.getOrientation     = SwapSelect_getOrientation;
SwapSelect.prototype.setDisabled        = SwapSelect_setDisabled;
SwapSelect.prototype.isDisabled         = SwapSelect_isDisabled;
SwapSelect.prototype.setupEventHandler  = SwapSelect_setupEventHandler;
SwapSelect.prototype.removeEventHandler = SwapSelect_removeEventHandler;
SwapSelect.prototype.unselectOptionList = SwapSelect_unselectOptionList;
SwapSelect.prototype.doMove             = SwapSelect_doMove;
SwapSelect.prototype.sort               = SwapSelect_sort;
SwapSelect.prototype.getOptionList      = SwapSelect_getOptionList;
SwapSelect.prototype.setKeepSortOrder   = SwapSelect_setKeepSortOrder;
SwapSelect.prototype.isKeepSortOrder    = SwapSelect_isKeepSortOrder;
SwapSelect.prototype.setKeepSelection   = SwapSelect_setKeepSelection;
SwapSelect.prototype.isKeepSelection    = SwapSelect_isKeepSelection;
SwapSelect.prototype.updateHiddenFields = SwapSelect_updateHiddenFields;
SwapSelect.prototype.createHidden       = SwapSelect_createHidden;
SwapSelect.prototype.addClientHandler   = SwapSelect_addClientHandler;
SwapSelect.prototype.getClientHandler   = SwapSelect_getClientHandler;
SwapSelect.prototype.toString           = SwapSelect_toString;


/*
+ ---------------------------------------------------------------------------------+
| Object...: Button
| Function.: used to manage text buttons
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 14.05.2005  G.Schulz          Inital version
+ ---------------------------------------------------------------------------------+
*/
function Button(id) {
	this.id   = id;
	this.type = 'textbutton';
	this.onclickHandler = null;
}
function Button_doDisableImage(node, attribute) {
	var att = '';

	if (arguments.length == 1) {
		att = 'src';
	} else {
		att = attribute;
	}

	var tokens = node.getAttribute(att).split('.');
	var imgtype = tokens[tokens.length - 1];

	if (null != imgtype) {
		if (node.getAttribute(att).lastIndexOf('1.' + imgtype) != -1) {
			node.setAttribute(att, node.getAttribute(att).substr(0, node.getAttribute(att).indexOf('.' + imgtype)-1) + '2.' + imgtype);
		}
	}
}
function Button_doEnableImage(node, attribute) {
	var att = '';

	if (arguments.length == 1) {
		att = 'src';
	} else {
		att = attribute;
	}

	var tokens = node.getAttribute(att).split('.');
	var imgtype = tokens[tokens.length - 1];

	if (null != imgtype) {
		if (node.getAttribute(att).lastIndexOf('2.' + imgtype) != -1) {
			node.setAttribute(att, node.getAttribute(att).substr(0, node.getAttribute(att).indexOf('.' + imgtype)-1) + '1.' + imgtype);
		}
	}
}
function Button_getButton() {
	var elements = document.getElementsByTagName('div');

	for (var i=0; i < elements.length; i++) {
		if (elements[i].id == this.id) {
			return elements[i];
		}
	}
}
function Button_disable() {

	var div = this.getButton();

	if (null == div) {
		return;
	}

	this.onclickHandler = div.onclick;
	div.onclick = '';


	var td = div.getElementsByTagName('td');

	// left image
	this.doDisableImage(td[0].firstChild);
	
	// middle image
	this.doDisableImage(td[1], 'background');
				
	// right image
	this.doDisableImage(td[2].firstChild);
}
function Button_enable() {
	var div = this.getButton();

	if (null == div) {
		return;
	}

	if (null != this.onclickHandler) {
		div.onclick = this.onclickHandler;
	}
	
	var td = div.getElementsByTagName('td');

	// left image
	this.doEnableImage(td[0].firstChild);
	
	// middle image
	this.doEnableImage(td[1], 'background');
				
	// right image
	this.doEnableImage(td[2].firstChild);

}
new Button();
Button.prototype.doDisableImage = Button_doDisableImage;
Button.prototype.doEnableImage  = Button_doEnableImage;
Button.prototype.getButton      = Button_getButton;
Button.prototype.disable        = Button_disable;
Button.prototype.enable         = Button_enable;


/*
+ ---------------------------------------------------------------------------------+
| Object...: StringHelp
| Function.: 
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 08.01.2005  G.Schulz          Inital version
|
+ ---------------------------------------------------------------------------------+
*/

function StringHelp() {
}
function StringHelp_truncate(str, maxlength) {
	if ((maxlength == -1) || (str == null) || (str.length <= maxlength)) {
		return str;
	} else {
		return str.substring(0, maxlength) + '...';
	}
}
new StringHelp();
StringHelp.truncate = StringHelp_truncate;


/*
+ ---------------------------------------------------------------------------------+
| Object...: RecurrenceControl
| Function.: JavaScript Object to manage a RecurrenceControl
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 26.06.2005  G.Schulz          Inital version
|
+ ---------------------------------------------------------------------------------+
*/
function RecurrenceControl(id, prefix) {
	this.id         = id;
	this.prefix     = prefix;
	
	if (null != id) {
		this.divNone    = document.getElementById(id + 'none');
		this.divDaily   = document.getElementById(id + 'daily');
		this.divWeekly  = document.getElementById(id + 'weekly')
		this.divMontly  = document.getElementById(id + 'monthly')
		this.divYearly  = document.getElementById(id + 'yearly');
		
		// setup handlers
		this.setupHandler();
	}
}
function RecurrenceControl_setupHandler() {
	var ctrl = this;
	var type = document.getElementsByName(this.prefix + '.type');

	for (var i=0; i < type.length; i++) {
		type[i].onclick = function() {ctrl.showRecurringType(this.value);}
	}

	// ---------------------------------
	// Handler Daily
	// ---------------------------------
	var dayDayMask  = document.getElementsByName(this.prefix + '.dayDayMask')[0];
	var dayInterval = document.getElementsByName(this.prefix + '.dayInterval')[0];
	
	dayInterval.onclick = function() { dayDayMask.checked = true;}
	dayInterval.onchange = function() { dayDayMask.checked = true;}
	
	// ---------------------------------
	// Handler Daily
	// ---------------------------------
	var monthSubtype = document.getElementsByName(this.prefix + '.monthSubtype');
	var monthDayOfMonth = document.getElementsByName(this.prefix + '.monthDayOfMonth')[0];
	var monthInterval = document.getElementsByName(this.prefix + '.monthInterval')[0];
	var monthNthInterval = document.getElementsByName(this.prefix + '.monthNthInterval')[0];
	var monthNthInstance = document.getElementsByName(this.prefix + '.monthNthInstance')[0];
	var monthNthDayMask = document.getElementsByName(this.prefix + '.monthNthDayMask')[0];
	
	monthDayOfMonth.onclick = function() {monthSubtype[0].checked = true;}
	monthDayOfMonth.onchange = function() {monthSubtype[0].checked = true;}
	monthInterval.onclick = function() {monthSubtype[0].checked = true;}
	monthInterval.onchange = function() {monthSubtype[0].checked = true;}
	monthNthInterval.onclick = function() {monthSubtype[1].checked = true;}
	monthNthInterval.onchange = function() {monthSubtype[1].checked = true;}
	monthNthInstance.onchange = function() {monthSubtype[1].checked = true;}
	monthNthDayMask.onchange = function() {monthSubtype[1].checked = true;}
	
	// ---------------------------------
	// Handler Yearly
	// ---------------------------------
	var yearSubtype = document.getElementsByName(this.prefix + '.yearSubtype');
	var yearDayOfMonth = document.getElementsByName(this.prefix + '.yearDayOfMonth')[0];
	var yearMonthOfYear = document.getElementsByName(this.prefix + '.yearMonthOfYear')[0];
	var yearNthInstance = document.getElementsByName(this.prefix + '.yearNthInstance')[0];
	var yearNthDayMask = document.getElementsByName(this.prefix + '.yearNthDayMask')[0];
	var yearNthMonthOfYear = document.getElementsByName(this.prefix + '.yearNthMonthOfYear')[0];
	
	yearDayOfMonth.onclick = function() {yearSubtype[0].checked = true;}
	yearDayOfMonth.onchange = function() {yearSubtype[0].checked = true;}
	yearMonthOfYear.onchange = function() {yearSubtype[0].checked = true;}
	yearNthInstance.onchange = function() {yearSubtype[1].checked = true;}
	yearNthDayMask.onchange = function() {yearSubtype[1].checked = true;}
	yearNthMonthOfYear.onchange = function() {yearSubtype[1].checked = true;}
}
function RecurrenceControl_showRecurringType(type) {
	// initialize
	this.reset();
	
	// switch
	this.divNone.style.display = 'none';
	this.divDaily.style.display = 'none';
	this.divWeekly.style.display = 'none';
	this.divMontly.style.display = 'none';
	this.divYearly.style.display = 'none';
	
	var div = document.getElementById(this.id + type);
	div.style.display = 'block';
}
function RecurrenceControl_reset() {
	// ---------------------------------
	// reset Daily
	// ---------------------------------
	document.getElementsByName(this.prefix + '.dayDayMask')[0].checked = true;
	document.getElementsByName(this.prefix + '.dayInterval')[0].value = 1;

	// ---------------------------------
	// reset Weekly
	// ---------------------------------
	document.getElementsByName(this.prefix + '.weekInterval')[0].value = 1;
	var chekboxes = document.getElementsByTagName('input');
	
	for (var i=0; i < chekboxes.length; i++) {
		if (chekboxes[i].type == 'checkbox') {
			chekboxes[i].checked = false;
		}
	}
	
	// ---------------------------------
	// reset Monthly
	// ---------------------------------
	document.getElementsByName(this.prefix + '.monthSubtype')[0].checked = true;
	document.getElementsByName(this.prefix + '.monthDayOfMonth')[0].value = 0;
	document.getElementsByName(this.prefix + '.monthInterval')[0].value = 1;
	document.getElementsByName(this.prefix + '.monthNthInterval')[0].value = 1;
	document.getElementsByName(this.prefix + '.monthNthInstance')[0].selectedIndex = 0;
	document.getElementsByName(this.prefix + '.monthNthDayMask')[0].selectedIndex = 0;
	
	// ---------------------------------
	// reset Yearly
	// ---------------------------------
	document.getElementsByName(this.prefix + '.yearSubtype')[0].checked = true;
	document.getElementsByName(this.prefix + '.yearDayOfMonth')[0].value = 1;
	document.getElementsByName(this.prefix + '.yearMonthOfYear')[0].selectedIndex = 0;
	document.getElementsByName(this.prefix + '.yearNthInstance')[0].selectedIndex = 0;
	document.getElementsByName(this.prefix + '.yearNthDayMask')[0].selectedIndex = 0;
	document.getElementsByName(this.prefix + '.yearNthMonthOfYear')[0].selectedIndex = 0;
}

new RecurrenceControl();
RecurrenceControl.prototype.reset             = RecurrenceControl_reset;
RecurrenceControl.prototype.showRecurringType = RecurrenceControl_showRecurringType;
RecurrenceControl.prototype.setupHandler      = RecurrenceControl_setupHandler;


/*
+ ---------------------------------------------------------------------------------+
| Array to manage all popup-windows on a page

| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 24.08.2005  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/

var popups = new Array();

/*
+ ---------------------------------------------------------------------------------+
| Object....: PopUp
| Function..: Provides a popup window
| Arguments.: id, referer, width, height
| 
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 24.08.2005  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function PopUp(id, referer, width, height) {
	this.id              = id;                  // Id for the popup window
	this.referer         = referer;             // opener
	this.width           = width;               // the width for the popup window
	this.height          = height;              // the height for the popup window
	this.popupTimeCount  = 500;                 // Time when the popup shuld be closed
	this.popupTimerID    = null;                // unique timer Id
	this.iframe          = null;                // IFrame used to create this popup
	this.div             = null;                // Div used to create this popup
	this.oldHandler      = null;                // old javascript handler 
	this.PREFIX          = 'popup_';

	if (null != id) {
		this.init();
		this.setupHandler();
		
		popups[id] = this;
	}
}
function PopUp_open() {

/*	
	// create div
	var x = 0;
	var y = 0;

	if (ie) {
		x = event.x;
		y = event.y;
	} else {
		x = this.referer.x;
		y = this.referer.y + this.referer.offsetHeight + 1;
	}
*/

	var x = getClientX(event) + document.body.scrollLeft;
	var y = getClientY(event) + document.body.scrollTop;

	// adjust x position of popup window
	var maxX		= document.body.clientWidth + document.body.scrollLeft;
	var popupWidth	= (null != this.width) ? this.width : 190;

	if ((x + popupWidth) > maxX) {
		x = x + (maxX - x - popupWidth);
	}

	if (x < 0) {
		x = 0;
	}

	// adjust y position of popup window
	var maxY		= document.body.clientHeight + document.body.scrollTop;
	var popupHeight	= (null != this.height) ? this.height : 190 + 20;

	if ((y + popupHeight) > maxY) {
		y = y + (maxY - y - popupHeight);
	}

	if (y < 0) {
		y = 0;
	}


	this.div.style.left     = x;
	this.div.style.top      = y;
	this.div.style.zIndex   = 99;
	this.div.style.width    = popupWidth;
	this.div.style.height   = popupHeight;
	this.div.innerHTML      = '';
	
	// create iframe
	this.iframe.style.left   = this.div.style.left;
	this.iframe.style.top    = this.div.style.top;
	this.iframe.style.width  = this.div.style.width;
	this.iframe.style.height = this.div.style.height;
	this.iframe.style.zIndex = this.div.style.zIndex - 1;

	// show
	this.show(true);
}
function PopUp_close() {
	this.show(false);
	
	var body = document.getElementsByTagName('body')[0];
	
	// assign old handler
	if ( null != this.referer && null != this.referer.onclick && null != this.oldHandler) {
		this.referer.onclick = this.oldHandler;
	}
	
	// remove popup
	body.removeChild(this.div);
	body.removeChild(this.iframe);
}
function PopUp_show(flag) {
	var style = (flag) ? 'block' : 'none';

	this.div.style.display    = style;
	this.iframe.style.display = style;
}
function PopUp_resize() {
	this.iframe.style.width  = this.div.style.width;
	this.iframe.style.height = this.div.style.height;
}
function PopUp_init() {
	this.div = document.createElement('DIV');
	this.div.id = this.PREFIX + 'div_' + this.id;
	this.div.style.position = 'absolute';
	this.div.style.left = 0;
	this.div.style.top = 0;
	this.div.style.backgroundColor = '#EFEFEF';
	this.div.style.border = '2px outset gray';
	this.div.style.display = 'none';

	this.iframe = document.createElement('IFRAME');
	this.iframe.id = this.PREFIX + 'iframe_' + this.id;
	this.iframe.style.position = 'absolute';
	this.iframe.style.left = 0;
	this.iframe.style.top = 0;
	this.iframe.style.display = 'none';
	
	var body = document.getElementsByTagName('body')[0];
	body.appendChild(this.div);
	body.appendChild(this.iframe);
}
function PopUp_setupHandler() {
	this.oldHandler = this.referer.onclick;
	var _ctrl = this;

	this.referer.onclick = function(event) {
					if (_ctrl.isVisible()) {
						_ctrl.close();
					} else {
						_ctrl.open();
					}
					return false;
				} 
	
	this.div.onmouseover = function(event) {
					_ctrl.stopPopupTimer();
				}

	this.div.onmouseout  = function(event) {
					// start timer if mouse is moved
					// out of the popupwindow
					if (!_ctrl.isChildNode(event)) {
						_ctrl.startPopupTimer();
					}
				}
}
function PopUp_isVisible() {
	return (this.div.style.display == 'block');
}
function PopUp_startPopupTimer() {
	var callback = "PopUp_timerclose('" + this.id + "')";
	this.popupTimerID = setTimeout(callback, this.popupTimeCount);
}
function PopUp_stopPopupTimer() {
	if (this.popupTimerID != null) {
		clearTimeout(this.popupTimerID);
		this.popupTimerID = null;
	}
}
function PopUp_timerclose(id) {
	var popup = popups[id];
	
	if (null != popup) {
		popup.close();
		popups[id] = null;
	}
}
function PopUp_resetPopupTimer() {
	this.stopPopupTimer();
	this.startPopupTimer();
}
function PopUp_isChildNode(event) {
	var wevent = null;

	if (ie || opera) {
		wevent = window.event;
	}
	else if(ns || safari || firefox || mozilla) {
		wevent = event;
	}
	
	if (null == wevent) {
		return false;
	}
	
	var x = wevent.x;
	var y = wevent.y


	if (x > this.div.offsetLeft && x <  this.div.offsetLeft +  this.div.offsetWidth) {
		true;
	}
	
	if (y > this.div.offsetTop && x <  this.div.offsetTop +  this.div.offsetHeight) {
		true;
	}

	if (x < 0 || y <0) {
		return true;
	}

	return false;
}
function PopUp_getDiv() {
	return this.div;
}
new PopUp();
PopUp.prototype.open            = PopUp_open;
PopUp.prototype.close           = PopUp_close;
PopUp.prototype.show            = PopUp_show;
PopUp.prototype.resize          = PopUp_resize;
PopUp.prototype.init            = PopUp_init;
PopUp.prototype.isVisible       = PopUp_isVisible;
PopUp.prototype.setupHandler    = PopUp_setupHandler;
PopUp.prototype.startPopupTimer = PopUp_startPopupTimer;
PopUp.prototype.stopPopupTimer  = PopUp_stopPopupTimer;
PopUp.prototype.resetPopupTimer = PopUp_resetPopupTimer;
PopUp.prototype.isChildNode     = PopUp_isChildNode;
//PopUp.prototype.timerclose      = PopUp_timerclose;

