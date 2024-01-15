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
      <h:outputText value="ProjectTrack"/>
    </title>

    <link rel="stylesheet" type="text/css" href="stylesheet.css"/>

    <script language="JavaScript">

       function set_image(button, img)
       {
         button.src = img;
       }

    </script>
  </head>

<body>


<h:form>

  <h:panelGrid columns="2" border="0" cellpadding="3" cellspacing="3">

     <h:graphicImage url="/images/logo.gif" alt="Welcome to ProjectTrack"
                     title="Welcome to ProjectTrack" width="149" height="160"/>

       <h:panelGrid columns="3" border="0" cellpadding="5" cellspacing="3"
                    headerClass="login-heading">

          <f:facet name="header">
            <h:outputText value="ProjectTrack" />
          </f:facet>

          <h:outputLabel for="userNameInput">
           <h:outputText value="Enter your user name:"/>
          </h:outputLabel>
          <h:inputText id="userNameInput" size="20" maxlength="30"
                       required="true">
            <f:validateLength minimum="5" maximum="30"/>
          </h:inputText>
          <h:message for="userNameInput"/>

          <h:outputLabel for="passwordInput">
            <h:outputText value="Password:"/>
          </h:outputLabel>
          <h:inputSecret id="passwordInput" size="20" maxlength="20"
                         required="true">
            <f:validateLength minimum="5" maximum="15"/>
          </h:inputSecret>
          <h:message for="passwordInput"/>

          <h:panelGroup/>
          <h:commandButton action="success" title="Submit"
                           image="#{facesContext.externalContext.requestContextPath}/images/submit.gif"
                           onmouseover="set_image(this,
                           '#{facesContext.externalContext.requestContextPath}/images/submit_over.gif')"
                           onmouseout="set_image(this,
                           '#{facesContext.externalContext.requestContextPath}/images/submit.gif');"/>

          <h:panelGroup/>

       </h:panelGrid>

  </h:panelGrid>
</h:form>

</body>
</html>
</f:view>
