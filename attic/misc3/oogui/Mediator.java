package oogui;

import java.util.*;
import javax.swing.*;

//this mediator is used to get the enumeration 
//and load the list
public class Mediator {
    JawtList kidList;
  
    //save the list in the constructor
  public Mediator(JawtList klist) {
      kidList = klist;
  }
  //load the list from the enumeration
  public void loadList(Enumeration enum) {
      kidList.clear();
       while(enum.hasMoreElements ()) {
           Swimmer sw = (Swimmer)enum.nextElement ();
           kidList.add (sw.getName ());
       }
  }
}
