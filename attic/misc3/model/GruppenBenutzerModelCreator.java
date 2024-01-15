
package ch.ess.pbroker.model;

import com.objectmatter.bsf.*;
import ch.ess.taglib.table.*;
import ch.ess.pbroker.db.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

public class GruppenBenutzerModelCreator extends ModelCreator {
  
  public JSPTableModel create(Database db, HttpServletRequest  request) {
	   
	DefaultTableModel dtm = new DefaultTableModel();
	//dtm.setColumnCount(6);
	
	String[] strArray = new String[6];

	String gruppeid = request.getParameter("gruppeid");
	if (gruppeid == null) {
	  gruppeid = (String)request.getAttribute("gruppeid");
	}

	Benutzer[] benutzer = null;
	if (gruppeid != null) {
	  BenutzerGruppen gruppe = (BenutzerGruppen)db.lookup(BenutzerGruppen.class, gruppeid);
	  if (gruppe != null) {
		benutzer = gruppe.getBenutzer();
	  }
	} else {
	  benutzer = (Benutzer[])db.list(Benutzer.class);
	}
  
	if (benutzer != null) {
	  for (int i = 0; i < benutzer.length; i++) {

		KundenMitarbeiter[]      m1 = benutzer[i].getKundenMitarbeiter();
		LieferantenMitarbeiter[] m2 = benutzer[i].getLieferantenMitarbeiter();
		PBrokerAGMitarbeiter[]   m3 = benutzer[i].getPBrokerAGMitarbeiter();
		ExterneMitarbeiter[]     m4 = benutzer[i].getExterneMitarbeiter();

		Personalien personalien = null;
		if ((m1 != null) && (m1.length > 0)) {
		  strArray[3] = "K";
		  personalien = m1[0].getPersonalien();
		} else if ((m2 != null) && (m2.length > 0)) {
		  strArray[3] = "L";
		  personalien = m2[0].getPersonalien();
		} else if ((m3 != null) && (m3.length > 0)) {
		  strArray[3] = "P";
		  personalien = m3[0].getPersonalien();
		} else if ((m4 != null) && (m4.length > 0)) {
		  strArray[3] = "E";
		  personalien = m4[0].getPersonalien();
		}

		strArray[0] = String.valueOf(benutzer[i].getBenutzerId()); 
		strArray[1] = String.valueOf(benutzer[i].getLoginId());  
		strArray[2] = String.valueOf(benutzer[i].getSprache());
		
		if (personalien != null) {
		  strArray[4] = personalien.getName();
		  strArray[5] = personalien.getVorname();
		} else {
		  strArray[4] = "";
		  strArray[5] = "";        
		}

		dtm.addRow(strArray);
	  }
	}
	db.discardAll();    

	return createJSPTableModel(request, dtm);	  
	 
  }      

}