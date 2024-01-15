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
      <h:outputText value="ProjectTrack - Project details"/>
    </title>
    <link rel="stylesheet" type="text/css"
          href="stylesheet.css"/>
  </head>

<body class="page-background">

<jsp:include page="header.jsp"/>

<h:form>

  <h:panelGrid id="projectPanel" columns="2" cellpadding="5"
      footerClass="project-background"
      columnClasses=",project-data"
      styleClass="project-background"
      rowClasses="project-row">

    <%-- Header --%>

    <f:facet name="header">
      <h:panelGrid columns="1" width="100%" cellpadding="3"
                   styleClass="project-background" rowClasses="page-header">
          <h:outputText value="Project details"/>
      </h:panelGrid>
    </f:facet>

    <%-- Panel data elements --%>

    <%@ include file="project_info.jsp" %>


    <h:outputText value="Completed artifacts:"/>
    <h:panelGrid columns="1" rowClasses="project-data"
                  cellpadding="0" cellspacing="0">
      <h:outputText value="Proposal document"/>
      <h:outputText value="Project plan"/>
    </h:panelGrid>

    <%-- Footer - history --%>

    <f:facet name="footer">

    <h:panelGroup>
      <h:panelGrid columns="1" cellpadding="5" styleClass="table-background">

       <f:facet name="header">
         <h:outputText value="History" styleClass="table-header"/>
       </f:facet>

       <h:panelGrid columns="1" width="100%" border="1"
                    styleClass="table-even-row">

        <h:panelGrid columns="3" cellpadding="7"
                     styleClass="table-even-row">
          <h:outputText value="Tuesday, March 4, 2003 04:30 PM"/>
          <h:outputText value="Proposal -> Planning"/>
          <h:outputText value="(Project Manager)"/>
        </h:panelGrid>

        <h:panelGrid columns="1" cellpadding="3"
                     styleClass="table-odd-row" width="100%">
          <h:outputText value="Comments:"/>
          <h:outputText value="Funding has been approved.
                               The users are excited about the prospect of
                               having something they can use."
                        styleClass="project-data"/>
        </h:panelGrid>
       </h:panelGrid>

       <h:panelGrid columns="1" width="100%" border="1"
                     styleClass="table-even-row">

        <h:panelGrid columns="3" cellpadding="7"
           styleClass="table-even-row">
          <h:outputText value="Monday, August 11, 2003 08:30 PM"/>
          <h:outputText value="Planning -> Requirements/Analysis"/>
          <h:outputText value="(Project Manager)"/>
        </h:panelGrid>

        <h:panelGrid columns="1" cellpadding="3"
           styleClass="table-odd-row" width="100%">
          <h:outputText value="Comments:"/>
          <h:outputText value="Initial resources have beeen allocated and a
                                rough plan has been developed."
                         styleClass="project-data"/>
        </h:panelGrid>
       </h:panelGrid>

      </h:panelGrid>

       <h:commandButton value="Ok" action="inbox" style="margin-top: 5px"/>

    </h:panelGroup>
    </f:facet>

  </h:panelGrid>

</h:form>

</body>
</html>
</f:view>
