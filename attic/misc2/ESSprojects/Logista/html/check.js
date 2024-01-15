function chkFM(form) {



if(!form.username.value) {

 alert("Geben Sie bitte Ihren Namen ein!");

 form.username.focus();

 return false;

}



if(!form.email.value) {

 alert("Geben Sie bitte Ihre E-Mail-Adresse ein!");

 form.email.focus();

 return false;

}



if(form.email.value.indexOf('@') == -1) {

 alert("Das ist keine E-Mail-Adresse!");

 form.email.focus();

 return false;

}



if(!form.pw.value) {

 alert("Geben Sie ein Passwort f&uuml;r das L&ouml;schen der Anzeige ein!");

 form.pw.focus();

 return false;

}



if(!form.titel.value) {

 alert("Geben Sie einen Titel f&uuml;r die Anzeige ein!");

 form.titel.focus();

 return false;

}

  

if(!form.com.value) {

 alert("Geben Sie bitte einen Anzeigentext ein!");

 form.com.focus();

 return false;

}



if(!form.tel.value) {

 alert("Geben Sie bitte Ihre Telefonnummer ein!");

 form.tel.focus();

 return false;

}



if(!form.gueltig.value) {

 alert("Geben Sie bitte ein G&uuml;ltigkeitsbereich ein!");

 form.gueltig.focus();

 return false;

}


}

  function jump (url) {

  window.open(url,'_self')

  }

 