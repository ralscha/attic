   <%
   'Maschinenanzeige l�schen
   'Der Recordset muss bereits auf der zu l�schenden Anzeige positioniert sein
   'Vor dem L�schen wird das Passwort mit der Anzeige �berpr�ft
   'Die Variable loeschenOK zeigt an ob das L�schen geklappt hat
    
    Dim loeschenOK
    loeschenOK = false
    
    if (Request("loeschen") <> "") OR (trim(Request("passwort")) <> "") then
    
	    if (Request("passwort") = MaschinenRS("Passwort")) OR Admin then
	      MaschinenRS.delete
	      Status = "Anzeige gel�scht"
	      loeschenOK = true
	    else
	      Status = "Passwort stimmt nicht �berein"
	    end if
	    
	 end if    
    
   %>	  