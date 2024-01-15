   <%
   'Eingegebene Login Information prüfen 
    
    Dim BenutzerRS
    Dim query
    
    if Request("Login") <> "" then
	    if Request("benutzer") <> "" AND Request("passwort") <> ""  then
	    
		    set BenutzerRS = Server.CreateObject("ADODB.RecordSet")		  
			  
			 query = "select Profil, Passwort from Benutzer where Name = '" & Request("benutzer") & "'"
			  
			 BenutzerRS.Open query, Conn, adOpenKeyset, adLockOptimistic	     
			  
			 if BenutzerRS.RecordCount > 0 then    
			    if BenutzerRS("passwort") = Request("passwort") then		    
			    	Session("Profil") = BenutzerRS("Profil")    
				 	Response.Redirect "frame.asp"    
				 else 
				   Status = "Passwort falsch"			   
				 end if
			 else
				 Session("Profil") = ""
				 Status = "Benutzer " & Request("benutzer") & " existiert nicht"
			 end if
			  
			 BenutzerRS.close
		 else 
		    Status = "Bitte Benutzer und Passwort eingeben"
		 end if
	 end if   
   %>	  