package oogui;

import java.awt.*;
import java.util.*;

public class Kids {
    protected Vector swimmers;

    //set up the vector
    public Kids(){
        swimmers = new Vector();
    }
    //add a kid to the list
    public void add(String line) {
        Swimmer sw = new Swimmer(line);
        swimmers.add(sw);
    }
    //return the vector
    public Vector getKidList() {
        return swimmers;
    }
    //get an enumeration of the kids
    public Enumeration getKids() {
        return swimmers.elements();
    }
}
