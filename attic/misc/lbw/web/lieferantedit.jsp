<%@ include file="include/taglibs.jspf"%>



<html>
  <head>
    <title></title>
    
    <script type="text/javascript" src="<c:url value='/scripts/scriptaculous/prototype.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/scriptaculous/scriptaculous.js'/>"></script>
    
    <script type='text/javascript' src="<c:url value='/dwr/interface/alarmService.js'/>"></script>
    <script type='text/javascript' src="<c:url value='/dwr/engine.js'/>"></script>
    <script type='text/javascript' src="<c:url value='/dwr/util.js'/>"></script>
    
    <misc:popupCalendarJs />
           
    <style type="text/css">    
.alarm { 
  width: 400px;
  padding-right: 15px; /* the gap on the right edge of the image (not content padding) */ 
  margin: 20px auto; /* use to position the box */
  z-index: 0;
  position: absolute;
  top: 130px;
  left: 420px;
}        
   </style>  
  </head>
  <body>
	
     <misc:initSelectOptions id="werkOptions" name="werkOptions" scope="request"/>
  
<script type="text/javascript">
    function showAddAlarm() {    
      Effect.Appear('addAlarmDiv');
      Element.hide('btnAlarmDelete');
    }
    
    function addOrUpdateAlarm() {
      var modelId = document.LieferantForm.modelId.value;
      var typ = 'ISO';
      var datum = document.LieferantForm.alarmDatum.value;
      var empfaenger = document.LieferantForm.alarmEmpfaenger.value;
      var subjekt = document.LieferantForm.alarmSubjekt.value;
      var text = document.LieferantForm.alarmText.value;      
    
      alarmService.updateAlarm(modelId, typ, datum, empfaenger, subjekt, text, updateAlarmCallback);     
    }
    
    function updateAlarmCallback(key) {
      if (key != null) {  
        Effect.Fade('addAlarmDiv');
        Element.hide('neuAlarmIcon');
        Element.show('editAlarmIcon');    
        
        DWRUtil.setValue('isoAlarmId', key);     
      } else {
        DWRUtil.setValue('isoAlarmId', '');        
      }
    }
    
    function editAlarm() {      
      alarmId = DWRUtil.getValue('isoAlarmId');
      Element.show('btnAlarmDelete');
      alarmService.getAlarm(alarmId, editAlarmCallback);      
    }
    
    function deleteAlarm() {
      var modelId = document.LieferantForm.modelId.value;
      var typ = 'ISO';      
      alarmService.deleteAlarm(modelId, typ, deleteAlarmCallback);      
    }
    
    function deleteAlarmCallback() {
      Effect.Fade('addAlarmDiv');
      Element.show('neuAlarmIcon');
      Element.hide('editAlarmIcon');
    }
    
    function editAlarmCallback(alarm) {   
      if (alarm != null) { 
        DWRUtil.setValues(alarm);
        Effect.Appear('addAlarmDiv');
      }
    }
    
    
</script>
    
	<html:form action="/lieferantEdit" focus="nr">
  <html:hidden property="modelId"/>
  <html:hidden property="version"/>
  
  <input type="hidden" value="${LieferantForm.isoAlarmId}" name="isoAlarmId" id="isoAlarmId"/>

  <forms:form type="edit" caption="lieferantedit.title" formid="frmLieferant" width="500">    
    <forms:html valign="top" join="false">
    
    <ctrl:tabset id="lieferanttab" styleId="lieferanttab" runat="client" property="tabset" formElement="true">
      <ctrl:tab tabid="adresse" title="lieferant.adresse" tooltip="lieferant.adresse">
        <forms:form type="edit" formid="lieferantAdresse" noframe="true">
    
    	  <forms:text label="lieferant.nr" property="nr" required="true" size="40" maxlength="255"/>			  
    	  <forms:text label="lieferant.kurz" property="kurz" required="true" size="40" maxlength="255"/>			  
    	  <forms:text label="lieferant.name" property="name" required="true" size="40" maxlength="255"/>			  
    	  <forms:text label="lieferant.strasse" property="strasse" required="false" size="40" maxlength="255"/>			  
    	  <forms:text label="lieferant.plz" property="plz" required="false" size="40" maxlength="255"/>			  
    	  <forms:text label="lieferant.ort" property="ort" required="false" size="40" maxlength="255"/>			  
        <forms:textarea valign="top" label="lieferant.bemerkung" property="bemerkung" required="false" cols="40" rows="4" maxlength="1000"/>        
        </forms:form>
      </ctrl:tab>
      
       <ctrl:tab tabid="werke" title="werk.werks" tooltip="werk.werks">
       <forms:form type="edit" formid="frmUserWerke" noframe="true">
       
       <forms:swapselect label="werk.werks" valign="top"              
         property="werkIds"              
         orientation="horizontal"      
         labelLeft="common.available"      
         labelRight="common.selected" 
         runat="client"          
         style="width: 170px;">
         <base:options name="werkOptions"/>

       </forms:swapselect>  
            </forms:form>
          </ctrl:tab>      

      <ctrl:tab tabid="iso" title="lieferant.iso" tooltip="lieferant.iso">
        <forms:form type="edit" formid="lieferantISO" noframe="true">
    	  <forms:text label="lieferant.iso" property="iso" required="false" size="40" maxlength="255"/>			  
        <forms:text label="lieferant.isoGesellschaft" property="isoGesellschaft" required="false" size="40" maxlength="255"/>       
    	  <forms:text label="lieferant.isoDatum" property="isoDatum" required="false" size="40" maxlength="255"/>			  
    	  <forms:plaintext converter="ch.ess.base.web.BigDecimalOneDecimalDigitsConverter" label="lieferant.isoPunkte" property="isoPunkte"/>		
        
        <c:if test="${not empty LieferantForm.modelId}">
        <forms:html label="alarm">
          <img src="images/alarmneu.gif" onclick="showAddAlarm()" alt="" name="neuAlarmIcon" id="neuAlarmIcon" width="16" height="18" border="0" style="display: none">
          <img src="images/alarmedit.gif" onclick="editAlarm()" alt="" name="editAlarmIcon" id="editAlarmIcon" width="16" height="18" border="0" style="display: none">
        </forms:html>
        </c:if>
        </forms:form>
      </ctrl:tab>
      
      <ctrl:tab tabid="vda" title="lieferant.vda" tooltip="lieferant.vda">
        <forms:form type="edit" formid="lieferantVDA" noframe="true">
    	  <forms:checkbox label="lieferant.vda" property="vda" required="false"/>			  
    	  <forms:text label="lieferant.vdaBemerkung" property="vdaBemerkung" required="false" size="40" maxlength="255"/>			  
    	  <forms:plaintext converter="ch.ess.base.web.BigDecimalOneDecimalDigitsConverter" label="lieferant.vdaPunkte" property="vdaPunkte"/>			  
	      </forms:form>
      </ctrl:tab>

      <ctrl:tab tabid="qs" title="lieferant.qs" tooltip="lieferant.qs">
        <forms:form type="edit" formid="lieferantQS" noframe="true">
        <forms:checkbox label="lieferant.qs" property="qs" required="false"/>			  
    	  <forms:text label="lieferant.qsBemerkung" property="qsBemerkung" required="false" size="40" maxlength="255"/>			  
    	  <forms:plaintext converter="ch.ess.base.web.BigDecimalOneDecimalDigitsConverter" label="lieferant.qsPunkte" property="qsPunkte"/>			  
        </forms:form>
      </ctrl:tab>
      
      <ctrl:tab tabid="polytec" title="lieferant.polytec" tooltip="lieferant.polytec">  
        <forms:form type="edit" formid="lieferantPolytec" noframe="true">    	  
        <forms:checkbox label="lieferant.polytec" property="polytec" required="false"/>			  
    	  <forms:text label="lieferant.polytecBemerkung" property="polytecBemerkung" required="false" size="40" maxlength="255"/>			  
    	  <forms:plaintext converter="ch.ess.base.web.BigDecimalOneDecimalDigitsConverter" label="lieferant.polytecPunkte" property="polytecPunkte"/>			  
        </forms:form>
      </ctrl:tab>
      
      <ctrl:tab tabid="other" title="lieferant.other" tooltip="lieferant.other">  
        <forms:form type="edit" formid="lieferantOther" noframe="true">       
        <forms:checkbox label="lieferant.iso14001" property="iso14001" required="false"/>       
    	  <forms:text label="lieferant.iso14001Bemerkung" property="iso14001Bemerkung" required="false" size="40" maxlength="255"/>			  
        <forms:checkbox label="lieferant.isoTs16949" property="isoTs16949" required="false"/>       
    	  <forms:text label="lieferant.isoTs16949Bemerkung" property="isoTs16949Bemerkung" required="false" size="40" maxlength="255"/>			  
        <forms:checkbox label="lieferant.oekoAudit" property="oekoAudit" required="false"/>       
    	  <forms:text label="lieferant.oekoAuditBemerkung" property="oekoAuditBemerkung" required="false" size="40" maxlength="255"/>			  
        </forms:form>
      </ctrl:tab>
    </ctrl:tabset>
    </forms:html>
                    		
		<forms:buttonsection join="false"> 
			<forms:button   base="buttons.src" name="btnSaveAndNew"  src="btnSaveAndNew1.gif"  title="button.title.saveandnew"/>		    
			<forms:button   base="buttons.src" default="true" name="btnSave"  src="btnSave1.gif"  title="button.title.save"/>
      <c:if test="${LieferantForm.deletable}">
			<forms:button  onclick="return confirmRequest('');" base="buttons.src" name="btnDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      </c:if>
			<forms:button   base="buttons.src" name="btnBack"  src="btnBack1.gif"  title="button.title.back"/>
		</forms:buttonsection>
	</forms:form>

  
  <div id="addAlarmDiv" class="alarm" style="display: none">
   <forms:form type="edit" caption="alarm.title" formid="frmAlarm"> 

   <forms:html label="alarm.datum" >
     <html:text property="alarmDatum" styleId="alarmDatum" size="10" maxlength="10" />
     <img src="images/cal.gif" alt="" name="selectAlarmDatum" id="selectAlarmDatum" width="16" height="16" border="0">
     <img title='<bean:message key="fw.form.required" bundle="com.cc.framework.message"/>' width='9' height='13' style='margin-left:3px;' border='0' src='fw/def/image/required.gif'>              
   </forms:html>   
   
   <forms:text label="alarm.empfaenger" property="alarmEmpfaenger" required="true" size="40" maxlength="255"/>
   <forms:text label="alarm.subjekt" property="alarmSubjekt" required="true" size="40" maxlength="255"/>
   <forms:textarea valign="top" label="alarm.text" property="alarmText" required="true" cols="40" rows="4"/>


    <forms:buttonsection join="false"> 
      <forms:button base="buttons.src" onclick="addOrUpdateAlarm();return false;" default="true" name="btnAlarmSave"  src="btnSave1.gif"  title="button.title.save"/>
      <forms:button base="buttons.src" onclick="deleteAlarm();return false;" styleId="btnAlarmDelete" name="btnAlarmDelete" src="btnDelete1.gif"  title="button.title.delete"/>            
      <forms:button base="buttons.src" onclick="Effect.Fade('addAlarmDiv');return false;" name="btnClose" src="btnClose1.gif"  title="button.title.close"/>                  
    </forms:buttonsection>   
   </forms:form>   

  </div>
  
  </html:form>  
  <misc:popupCalendar element="alarmDatum" trigger="selectAlarmDatum" showOthers="true"/>
  <misc:confirm key="lieferant.delete"/>
  
  <script type="text/javascript">    
    <c:if test="${not empty LieferantForm.modelId}">
     <c:if test="${empty LieferantForm.isoAlarmId}">
       Element.show('neuAlarmIcon');
     </c:if>
     <c:if test="${not empty LieferantForm.isoAlarmId}">
       Element.show('editAlarmIcon');
     </c:if>           
    </c:if>
  
  </script>
  
</body>
</html>
