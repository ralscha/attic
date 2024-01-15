   <%
   'Maschinen Anzeige in Tabelle einfügen
    
    if (Request("eintragen") <> "") then
    
	    Dim query
	    query = "select Name, Email, Telefon, Passwort, Titel, Beschreibung, Gueltigbis, MaschinenRubrik, Loeschennach, DatumErstellung from MaschinenPool"
	    
	    MaschinenRS.Open query, Conn, adOpenKeyset, adLockOptimistic
	    
	    MaschinenRS.AddNew
	    
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
	    MaschinenRS.close
	    
	    Status = "Anzeige: " & Request("Titel") & " eingetragen"
	 end if    
    
   %>	  