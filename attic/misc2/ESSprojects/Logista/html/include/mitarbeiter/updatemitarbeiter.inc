   <%
   'Mitarbeiteranzeige aktualisieren
   'Der Recordset muss bereits auf der zu ändernden Anzeige positioniert sein
    
    if (Request("aktualisieren") <> "") then        
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
	    
	    Status = "Anzeige: " & Request("Titel") & " aktualisiert"
	 end if    
    
   %>	  