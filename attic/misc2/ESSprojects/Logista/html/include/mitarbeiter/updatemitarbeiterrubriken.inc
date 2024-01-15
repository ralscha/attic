<%
  
  'Wenn der Aktualisiern-Button angeklickt wurde werden alle Beschreibungen 
  'in der Rubrik-Tabelle aktualisiert und falls eine neue Rubrik eingetragen
  'wurde in die Tabelle eingefügt.
  'Mit "Löschen" markierte Rubriken werden in der Tabelle gelöscht
  
  Dim length
  Dim i
  
  if Request("aktualisieren") <> "" then
  
     'Löschen der selektierten Rubriken
	  length = Request("loeschen").Count
	  if length > 0 then 		    
			for i = 1 to length  
			  Conn.Execute("delete from MitarbeiterRubrik where ID = " & Request("loeschen").Item(i))
			next
	  end if
	
	
	  MitarbeiterRubrikRS.Open rubrikquery, Conn, adOpenKeyset, adLockOptimistic
	
	  'Alle Beschreibungen lesen und updaten
	  do until MitarbeiterRubrikRS.eof 	
	    MitarbeiterRubrikRS("Beschreibung") = Request(MitarbeiterRubrikRS("ID"))	
	    MitarbeiterRubrikRS.Update
	 	 MitarbeiterRubrikRS.moveNext
	  loop 
	
	  'Falle neue Rubrik vorhanden einfügen
	  if trim(Request("neuerubrik")) <> "" then
	    MitarbeiterRubrikRS.AddNew
	    MitarbeiterRubrikRS("Beschreibung") = Request("neuerubrik")
	    MitarbeiterRubrikRS.Update
	  end if
	
	  MitarbeiterRubrikRS.Close
  end if 
%>