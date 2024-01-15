<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML//EN">

<!--
  Inventory.jsp -
  Display inventory and obtain user
  specification of pounds of each produce item to purchase.
-->

<html>

<%--
    Include page header.
--%>
<%@ include file="header.html" %>

<!--
  Display title and sub-title.
-->
<BR>
<CENTER>
<TITLE> Grocery Joe's </TITLE>
<FONT SIZE="+1">
<B> Produce Purchases</B>
</FONT>
<BODY BGCOLOR="#FFFFF">

<!--
  Page directive.
-->
<%@ page import="jsp_paper.*" errorPage="error.jsp" %>

<%--
    Create form defining page layout.
--%>
<BR><BR>
<FORM METHOD="post" ACTION="/jsp_paper/servlet/CustomerServlet">
<TABLE WIDTH=450 CELLSPACING="0" CELLPADDING="0" BORDER="1">

<%--
    Create header for table.
--%>
  <TR>
      <TD WIDTH=5% BGCOLOR="#ECA613"> <B>SKU#</B></TD>
      <TD BGCOLOR="#ECA613"> <B>Description</B></TD>
      <TD WIDTH=5% ALIGN=center BGCOLOR="#ECA613" > <B>Pounds</B></TD>
      <TD WIDTH=25% BGCOLOR="#ECA613"> <B>Price/Lb. (US $)</B> </TD>
  </TR>

<%--
    Declare a shopping basket and a Product catalog.
--%>
<%! BasketBean basket;
    Product[] catalogue;
%>

<%--
     Retrieve the  current shopping basket from the HttpSession object
     and the product catalogue from the Inventory object.
--%>
<%
    basket = (BasketBean)session.getAttribute(BasketBean.BASKET);
    catalogue = InventoryBean.getCatalogue();
%>

<%--
    Loop through each Product in the catlogue and display
    an HTML table row representing that Product.
--%>

<%-- Loop header scriptlet. --%>
<%
   for(int i = 0; i < catalogue.length; i++) {
      Product product = catalogue[i];
%>

<%-- Loop body including HTML and JSP expressions. --%>
  <TR>
      <TD> <%= product.getSKU() %> </TD>
      <TD> <%= product.getName() %> </TD>
      <TD> <INPUT TYPE=text SIZE=6 MAXLENGTH=6
            NAME="pounds" VALUE=<%= basket.getPounds(product)%>>
      </TD>
      <TD ALIGN=right> <%= product.getPrice() %> </TD>
  </TR>

<%-- Loop end brace scriptlet. --%>
<% } %>

<%--
    Create footer for table including purchase total.
--%>
<TR>
      <TD COLSPAN=4 align=right BGCOLOR="#ECA613"><B>
      Subtotal: $<%= basket.getStringifiedValue(basket.getTotal()) %> </B></TD>
</TR>
</TABLE>

<%--
    Create buttons for form.
--%>
<BR>
<TABLE WIDTH=450 CELLSPACING="0" CELLPADDING="0" BORDER="0">
  <TR>
      <TD ALIGN=left><INPUT TYPE=submit
      NAME=<%= BasketBean.PAGE %> VALUE=<%= BasketBean.UPDATE %>></TD>
      <TD ALIGN=left><INPUT TYPE=reset VALUE="Reset"></TD>
      <TD ALIGN=right><INPUT TYPE=submit
      NAME=<%= BasketBean.PAGE %> VALUE=<%= BasketBean.PURCHASE %>></TD>
  </TR>
</TABLE>
</FORM>
</CENTER>

<%--
    Include page footer.
--%>
<BR><BR>
<%@ include file="footer.html" %>

</body>
</html>
