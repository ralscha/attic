   <%      
       'Selektiert die Maschinenanzeige mit der entsprechenden ID
    
	    Dim selectquery
	    selectquery = "select Name, Email, Telefon, Passwort, Titel, Beschreibung, Gueltigbis, MaschinenRubrik, Loeschennach, DatumErstellung from MaschinenPool where ID = " & Request("maschinenid")
	    
	    MaschinenRS.Open selectquery, Conn, adOpenKeyset, adLockOptimistic
	        
   %>	  