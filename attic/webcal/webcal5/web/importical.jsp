<%@ include file="include/taglibs.jspf"%>


<html>
  <head>
    <title></title>
  </head>

  <body>
	     
	<html:form action="/importIcal" enctype="multipart/form-data">
						          
	<forms:form type="edit" caption="event.import.menu" formid="frmImport">
		<forms:file property="importFile" size="50" label="event.import.file"></forms:file>
       
                		                            			
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnImport"  src="btnImport1.gif"  title="button.title.import"/>		   
        </forms:buttonsection>  
	</forms:form>
  </html:form>	  
  
  
  <a href="exportIcal.do">Export</a>
</body>
</html>