 <%
 
   'Gibt den entsprechenden Text für den Code zurück
   ' 'S' -> "Suche"
   ' 'B' -> "Biete"
   
   function SucheBiete(code) 
     if code = "S" then
       SucheBiete = "Suche"
     else 
       if code = "B" then
         SucheBiete = "Biete"
       else
         SucheBiete = ""
       end if
     end if
   end function
   	

   'Liefert die Anzahl Mitarbeiter Rubriken
   function totalRubriken()  
   
      Dim totalquery
   	totalquery = "select count(*) as total from MitarbeiterRubrik" 
   	MitarbeiterRubrikRS.open totalquery, Conn, adOpenKeyset, adLockReadOnly     
     
   	if (MitarbeiterRubrikRS.RecordCount > 0) then
      	 totalRubriken = MitarbeiterRubrikRS("total")
   	else
      	 totalRubriken = 0
  	   end if
     
   	MitarbeiterRubrikRS.close   
   end function
   
   'Liefert Anzahl Mitarbeiter Anzeigen für die entsprechende Rubrik
   function totalMitarbeiterRubrikAnzeigen(rubrikid, sb)      
 
     dim mitarbeiterquery
     dim count
               
     mitarbeiterquery = "select count(*) as total from MitarbeiterPool where MitarbeiterRubrik = " & rubrikid & "AND SucheBiete = '" & sb & "'"
     MitarbeiterRS.open mitarbeiterquery, Conn, adOpenKeyset, adLockReadOnly     
     
     if (MitarbeiterRS.RecordCount > 0) then
       totalMitarbeiterRubrikAnzeigen = MitarbeiterRS("total")
     else
       totalMitarbeiterRubrikAnzeigen = 0
     end if
     
     MitarbeiterRS.close
     
   end function
   
   'Liefert die Anzahl neuen Anzeigen in dieser Rubrik
   function totalNeueAnzeigenRubrik(rubrikid, sb)
     dim mitarbeiterquery
     dim count
           
     dim neuDatum 
     neuDatum = dateadd("d", -neuDiff, now)
           
     mitarbeiterquery = "select count(*) as total from MitarbeiterPool where MitarbeiterRubrik = " & rubrikid & " AND SucheBiete = '" & sb & "' AND DatumErstellung >= #"_
     								 & month(neuDatum) & "/" & day(neuDatum) & "/" & year(neuDatum) & "#"
     								 
     MitarbeiterRS.open mitarbeiterquery, Conn, adOpenKeyset, adLockReadOnly     
     
     if (MitarbeiterRS.RecordCount > 0) then
       totalNeueAnzeigenRubrik = MitarbeiterRS("total")
     else
       totalNeueAnzeigenRubrik = 0
     end if
     
     MitarbeiterRS.close        
   end function   
   
   'Lieft das Total aller Anzeigen
   function totalMitarbeiterAnzeigen(sb)      

     dim mitarbeiterquery
     dim count
     
  
     mitarbeiterquery = "select count(*) as total from MitarbeiterPool where SucheBiete = '" & sb & "'"
     MitarbeiterRS.open mitarbeiterquery, Conn, adOpenKeyset, adLockReadOnly     
     
     if (MitarbeiterRS.RecordCount > 0) then
       totalMitarbeiterAnzeigen = MitarbeiterRS("total")
     else
       totalMitarbeiterAnzeigen = 0
     end if
     
     MitarbeiterRS.close
     
   end function

   'Gibt die Rubrikenbeschreibung zurück
   function rubrikBeschreibung(rubrikid)
     dim query
     dim MitarbeiterRubrikRS 
     set MitarbeiterRubrikRS = Server.CreateObject("ADODB.RecordSet")
     
     query = "select Beschreibung from MitarbeiterRubrik where ID = " + rubrikid
     MitarbeiterRubrikRS.Open query, Conn, adOpenKeyset, adLockReadOnly
     rubrikBeschreibung = MitarbeiterRubrikRS("Beschreibung")
     MitarbeiterRubrikRS.close
   end function 
  %>
   
