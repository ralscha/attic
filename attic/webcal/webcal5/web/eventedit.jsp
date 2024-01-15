<%@ include file="include/taglibs.jspf"%>


<html>
  <head>
    <title></title>
    <misc:popupCalendarJs />    
  </head>
  <body>
	
  <html:form action="/eventEdit">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>
  <html:hidden property="from"/>
  <html:hidden property="inputMonth"/>
  <html:hidden property="inputYear"/>
  <html:hidden property="inputDay"/>  

	<forms:form type="edit" caption="eventedit.title" formid="frmEvent">
            
    
     <forms:html valign="top" join="false">
	   <ctrl:tabset
        property="tabset"
				id="eventtab"
				styleId="eventtab"
				width="100%"
				bgcolor="#DADFE0"
				formElement="true">

				<ctrl:tab	tabid="generalTab"	title="event.general" tooltip="event.general">            
          <misc:initSelectOptions id="categoryOption" name="categoryOptions" scope="request" />
          <misc:initSelectOptions id="importanceOption" name="importanceOptions" scope="session" />		  	  	  	  	
          <misc:initSelectOptions id="sensitivityOption" name="sensitivityOptions" scope="session" />		  	  	  	  	
        
          <forms:form type="edit" formid="frmEventGeneral" noframe="true" width="725">
          <forms:text label="event.subject" property="subject"  required="true"  size="75" maxlength="255"/>		    		  
          <forms:text label="event.location" property="location"  required="false"  size="75" maxlength="255"/>		    		  						        
      
          <forms:row>
          
            <forms:html label="event.start" >
		          <html:text property="start" styleId="start" size="10" maxlength="10" onchange="if (end.value=='') end.value=this.value" />
      	      <img src="images/cal.gif" alt="" name="selectStart" id="selectStart" width="16" height="16" border="0">
        		  <img title='<bean:message key="fw.form.required" bundle="com.cc.framework.message"/>' width='9' height='13' style='margin-left:3px;' border='0' src='fw/def/image/required.gif'>      	      
            </forms:html>
            
            <forms:html label="event.timeformat">
              <html:text property="startHour" styleId="startHour" size="2" maxlength="2"/>:<html:text property="startMinute" styleId="startMinute" size="2" maxlength="2"/>
            </forms:html>
                               
            <forms:checkbox label="event.allday" property="allday"/>		    		  						        
          </forms:row>
          <forms:row>

            <forms:html label="event.end" >
		          <html:text property="end" styleId="end" size="10" maxlength="10" />
      	      <img src="images/cal.gif" alt="" name="selectEnd" id="selectEnd" width="16" height="16" border="0">
            </forms:html>
            
            <forms:html label="event.timeformat">
              <html:text property="endHour" styleId="endHour" size="2" maxlength="2"/>:<html:text property="endMinute" styleId="endMinute" size="2" maxlength="2"/>
            </forms:html>
                        	    		  						                    
          </forms:row>
          
          <forms:row>                
          <forms:select label="category"  property="categoryId" required="true" >		    
      		    <base:options empty="empty" name="categoryOption"/>
      		</forms:select>    
          				
          <forms:select label="event.importance"  property="importance" required="true" >		    
      		    <base:options empty="empty" name="importanceOption"/>
      		</forms:select> 
          
          <forms:select label="event.sensitivity"  property="sensitivity" required="true" >		 
      		    <base:options empty="empty" name="sensitivityOption"/>
      		</forms:select>                         
          </forms:row>
          				        
          <forms:textarea label="event.description" property="description"  required="false"  rows="4" cols="75" valign="top"/>		    		  
         </forms:form>
         <misc:popupCalendar element="start" trigger="selectStart" showOthers="true"/>
         <misc:popupCalendar element="end" trigger="selectEnd" showOthers="true"/>
         
        </ctrl:tab>
        
		  <ctrl:tab tabid="userTab" title="user.users" tooltip="user.users">
		    <html:hidden property="allday"/>

     	 <misc:initSelectOptions id="groupUserOptions" name="groupUserOptions" scope="request" eventGroup="true" />
			 <misc:initSelectOptions id="userInSameGroupOptions" name="userInSameGroupOptions" scope="session" eventGroup="true"/>  
			  
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
         <base:options name="userInSameGroupOptions"/>

       </forms:swapselect>             
              
          </forms:form>         
		  </ctrl:tab>
		  
		  <ctrl:tab tabid="reminderTab" title="event.reminder" tooltip="event.reminder">   
          <html:hidden property="allday"/> 
                    
          <misc:initSelectOptions id="emailUserOptions" name="emailUserOptions" scope="request" />  
        	 <misc:initSelectOptions id="timeOption" name="timeOptions" scope="session" />	  	  	  	  	
        
          <forms:form noframe="true" type="edit" formid="frmEventReminder" width="700">       
          <forms:html>
          		<ctrl:list noframe="true" id="reminderList" 
          			 property="reminderList"     			 
          			 rows="${sessionScope.noRows}"  
                 title="event.reminder"	              		
                 formElement="true">
          		<ctrl:columntext title="user.eMail" property="email" width="250" maxlength="50" sortable="true"/>
              <ctrl:columntext title="event.reminder.time" property="timeStr" width="200" sortable="true"/>
          		<ctrl:columndelete tooltip="common.delete"/>		
          	  </ctrl:list>
          </forms:html>

          <forms:row>
            <forms:html>
              <bean:message key="user.eMail"/>
            </forms:html>
            <forms:html>
            </forms:html>
            <forms:html>
              <bean:message key="event.reminder.time"/>
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
            <forms:html>
              <input id='btnAdd' title='<bean:message key="button.title.add"/>' name='btnAdd' src='<bean:message key="buttons.src"/>/btnAdd1.gif' type='image'>            
            </forms:html>                    
          </forms:row>
          </forms:form>
        
        </ctrl:tab>

				<ctrl:tab tabid="recurrenceTab" title="event.recurrence" tooltip="event.recurrence">    
          <html:hidden property="allday"/> 
        
          <misc:initSelectOptions id="weekDayOption" name="weekdayOptions" scope="session" />	
          <misc:initSelectOptions id="monthOption" name="monthOptions" scope="session" />	  	  	  	  	          
          <misc:initSelectOptions id="posOption" name="posOptions" scope="session" />	  	  	  	  	          
          
          <forms:form noframe="true" type="edit" formid="frmEventRecurrence" width="700">    
            <forms:checkbox label="event.recurrence.active" property="recurrenceActive"/>
         		<forms:section title="event.recurrence.pattern">
              <forms:row>
                <forms:radio property="recurrenceType" label="event.recurrence.daily" value="0"/>
                <forms:html><bean:message key="event.recurrence.daily.every"/></forms:html>
                <forms:text property="dailyEveryDay" size="4" maxlength="6"/>
                <forms:html><bean:message key="event.recurrence.days"/></forms:html>              
              </forms:row>

              <forms:row>
                <forms:radio property="recurrenceType" label="event.recurrence.weekly" value="1"/>
                <forms:html><span style="white-space: nowrap;"><bean:message key="event.recurrence.recurevery"/></span></forms:html>
                <forms:text property="weeklyEveryWeek" size="4" maxlength="6"/>
                <forms:html><bean:message key="event.recurrence.weekson"/>:</forms:html>
                <forms:html>
                  <table cellspacing="0" cellpadding="0">
                  <tr>
                  <c:forEach items="${sessionScope.weekDayOption.labelValue}" var="labelValue" varStatus="stat">                
                	<td><html:multibox property="weeklyWeekday" value="${labelValue.value}"/>${labelValue.label}</td>
                	<c:if test="${stat.index == 3}">
                      </tr><tr>
                	</c:if>
                  </c:forEach>
                  <td>&nbsp;</td>
                  </tr>
                  </table>
                </forms:html>
                
              </forms:row>
              
              <forms:row>
                <forms:radio property="recurrenceType" label="event.recurrence.monthly" value="2"/>

                <forms:html><bean:message key="event.recurrence.day"/></forms:html>
                <forms:text property="monthlyDay" size="4" maxlength="6"/>
                <forms:html><bean:message key="event.recurrence.ofevery"/></forms:html>
                <forms:text property="monthlyEveryMonth" size="4" maxlength="6"/>
                <forms:html><bean:message key="event.recurrence.months"/></forms:html>
                
              </forms:row>

              <forms:row join="true">
                <forms:radio property="recurrenceType" label="" value="3"/>
                
                <forms:html><bean:message key="event.recurrence.the"/></forms:html>
                <forms:select property="monthlyPos">		    
            		    <base:options empty="empty" name="posOption"/>
            		</forms:select>                
                <forms:select property="monthlyWeekdayPos">		    
            		    <base:options empty="empty" name="weekDayOption"/>
            		</forms:select>                
                
                <forms:html><bean:message key="event.recurrence.ofevery"/></forms:html>
                <forms:text property="monthlyEveryMonthPos" size="4" maxlength="6"/>
                <forms:html><bean:message key="event.recurrence.months"/></forms:html>
                
              </forms:row>
                                            
              <forms:row>
                 <forms:radio property="recurrenceType" label="event.recurrence.yearly" value="4"/>                                          
                 <forms:html><bean:message key="event.recurrence.yearly.every"/></forms:html>
                 <forms:text property="yearlyEveryDay" size="4" maxlength="6"/>
                 <forms:select property="yearlyEveryMonth">		    
            		    <base:options empty="empty" name="monthOption"/>
            		 </forms:select>               
              </forms:row>
              
              <forms:row join="true">
                <forms:radio property="recurrenceType" label="" value="5"/>
                <forms:html><bean:message key="event.recurrence.the"/></forms:html>
                <forms:select property="yearlyPos">		    
            		    <base:options empty="empty" name="posOption"/>
            		</forms:select>                
                <forms:select property="yearlyWeekdayPos">		    
            		    <base:options empty="empty" name="weekDayOption"/>
            		</forms:select>  
                <forms:html><bean:message key="event.recurrence.of"/></forms:html>
                 <forms:select property="yearlyMonthPos">		    
            		    <base:options empty="empty" name="monthOption"/>
            		 </forms:select>                       
              </forms:row>    
                        
            </forms:section> 
            
         		<forms:section title="event.recurrence.range">
              <forms:row>
                 <forms:radio label="" property="rangeType" value="0"/>                                          
                 <forms:html><bean:message key="event.recurrence.range.noend"/></forms:html>
              </forms:row>
              <forms:row join="true">
                 <forms:radio property="rangeType" label="" value="1"/>                                          
                 <forms:html><span style="white-space: nowrap;"><bean:message key="event.recurrence.range.endafter"/>:</span></forms:html>  
                 <forms:text property="rangeOccurrences" size="4" maxlength="6"/>
                 <forms:html><bean:message key="event.recurrence.range.occurrences"/></forms:html>
              </forms:row>
              <forms:row join="true">
                 <forms:radio property="rangeType" label="" value="2"/>                                          
                 <forms:html><span style="white-space: nowrap;"><bean:message key="event.recurrence.range.endby"/>:</span></forms:html>                                                
                 <forms:text property="rangeEnd" styleId="rangeEnd" size="10" maxlength="10"/>
                 <forms:html> 		  			            
                   <img src="images/cal.gif" alt="" name="selectRangeEnd" id="selectRangeEnd" width="16" height="16" border="0">
                 </forms:html> 
              </forms:row>
              
            </forms:section> 
            
          </forms:form>
          <misc:popupCalendar element="rangeEnd" trigger="selectRangeEnd" showOthers="true"/>
        </ctrl:tab>
        
      </ctrl:tabset>				        
     </forms:html>
								
		<forms:buttonsection join="false">
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>			
						
      <c:if test="${EventForm.deletable}">
			<forms:button onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>   
      </c:if>   
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>
  </html:form>	
  
  <misc:confirm key="event.delete"/>
</body>
</html>