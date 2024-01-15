
package ch.ess.pbroker.model;

import com.objectmatter.bsf.*;
import ch.ess.taglib.table.*;
import ch.ess.pbroker.db.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

public class BenutzergruppenModelCreator extends ModelCreator {

  public JSPTableModel create(Database db, HttpServletRequest  request) {
	   
	DefaultTableModel dtm = new DefaultTableModel();
	//dtm.setColumnCount(4);

	
	String[] strArray = new String[4];

	BenutzerGruppen[] gruppen = (BenutzerGruppen[])db.list(BenutzerGruppen.class);

	if (gruppen != null) {
	  for (int i = 0; i < gruppen.length; i++) {
		Benutzer[] benutzer = (Benutzer[])gruppen[i].getBenutzer();
		BenutzerRechte[] rechte = (BenutzerRechte[])gruppen[i].getBenutzerRechte();
		
		strArray[0] = String.valueOf(gruppen[i].getBenutzerGruppeId()); // Id
		strArray[1] = String.valueOf(gruppen[i].getBenutzerGruppe()); // Gruppenbezeichnung
		
		if (benutzer != null)
		  strArray[2] = String.valueOf(benutzer.length); // Anzahl Benutzer
		else 
		  strArray[2] = "0"; // Anzahl Benutzer
		
		if (rechte != null) 
		  strArray[3] = String.valueOf(rechte.length); // Anzahl Rechte
		else
		  strArray[3] = "0";
		dtm.addRow(strArray);
	  
	  }
	  
	}
	db.discardAll();    

	return createJSPTableModel(request, dtm);	  
	 
  }      

}