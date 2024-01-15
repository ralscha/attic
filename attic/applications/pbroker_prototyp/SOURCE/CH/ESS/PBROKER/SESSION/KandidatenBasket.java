

package ch.ess.pbroker.session;

import java.util.*;
import ch.ess.pbroker.db.*;

public class KandidatenBasket {

	private Map kandidatenMap;

	public KandidatenBasket() {
		kandidatenMap = new TreeMap();
	}

  public boolean containsKandidat(int id) {
    return kandidatenMap.keySet().contains(new Integer(id));
  }

	public void addKandidat(Kandidaten kandidat) {
		kandidatenMap.put(new Integer(kandidat.getKandidatid()), kandidat);
	}

	public void removeKandidat(int id) {
		kandidatenMap.remove(new Integer(id));
	}

  public void removeKandidat(Kandidaten kandidat) {
    removeKandidat(kandidat.getKandidatid());
  }

  public void removeAll() {
    kandidatenMap.clear();
  }

  public Map getKandidatenMap() {
    return kandidatenMap;
  }

  public Iterator getKandidatenIterator() {
    return kandidatenMap.values().iterator();
  }


  public int getSize() {
    return kandidatenMap.size();
  }
}