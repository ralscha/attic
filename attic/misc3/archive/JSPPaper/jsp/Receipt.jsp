<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML//EN">

<!--
  Confirm.jsp -
    Confirm the customer's purchase and show a reciept.
-->

<html>

<!--
  Include page header.
-->
<%@ include file="header.html" %>

<BODY BGCOLOR="#FFFFF">

<!--
  Display title and sub-title.
-->
<CENTER>
<TITLE> Grocery Joe's</TITLE>
<FONT SIZE="+1">
<B>Receipt</B>
</FONT>

<!--
  Page directive.
-->
<%@ page import="jsp_paper.*"
         errorPage="error.jsp" %>

<!--
  Declare ReceiptBean instance and set properties from the HTML
  request stream.
-->
<jsp:useBean
 id="receipt_bean"
 scope="request"
 class="jsp_paper.ReceiptBean"
/>

<%--
<jsp:setProperty name="receipt_bean" property="name" param="name" />
<jsp:setProperty name="receipt_bean" property="email" param="email" />
<jsp:setProperty name="receipt_bean" property="street1" param="street1" />
<jsp:setProperty name="receipt_bean" property="street2" param="street2" />
<jsp:setProperty name="receipt_bean" property="city" param="city" />
<jsp:setProperty name="receipt_bean" property="state" param="state" />
<jsp:setProperty name="receipt_bean" property="phone" param="phone" />
--%>
<jsp:setProperty name="receipt_bean" property="*" />
</jsp:useBean>
	
<!--
  Create table printing out receipt values from receipt Bean.
-->
<BR><BR>
<TABLE WIDTH=450 CELLSPACING="0" CELLPADDING="0" BORDER="0"> 
<TR>
  <TD WIDTH=100%> <B>Name:
  <jsp:getProperty name="receipt_bean" property="name"/></B></TD>
</TR>
<TR>
  <TD WIDTH=100%> <B>EMail: 
  <jsp:getProperty name="receipt_bean" property="email"/></B></TD> 
</TR>
<TR>
  <TD WIDTH=100% > <B>Street: 
  <jsp:getProperty name="receipt_bean" property="street1"/></B></TD> 
</TR>
<TR>
  <TD WIDTH=100% > <B>Street:
  <jsp:getProperty name="receipt_bean" property="street2"/></B></TD> 
</TR>
<TR>
  <TD WIDTH=100%> <B>City:
  <jsp:getProperty name="receipt_bean" property="city"/></B></TD> 
</TR>
<TR>
  <TD WIDTH=100%> <B>State: 
  <jsp:getProperty name="receipt_bean" property="state"/></B></TD>
</TR>
<TR>
  <TD WIDTH=100%> <B>Phone:
  <jsp:getProperty name="receipt_bean" property="phone"/></B></TD>
</TR>
</TABLE>

<!--
  Print purchase total.
-->
<%
   BasketBean basket = (BasketBean)session.getAttribute(BasketBean.BASKET);
%>
<B>
   Total Purchase Price: $<%= basket.getStringifiedValue(basket.getTotal()) %> </B>
<%   
   basket.clear();
%>

<BR>
<BR>
</CENTER>
<BR><BR>

<!--
  Include page footer.
-->
<%@ include file="footer.html" %>
</body>
</html>
