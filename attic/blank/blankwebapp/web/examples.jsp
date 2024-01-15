<%@ include file="include/taglibs.jspf"%>


<html>
  <head>
    <title></title>
    <misc:popupCalendarJs />
  </head>
  <body>
  
  <form action="#">
    <input type="text" name="testInput1" id="testInput1" value="" size="10" maxlength="10">
    <img src="images/cal.gif" alt="" name="selectDate1" id="selectDate1" width="16" height="16" border="0">
    <br />
    <input type="text" name="testInput2" id="testInput2" value="" size="10" maxlength="10">
    <img src="images/cal.gif" alt="" name="selectDate2" id="selectDate2" width="16" height="16" border="0">
    <br />
    <input type="text" name="testInput3" id="testInput3" value="" size="10" maxlength="10">
    <img src="images/cal.gif" alt="" name="selectDate3" id="selectDate3" width="16" height="16" border="0">
    <br />  
    <input type="text" name="testInput4" id="testInput4" value="" size="10" maxlength="10">
    <img src="images/cal.gif" alt="" name="selectDate4" id="selectDate4" width="16" height="16" border="0">

        
  
  </form>  
    <misc:popupCalendar element="testInput1" trigger="selectDate1" />
    <misc:popupCalendar element="testInput2" trigger="selectDate2" showOthers="true"/>
    <misc:popupCalendar element="testInput3" trigger="selectDate3" weekNumbers="false"/>
    <misc:popupCalendar element="testInput4" trigger="selectDate4" showsTime="true"/>            
  </body>  
</html>


	
