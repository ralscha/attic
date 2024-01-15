<%-- Edit the artifact list.
     Must be included in a panel grid with two columns. --%>

 <h:outputLabel for="artifactSelect">
    <h:outputText value="Completed artifacts:"/>
  </h:outputLabel>
  <h:selectManyCheckbox id="artifactSelect" layout="pageDirection"
                        styleClass="project-input">
    <f:selectItem itemValue="0" itemLabel="Proposal document"/>
    <f:selectItem itemValue="1" itemLabel="Requirements document" />
    <f:selectItem itemValue="2" itemLabel="Architecture specification" />
    <f:selectItem itemValue="3" itemLabel="Test plan" />
    <f:selectItem itemValue="4" itemLabel="Deployment guidelines" />
    <f:selectItem itemValue="5" itemLabel="Maintenance documentation"/>
    <f:selectItem itemValue="6" itemLabel="User documentation"/>
  </h:selectManyCheckbox>
