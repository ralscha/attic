   <%
   'Maschinenanzeige löschen
   'Der Recordset muss bereits auf der zu löschenden Anzeige positioniert sein
   'Vor dem Löschen wird das Passwort mit der Anzeige überprüft
   'Die Variable loeschenOK zeigt an ob das Löschen geklappt hat
    
    Dim loeschenOK
    loeschenOK = false
    
    if (Request("loeschen") <> "") OR (trim(Request("passwort")) <> "") then
    
	    if (Request("passwort") = MaschinenRS("Passwort")) OR Admin then
	      MaschinenRS.delete
	      Status = "Anzeige gelöscht"
	      loeschenOK = true
	    else
	      Status = "Passwort stimmt nicht überein"
	    end if
	    
	 end if    
    
   %>	  