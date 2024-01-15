<% 

   'Erstellt die Recordsets für die MaschinenPool- und Rubrik-Tablle
  

   Dim MaschinenRubrikRS
   Dim MaschinenRS
      
   set MaschinenRS = Server.CreateObject("ADODB.RecordSet")
   set MaschinenRubrikRS = Server.CreateObject("ADODB.RecordSet")		
   
   'Query für MaschinenRubriken
   'Oeffnen mit openmaschinenrubriken.inc
   
   Dim rubrikquery  
	rubrikquery = "select ID, Beschreibung from MaschinenRubrik order by Beschreibung"     
	
%>