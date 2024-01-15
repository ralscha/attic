<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML//EN">

<%@ page import="jsp_paper.*"
         errorPage="error.jsp" %>

<html>
  <TITLE> Grocery Joe's Checkout</title>

<BODY BGCOLOR="#FFFFF">
<BR><BR>
<%@ include file="header.html" %>

<CENTER>
<BR>
<FONT SIZE="+1"> <B> Customer Information</B> </FONT> 
<BR><BR>
<FORM METHOD="post" ACTION="/jsp_paper/servlet/CustomerServlet">
<TABLE WIDTH=450 CELLSPACING="0" CELLPADDING="0" BORDER="1"> 
<TR>
  <TD WIDTH=10% BGCOLOR="#ECA613"> <B>Name:</B></TD>
  <TD WIDTH=90%> <INPUT TYPE=text SIZE=50 NAME="name" ></TD>
</TR>
<TR>
  <TD WIDTH=10% BGCOLOR="#ECA613"> <B>EMail: </B></TD>
  <TD WIDTH=90%> <INPUT TYPE=text SIZE=50 NAME="email"></TD>
</TR>
<TR>
  <TD WIDTH=10% BGCOLOR="#ECA613" > <B>Street</B></TD>
  <TD WIDTH=90%> <INPUT TYPE=text SIZE=50 NAME="street1"></TD>
</TR>
<TR>
  <TD WIDTH=10% BGCOLOR="#ECA613" > <B>Street</B></TD>
  <TD WIDTH=90%> <INPUT TYPE=text SIZE=50 NAME="street2"></TD>
</TR>
<TR>
  <TD WIDTH=10% BGCOLOR="#ECA613"> <B>City</B> </TD>
  <TD WIDTH=90%> <INPUT TYPE=text SIZE=50 NAME="city"></TD>
</TR>
<TR>
  <TD WIDTH=10% BGCOLOR="#ECA613"> <B>State</B> </TD>
  <TD WIDTH=90%> <INPUT TYPE=text SIZE=50 NAME="state"></TD>
</TR>
<TR>
  <TD WIDTH=10% BGCOLOR="#ECA613"> <B>Phone</B> </TD>
  <TD WIDTH=90%> <INPUT TYPE=text SIZE=50 NAME="phone"></TD>
</TR>
</TABLE>

<%
   BasketBean basket = (BasketBean)session.getAttribute(BasketBean.BASKET);
%>
<B>
   Total Purchase Price: $<%= basket.getStringifiedValue(basket.getTotal()) %>
</B>
<BR>
<BR>
<TABLE WIDTH=450 CELLSPACING="0" CELLPADDING="0" BORDER="0">
  <TR>
      <TD ALIGN=left><INPUT TYPE=reset VALUE="Reset"></TD>
      <TD ALIGN=right><INPUT TYPE=submit NAME=<%= BasketBean.PAGE %> VALUE=<%= BasketBean.RECEIPT %> ></TD>
  </TR>
</TABLE>
</FORM>
</CENTER>
<BR><BR>
<%@ include file="footer.html" %>
</body>
</html>
