<%-- Edit and display fields for main project details (approval and rejection).
     Must be included in a panel grid with two columns. --%>

    <h:outputText value="Name:"/>
    <h:outputText value="Inventory Manager 2.0"
                  styleClass="project-data"/>

    <h:outputText value="Type:"/>
    <h:outputText value="Internal Web Application"
                  styleClass="project-data"/>

    <h:outputText value="Initiated by:"/>
    <h:outputText value="Rip Van Winkle"
                  styleClass="project-data"/>

    <h:outputText value="Requirements contact:"/>
    <h:outputText value="Joan TooBusy"
                  styleClass="project-data"/>

    <h:outputText value="Requirements contact e-mail:"/>
    <h:outputText value="toobusy@deathmarch.com"
                  styleClass="project-data"/>

    <h:outputText value="Initial comments:"/>
    <h:outputText value="The first version is horrible and completely unusable.
                         It's time to rewrite it."
                  styleClass="project-data"/>
<%--
  <h:outputLabel for="artifactSelect">
    <h:outputText value="Completed artifacts:"/>
  </h:outputLabel>
  <h:selectManyCheckbox id="artifactSelect" layout="PAGE_DIRECTION">
    <f:selectItem itemValue="0" itemLabel="Proposal document"/>
    <f:selectItem itemValue="1" itemLabel="Requirements document" />
    <f:selectItem itemValue="2" itemLabel="Architecture specification" />
    <f:selectItem itemValue="3" itemLabel="Test plan" />
    <f:selectItem itemValue="4" itemLabel="Deployment guidelines" />
    <f:selectItem itemValue="5" itemLabel="Maintenance documentation"/>
    <f:selectItem itemValue="6" itemLabel="User documentation"/>
  </h:selectManyCheckbox>
--%>
