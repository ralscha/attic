package oogui;

import java.util.*;
public class FemaleKids extends Kids {
    //set up vector
    public FemaleKids(Kids kds) {
        swimmers = kds.getKidList();
    }
    //return female sonly
    public Enumeration getKids() {
        Vector kds = new Vector();
        Enumeration enum = swimmers.elements();
        while(enum.hasMoreElements ()) {
            Swimmer sw = (Swimmer)enum.nextElement ();
            if(sw.isFemale ())
                kds.add (sw);
        }
        return kds.elements();
    }
}
