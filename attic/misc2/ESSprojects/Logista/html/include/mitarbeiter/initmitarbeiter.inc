<% 

   'Erstellt die Recordsets für die MitarbeiterPool- und Rubrik-Tablle

   Dim MitarbeiterRubrikRS
   Dim MitarbeiterRS
   Dim rubrikquery
   
   set MitarbeiterRS = Server.CreateObject("ADODB.RecordSet")
   set MitarbeiterRubrikRS = Server.CreateObject("ADODB.RecordSet")		  
   
   rubrikquery = "select ID, Beschreibung from MitarbeiterRubrik order by Beschreibung"
	
%>