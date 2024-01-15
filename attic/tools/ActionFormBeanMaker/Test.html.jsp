<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:form action="maker.do" method="post" name="makerForm" scope="???" type="???">

<struts:submit property="submit" value="OK"/>

<struts:submit property="submit" value="Cancel"/>

<struts:text property="text" size="20" maxlength="20"/>

<struts:password property="password" size="20"/>

<struts:hidden property="hidden"/>

<struts:radio property="radio" value="nullRadio">Null</struts:radio>

<struts:radio property="radio" value="firstRadio">First</struts:radio>

<struts:radio property="radio" value="secondRadio">Second</struts:radio>

<struts:radio property="radio" value="thirdRadio">Third</struts:radio>

<struts:checkbox property="checkbox" value="USA">USA</struts:checkbox>

<struts:checkbox property="checkbox" value="Canada">Canada</struts:checkbox>

<struts:textarea property="textArea" cols="35" rows="6"\>

<struts:select property="select">
  <struts:option value="1">Holland</struts:option>
  <struts:option value="2">Belgien</struts:option>
  <struts:option value="3">Deutschland</struts:option>
</struts:select>

<struts:select property="selectmulti">
  <struts:option value="1">Books</struts:option>
  <struts:option value="2">Sport</struts:option>
  <struts:option value="3">Computer</struts:option>
</struts:select>

</struts:form>
