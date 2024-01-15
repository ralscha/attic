
package ch.ess.pbroker.model;

import com.objectmatter.bsf.*;
import ch.ess.taglib.table.*;
import ch.ess.pbroker.db.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.table.*;

public class LieferantenModelCreator extends ModelCreator {
  
  public JSPTableModel create(Database db, HttpServletRequest  request) {
		   
	DefaultTableModel dtm = new DefaultTableModel();	  
	dtm.setColumnIdentifiers(new String[] {"ID", "Lieferant", "Strasse", "PLZ", "Ort", "Land", "Telefon", "E-Mail", "Fax", ""});

	Object[] oArray = new Object[10];

	Lieferanten[] lieferanten = (Lieferanten[])db.get(Lieferanten.class);  

	for (int i = 0; i < lieferanten.length; i++) {      
	  oArray[9] = lieferanten[i];
	  oArray[0] = lieferanten[i].getLieferantId();
	  oArray[1] = lieferanten[i].getLieferant();
	  oArray[2] = lieferanten[i].getStrasse();
	  oArray[3] = lieferanten[i].getPLZ();
	  oArray[4] = lieferanten[i].getOrt();
	  oArray[5] = lieferanten[i].getLand();
	  oArray[6] = lieferanten[i].getTelefon();
	  oArray[7] = lieferanten[i].getEMail();
	  oArray[8] = lieferanten[i].getFax();

	  dtm.addRow(oArray);
	}

	db.discardAll();    

	return createJSPTableModel(request, new TableSorter(dtm));	  
	 
  }  

}