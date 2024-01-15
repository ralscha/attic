	<%	
	   'Start Iteration über alle Mitarbeiteranzeigen einer Rubrik
	   
	   Dim detailquery
	   detailquery = "select ID, SucheBiete, Name, Email, Telefon, Titel, Beschreibung, Gueltigbis, DatumErstellung from MitarbeiterPool where MitarbeiterRubrik = " & Request("rubrikid") & " AND SucheBiete = '" & Request("suchebiete") & "' ORDER BY DatumErstellung DESC" 

	   MitarbeiterRS.open detailquery, Conn, adOpenForwardOnly, adLockReadOnly    
	   
	   
	 %>