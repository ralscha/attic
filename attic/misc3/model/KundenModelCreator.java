
package ch.ess.pbroker.model;

import com.objectmatter.bsf.*;
import ch.ess.taglib.table.*;
import ch.ess.pbroker.db.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

public class KundenModelCreator extends ModelCreator {
  
  public JSPTableModel create(Database db, HttpServletRequest  request) {
		   
	DefaultTableModel dtm = new DefaultTableModel();	  
	dtm.setColumnIdentifiers(new String[] {"ID", "Kunde", "Strasse", "PLZ", "Ort", "Land", "Telefon", "E-Mail", "Fax", ""});

	Object[] oArray = new Object[10];

	Kunden[] kunden = (Kunden[])db.get(Kunden.class);  

	for (int i = 0; i < kunden.length; i++) {      
	  oArray[9] = kunden[i];
	  oArray[0] = kunden[i].getKundeId();
	  oArray[1] = kunden[i].getKunde();
	  oArray[2] = kunden[i].getStrasse();
	  oArray[3] = kunden[i].getPLZ();
	  oArray[4] = kunden[i].getOrt();
	  oArray[5] = kunden[i].getLand();
	  oArray[6] = kunden[i].getTelefon();
	  oArray[7] = kunden[i].getEMail();
	  oArray[8] = kunden[i].getFax();

	  dtm.addRow(oArray);
	}

	db.discardAll();    

	return createJSPTableModel(request, new TableSorter(dtm));	  
	 
  }  

}