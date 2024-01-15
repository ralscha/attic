 <%
 
   function totalRubriken() 
   
      Dim totalquery
   
      totalquery = "select count(*) as total from MaschinenRubrik"
	
      MaschinenRubrikRS.open totalquery, Conn, adOpenKeyset, adLockReadOnly     
     
      if (MaschinenRubrikRS.RecordCount > 0) then
         totalRubriken = MaschinenRubrikRS("total")
      else
         totalRubriken = 0
      end if
     
      MaschinenRubrikRS.close
   
   end function
 
 
   function totalMaschinenRubrikAnzeigen(rubrikid)      
     dim maschinenquery
     dim count
           
     maschinenquery = "select count(*) as total from MaschinenPool where MaschinenRubrik = " & rubrikid
     MaschinenRS.open maschinenquery, Conn, adOpenKeyset, adLockReadOnly     
     
     if (MaschinenRS.RecordCount > 0) then
       totalMaschinenRubrikAnzeigen = MaschinenRS("total")
     else
       totalMaschinenRubrikAnzeigen = 0
     end if
     
     MaschinenRS.close
     
   end function
   
   function totalNeueAnzeigenRubrik(rubrikid)
     dim maschinenquery
     dim count
           
     dim neuDatum 
     neuDatum = dateadd("d", -neuDiff, now)
           
     maschinenquery = "select count(*) as total from MaschinenPool where MaschinenRubrik = " & rubrikid & " AND DatumErstellung >= #"_
     								 & month(neuDatum) & "/" & day(neuDatum) & "/" & year(neuDatum) & "#"
     								 
     MaschinenRS.open maschinenquery, Conn, adOpenKeyset, adLockReadOnly     
     
     if (MaschinenRS.RecordCount > 0) then
       totalNeueAnzeigenRubrik = MaschinenRS("total")
     else
       totalNeueAnzeigenRubrik = 0
     end if
     
     MaschinenRS.close
        
   end function
   
   
   
   
   function totalMaschinenAnzeigen()      

     dim maschinenquery
     dim count
     
 
     maschinenquery = "select count(*) as total from MaschinenPool"
     MaschinenRS.open maschinenquery, Conn, adOpenKeyset, adLockReadOnly     
     
     if (MaschinenRS.RecordCount > 0) then
       totalMaschinenAnzeigen = MaschinenRS("total")
     else
       totalMaschinenAnzeigen = 0
     end if
     
     MaschinenRS.close
     
   end function

   function rubrikBeschreibung(rubrikid)
     dim query
     dim MaschinenRubrikRS 
     set MaschinenRubrikRS = Server.CreateObject("ADODB.RecordSet")
     
     query = "select Beschreibung from MaschinenRubrik where ID = " + rubrikid
     MaschinenRubrikRS.Open query, Conn, adOpenForwardOnly, adLockReadOnly
     rubrikBeschreibung = MaschinenRubrikRS("Beschreibung")
     MaschinenRubrikRS.close
   end function 
  %>