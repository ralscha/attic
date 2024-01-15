/***********************************************************************
 * (C) 2004 SCC Informationssysteme GmbH                               *
 *     www.scc-gmbh.com / www.common-controls.com                      *
 *                                                                     *
 * Note: This file belongs to the Common Controls Presentation         *
 *       Framework. Permission is given to use this script only        *
 *       together with the Common Controls Presentation Framework      *
 ***********************************************************************/

/*
+ ---------------------------------------------------------------------------------+
| Calendar Message Resources
| 
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 22.05.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function CalendarResource() {
	// the local
	this.locale       = 'EN';

	// ----- an optional Localized Calendar Title
	this.TITLE_DE         = 'Kalender';
	this.TITLE_EN         = 'Calendar';
	this.TITLE_FR         = 'Calendrier';
	
	// ----- Localized Weekdays
	this.DOWS_DE          = ['Sonntag','Montag','Dienstag','Mittwoch','Donnerstag','Freitag','Samstag'];
	this.DOWS_EN          = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
	this.DOWS_FR          = ['Dimanche', 'Lundi','Mardi', 'Mercredi','Jeudi','Vendredi','Samedi'];

	// ----- Localized Month names
	this.MONTHS_DE        = ['Januar','Februar','März','April','Mai','Juni','Juli','August','September','Oktober','November', 'Dezember'];
	this.MONTHS_EN        = ['January','February','March','April','May','June','July','August','September','October','November', 'December'];
	this.MONTHS_FR        = ['Janvier','Février','Mars','Avril','Mai','Juin','Juillet','Août','Septembre','Octobre','Novembre', 'Décembre'];

	// ----- Localized AM/PM Strings
	this.AMPM_DE          = ['AM', 'PM'];
	this.AMPM_EN          = ['AM', 'PM'];
	this.AMPM_FR          = ['AM', 'PM'];

	// ----- Localized label of the 'today' button which selects the current date
	this.BUTTON_TODAY_DE  = 'Heute';
	this.BUTTON_TODAY_EN  = 'Today';
	this.BUTTON_TODAY_FR  = 'Aujourd\'hui';
	// ----- Localized Button Labels
	this.BUTTON_OK_DE     = 'Ok';
	this.BUTTON_OK_EN     = 'OK';
	this.BUTTON_OK_FR     = 'Choisir';
	this.BUTTON_CANCEL_DE = 'Abbrechen';
	this.BUTTON_CANCEL_EN = 'Cancel';
	this.BUTTON_CANCEL_FR = 'Abandon';
}
function CalendarResource_setWeekdays(locale, arr) {
	this['DOWS_' + locale.toUpperCase()] = arr;
}
function CalendarResource_getWeekdays(locale) {
	locale = (null == locale) ? this.locale : locale;
	return this['DOWS_' + locale];
}
function CalendarResource_setMonths(locale, arr) {
	this['MONTHS_' + locale.toUpperCase()] = arr;
}
function CalendarResource_getMonths(locale) {
	locale = (null == locale) ? this.locale : locale;
	return this['MONTHS_' + locale];
}
function CalendarResource_setWindowTitle(locale, title) {
	this['TITLE_' + locale.toUpperCase()] = title;
}
function CalendarResource_getWindowTitle(locale) {
	locale = (null == locale) ? this.locale : locale;
	return this['TITLE_' + locale];
}
function CalendarResource_setButtonLabel(locale, type, label) {
	locale = (null == locale) ? this.locale : locale.toUpperCase();
	var btn = 'BUTTON_' + type.toUpperCase() + '_' + locale;
	return this[btn] = label;
}
function CalendarResource_getButtonLabel(locale, type) {
	locale = (null == locale) ? this.locale : locale.toUpperCase();
	var btn = 'BUTTON_' + type.toUpperCase() + '_' + locale;
	return this[btn];
}
function CalendarResource_setAmPmStrings(locale, arr) {
	this['AMPM_' + locale.toUpperCase()] = arr;
}
function CalendarResource_getAmPmStrings(locale) {
	locale = (null == locale) ? this.locale : locale.toUpperCase();
	return this['AMPM_' + locale];
}
function CalendarResource_setLocale(locale) {
	this.locale = locale.toUpperCase();
}
function CalendarResource_getLocale() {
	return this.locale;
}
function CalendarResource_toString(locale) {
	var out = '';
	
	locale = (null == locale) ? this.locale : locale.toUpperCase();
	
	out += '******** CalendarResources ********' + LF;
	out += 'Locale...........: ' + locale + LF;
	out += 'Weekdays.........: ' + this.getWeekdays(locale) + LF;
	out += 'Months...........: ' + this.getMonths(locale) + LF;
	out += 'WindowTitle......: ' + this.getWindowTitle(locale) + LF;
	out += 'BtnLabel OK......: ' + this.getButtonLabel(locale, 'OK') + LF;
	out += 'BtnLabel Cancel..: ' + this.getButtonLabel(locale, 'Cancel') + LF;
	out += 'BtnLabel Today...: ' + this.getButtonLabel(locale, 'CDT') + LF;
	out += 'AM/PM Strings....: ' + this.getAmPmStrings(locale) + LF;
	return out;
}
var DTPRes = new CalendarResource();
CalendarResource.prototype.setWeekdays    = CalendarResource_setWeekdays;
CalendarResource.prototype.getWeekdays    = CalendarResource_getWeekdays;
CalendarResource.prototype.setMonths      = CalendarResource_setMonths;
CalendarResource.prototype.getMonths      = CalendarResource_getMonths;
CalendarResource.prototype.setWindowTitle = CalendarResource_setWindowTitle;
CalendarResource.prototype.getWindowTitle = CalendarResource_getWindowTitle;
CalendarResource.prototype.setButtonLabel = CalendarResource_setButtonLabel;
CalendarResource.prototype.getButtonLabel = CalendarResource_getButtonLabel;
CalendarResource.prototype.setAmPmStrings = CalendarResource_setAmPmStrings;
CalendarResource.prototype.getAmPmStrings = CalendarResource_getAmPmStrings;
CalendarResource.prototype.setLocale      = CalendarResource_setLocale;
CalendarResource.prototype.getLocale      = CalendarResource_getLocale;
CalendarResource.prototype.toString       = CalendarResource_toString;

