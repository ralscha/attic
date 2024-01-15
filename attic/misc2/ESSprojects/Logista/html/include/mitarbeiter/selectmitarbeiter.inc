   <%
    
       'Selektiert die Mitarbeiteranzeige mit der entsprechenden ID
	    
	    Dim selectquery
	    selectquery = "select SucheBiete, Name, Email, Telefon, Passwort, Titel, Beschreibung, Gueltigbis, MitarbeiterRubrik, Loeschennach, DatumErstellung from MitarbeiterPool where ID = " & Request("mitarbeiterid")
	    
	    MitarbeiterRS.Open selectquery, Conn, adOpenKeyset, adLockOptimistic
	    
    
   %>	  