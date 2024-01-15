
package ch.ess.pbroker.model;

import java.util.*;

public class ModelCreatorFactory {

	private static Map creatorMap = new HashMap();
	
	public static ModelCreator getCreator(String id) {
		ModelCreator creator = (ModelCreator)creatorMap.get(id);
		if (creator == null) {
			creator = chooseCreator(id);
			if (creator != null) {
				creatorMap.put(id, creator);
			}
		}
		return creator;
	
	} 

	private static ModelCreator chooseCreator(String id) {
		if (id.equals("BenutzerGruppen")) {
			return new BenutzergruppenModelCreator();
		} else if (id.equals("GruppenBenutzer")) {
		  return new GruppenBenutzerModelCreator();
		} else if (id.equals("Kandidaten")) {
		  return new KandidatenModelCreator();
		} else if (id.equals("Kunden")) {
		  return new KundenModelCreator();
		} else if (id.equals("Lieferanten")) {
		  return new LieferantenModelCreator();
		}

		return null;
	}

}