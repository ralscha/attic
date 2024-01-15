<%--
   JavaServer Faces in Action example code, Copyright (C) 2004 Kito D. Mann.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="jsf-in-action-components" prefix="jia"%>

<f:view>

<html>
  <head>
    <title>
      <h:outputText value="ProjectTrack - Create a new project"/>
    </title>
    <link rel="stylesheet" type="text/css"
          href="stylesheet.css"/>
  </head>

<body class="page-background">

<jsp:include page="header.jsp"/>

<h:form>
  <h:panelGrid columns="3" cellpadding="5"
      footerClass="project-background"
      styleClass="project-background"
      rowClasses="project-row"
      columnClasses=",project-input">

    <%-- Header --%>

    <f:facet name="header">
      <h:panelGrid columns="1" width="100%" cellpadding="3"
                   styleClass="project-background" headerClass="page-header">
        <f:facet name="header">
          <h:outputText value="Create a project"/>
        </f:facet>
        <h:outputText value="Application messages." styleClass="errors"/>
      </h:panelGrid>
    </f:facet>

    <%-- Panel data elements --%>

    <h:outputLabel for="nameInput">
      <h:outputText value="Name:"/>
    </h:outputLabel>
    <h:inputText id="nameInput" size="40" required="true">
      <f:validateLength minimum="5"/>
    </h:inputText>
    <h:message for="nameInput" styleClass="errors"/>

    <h:outputLabel for="typeSelectOne">
      <h:outputText value="Type:"/>
    </h:outputLabel>
    <h:selectOneMenu id="typeSelectOne" title="Select the project type"
                     required="true">
      <f:selectItem  itemValue="" itemLabel=""/>
      <f:selectItem  itemValue="0" itemLabel="Internal Database"/>
      <f:selectItem  itemValue="5" itemLabel="External Database"/>
      <f:selectItem  itemValue="10" itemLabel="Internal Web Application" />
      <f:selectItem  itemValue="15" itemLabel="External Web Application" />
      <f:selectItem  itemValue="20" itemLabel="Internal Desktop Application" />
      <f:selectItem  itemValue="25" itemLabel="External Desktop Application"/>
    </h:selectOneMenu>
    <h:message for="typeSelectOne" styleClass="errors"/>

    <h:outputLabel for="initiatedByInput">
      <h:outputText value="Initiated by:"/>
    </h:outputLabel>
    <h:inputText id="initiatedByInput" size="40" required="true">
      <f:validateLength minimum="2"/>
    </h:inputText>
    <h:message for="initiatedByInput" styleClass="errors"/>

    <h:outputLabel for="requirementsInput">
      <h:outputText value="Requirements contact:"/>
    </h:outputLabel>
    <h:inputText id="requirementsInput" size="40"/>
    <h:panelGroup/>

  <h:outputLabel for="requirementsEmailInput">
    <h:outputText value="Requirements contact e-mail:"/>
  </h:outputLabel>
  <h:inputText id="requirementsEmailInput" size="40">
    <jia:validateRegexp expression="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"
                       errorMessage="Please enter a valid e-mail address."/>
  </h:inputText>
  <h:message for="requirementsEmailInput" styleClass="errors"/>

  <%@ include file="project_artifacts.jsp" %>
  <h:panelGroup/>

   <%-- Footer --%>

   <f:facet name="footer">

       <h:panelGroup>

         <%-- Comments block --%>
        <h:panelGrid columns="1" cellpadding="5"
                     styleClass="table-background"
                     rowClasses="table-odd-row,table-even-row">
          <h:outputLabel for="commentsInput">
            <h:outputText value="Your comments:"/>
          </h:outputLabel>
          <h:inputTextarea id="commentsInput" rows="10" cols="80"/>
        </h:panelGrid>

         <%-- Button panel --%>
         <h:panelGrid columns="2" rowClasses="table-odd-row">
           <h:commandButton value="Save" action="save"/>
           <h:commandButton value="Cancel" action="cancel" immediate="true"/>
         </h:panelGrid>
         <h:panelGroup/>

       </h:panelGroup>

   </f:facet>

  </h:panelGrid>

</h:form>

</body>
</html>
</f:view>
