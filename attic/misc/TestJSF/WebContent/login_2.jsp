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
</head>

<body>

<h:form>

<table cellpadding="0" cellspacing="0">
  <tr>
   <td>
     <h:graphicImage url="/images/logo.gif" alt="Welcome to ProjectTrack"
                     title="Welcome to ProjectTrack" width="149" height="160"/>
   </td>
   <td>
     <table cellpadding="5" cellspacing="3">
       <tr>
         <td colspan="2">
           <font face="Arial, sans-serif" size="4" style="font-size: 26pt">
             <h:outputText value="ProjectTrack"/>
           </font>
         </td>
       </tr>
       <tr>
         <td>
           <h:outputLabel for="userNameInput">
             <h:outputText value="Enter your user name:"/>
           </h:outputLabel>
         </td>
         <td>
           <h:inputText id="userNameInput" size="20" maxlength="30"/>
         </td>
       </tr>
       <tr>
         <td>
           <h:outputLabel for="passwordInput">
             <h:outputText value="Password:"/>
           </h:outputLabel>
         </td>
         <td>
           <h:inputSecret id="passwordInput" size="20" maxlength="20"/>
         </tr>
       </tr>
       <tr>
         <td/>
         <td>
           <h:commandButton action="success" value="submit" title="Submit"/>
       </tr>
     </table>
   </td>
  </tr>
</table>

</h:form>

</body>
</html>
</f:view>
