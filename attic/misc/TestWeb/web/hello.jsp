<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<f:view>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello World</title>
</head>
<body>
  <h:form id="welcomeForm">
    <h:outputText id="welcomeOutput" value="Welcome to JavaServer Faces!"
                  style="font-family: Arial, sans-serif; font-size: 24; color: green;"/> 
    <p>
      <h:message id="errors" for="helloInput" style="color: red"/>
    </p>

    <p>
      <h:outputLabel for="helloInput">
        <h:outputText id="helloInputLabel" value="Enter number of controls to display:"/>
      </h:outputLabel>
      <h:inputText id="helloInput" value="#{helloBean.numControls}" required="true">
        <f:validateLongRange minimum="1" maximum="500"/>
      </h:inputText>
    </p>
    
    <p>
      <h:panelGrid id="controlPanel" binding="#{helloBean.controlPanel}" 
         columns="20" border="1" cellspacing="0"/>
    </p>
    
    <h:commandButton id="redisplayCommand" type="submit" value="Redisplay" 
            actionListener="#{helloBean.addControls}"/>

    <h:commandButton id="goodbyeCommand" type="submit" value="Goodbye" 
            action="#{helloBean.goodbye}" immediate="true"/>

  </h:form>

</body>
</html>
</f:view>