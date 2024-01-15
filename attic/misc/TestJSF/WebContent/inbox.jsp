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

<f:view>
<html>
  <head>
    <title>
      <h:outputText value="ProjectTrack - Inbox"/>
    </title>
    <link rel="stylesheet" type="text/css"
          href="stylesheet.css"/>
  </head>

<body class="page-background">

<jsp:include page="header.jsp"/>

<h:form>

  <h:panelGrid headerClass="page-header" styleClass="table-background"
              columns="1" cellpadding="5">

    <%-- Header --%>
    <f:facet name="header">
      <h:outputText value="Inbox - approve or reject projects"/>
    </f:facet>

    <%-- Panel data --%>

    <h:outputText value="Application messages." styleClass="errors"/>
    <h:panelGrid columns="6"
                  styleClass="table-background"
                  rowClasses="table-odd-row,table-even-row" cellpadding="3">

      <%-- Header (technically the first row) --%>

      <h:commandLink styleClass="table-header">
      	<h:outputText value="Project name"/>
      </h:commandLink>
      <h:commandLink styleClass="table-header">
      	<h:outputText value="Type"/>
      </h:commandLink>
      <h:commandLink styleClass="table-header">
      	<h:outputText value="Status"/>
      </h:commandLink>
      <h:panelGroup/>
      <h:panelGroup/>
      <h:panelGroup/>

      <%-- Panel data  --%>

      <h:outputText value="Inventory Manager v2.0"/>
      <h:outputText value="Internal Desktop Application"/>
      <h:outputText value="Requirements/Analysis"/>
      <h:commandLink action="approve">
        <h:outputText value="Approve"/>
      </h:commandLink>
      <h:commandLink action="reject">
        <h:outputText value="Reject"/>
      </h:commandLink>
      <h:commandLink action="details">
        <h:outputText value="Details"/>
      </h:commandLink>

      <h:outputText value="TimeTracker"/>
      <h:outputText value="Internal Web Application"/>
      <h:outputText value="Requirements/Analysis"/>
      <h:commandLink action="approve">
        <h:outputText value="Approve"/>
      </h:commandLink>
      <h:commandLink action="reject">
        <h:outputText value="Reject"/>
      </h:commandLink>
      <h:commandLink action="details">
        <h:outputText value="Details"/>
      </h:commandLink>

    </h:panelGrid>

  </h:panelGrid>

</h:form>

</body>
</html>
</f:view>
