<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts.tld" prefix="struts" %>

<struts:form action="rekrutierungForm" method="post" name="rekrutierung" scope="???" type="???">

<struts:text name="pensum"/>

<struts:checkbox name="swisscomerfahrung" value="se"></struts:checkbox>

<struts:text name="projekt"/>

<struts:text name="termin"/>

<struts:submit name="save" value="Abspeichern"/>

<struts:textarea name="taetigkeitsgebiete" cols="30" rows="4">
</struts:textarea>

<struts:textarea name="skills" cols="30" rows="4">
</struts:textarea>

<struts:textarea name="beschreibung" cols="30" rows="4">
</struts:textarea>

<struts:textarea name="bemerkung" cols="30" rows="4">
</struts:textarea>

</struts:form>
