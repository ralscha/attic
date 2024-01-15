
package ch.ess.pbroker.model;

import com.objectmatter.bsf.*;
import ch.ess.taglib.table.*;
import ch.ess.pbroker.db.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

public class KandidatenModelCreator extends ModelCreator {
  
  public JSPTableModel create(Database db, HttpServletRequest  request) {
		   
	  DefaultTableModel dtm = new DefaultTableModel();	  
	  dtm.setColumnIdentifiers(new String[] {"ID", "Name", "Vorname", "Strasse", "PLZ", "Ort", "Telefon", "E-Mail", ""});

	  Object[] oArray = new Object[9];

	  String lieferantid = request.getParameter("lieferantid");
	  if (lieferantid == null) {
	    lieferantid = (String)request.getAttribute("lieferantid");
	  }

	  Kandidaten[] kandidaten = null;
	  if (lieferantid != null) {
	    OQuery query = new OQuery(Kandidaten.class);
	    query.add(lieferantid, "lieferantId");
	  
	    kandidaten = (Kandidaten[])query.list(db);
	  } else {
	    kandidaten = (Kandidaten[])db.list(Kandidaten.class);
	  }
  

	  for (int i = 0; i < kandidaten.length; i++) {      
	    Personalien per = kandidaten[i].getPersonalien();
	    oArray[8] = kandidaten[i];
	    oArray[0] = kandidaten[i].getKandidatId();
	    oArray[1] = per.getName();
	    oArray[2] = per.getVorname();
	    oArray[3] = per.getStrasse();
	    oArray[4] = per.getPLZ();
	    oArray[5] = per.getOrt();
	    oArray[6] = per.getTelefon();
	    oArray[7] = per.getEMail();

	    dtm.addRow(oArray);
	  }

	  return createJSPTableModel(request, new TableSorter(dtm));	  
	 
  }      

}