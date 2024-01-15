<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<html>
<head>
<title>Test for ActionFormBeanMaker</title>
</head>
<body bgcolor="Silver">
<h1>Test for ActionFormBeanMaker</h1>


<struts:form action="maker.do" method="get" name="makerForm" type="MakerForm">
<struts:submit property="submit" value="OK"/>
<struts:submit property="submit" value="Cancel"/><p>
Text: <struts:text property="text" size="20" maxlength="20"/>
Password: <struts:password property="password" size="20"/><br>
<struts:hidden property="hidden"/>


<struts:select property="select">
  <struts:option value="1">Holland</struts:option>
  <struts:option value="2">Belgien</struts:option>
  <struts:option value="3">Deutschland</struts:option>
</struts:select><p>

<struts:radio property="radio" value="nullRadio">Null</struts:radio>
<struts:radio property="radio" value="firstRadio">First</struts:radio>
<struts:radio property="radio" value="secondRadio">Second</struts:radio>
<struts:radio property="radio" value="thirdRadio">Third</struts:radio><p>
<struts:checkbox property="administrator">administrator</struts:checkbox>
<p>
<struts:checkbox property="os" value="1">Win98</struts:checkbox>
<struts:checkbox property="os" value="2">Win2000</struts:checkbox>
<struts:checkbox property="os" value="3">Linux</struts:checkbox>
<p>
<select name="test" size="3" multiple>
	<option value="1" SELECTED>t</option>
	<option value="2">a</option>
	<option value="3">g</option>
	<option value="4">e</option>
	<option value="5">n</option>
</select>
</struts:form>

<jsp:useBean id="testBean" scope="page" class="TestBean"/>
<struts:iterate id="each" name="testBean" property="list">
<%= (String)each %><br>
</struts:iterate>
<p>


</body>
</html>