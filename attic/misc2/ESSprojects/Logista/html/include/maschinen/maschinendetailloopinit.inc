	<%
	   'Start Iteration über alle Maschinenanzeigen einer Rubrik
	
	   Dim detailquery
	   detailquery = "select ID, Name, Email, Telefon, Titel, Beschreibung, Gueltigbis, DatumErstellung from MaschinenPool where MaschinenRubrik = " & Request("rubrikid") & " ORDER BY DatumErstellung DESC" 

	   MaschinenRS.open detailquery, Conn, adOpenForwardOnly, adLockReadOnly    
	   
	   
	 %>