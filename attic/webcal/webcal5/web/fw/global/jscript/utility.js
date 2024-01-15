/*
 * $Header:  $
 * $Revision:  $
 * $Date: $
 *
 * ====================================================================
 *
 * Copyright (c) 2000 - 2004 SCC Informationssysteme GmbH. All rights
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
// Helper for the Common Controls Framework
/////////////////////////////////////////////////////////////


/*
+ ---------------------------------------------------------------------------------+
| Purpose..:  Helper for the Common Controls Framework
|             
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 23.12.2002  G.Schulz (SCC)    Initial version
| 24.10.2004  G.Schulz (SCC)    JScript support for CCUtility_crtCtrla function
|                               New function CCUtility_submitEnclosingForm
| 06.07.2005  G.Schulz (SCC)    form.onsubmit(); added within method submitEnclosingForm()
+ ---------------------------------------------------------------------------------+
*/

function CCUtility() {}
function CCUtility_getEnclosingForm(node) {

	if (null != node && node.nodeName == 'FORM') {
		return node;
	}

	// search the form wich embbeds the Element
	var parent = node.parentNode;
	
	if (null == parent) return null;

	if (parent.nodeName == 'FORM') {
		return parent;
	} else {
		return arguments.callee(parent);
	}
}
function CCUtility_submitEnclosingForm(node) {

	var form = this.getEnclosingForm(node);
	
	if (null == form) {
		// form not specified -> do nothing

		return false;
	} else {
		var rtc = true;
		
		if (null != form.onsubmit) {
			rtc = form.onsubmit();
		}

		if (rtc) form.submit();
	}
}
function CCUtility_resetEnclosingForm(node) {

	var form = this.getEnclosingForm(node);
	
	if (null != form) {
		form.reset();
	}

	// do not submit the form
	return false;
}
function CCUtility_setActiveButton(name) {
	
	// clear last 
	var input = document.getElementsByTagName('Input');
	
	for (var i=0; i < input.length; i++) {
		var btnName = input[i];
		
		if (null != btnName.name && btnName.name.indexOf('btn') > 0 && btnName.name.lastIndexOf('Hidden') > 0) {
			input[i].value = '';
		}
	}

	document.getElementById(name).value='clicked'
}

function CCUtility_createHidden(fldName, fldValue) {
	var input = null;

	// check if the hidden field already exist
	input = document.getElementById(fldName);

	if (null != input) {
		input.value=fldValue;
		return input;
	} else {
		var input=document.createElement('INPUT');
		input.type='hidden';
		input.id=fldName;
		input.name=fldName;
		input.value=fldValue;
		return input;
	}
}

/**
 * node......: Current node which generates the event
 * param.....: action used by the control to identify the event
 * formId....: optional id of the form, if null the enclosing form is searched by the script
 * userscript: optional jscript which must return true before the form is sumbitted
 */
function CCUtility_crtCtrla(node, param, formId, userscript) {
	var form = null;

	// if a user script was defined execute the script
	// To replace by CCUtility_executeUserScript
	if (null != userscript) {
		var rtc = true;

		var userfct = new Function(userscript);
		var obj = userfct();

		if (typeof obj == 'boolean') {
			rtc = new Boolean(obj);
		}

		if (!rtc.valueOf()) {	
			return false;
		}
	}

	// first check if the formId was specified
	if (null != formId && '' != formId) {
		form = document.getElementById(formId);
	} else {
		// try to find the enclosing form
		form = this.getEnclosingForm(node);
	}

	if (null != form) {
		// append the hidden field which contains the 
		// name of the control, action and additional parameter 
		if (param.length != 0 && param.substring(0,4) != 'null') {
			form.appendChild(this.createHidden('ctrla', param));
		}
		form.submit();
	} else {
		// not found. Do nothing
	}
}
function CCUtility_executeUserScript(userscript) {

	if (null != userscript && '' != userscript) {
		var userfct = new Function(userscript);
		var obj = userfct();

		if (typeof obj == 'boolean') {
			var rtc = new Boolean(obj);
			return rtc.valueOf();
		}
	}

	return false;
}
function CCUtility_showFormElement(obj, show) {

	var row = this.getObject(obj);

	if (row == null) {
		return;
	}
 
	var display = (arguments.length >= 2) ? show : '';
	
	row.style.display = display;
	
	var nextRow = row.nextSibling;

	if ((nextRow != null) && (nextRow.getAttribute('rowtype') == 'separator')) {
		nextRow.style.display = display;
	}
}
function CCUtility_hideFormElement(obj) {
	this.showFormElement(obj, 'none');
}
function CCUtility_showSingleFormElement(arr, id) {

	if (null == arr) {
		return;
	}

	for (var i=0; i < arr.length; i++) {
		var row = this.getObject(arr[i]);
		
		if (row.id != id) {
			this.hideFormElement(row);
		} else {
			this.showFormElement(row);
		}
	}
}
function CCUtility_getObject(obj) {

	// check argument
	if (obj == null) {
		return null;
	} else if (typeof obj == 'string') {
		return document.getElementById(obj);
	} else if (typeof obj == 'object') {
		return obj;
	} else {
		return null;
	}
}
function CCUtility_decodeURIComponent(uri) {
//	notSupported = Browser.extractVersion() < 5.5;
	
	if (typeof decodeURIComponent == 'undefined') {
	//TODO
		uri = uri.split('%3D').join('=');
		uri = uri.split('%3B').join(';');
		uri = uri.split('%5B').join('[');
		uri = uri.split('%5D').join(']');
		return uri;
	} else {
		return decodeURIComponent(uri);
	}
}
new CCUtility();
CCUtility.getEnclosingForm      = CCUtility_getEnclosingForm;
CCUtility.submitEnclosingForm   = CCUtility_submitEnclosingForm;
CCUtility.resetEnclosingForm    = CCUtility_resetEnclosingForm;
CCUtility.setActiveButton       = CCUtility_setActiveButton;
CCUtility.createHidden          = CCUtility_createHidden;
CCUtility.crtCtrla              = CCUtility_crtCtrla;
CCUtility.executeUserScript     = CCUtility_executeUserScript;
CCUtility.showFormElement       = CCUtility_showFormElement;
CCUtility.hideFormElement       = CCUtility_hideFormElement;
CCUtility.showSingleFormElement = CCUtility_showSingleFormElement;
CCUtility.getObject             = CCUtility_getObject;
CCUtility.decodeURIComponent    = CCUtility_decodeURIComponent;


/*
+ ---------------------------------------------------------------------------------+
| Purpose..: Helper for the ListControl object
|
| Date        Author            Notice
| ----------  ----------------  ----------------------------------------------------
| 08.04.2004  G.Schulz (SCC)    Erstversion
| 08.06.2004  G.Schulz (SCC)    ListHelp.checkAll added; ListHelp_uncheck renamed
|
+ ---------------------------------------------------------------------------------+
*/
function ListHelp() {
}
/**
 * Unchecks all Checkboxes specified in the argument
 * param: items An array of input fields
 */
function ListHelp_uncheckAll(items) {

	for (var i=0; i < items.length; i++) {
		if (items[i].type == 'checkbox' ) {

			// uncheck checkbox
			items[i].checked = false;
		}
	}
}
function ListHelp_checkAll(items) {
	for (var i=0; i < items.length; i++) {
		if (items[i].type == 'checkbox' ) {

			// uncheck checkbox
			items[i].checked = true;
		}
	}
}
function ListHelp_isChecked(items) {
	for (var i=0; i < items.length; i++) {
		if (items[i].type == 'checkbox' || items[i].type == 'radio') {
			if (items[i].checked) {
				return true;
			}
		}
	}
}
new ListHelp();
ListHelp.uncheckAll     = ListHelp_uncheckAll;
ListHelp.checkAll       = ListHelp_checkAll;
ListHelp.isChecked      = ListHelp_isChecked;


/*
+ ---------------------------------------------------------------------------------+
| Object....: HTTPUtil
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 22.05.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function HTTPUtil() {
}
function HTTPUtil_getParameters(location) {
	var url = new String(location);
	
	if (null == url) {
		return null;
	}
	var qString = url.split('?');

	if (null == qString || qString.length == 1) {
		return null;
	}
	
	var params = qString[1].split('&');
	return params;
}
function HTTPUtil_getParameter(key, params) {
	
	if (null == params || null == key) {
		return '';
	}
	
	for (var i=0; i < params.length; i++) {
		var arr = params[i].split('=');
		
		if (arr[0].toUpperCase() == key.toUpperCase()) {
			return arr[1];
		}
	}
}
function HTTPUtil_isSecure() {
	return ('https:' == window.location.protocol);
}
new HTTPUtil();
HTTPUtil.getParameters  = HTTPUtil_getParameters;
HTTPUtil.getParameter   = HTTPUtil_getParameter;
HTTPUtil.isSecure       = HTTPUtil_isSecure

