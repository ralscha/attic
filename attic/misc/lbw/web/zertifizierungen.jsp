<%@ include file="include/taglibs.jspf"%>



<html>
  <head><title></title></head>
  <body>
  
   <menu:crumbs value="crumb1" labellength="40">
      <menu:crumb crumbid="crumb1"  title="reports.zertifizierungen" onclick="return false;"/>
    </menu:crumbs>
    <br>
  
  <cewolf:chart id="iso" type="pie">
    <cewolf:data><cewolf:producer id="isoZertifizierungProducer"/></cewolf:data>
  </cewolf:chart>
  <cewolf:chart id="vda" type="pie">
    <cewolf:data><cewolf:producer id="vdaZertifizierungProducer"/></cewolf:data>
  </cewolf:chart>
  <cewolf:chart id="qs" type="pie">
    <cewolf:data><cewolf:producer id="qsZertifizierungProducer"/></cewolf:data>
  </cewolf:chart> 
  
  
  <cewolf:chart id="iso14001" type="pie">
    <cewolf:data><cewolf:producer id="iso14001ZertifizierungProducer"/></cewolf:data>
  </cewolf:chart>  
  <cewolf:chart id="oekoAudit" type="pie">
    <cewolf:data><cewolf:producer id="oekoAuditZertifizierungProducer"/></cewolf:data>
  </cewolf:chart>  
  <cewolf:chart id="isoTS16949" type="pie">
    <cewolf:data><cewolf:producer id="iso16949ZertifizierungProducer"/></cewolf:data>
  </cewolf:chart>           

  <ctrl:tabset name="selectedTab" id="zertifizierungtab" styleId="zertifizierungtab" runat="client">
    <ctrl:tab tabid="iso" title="reports.zertifizierung.iso" tooltip="reports.zertifizierung.iso">
      <forms:form type="display" formid="iso" noframe="true">
      <forms:html>
        <cewolf:img chartid="iso" renderer="cewolf" width="700" height="400"/>
      </forms:html>
      </forms:form>
    </ctrl:tab>
    <ctrl:tab tabid="vda" title="reports.zertifizierung.vda" tooltip="reports.zertifizierung.vda">
      <forms:form type="display" formid="vda" noframe="true">
      <forms:html>
        <cewolf:img chartid="vda" renderer="cewolf" width="700" height="400"/>
      </forms:html>
      </forms:form>
    </ctrl:tab>
    <ctrl:tab tabid="qs" title="reports.zertifizierung.qs" tooltip="reports.zertifizierung.qs">
      <forms:form type="display" formid="qs" noframe="true">
      <forms:html>
        <cewolf:img chartid="qs" renderer="cewolf" width="700" height="400"/>
      </forms:html>
      </forms:form>
    </ctrl:tab>    
    
    <ctrl:tab tabid="iso14001" title="reports.zertifizierung.iso14001" tooltip="reports.zertifizierung.iso14001">
      <forms:form type="display" formid="iso14001" noframe="true">
      <forms:html>
        <cewolf:img chartid="iso14001" renderer="cewolf" width="700" height="400"/>
      </forms:html>
      </forms:form>
    </ctrl:tab>   
    <ctrl:tab tabid="oekoAudit" title="reports.zertifizierung.oekoAudit" tooltip="reports.zertifizierung.oekoAudit">
      <forms:form type="display" formid="oekoAudit" noframe="true">
      <forms:html>
        <cewolf:img chartid="oekoAudit" renderer="cewolf" width="700" height="400"/>
      </forms:html>
      </forms:form>
    </ctrl:tab>   
    <ctrl:tab tabid="isoTS16949" title="reports.zertifizierung.isoTS16949" tooltip="reports.zertifizierung.isoTS16949">
      <forms:form type="display" formid="isoTS16949" noframe="true">
      <forms:html>
        <cewolf:img chartid="isoTS16949" renderer="cewolf" width="700" height="400"/>
      </forms:html>
      </forms:form>
    </ctrl:tab>           
    
  </ctrl:tabset>


    
    
</body>
</html>
