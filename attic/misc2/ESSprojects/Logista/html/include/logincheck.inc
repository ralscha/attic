<%
   'Prüfen ob der Benutzer bereits eingeloggt ist. Wenn nein wird auf die Login-Seite "redirected"
     
     if Session("Profil") = "" then
	     
	     Response.Redirect "login.asp"
	     
	  end if
  %>