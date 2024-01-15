<%-- Edit comments field. Intended for use within a panel grid with one column. --%>
    <h:panelGrid columns="1" cellpadding="5"
                 styleClass="table-background"
                 rowClasses="table-odd-row,table-even-row">
      <h:outputLabel for="commentsInput">
        <h:outputText value="Your comments:"/>
      </h:outputLabel>
      <h:inputTextarea id="commentsInput" rows="10" cols="80"/>
    </h:panelGrid>
