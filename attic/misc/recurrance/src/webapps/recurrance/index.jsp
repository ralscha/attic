<%@ page import="com.managestar.recurrance.Recurrance,
                 com.managestar.recurrance.RecurranceRuleXml,
                
                 java.util.Calendar,
                 java.util.Date,
                 java.util.List"%>

<HTML>
<HEAD><TITLE>Recurrance</TITLE></HEAD>
<BODY>
<H1>Recurrance</H1>
<P>Use this page to quickly test that recurrance works the way you would expect.  
Just enter a recurrance rule below and click list to display the dates that match the
given rule.  A list of example rules is given at the bottom of the page.
<P>You can also use the source of this jsp as an example of how to use the recurrance
API.
<FORM>
<INPUT TYPE="HIDDEN" VALUE="here" NAME="sub">
<TABLE BORDER=0>
<TR><TD VALIGN="TOP">
<TABLE BORDER=0>

<TR>
<TD>&nbsp;</TD><TD>
<TD>year</TD>
<TD>month</TD>
<TD>day</TD>
<TD>hour</TD>
<TD>minute</TD>
<TD>second</TD>

</TR>

<TR>
<TD>start date:</TD><TD>
<TD><INPUT TYPE="TEXT" VALUE="2002" NAME="syear"></TD>
<TD><INPUT TYPE="TEXT" VALUE="6" NAME="smonth"></TD>
<TD><INPUT TYPE="TEXT" VALUE="15" NAME="sday"></TD>
<TD><INPUT TYPE="TEXT" VALUE="13" NAME="shour"></TD>
<TD><INPUT TYPE="TEXT" VALUE="00" NAME="sminute"></TD>
<TD><INPUT TYPE="TEXT" VALUE="00" NAME="ssecond"></TD>
</TR>

<TR>
<TD>end date:</TD><TD>
<TD><INPUT TYPE="TEXT" VALUE="2007" NAME="eyear"></TD>
<TD><INPUT TYPE="TEXT" VALUE="12" NAME="emonth"></TD>
<TD><INPUT TYPE="TEXT" VALUE="31" NAME="eday"></TD>
<TD><INPUT TYPE="TEXT" VALUE="8" NAME="ehour"></TD>
<TD><INPUT TYPE="TEXT" VALUE="30" NAME="eminute"></TD>
<TD><INPUT TYPE="TEXT" VALUE="30" NAME="esecond"></TD>
</TR>

<TR>
<TD>rule:</TD><TD>
<TD COLSPAN="5">
<TEXTAREA NAME="rule" ID="rule" ROWS="10" COLS="75">
<%
  String rule = request.getParameter("rule");
  if(rule==null){
    rule="";
  }
  if(request.getParameter("convertToXml")!=null){
    if("terse".equals(request.getParameter("wordiness"))){
      RecurranceRuleXml.verbose = false;
    }
    else{
      RecurranceRuleXml.verbose = true;
    }
    out.println(RecurranceRuleXml.convertRRuleToXml(rule).trim());
  }
  else if(request.getParameter("convertToRRule")!=null){
    out.println(RecurranceRuleXml.convertXmlToRRule(rule).trim());
  }
  else{
    out.println(rule);
  }
%>
</TEXTAREA></TD>
<TD>
<INPUT TYPE="SUBMIT" VALUE="Recur" NAME="submit"><BR>
<INPUT TYPE="SUBMIT" VALUE="Convert To XML" NAME="convertToXml"><BR>
<INPUT TYPE="SUBMIT" VALUE="Convert To RRULE" NAME="convertToRRule"><BR>
<INPUT TYPE='button' value="Clear" ONCLICK="document.getElementById('rule').value='';"><BR>
<INPUT TYPE="radio" name="wordiness" VALUE="terse" <%if("terse".equals(request.getParameter("wordiness"))){%>CHECKED="true"<%}%> >terse<BR>
<INPUT TYPE="radio" name="wordiness" VALUE="verbose" <%if("verbose".equals(request.getParameter("wordiness")) || null == request.getParameter("wordiness")){%>CHECKED="true"<%}%>>verbose<br>
</TD>
</TR>
<TR>
<TD COLSPAN="7">
<UL>INSTRUCTIONS:<BR>
<LI>Use the convert buttons to convert RRULES<->XML versions of recurrance.
<LI>The Terse/Verbose radio buttons only come in to play when converting from RRULE to XML.
<LI>Trying to convert something to itself will cause an error.
<LI>Copy examples below into the box to play with them.
<TD>
</TR>

</TABLE>

</TD>
<TD>
<B>
<%
if(null!=request.getParameter("sub")){
  boolean fail = false;
  Calendar start = Calendar.getInstance();
  Calendar end = Calendar.getInstance();

  try{
    Recurrance.fastSet(start,
                       request.getParameter("syear"),
                       request.getParameter("smonth"),
                       request.getParameter("sday"),
                       request.getParameter("shour"),
                       request.getParameter("sminute"),
                       request.getParameter("ssecond"));
  }
  catch(Exception e){
    fail = true;
    out.println("Your start date was not parseable!<br>");
  }
  try{
    Recurrance.fastSet(end,
                       request.getParameter("eyear"),
                       request.getParameter("emonth"),
                       request.getParameter("eday"),
                       request.getParameter("ehour"),
                       request.getParameter("eminute"),
                       request.getParameter("esecond"));
  }
  catch(Exception e){
    fail = true;
    out.println("Your end date was not parseable!<br>");
  }
  
  try{
    Recurrance r = new Recurrance(request.getParameter("rule"), start.getTime(), end.getTime() );
    List results = r.getAllMatchingDates();
    for(int ctr=0;ctr<results.size();ctr++){
      out.println(""+results.get(ctr)+"<br>");
    }
    out.println("<br><br>Next Occurance: "+r.next(new Date(System.currentTimeMillis()))+"<br>");
    out.println("Prev Occurance: "+r.prev(new Date(System.currentTimeMillis()))+"<br>");
  }
  catch(Exception e){
    e.printStackTrace();
    out.println("Your request threw an exception!  "+e.getMessage()+"<br>");
  }
}

%></B>
</TD>
</TR>
</TABLE>

<BR><BR><BR>

<B>Examples:</b><BR>
FREQ=YEARLY;INTERVAL=1<br>
FREQ=YEARLY;COUNT=1;INTERVAL=1;<br>
FREQ=YEARLY;COUNT=3;INTERVAL=2;<br>
FREQ=YEARLY;COUNT=3;INTERVAL=1;BYMONTHDAY=7,8,12;<br>
FREQ=MONTHLY;INTERVAL=1<br>
FREQ=MONTHLY;COUNT=5;INTERVAL=2<br>
FREQ=YEARLY;COUNT=5;INTERVAL=1;BYMONTH=1,3,5;<br>
FREQ=MONTHLY;INTERVAL=7;BYHOUR=0,12;BYMINUTE=0;BYSECOND=0<br>
FREQ=YEARLY;INTERVAL=1;BYMONTH=7;BYHOUR=0,12;BYMINUTE=0;BYSECOND=0<br>
FREQ=YEARLY;INTERVAL=1;BYMONTH=10,11,12;BYHOUR=0,12;BYMINUTE=0;BYSECOND=0<br>
FREQ=YEARLY;COUNT=20;INTERVAL=1;BYWEEKNO=1,10,20,30,40,50;BYDAY=TH<br>
FREQ=WEEKLY;COUNT=20;INTERVAL=2;<br>
FREQ=DAILY;COUNT=20;INTERVAL=3;BYHOUR=10;BYMINUTE=30;BYSECOND=0<br>
FREQ=YEARLY;COUNT=20;INTERVAL=1;BYYEARDAY=1,130,-130,-1<br>
FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=SU;BYHOUR=8,9;BYMINUTE=30<br>
FREQ=YEARLY;INTERVAL=2;BYMONTH=1;BYDAY=-1SU;BYHOUR=8,9;BYMINUTE=30<br>
FREQ=YEARLY;BYMONTH=1;BYDAY=SU,MO,TU,WE,TH,FR;COUNT=20<br>
FREQ=WEEKLY;INTERVAL=3;WKST=SU;BYDAY=TU,TH;COUNT=30<br>
FREQ=WEEKLY;COUNT=10;WKST=SU;BYDAY=TU,TH<br>
FREQ=WEEKLY;INTERVAL=2;WKST=SU;BYDAY=MO,WE,FR;COUNT=10<br>
FREQ=MONTHLY;COUNT=10;BYDAY=1FR<br>
FREQ=MONTHLY;INTERVAL=2;COUNT=10;BYDAY=1SU,-1SU<br>
FREQ=MONTHLY;COUNT=6;BYDAY=-2MO<br>
FREQ=MONTHLY;BYMONTHDAY=-3;COUNT=10<br>
FREQ=MONTHLY;COUNT=10;BYMONTHDAY=2,15<br>
FREQ=MONTHLY;COUNT=10;BYMONTHDAY=1,-1<br>
FREQ=MONTHLY;INTERVAL=18;COUNT=10;BYMONTHDAY=10,11,12,13,14<br>
FREQ=YEARLY;INTERVAL=3;COUNT=10;BYYEARDAY=1,100,200<br>
FREQ=YEARLY;BYDAY=20MO<br>
FREQ=YEARLY;BYWEEKNO=20;BYDAY=MO;COUNT=10<br>
FREQ=YEARLY;BYMONTH=3;BYDAY=TH;COUNT=10<br>
FREQ=YEARLY;BYDAY=TH;BYMONTH=6,7,8;COUNT=15<br>
FREQ=MONTHLY;BYDAY=FR;BYMONTHDAY=13;COUNT=10<br>
FREQ=MONTHLY;BYDAY=SA;BYMONTHDAY=7,8,9,10,11,12,13;COUNT=30<br>
FREQ=YEARLY;INTERVAL=4;BYMONTH=11;BYDAY=TU;BYMONTHDAY=2,3,4,5,6,7,8;COUNT=30<br>
FREQ=HOURLY;COUNT=50;INTERVAL=1;BYYEARDAY=177<br>
FREQ=HOURLY;COUNT=20;INTERVAL=2;BYMINUTE=0,15,30,45;<br>
FREQ=MINUTELY;COUNT=20;INTERVAL=15;BYHOUR=0,2,4,6,8;<br>
FREQ=MONTHLY;COUNT=3;BYDAY=TU,WE,TH;BYSETPOS=3<br>
FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-2<br>
FREQ=WEEKLY;INTERVAL=2;COUNT=4;BYDAY=TU,SU;WKST=MO<br>
FREQ=WEEKLY;INTERVAL=2;COUNT=4;BYDAY=TU,SU;WKST=SU<br>





   
<BR><BR><BR>
<B>RELEASE NOTES:</B>
<UL>
<LI>This is a 'release early' version of the software.  Everything works pretty well, but 
there is still some clean up that code be done to hopefully make things run faster.
<LI>This installation testing is something I whipped up fairly quickly.  It works for me to
test an install, but your milage may vary
</UL>

For any questions or comments:   jason@h3c.com
