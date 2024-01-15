<%@ include file="include/taglibs.jspf"%>


<html>
  <head>
    <title></title>
  </head>

  <body>
	     
	<html:form action="/license" focus="license">
						          
	<forms:form type="edit" caption="license" formid="frmImport">

    <forms:text label="license" property="license" size="100" maxlength="1000"/>
                                                                                     			
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>		   
    </forms:buttonsection>  
	</forms:form>
  </html:form>	  
  
</body>
</html>