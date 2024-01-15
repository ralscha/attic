<%
   'Initialisiert die Connection zur Datenbank
   'Ermittelt ob sich ein Benutzer mit Administratorenrechte eingeloggt hat
 
 
   Dim Conn
   Dim Status
   Dim Admin
   
   Admin = false
   if Session("Profil") = "A" then
     Admin = true
   end if
   
   
   'Anzeige von neuen Anzeigen. Als neu werden alle Anzeigen mit Erstellungsdatum NOW - neuDiff gezählt
   Dim neuDiff
   neuDiff = 2
      
	Set Conn = Server.CreateObject("ADODB.Connection")
	Conn.Open "DSN=Logista"
	'Conn.Open "Driver={SQL Server}; Server=(local);UID=sa;PWD=;Database=Logista"

	Status = ""
	
	
	
	function ConvertBeschreibung(beschreibung, toDb)
	   
	   if toDb then
	    ConvertBeschreibung = Replace(beschreibung, vbCRLF, "<br>")
	   else
	    ConvertBeschreibung = Replace(beschreibung, "<br>", vbCRLF)
	   end if
	
	end function

%>