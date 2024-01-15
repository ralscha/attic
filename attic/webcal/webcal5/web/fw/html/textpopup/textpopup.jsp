<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@ page import="com.cc.framework.ui.painter.html.HtmlPainterHelp" %>

<html>
<head>
	<%-- Title --%>
	<title>Textpopup</title>

	<% HtmlPainterHelp.createBase(pageContext); %>

	<%-- Framework Includes --%>
	<% HtmlPainterHelp.createFameworkIncludes(pageContext); %>
</head>
<body onLoad="init();" style="margin-bottom: 0; margin-left: 0; margin-right: 0; margin-top: 0;">

<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="5" class="fc">
	<tr valign="top" height="100%">
		<td class="fd">
			<textarea id="textpopup" style="width:100%; height:100%; font-size: 10pt"></textarea>
		</td>
	</tr>
	<tr height="25">
		<td align="right" valign="middle" class="fd">
			<table border="0" cellspacing="0" cellpadding="3">
				<tr>
					<td nowrap>
<%
					HtmlPainterHelp
						.createButton(
							pageContext,
							"btnCancel",
							"fw.textpopup.button.cancel@com.cc.framework.message",
							"fw.textpopup.button.cancel.tooltip@com.cc.framework.message",
							"btnCancel",
							"onCancel();");
%>
					</td>
					<td nowrap id="button" style="display:block; padding-left:5px;">
<%
					HtmlPainterHelp
						.createButton(
							pageContext,
							"btnApply",
							"fw.textpopup.button.apply@com.cc.framework.message",
							"fw.textpopup.button.apply.tooltip@com.cc.framework.message",
							"btnApply",
							"onClose();");
%>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

</body>
</html>

<script type="text/javascript">
var locale        = 'EN';                                // Default locale if not specified
var fieldId       = null;                                // Id of the field the calendar is assoziated to
var field         = null;
var winTitle      = '';
var maxlength     = 0;
var winHeight     = 0;
var isMaxlength   = false;
var remainingText = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.textarea.maxlength.message") %>;
var winTitle      = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.textpopup.window.title") %>;
var ta_textpopup  = null;
var readonly      = false;
var initalvalue   = null; // BUG safari onchange

if (null != window.location) {
	var winlocation = CCUtility.decodeURIComponent(window.location);
	var params   = HTTPUtil.getParameters(winlocation);
	fieldId      = HTTPUtil.getParameter('fieldid', params);
	field        = window.opener.document.getElementById(fieldId);
	maxlength    = HTTPUtil.getParameter('maxlength', params);
	isMaxlength = (null == maxlength || isNaN(maxlength) || 0 == maxlength) ? false : true;
	winHeight    = HTTPUtil.getParameter('height', params);
	var lc       = HTTPUtil.getParameter('locale', params);
	locale       = (null == lc || lc.toUpperCase() == 'NULL') ? 'EN' : lc.toUpperCase();
	readonly     = ('true' == HTTPUtil.getParameter('readonly', params)) ? true : false;

	// set text
	var textarea = document.getElementById('textpopup');
	var obj = window.opener.document.getElementById(fieldId);
	if (null != obj) {
		textarea.value = obj.value;
		initalvalue = obj.value;
	}

	if (readonly) {
		textarea.setAttribute('readOnly', readonly);
		
		// do not display the Apply button
		var tb = document.getElementById('button');
		tb.style.display = 'none';
	}
}

// create a textarea object
ta_textpopup = new Textarea('textpopup', maxlength, remainingText, true);

if (isMaxlength) {
	if (!readonly) {
		textarea.style.height = parseInt(winHeight) - 60;
	} else {
		textarea.style.height = parseInt(winHeight) - 40;
	}
} else {
	textarea.style.height = '100%';
}

// set the window title
document.title = winTitle;
</script>

<script type="text/javascript">
var messageCancel = <%= HtmlPainterHelp.getStringLiteral(pageContext, "fw.textpopup.button.cancel.message") %>;

function isDirty() {
	if (safari) {
		var newvalue = document.getElementById('textpopup').value;
		return (initalvalue != newvalue);
	} else {
		return ta_textpopup.isDirty();
	}
}
function onCancel() {
	// check if the area has changed
	if (isDirty()) {
		var rtc = window.confirm(messageCancel);

		if (rtc) {
			window.close();
		} else {
			return false;
		}
	} else{
		window.close();
	}
}
function onClose() {
	// check if the area has changed
	if (isDirty()) {
		 if (null != window.opener) {
			var field = window.opener.document.getElementById(fieldId);
			
			if (null != field) {
				field.value = document.getElementById('textpopup').value;

				// refresh
				field.onkeypress();
			}
		}
	}
	
	window.close();
}
</script>
