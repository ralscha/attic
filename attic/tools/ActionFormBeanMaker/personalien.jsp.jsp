<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:form action="rekrutierung/savePersonalien.do" method="post" name="personalienForm" scope="???" type="???">

<struts:text property="titel" size="39"/>

<struts:text property="name" size="40"/>

<struts:text property="vorname" size="40"/>

<struts:text property="strasse" size="40"/>

<struts:text property="plz" size="40"/>

<struts:text property="ort" size="40"/>

<struts:text property="land" size="40"/>

<struts:text property="telefon" size="40"/>

<struts:text property="telefonmobil" size="40"/>

<struts:text property="fax" size="40"/>

<struts:text property="email" size="40"/>

<struts:text property="geburts" size="8"/>

<struts:text property="sozial" size="20"/>

<struts:submit property="null" value="Abspeichern"/>

<struts:select property="Anrede">
  <struts:option value="Herr">Herr</struts:option>
  <struts:option value="Frau">Frau</struts:option>
</struts:select>

</struts:form>
