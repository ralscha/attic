<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="/tags/ess-misc"  prefix="misc" %>

<div id="overDiv" style="position:absolute; visibility:hidden; z-index:1000;"></div>
<script language="JavaScript" src="<c:url value='/scripts/overlib.js'/>" type="text/javascript"></script>  
<script language="JavaScript" src="<c:url value='/scripts/picker.js'/>" type="text/javascript"></script>  
<misc:popupCalendarJs showWeekNumber="true" showToday="true" />
<p>&nbsp;</p>
 <div onmouseover="return overlib('This is an overlib example', FGCOLOR, '#EEEEEE', WIDTH, 110);" onmouseout="return nd();">
   an overlib example
 </div>

<p>&nbsp;</p>
<form action="#" name="myform" id="myform">
<table>
<tr><td>
<label for="date">Date</label>
</td>
<td>
<input type="text" name="date" id="date" size="10"><misc:popupCalendar form="myform" element="date" />
</td>
</tr>
<tr>
<td><label for="date">Date No Past</label></td>
<td><input type="text" name="dateNoPast" id="dateNoPast" size="10"><misc:popupCalendar form="myform" element="dateNoPast" past="false" /></td>
</tr>

<tr>
<td><label for="colour">Colour</label></td>
<td><input type="text" name="colour" id="colour" size="10"><a href="javascript:TCP.popup(document.forms['myform'].elements['colour'])"><img width="15" height="13" border="0" alt="Click Here to Pick up the color" src="<c:url value='/images/sel.gif'/>"></a></td>
</tr>
</table>
</form>





