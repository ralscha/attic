   <%
   'Maschinenanzeige aktualisieren
   'Der Recordset muss bereits auf der zu ändernden Anzeige positioniert sein

    if (Request("aktualisieren") <> "") then
	    MaschinenRS("Name") = Request("username")
	    MaschinenRS("Email") = Request("email")
	    MaschinenRS("Telefon") = Request("tel")
	    MaschinenRS("Passwort") = Request("pw")
	    MaschinenRS("Titel") = Request("titel")
	    	    
	    MaschinenRS("Beschreibung") = ConvertBeschreibung(Request("com"), true)
	    MaschinenRS("Gueltigbis") = Request("gueltig")
	    MaschinenRS("MaschinenRubrik") = Request("rubrik")
	    MaschinenRS("Loeschennach") = Request("dauer")
	    MaschinenRS("DatumErstellung") = now
	    
	    MaschinenRS.update
	    
	    Status = "Anzeige: " & Request("Titel") & " aktualisiert"
	 end if    
    
   %>	  