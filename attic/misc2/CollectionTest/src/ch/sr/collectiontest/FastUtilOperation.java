package ch.sr.collectiontest;


import it.unimi.dsi.fastUtil.*;

import ch.sr.benchmark.*;

public class FastUtilOperation implements Operation {

  String[] r = new String[50000];
  Int2ObjectHashMap map;
  
  public void warmUp() {
    for (int i = 0; i < r.length; i++) {
      r[i] = String.valueOf(Math.random());
    } 
    map = new Int2ObjectHashMap();
    for (int i = 0; i < 10000; i++) {
      map.put(i, r[i]);
    }     
  }

  public void execute() {

    for (int i = 0; i < 10000; i++) {
      String str = (String)map.get(i);
    }    
  }

  public int getIterationCount() {
    return 10;
  }


}
