package oogui;

import java.util.*;
public class MaleKids extends Kids {
    public MaleKids(Kids sw) {
        swimmers = sw.getKidList ();
    }
    public Enumeration getKids() {
       Vector kds = new Vector();
       Enumeration enum = swimmers.elements();
       while(enum.hasMoreElements ()) {
           Swimmer sw = (Swimmer)enum.nextElement ();
           if(! sw.isFemale ())
               kds.add (sw);
       }
       return kds.elements();

    }
}
