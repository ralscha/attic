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
			  Conn.Execute("delete from MaschinenRubrik where ID = " & Request("loeschen").Item(i))
			next
	  end if
	
	
	  MaschinenRubrikRS.Open rubrikquery, Conn, adOpenKeyset, adLockOptimistic
	
	  'Alle Beschreibungen lesen und updaten
	  do until MaschinenRubrikRS.eof 	
	    MaschinenRubrikRS("Beschreibung") = Request(MaschinenRubrikRS("ID"))	
	    MaschinenRubrikRS.Update
	 	 MaschinenRubrikRS.moveNext
	  loop 
	
	  'Falle neue Rubrik vorhanden einfügen
	  if trim(Request("neuerubrik")) <> "" then
	    MaschinenRubrikRS.AddNew
	    MaschinenRubrikRS("Beschreibung") = Request("neuerubrik")
	    MaschinenRubrikRS.Update
	  end if
	
	  MaschinenRubrikRS.Close	  	  
  end if 
%>