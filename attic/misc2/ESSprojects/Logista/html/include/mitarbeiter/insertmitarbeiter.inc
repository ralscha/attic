   <%
   'Mitarbeiter Anzeige in Tabelle eintragen
    
    if (Request("eintragen") <> "") then
    
	    Dim query
	    query = "select SucheBiete, Name, Email, Telefon, Passwort, Titel, Beschreibung, Gueltigbis, MitarbeiterRubrik, Loeschennach, DatumErstellung from MitarbeiterPool"
	    
	    MitarbeiterRS.Open query, Conn, adOpenKeyset, adLockOptimistic
	    
	    MitarbeiterRS.AddNew
	    
	    MitarbeiterRS("SucheBiete") = Request("suchebiete")
	    MitarbeiterRS("Name") = Request("username")
	    MitarbeiterRS("Email") = Request("email")
	    MitarbeiterRS("Telefon") = Request("tel")
	    MitarbeiterRS("Passwort") = Request("pw")
	    MitarbeiterRS("Titel") = Request("titel")
	    
	    MitarbeiterRS("Beschreibung") = ConvertBeschreibung(Request("com"), true)
	    MitarbeiterRS("Gueltigbis") = Request("gueltig")
	    MitarbeiterRS("MitarbeiterRubrik") = Request("rubrik")
	    MitarbeiterRS("Loeschennach") = Request("dauer")
	    MitarbeiterRS("DatumErstellung") = now
	    
	    MitarbeiterRS.update
	    MitarbeiterRS.close
	    
	    Status = "Anzeige: " & Request("Titel") & " eingetragen"
	 end if    
    
   %>	  