<%@ include file="include/taglibs.jspf"%>


<html>
  <head>
    <title></title>
    <misc:popupCalendarJs />    
    
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
	
  <html:form action="/taskEdit" enctype="multipart/form-data">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>

	<forms:form type="edit" caption="taskedit.title" formid="frmTask" width="500">
            
    
     <forms:html valign="top" join="false">
	   <ctrl:tabset
        property="tabset"
				id="tasktab"
				styleId="tasktab"
				width="100%"
				runat="client"
				bgcolor="#DADFE0"
				formElement="true">

				<ctrl:tab	tabid="generalTab"	title="task.general" tooltip="task.general">            
          <misc:initSelectOptions id="categoryOption" name="categoryOptions" scope="request" />
          <misc:initSelectOptions id="importanceOption" name="importanceOptions" scope="session" />		  	  	  	  	
          <misc:initSelectOptions id="statusOption" name="statusOptions" scope="session" />		 
           	  	  	  	        
          <forms:form type="edit" formid="frmTaskGeneral" noframe="true">
                    
          <forms:text label="event.subject" property="subject"  required="true"  size="75" maxlength="255"/>		    		  
          <forms:text label="event.location" property="location"  required="false"  size="75" maxlength="255"/>		    		  						        
      
          <forms:row>
            <forms:html label="event.start" >
		          <html:text property="start" styleId="start" size="10" maxlength="10"/>
      	      <img src="images/cal.gif" alt="" name="selectStart" id="selectStart" width="16" height="16" border="0">
            </forms:html>
            
            <forms:html label="event.timeformat">
              <html:text property="startHour" styleId="startHour" size="2" maxlength="2"/>:<html:text property="startMinute" styleId="startMinute" size="2" maxlength="2"/>
            </forms:html>
          </forms:row>

         <forms:row>
            <forms:html label="task.due" >
		          <html:text property="due" styleId="due" size="10" maxlength="10"/>
      	      <img src="images/cal.gif" alt="" name="selectDue" id="selectDue" width="16" height="16" border="0">
            </forms:html>
            
            <forms:html label="event.timeformat">
              <html:text property="dueHour" styleId="dueHour" size="2" maxlength="2"/>:<html:text property="dueMinute" styleId="dueMinute" size="2" maxlength="2"/>
            </forms:html>
          </forms:row>          
          
          <forms:row>
            <forms:select label="task.status" property="status" styleId="status" required="true" onchange="taskUpdater.updateCompletePercent(this.value, complete.value, updateCompletePercent)">		    
       		    <base:options empty="empty" name="statusOption"/>
            </forms:select> 

            <forms:text label="task.complete" property="complete" styleId="complete" required="true"  size="5" maxlength="3" onchange="taskUpdater.updateStatus(this.value, DWRUtil.getValue('status'), updateStatus)"/>		    		  
          </forms:row>
          
            <forms:html label="task.completeDate" >
		          <html:text property="completeDate" styleId="completeDate" size="10" maxlength="10"/>
      	      <img src="images/cal.gif" alt="" name="selectCompleteDate" id="selectCompleteDate" width="16" height="16" border="0">
            </forms:html>          
          
          <forms:row>
	          <forms:select label="category"  property="categoryId" required="false" >		    
	      		  <base:options empty="empty" name="categoryOption"/>
	        	</forms:select>    
	          
	          <forms:select label="event.importance"  property="importance" required="true" >		    
	       		  <base:options empty="empty" name="importanceOption"/>
	        	</forms:select> 

          </forms:row>
                    
          <forms:textarea label="event.description" property="description"  required="false"  rows="4" cols="75" valign="top"/>		    		  

          
         </forms:form>
         <misc:popupCalendar element="start" trigger="selectStart" showOthers="true"/>
         <misc:popupCalendar element="due" trigger="selectDue" showOthers="true"/>
         <misc:popupCalendar element="completeDate" trigger="selectCompleteDate" showOthers="true"/>         
         
        </ctrl:tab>
        		  
			  <ctrl:tab tabid="userTab" title="user.users" tooltip="user.users">
	
	     	 <misc:initSelectOptions id="groupUserOptions" name="groupUserOptions" scope="request" taskGroup="true" />
				 <misc:initSelectOptions id="taskUserInSameGroupOptions" name="userInSameGroupOptions" scope="session" taskGroup="true" />  
				  
	       <forms:form noframe="true" type="edit" formid="frmEventUser" width="700">       
	          <c:if test="${groupUserOptions.size > 0}">
	          <forms:select  label="group"  property="groupId"  required="false" >		    
	      		    <base:options  empty="empty" name="groupUserOptions"/>
	      		</forms:select>  
	      		</c:if>
	                          
		       <forms:swapselect label="user.users" valign="top"              
		         property="userIds"              
		         orientation="horizontal"      
		         labelLeft="common.available"      
		         labelRight="common.selected" 
		         runat="client"     
		         size="18"      
		         style="width: 250px;">
		         <base:options name="taskUserInSameGroupOptions"/>
		
		       </forms:swapselect>             
	              
	       </forms:form>         
			  </ctrl:tab>        		  
        		  
		    <ctrl:tab tabid="reminderTab" title="event.reminder" tooltip="event.reminder">   
                    
          <misc:initSelectOptions id="emailUserOptions" name="emailUserOptions" scope="request" />  
        	<misc:initSelectOptions id="timeOption" name="timeOptions" scope="session" />	  	  	  	  	
        	<misc:initSelectOptions id="taskReminderBeforeOptions" name="taskReminderBeforeOptions" scope="session" />	  	          
        
          <forms:form noframe="true" type="edit" formid="frmTaskReminder" width="600">       
          <forms:html>
          		<ctrl:list noframe="true" id="reminderList" 
          			 property="reminderList"     			 
          			 rows="${sessionScope.noRows}"  
                 title="event.reminder"	              		
                 formElement="true">
          		<ctrl:columntext title="user.eMail" property="email" width="250" maxlength="50" sortable="true"/>
              <ctrl:columntext title="task.reminder.time" property="timeStr" width="150" sortable="true"/>
              <ctrl:columntext title="task.reminder.before" property="beforeStr" width="150" sortable="true"/>              
          		<ctrl:columndelete tooltip="common.delete"/>		
          	  </ctrl:list>
          </forms:html>

          <forms:row>
            <forms:html>
              <bean:message key="user.eMail"/>
            </forms:html>
            <forms:html>
            </forms:html>
            <forms:html colspan="2">
              <bean:message key="task.reminder.time"/>
            </forms:html>
            <forms:html>
              <bean:message key="task.reminder.before"/>
            </forms:html>            
            
          </forms:row>
          <forms:row join="true">                
        			  <forms:text property="reminderEmail" required="false" size="30" maxlength="255"/>
                <forms:select  property="reminderEmailSelect"  required="false" >		    
            		    <base:options  empty="empty" name="emailUserOptions"/>
            		</forms:select>    
                
                <forms:text property="reminderTime" required="false" size="5" maxlength="5"/>
          	    <forms:select    property="reminderTimeUnit">
          	      <base:options  name="timeOption" empty="empty"/>
          	    </forms:select>				 
          	    <forms:select    property="reminderBefore">
          	      <base:options  name="taskReminderBeforeOptions"/>
          	    </forms:select>		                
                               
            <forms:html>
              <input id='btnAdd' title='<bean:message key="button.title.add"/>' name='btnAdd' src='<bean:message key="buttons.src"/>/btnAdd1.gif' type='image'>            
            </forms:html>                    
          </forms:row>
          </forms:form>
        
        </ctrl:tab>

				
		  <ctrl:tab tabid="attachmentTab" title="task.attachment" tooltip="task.attachment">   
                    
        
          <forms:form noframe="true" type="edit" formid="frmTaskAttachment" width="500">       
          <forms:html>
          		<ctrl:list noframe="true" id="attachmentList" 
          			 property="attachmentList"     			 
          			 rows="${sessionScope.noRows}"  
                 title="task.attachment"	              		
                 formElement="true">
          		<ctrl:columntext title="task.attachment.fileName" property="fileName" width="450" maxlength="100" sortable="true"/>
          		<ctrl:columntext title="task.attachment.size" property="fileSize" width="100" sortable="true" align="right" converter="ch.ess.base.web.DocumentSizeConverter"/>          		
							<ctrl:columnhtml id="row" type="org.apache.commons.beanutils.DynaBean" title="">
								<c:if test="${not empty row.map.dbId}">
									<c:url value="taskDocument.do" var="downloadLink">
										<c:param name="id" value="${row.map.dbId}" />
									</c:url>
									<a href="${downloadLink}" title="${row.map.fileName}" target="_parent"><img src="images/download.gif" border="0"></a>
								</c:if>
							</ctrl:columnhtml>
          		
          		<ctrl:columndelete tooltip="common.delete"/>		
          	  </ctrl:list>
          </forms:html>

          
          <forms:row join="true">                
        		<forms:file styleId="attachmentFormFile" property="attachmentFormFile" size="70"/>
                               
            <forms:html>
              <input onclick="startProgress()" id='btnAddAttachment' title='<bean:message key="button.title.add"/>' name='btnAddAttachment' src='<bean:message key="buttons.src"/>/btnAdd1.gif' type='image'>            
            </forms:html>                    
          </forms:row>
          </forms:form>
        
        </ctrl:tab>
				
				
        
      </ctrl:tabset>				        
     </forms:html>
								
		<forms:buttonsection join="false">
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>			
						
      <c:if test="${TaskForm.deletable}">
			<forms:button onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>   
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
      document.getElementById('btnAddAttachment').disabled = true;
      document.getElementById('attachmentFormFile').disabled = true;        

      var fileIndex = uploadInfo.fileIndex;
      var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);
      document.getElementById('progressBarText').innerHTML = '<bean:message key="common.upload.progress"/>: ' + progressPercent + '%';
      document.getElementById('progressBarBoxContent').style.width = parseInt(progressPercent * 3.5) + 'px';
      window.setTimeout('refreshProgress()', 1000);
    } else {
      document.getElementById('btnAddAttachment').disabled = false;
      document.getElementById('attachmentFormFile').disabled = false;
    }

    return true;
	}

	function startProgress() {
        	
    document.getElementById('progressBar').style.display = 'block';
    document.getElementById('progressBarText').innerHTML = '<bean:message key="common.upload.progress"/>: 0%';

    // wait a little while to make sure the upload has started ..
    window.setTimeout("refreshProgress()", 1500);
    return true;
  }
  
  </script>  
  
  <misc:confirm key="task.delete"/>
</body>
</html>