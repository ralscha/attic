<%@ include file="include/taglibs.jspf"%>

<html>
  <head><title></title>
    <script type='text/javascript' src='dwr/interface/taskUpdater.js'></script>
    <script type='text/javascript' src='dwr/interface/uploadMonitor.js'></script>
    <script type='text/javascript' src='dwr/engine.js'></script>
    <script type='text/javascript' src='dwr/util.js'></script>    
    
     <style type="text/css">     
       #progressBar { padding-top: 5px; }
       #progressBarText { font-family : Arial; font-size : 10pt; }
       #progressBarBox { width: 350px; height: 20px; border: 1px inset; background: #eee;}
       #progressBarBoxContent { width: 0; height: 20px; border-right: 1px solid #444; background: #9ACB34; }
     </style>  
  </head>
  <body>	

	<html:form action="/fileEdit" enctype="multipart/form-data">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>
  
  <misc:initSelectOptions id="directoryOptions" name="directoryOptions" scope="request" />
  <misc:initSelectOptions id="userOption" name="userOptions" scope="session" permission="document"/>
  <misc:initSelectOptions id="groupOption" name="groupOptions" scope="session" />
  
	<forms:form type="edit" caption="fileedit.title" formid="frmFile">
    
    <forms:html valign="top" join="false">
	   <ctrl:tabset
        property="tabset"
				id="directorytab"
				styleId="directorytab"
				width="100%"
				bgcolor="#DADFE0"
				formElement="true">
    
      <ctrl:tab	tabid="general"	title="directory.general" tooltip="directory.general">  
      
        <c:if test="${FileForm.hasWritePermission}"> 
        <c:set value="edit" var="displayEditValue"/>
        </c:if>
        <c:if test="${not FileForm.hasWritePermission}"> 
        <c:set value="display" var="displayEditValue"/>
        </c:if> 
        
        <forms:form type="${displayEditValue}" formid="frmFileGeneral" noframe="true">
                
			    <forms:select label="directory.title" property="directoryId" required="true">		    
			      <base:options empty="empty" name="directoryOptions"/>
			 		</forms:select> 
			    
			    <c:if test="${not empty FileForm.modelId}">
			    <forms:text label="directory.fileName" property="name" size="73" maxlength="255"/>                  
			    <forms:plaintext label="directory.fileSize" property="fileSize" />
			    </c:if>
				  <forms:file label="directory.file" styleId="formFile" property="formFile" size="60"/>
		    
			    <forms:textarea property="description" valign="top" label="directory.description" rows="5" cols="70" maxlength="1000"/>
		    </forms:form>       
	    </ctrl:tab>
	    
      <ctrl:tab	tabid="permission" title="directory.permission" tooltip="directory.permission">    
        <forms:form type="edit" formid="frmFilePermission" noframe="true">

  			<forms:html valign="top" label="user.users">
					<ctrl:list id="userPermissionList" property="userPermissionList" noframe="true"	rows="${sessionScope.noRows}" title="directory.permission" formElement="true">
						<ctrl:columntext title="user.name" property="name" width="100" maxlength="50" sortable="true" />
						<ctrl:columntext title="user.firstName" property="firstName" width="100" sortable="true" />
						<ctrl:columncheckbox title="directory.writePermission"	property="write" width="90" sortable="true" />
						<ctrl:columncheckbox title="directory.readPermission" property="read"	width="90" sortable="true" />
						<c:if test="${FileForm.hasWritePermission}">
							<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.name}');" />
						</c:if>
					</ctrl:list>
					</forms:html>
					<c:if test="${FileForm.hasWritePermission}">
						<forms:row join="true">
							<forms:html label="">
							<table>
								<tr>
									<td>&nbsp;<bean:message key="user" /></td>
									<td>&nbsp;<bean:message key="directory.writePermission" /></td>
									<td>&nbsp;<bean:message key="directory.readPermission" /></td>
									<td></td>
								</tr>
								<tr>
									<td>
									  <ctrl:select property="userId">
   										<base:options empty="empty" name="userOption" />
									  </ctrl:select>
									</td>
									<td><ctrl:checkbox property="userWritePermission" /></td>
									<td><ctrl:checkbox property="userReadPermission" /></td>
									<td><input id='btnAdd' title='<bean:message key="button.title.add"/>' name='btnAdd' src='<bean:message key="buttons.src"/>/btnAdd1.gif' type='image'></td>
								</tr>
							</table>
							</forms:html>
						</forms:row>
					</c:if>   
					
					<c:if test="${groupOption.size > 0}">									
 			    <forms:html valign="top" label="group.groups" join="false">
					<ctrl:list id="groupPermissionList" property="groupPermissionList" noframe="true"	rows="${sessionScope.noRows}" title="directory.permission" formElement="true">
						<ctrl:columntext title="group" property="name" width="200" maxlength="50" sortable="true" />
						<ctrl:columncheckbox title="directory.writePermission"	property="write" width="90" sortable="true" />
						<ctrl:columncheckbox title="directory.readPermission" property="read"	width="90" sortable="true" />
						<c:if test="${FileForm.hasWritePermission}">
							<ctrl:columndelete tooltip="common.delete" onclick="return confirmRequest('@{bean.map.name}');" />
						</c:if>
					</ctrl:list>
					</forms:html>
					<c:if test="${FileForm.hasWritePermission}">
						<forms:row join="true">
							<forms:html label="">
							<table>
								<tr>
									<td>&nbsp;<bean:message key="group" /></td>
									<td>&nbsp;<bean:message key="directory.writePermission" /></td>
									<td>&nbsp;<bean:message key="directory.readPermission" /></td>
									<td></td>
								</tr>
								<tr>
									<td>
									  <ctrl:select property="groupId">
   										<base:options empty="empty" name="groupOption" />
									  </ctrl:select>
									</td>
									<td><ctrl:checkbox property="groupWritePermission" /></td>
									<td><ctrl:checkbox property="groupReadPermission" /></td>
									<td><input id='btnAdd' title='<bean:message key="button.title.add"/>' name='btnAdd' src='<bean:message key="buttons.src"/>/btnAdd1.gif' type='image'></td>
								</tr>
							</table>
							</forms:html>
						</forms:row>
					</c:if>  		
					</c:if>			        
        
        </forms:form>
      </ctrl:tab>
    </ctrl:tabset>
    </forms:html>    		  
	    
	  	           		
		<forms:buttonsection join="false">
		  <c:if test="${FileForm.hasWritePermission}"> 
			<forms:button   base="buttons.src" onclick="startProgress()" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" onclick="startProgress()" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
      <c:if test="${FileForm.deletable}">
			<forms:button  onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      </c:if>
      </c:if>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  
  <div id="progressBar" style="display: none;">
    <div id="theMeter">
      <div id="progressBarText"></div>

      <div id="progressBarBox">
        <div id="progressBarBoxContent"></div>
      </div>
    </div>
  </div>  
  
  <misc:confirm key="directory.delete"/>
  
  <script type="text/javascript">
  function updateCompletePercent(values) {
    DWRUtil.setValues(values);
  }
  function updateStatus(values) {
    DWRUtil.setValues(values);
  }  
  
  function refreshProgress() {
    uploadMonitor.getUploadInfo(updateProgress);
  }

  function updateProgress(uploadInfo) {
    if (uploadInfo.inProgress) {
      document.getElementById('formFile').disabled = true;        

      var fileIndex = uploadInfo.fileIndex;
      var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);
      document.getElementById('progressBarText').innerHTML = '<bean:message key="common.upload.progress"/>: ' + progressPercent + '%';
      document.getElementById('progressBarBoxContent').style.width = parseInt(progressPercent * 3.5) + 'px';
      window.setTimeout('refreshProgress()', 1000);
    } else {
      document.getElementById('formFile').disabled = false;
    }

    return true;
  }

  function startProgress() {
    try {
	    if (document.getElementById('formFile').value != '') {      
	      document.getElementById('progressBar').style.display = 'block';
	      document.getElementById('progressBarText').innerHTML = '<bean:message key="common.upload.progress"/>: 0%';
	  
	      // wait a little while to make sure the upload has started ..
	      window.setTimeout("refreshProgress()", 1500);
	    }
	    return true;
    } catch(e) {}
  }
  
  </script>   
</body>
</html>
