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


/*
+ ---------------------------------------------------------------------------------+
| Object...: Browser()
| Function.: Utility to test for browser and browser versions
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 23.12.2002  G.Schulz (SCC)    Inital version
| 31.12.2003  G.Schulz (SCC)    support for opera browser added
| 28.03.2004  G.Schulz (SCC)    support for safari (mac) browser added
| 22.12.2004  G.Schulz (SCC)    support for mozilla and firefox added
|
+ ---------------------------------------------------------------------------------+
*/
function Browser() {
}
function Browser_getAppName() {
	return navigator.appName;
}
function Browser_getAppVersion() {
	return navigator.appVersion;
}
function Browser_getUserAgent() {
	return navigator.userAgent;
}
function Browser_isNS() {
	var ns = this.getUserAgent().indexOf('Netscape') != -1;

	if (ns && this.isFirefox()) {
		return false;	
	} else if (ns && this.isSafari()) {
		return false;
	} else {
		return ns;
	}
}
function Browser_isSafari() {
	return this.getUserAgent().indexOf('Safari') != -1;
}
function Browser_isIE() {
	if (this.isOpera()) {
		return false;
	} else {
		return this.getAppName().indexOf('Microsoft') != -1;
	}
}
function Browser_isOpera() {
	return this.getUserAgent().indexOf('Opera') != -1;
}
function Browser_isMozilla() {
	if (this.isNS()) {
		return false;
	} else if (this.isOpera()) {
		return false;
	} else if (this.isFirefox()) {
		return false;
	} else {
		return this.getUserAgent().indexOf('Mozilla') != -1;
	}
}
function Browser_isFirefox() {
	return this.getUserAgent().indexOf('Firefox') != -1;
}
function Browser_extractVersion() {
	var out = '';
	var ua = this.getUserAgent();

	// Dont change the order !
	if (this.isSafari()) {
		var arr = ua.split('Safari');
		return arr[1].split('/')[1];
	}

	if (this.isNS()) {
		var arr = ua.split(' ');
		for (var i=0; i < arr.length; i++) {
			if ( arr[i].indexOf('Netscape') != -1) {
				return arr[i].split('/')[1];
			}
		}
	}

	if (this.isIE()) {
		var arr = ua.split(';');
		for (var i=0; i < arr.length; i++) {
			if ( arr[i].indexOf('MSIE') != -1) {
				return arr[i].split(' ')[2];
			}
		}
	}
	
	if (this.isOpera()) {
		// Opera.([\d]+\.[\d]+)
		var arr = ua.split('Opera');
		return arr[1].split('[')[0];
	}

	if (this.isMozilla()) {
		var arr = ua.split(' ');
		return arr[0].split('/')[1];
	}

	if (this.isFirefox()) {
		var arr = ua.split('Firefox');
		return arr[1].split('/')[1];
	}

	// Default
	return out;
}
function Browser_getPlatform() {
	return navigator.platform;
}
function Browser_isSupported() {
	// Check if the Browser is supported by the application
	var version = this.extractVersion();

	if (this.isIE() && parseFloat(version) > 5) {
		return true;
	}

	if (this.isNS() && parseFloat(version) > 7) {
		return true;
	}
/*
	if (this.isOpera() && parseFloat(version) >= 7) {
		return true;
	}
*/
	if (this.isSafari() && parseFloat(version) >= 7) {
		return true;
	}

	if (this.isFirefox() && parseFloat(version) >= 1) {
		return true;
	}

	if (this.isMozilla() && parseFloat(version) >= 5) {
		return true;
	}
	
	return false;
}
function Browser_getPlugInList() {
	var out = '';
	var _plugin;
	
	navigator.plugins.refresh();
	
	if (navigator.plugins.length == 0) {
		if (this.isIE) {
			out = 'Information not available';
		} else {
			out = 'none';
		}
	} else {
		for(var i=0; i < navigator.plugins.length; i++) {
			_plugin = navigator.plugins[i];
			out += _plugin.name + ' (' + _plugin.description + ')' + '<BR>';
		}
	}
	return out;
}
function Browser_getMimeTypeList() {
	var out = '';
	
	if (navigator.mimeTypes.length == 0) {
		out = 'none';
	} else {
		for(var i=0; i < navigator.mimeTypes.length; i++) {
			var _mimeTypes = navigator.mimeTypes[i];
			out += _mimeTypes.name + ' (' + _mimeTypes.description + ')' + '<BR>';
		}
	}
	return out;
}
function Browser_isCookieEnabled() {
	return navigator.cookieEnabled;
}
function Browser_toString() {
	var out = '';
	out += '****** Browser *******' + LF;
	out += 'AppName.......: ' + this.getAppName() + LF;
	out += 'AppVersion....: ' + this.getAppVersion() + LF;
	out += 'Platform......: ' + this.getPlatform() + LF;
	out += 'userAgent.....: ' + this.getUserAgent() + LF;
	out += 'IE............: ' + this.isIE() + LF;
	out += 'NS............: ' + this.isNS() + LF;
	out += 'Opera.........: ' + this.isOpera() + LF;
	out += 'Safari........: ' + this.isSafari() + LF;
	out += 'Mozilla.......: ' + this.isMozilla() + LF;
	out += 'Firefox.......: ' + this.isFirefox() + LF;
	out += 'Version.......: ' +	this.extractVersion() + LF;
	out += 'Is Supported..: ' +	this.isSupported();
	return out;
}
function Browser_javaEnabled() {
	return navigator.javaEnabled();
}
function Browser_getJavaEnabledMessage() {
	if (this.javaEnabled()) {
		return 'Yes';
	} else {
		var info = "Warning!<br>";
		var txt = "No JavaScript enabled. JavaScript is required to run the Application.";
		txt += "<br><b>Please turn on JavaScript in your Browser.</b>&nbsp;<a href='help/jscript/ie/enablejscript.html' target='_blank' class='help'>[Help...]</a>";
		return info.fontcolor('red').bold() + txt;
	}
}
new Browser();
Browser.getAppName            = Browser_getAppName;
Browser.getAppVersion         = Browser_getAppVersion;
Browser.getUserAgent          = Browser_getUserAgent;
Browser.extractVersion        = Browser_extractVersion;
Browser.getPlatform           = Browser_getPlatform;
Browser.javaEnabled           = Browser_javaEnabled;
Browser.getJavaEnabledMessage = Browser_getJavaEnabledMessage;
Browser.isNS                  = Browser_isNS;
Browser.isIE                  = Browser_isIE;
Browser.isSafari              = Browser_isSafari;
Browser.isOpera               = Browser_isOpera;
Browser.isMozilla             = Browser_isMozilla;
Browser.isFirefox             = Browser_isFirefox;
Browser.isSupported           = Browser_isSupported;
Browser.getPlugInList         = Browser_getPlugInList;
Browser.getMimeTypeList       = Browser_getMimeTypeList;
Browser.isCookieEnabled       = Browser_isCookieEnabled;
Browser.toString              = Browser_toString;

var ie      = Browser.isIE();
var ns      = Browser.isNS();
var opera   = Browser.isOpera();
var safari  = Browser.isSafari();
var mozilla = Browser.isMozilla();
var firefox = Browser.isFirefox();


/*
+ ---------------------------------------------------------------------------------+
| Object...: Environment
| Function.: Checks the User-Environment
|
| Date        Author            Description
| ----------  ----------------  ----------------------------------------------------
| 23.12.2002  G.Schulz (SCC)    Inital Version
|
+ ---------------------------------------------------------------------------------+
*/
function Environment() {
}
function Environment_getWidth() {
	return screen.width;
}
function Environment_getHeight() {
	return screen.height;
}
function Environment_screenAttributes() {
	var out = '';
	
	out += 'Width: ' + this.getWidth() + '; ';
	out += 'Height: ' + this.getHeight();
	return out;
}
function Environment_toString() {
}
new Environment();
Environment.getWidth             = Environment_getWidth;
Environment.getHeight            = Environment_getHeight;
Environment.screenAttributes     = Environment_screenAttributes;
Environment.toString             = Environment_toString;

