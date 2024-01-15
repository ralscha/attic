package ch.ess.cal;

import java.util.*;

import org.apache.commons.lang.enum.*;

public class PermissionEnum extends Enum {

  public static final PermissionEnum ADMIN = new PermissionEnum("admin");

  private PermissionEnum(String attributeName) {
    super(attributeName);
  }

  public static PermissionEnum getEnum(String attributName) {
    return (PermissionEnum)getEnum(PermissionEnum.class, attributName);
  }

  public static Map getEnumMap() {
    return getEnumMap(PermissionEnum.class);
  }

  public static List getEnumList() {
    return getEnumList(PermissionEnum.class);
  }

  public static Iterator iterator() {
    return iterator(PermissionEnum.class);
  }
}

