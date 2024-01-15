<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
  </head>
  <body>
	    
	    
  <html:form action="/directoryList" focus="value(directoryName)">
	<forms:form type="search" formid="frmListDirectory">

      <forms:html>
      <table>
        <tr>
          <td><bean:message key="directory.title"/></td>
          <td><bean:message key="directory.file"/></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><ctrl:text property="value(directoryName)" size="20" maxlength="255"/></td>
          <td><ctrl:text property="value(fileName)" size="20" maxlength="255"/></td>
    			<td><ctrl:button base="buttons.src" name="btnSearch"  src="btnSearch1.gif"  title="button.title.search"/></td>
        </tr>
      </table>
      </forms:html>   
	  	
	</forms:form>
	</html:form>
	
	<p></p>	    
	       

		<util:imagemap name="im_tree">
			<util:imagemapping rule="file" src="fw/def/image/tree/20/item.gif" width="16" height="16" />
      <util:imagemapping rule="doc" src="images/doc_word.png" width="16" height="16" />
      <util:imagemapping rule="xls" src="images/doc_excel.png" width="16" height="16" />      
      <util:imagemapping rule="pdf" src="images/doc_pdf.gif" width="16" height="16" />        
		</util:imagemap>			
	
		<ctrl:treelist 
		  id="directoryList" 
		  action="/directoryList.do" 
		  name="listControl" 
		  scope="session" 
		  title="directory.menu"
			createButton="true" 
			refreshButton="true" 
			root="false"			
			linesAtRoot="true" 
			expandMode="multiple" 
			groupselect="false" 
			rows="${sessionScope.noRows}"
			runat="client" 
			checkboxes="false"
			width="600">
			<ctrl:columntree title="directory.name" property="name"  width="300" maxlength="50" sortable="true" imageProperty="image" imagemap="im_tree"/>
      <ctrl:columntext title="directory.fileSize" property="fileSize" width="100" sortable="true" align="right" converter="ch.ess.base.web.DocumentSizeConverter"/>                   

      <ctrl:columngroup title="common.action" align="center">
      <ctrl:columnhtml id="row" type="org.apache.commons.beanutils.DynaBean">
        <c:if test="${not empty row.map.fileId}">
          <c:url value="showDocument.do" var="downloadLink">
            <c:param name="id" value="${row.map.fileId}" />
          </c:url>
          <a href="${downloadLink}" title="${row.map.fileName}" target="_parent"><img src="images/download.gif" border="0"></a>
        </c:if>
      </ctrl:columnhtml>            
			<ctrl:columnadd tooltip="directory.add" property="addable" />
      <misc:columnupload tooltip="directory.upload" property="uploadable" />
			<ctrl:columnedit tooltip="common.edit" property="editable" />
			<ctrl:columndelete tooltip="common.delete" property="deletable"	onclick="return confirmRequest(' @{bean.map.name}');" />
			</ctrl:columngroup>
   	</ctrl:treelist>			

	
    <misc:confirm key="directory.delete"/>
</body>
</html>