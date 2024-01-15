<%@ page language="java" isErrorPage="true" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html:html locale="true">
<head>
<title><bean:message key="KontaktHinzufuegen"/></title>
	<html:base/><link rel="STYLESHEET" type="text/css" href="css.css">
<script language="JavaScript" src="tab.js" type="text/javascript"></script>
<script language="JavaScript" src="window.js" type="text/javascript"></script>
</head>
<body alink="#0000ff" vlink="#0000ff" bgcolor="#ffffff">
<p>
<table border="0" cellpadding="0" cellspacing="0" width="100%">

<tr bordercolor="#FFFFFF" bgcolor="#F0F0F0">
<td valign="middle"><p><b><bean:message key="Adressbuch"/> ESS Development</b></p>
</td>
</tr>

</table>

	<html:form method="post" action="/storeContact" onsubmit="return validateContactForm(this);" enctype="multipart/form-data">	
	<ILAYER id="panelLocator" width="450" height="400"></ILAYER>

	<DIV id="p1" style="background-color: transparent; position: relative; width: 450px; height: 400px">
		<DIV id="p1panel0" class="panel" style="background-color: #EEEEEE;  z-index:4; width: 450px; height: 400px">	
	       <table width="400">
			<tr>
				<td width="144">
					<bean:message key="title"/>
				</td>
				<td>
					<html:text size="35" property="value(title)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="firstName"/>
				</td>
				<td>
					<html:text size="35" property="value(firstName)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="middleName"/>
				</td>
				<td>
					<html:text size="35" property="value(middleName)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="lastName"/>
				</td>
				<td>
					<html:text size="35" property="value(lastName)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="nickName"/>
				</td>
				<td>
					<html:text size="35" property="value(nickName)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="addressStreet"/>
				</td>
				<td>
					<html:text size="35" property="value(addressStreet)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="addressPOBox"/>
				</td>
				<td>
					<html:text size="35" property="value(addressPOBox)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="addressState"/>
				</td>
				<td>
					<html:text size="35" property="value(addressState)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="addressPostalCode"/>
				</td>
				<td>
					<html:text size="35" property="value(addressPostalCode)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="addressCity"/>
				</td>
				<td>
					<html:text size="35" property="value(addressCity)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="addressCountry"/>
				</td>
				<td>
					<html:text size="35" property="value(addressCountry)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="email"/>
				</td>
				<td>
					<html:text size="35" property="email"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="homepage"/>
				</td>
				<td>
					<html:text size="35" property="value(homepage)"/>
				</td>
			</tr>
			</table>
<html:messages id="error">
<b class="rot"><%= error %></b><br>
</html:messages>

<html:messages id="msg" message="true">
<b class="lime"><%= msg %></b><br>
</html:messages>		
		</DIV>
		<DIV onClick="selectTab(0)" id="p1tab0" class="tab" style="background-color:#EEEEEE; left:0px; top:0px; z-index:5; clip:rect(0 auto 30 0)">
<bean:message key="Allgemein"/>
		</DIV>
		<DIV id="p1panel1" class="panel" style="background-color: #EEEEEE;  z-index:3; width: 450px; height: 400px">
		  <table width="400">
			<tr>
				<td>
					<bean:message key="companyName"/>
				</td>
				<td>
					<html:text size="35" property="value(companyName)"/>
				</td>
			</tr>		  
			<tr>
				<td width="144">
					<bean:message key="businessNumber"/>
				</td>
				<td>
					<html:text size="35" property="value(businessNumber)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="officeNumber2"/>
				</td>
				<td>
					<html:text size="35" property="value(officeNumber2)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="officeLocation"/>
				</td>
				<td>
					<html:text size="35" property="value(officeLocation)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="departmentName"/>
				</td>
				<td>
					<html:text size="35" property="value(departmentName)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="companyMainPhoneNumber"/>
				</td>
				<td>
					<html:text size="35" property="value(companyMainPhoneNumber)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="fax"/>
				</td>
				<td>
					<html:text size="35" property="value(fax)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="mobileNumber"/>
				</td>
				<td>
					<html:text size="35" property="value(mobileNumber)"/>
				</td>
			</tr>
			</table>
		
		</DIV>
		<DIV onClick="selectTab(1)" id="p1tab1" class="tab" style="background-color:#EEEEEE; left:90px; top:0px; z-index:4; clip:rect(0 auto 30 0)">
<bean:message key="Business"/>
		</DIV>
		<DIV id="p1panel2" class="panel" style="background-color: #EEEEEE;  z-index:2; width: 450px; height: 400px">
		<table width="400">
			<tr>
				<td width="144">
					<bean:message key="homeNumber"/>
				</td>
				<td>
					<html:text size="35" property="value(homeNumber)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="homeNumber2"/>
				</td>
				<td>
					<html:text size="35" property="value(homeNumber2)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="homeStreet"/>
				</td>
				<td>
					<html:text size="35" property="value(homeStreet)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="homePOBox"/>
				</td>
				<td>
					<html:text size="35" property="value(homePOBox)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="homeState"/>
				</td>
				<td>
					<html:text size="35" property="value(homeState)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="homePostalCode"/>
				</td>
				<td>
					<html:text size="35" property="value(homePostalCode)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="homeCity"/>
				</td>
				<td>
					<html:text size="35" property="value(homeCity)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="homeCountry"/>
				</td>
				<td>
					<html:text size="35" property="value(homeCountry)"/>
				</td>
			</tr>
			</table>
		
		</DIV>
		<DIV onClick="selectTab(2)" id="p1tab2" class="tab" style="background-color:#EEEEEE; left:180px; top:0px; z-index:3; clip:rect(0 auto 30 0)">
<bean:message key="Home"/>
		</DIV>
		<DIV id="p1panel3" class="panel" style="background-color: #EEEEEE;  z-index:1; width: 450px; height: 400px">
		   <table width="400">
			<tr>
				<td width="144">
					<bean:message key="otherNumber"/>
				</td>
				<td>
					<html:text size="35" property="value(otherNumber)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="otherStreet"/>
				</td>
				<td>
					<html:text size="35" property="value(otherStreet)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="otherPOBox"/>
				</td>
				<td>
					<html:text size="35" property="value(otherPOBox)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="otherState"/>
				</td>
				<td>
					<html:text size="35" property="value(otherState)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="otherPostalCode"/>
				</td>
				<td>
					<html:text size="35" property="value(otherPostalCode)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="otherCity"/>
				</td>
				<td>
					<html:text size="35" property="value(otherCity)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="otherCountry"/>
				</td>
				<td>
					<html:text size="35" property="value(otherCountry)"/>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<bean:message key="comment"/>
				</td>
				<td>
					<html:textarea cols="29" rows="4" property="value(comment)"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:message key="birthday"/>
				</td>
				<td>
					<html:text size="10" property="birthday"/>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<bean:message key="hobbies"/>
				</td>
				<td>
					<html:textarea cols="29" rows="3" property="value(hobbies)"/>
				</td>
			</tr>
			</table>		
		</DIV>
		<DIV onClick="selectTab(3)" id="p1tab3" class="tab" style="background-color:#EEEEEE; left:270px; top:0px; z-index:2; clip:rect(0 auto 30 0)">
<bean:message key="Other"/>
		</DIV>
	
		<DIV id="p1panel4" class="panel" style="background-color: #EEEEEE;  z-index:1; width: 450px; height: 400px">
		   <table width="400">
			<tr>
				<td width="144">
					<bean:message key="File"/>
				</td>
				<td>
					<html:file size="35" accept="image/tiff,image/jpeg,image/gif,image/x-png" property="uploadFile"/>
				</td>
			</tr>
			<tr><td colspan="2">&nbsp;</td></tr>
			
			<tr><td colspan="2">
			<logic:notEmpty name="contactForm" property="value(contentType)">	
            <html:hidden property="value(contentType)"/>
            <html:hidden property="value(width)"/>
            <html:hidden property="value(height)"/>
            <html:hidden property="value(originalWidth)"/>
            <html:hidden property="value(originalHeight)"/>

			<a href="javascript:newWindow('<c:url value="showPicture.do"/>','<bean:message key="Foto"/>', <bean:write name="contactForm" property="value(originalWidth)"/>, <bean:write name="contactForm" property="value(originalHeight)"/>, false)"><img src="<c:url value='showPicture.do'/>" alt="" width="<bean:write name='contactForm' property='value(width)'/>" height="<bean:write name="contactForm" property="value(height)"/>" border="0"></a>
			<br>
			<bean:message key="loeschenFrage"/><html:checkbox property="delete"/>
			</logic:notEmpty>
			</td></tr>
			</table>
	
		</DIV>
		<DIV onClick="selectTab(4)" id="p1tab4" class="tab" style="background-color:#EEEEEE; left:360px; top:0px; z-index:1; clip:rect(0 auto 30 0)">
<bean:message key="Foto"/>
		</DIV>
	</DIV>	

	<p>&nbsp;</p>
	<br>
	<input type="submit" name="save" value="<bean:message key="Abspeichern"/>">
	<input type="submit" name="storeadd" value="<bean:message key="AbspeichernUndNeu"/>">
	<html:cancel><bean:message key="Abbrechen"/></html:cancel>
</html:form>


<html:javascript formName="contactForm"
        dynamicJavascript="true"
         staticJavascript="false"/>
<script language="Javascript1.1" src="staticValidator.jsp"></script>
</body>
</html:html>