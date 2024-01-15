<%-- Application header fragment. Intended to be included in another page.
     That page should have a reference to the stylesheet. --%>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="jsf-in-action-components" prefix="jia"%>

<f:subview id="header">

  <h:form>

    <h:panelGrid columns="3" cellspacing="0" cellpadding="0"
                  styleClass="header" width="100%">

      <jia:navigatorToolbar id="toolbar" layout="horizontal"
           headerClass="toolbar-header" itemClass="toolbar-command"
           selectedItemClass="toolbar-command" iconClass="toolbar-icon"
           immediate="false">

        <f:facet name="header">
          <h:outputText value="ProjectTrack:"/>
        </f:facet>

        <jia:navigatorItem name="inbox" label="Inbox" icon="/images/inbox.gif"
                           action="inbox"/>
        <jia:navigatorItem name="showAll" label="Show All" icon="/images/show_all.gif"
                           action="show_all"/>
        <jia:navigatorItem name="createNew" label="Create New" icon="/images/create.gif"
                           action="create"/>
        <jia:navigatorItem name="logout" label="Logout" icon="/images/logout.gif"
                           action="logout"/>

    </jia:navigatorToolbar>
<%--

   Toolbar without HtmlNavigator component.

      <h:panelGrid id="header" columns="5" cellpadding="4" cellspacing="0" border="0">

        <h:outputText value="ProjectTrack:" styleClass="toolbar-header"/>

        <h:commandLink action="inbox">
          <h:graphicImage url="images/inbox.gif" styleClass="toolbar-icon"
                          alt="Inbox"/>
          <h:outputText value="Inbox" styleClass="toolbar-command"/>
        </h:commandLink>

        <h:commandLink action="show_all">
          <h:graphicImage url="images/show_all.gif" styleClass="toolbar-icon"
                          alt="Show all projects"/>
          <h:outputText value="Show all" styleClass="toolbar-command"/>
        </h:commandLink>

        <h:commandLink action="create">
          <h:graphicImage url="images/create.gif" styleClass="toolbar-icon"
                          alt="Create a new project"/>
          <h:outputText value="Create new" styleClass="toolbar-command"/>
        </h:commandLink>

        <h:commandLink action="logout">
          <h:graphicImage url="images/logout.gif" styleClass="toolbar-icon"
                          alt="Logout"/>
          <h:outputText value="Logout" styleClass="toolbar-command"/>
        </h:commandLink>

      </h:panelGrid>
--%>
      <h:panelGroup>
        <h:outputLabel for="languageSelect">
          <h:outputText value="Language:"  styleClass="language-select"/>
        </h:outputLabel>
        <h:selectOneListbox id="languageSelect" size="1" styleClass="language-select">
          <f:selectItem itemLabel="English" itemValue="English"/>
          <f:selectItem itemLabel="Russian" itemValue="Russian"/>
        </h:selectOneListbox>
        <h:commandButton value="Go!" styleClass="language-select-button"/>
      </h:panelGroup>

      <h:outputText value="(proj_mgr)" styleClass="user-name"/>

    </h:panelGrid>
  </h:form>
</f:subview>


