package ch.ess.type;

import java.util.*;

public class AlignmentType extends Type {

  private static Map typeMap = new HashMap();


  private AlignmentType(String typ, String key) {
    this.typ = typ;
    this.key = key;
    
    typeMap.put(this.typ, this);
  }

  
  public static AlignmentType getType(Object value) {
    return (AlignmentType)typeMap.get(value);
  }

  public static final AlignmentType LEFT = new AlignmentType("left", null);
  public static final AlignmentType RIGHT = new AlignmentType("right", null);
  public static final AlignmentType CENTER = new AlignmentType("center", null);

}
