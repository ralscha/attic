package ch.ess.cal.enums;

/**
 * Utility class designed to inspect StringValuedEnums.
 */
public class StringValuedEnumReflect {

  /**
   * Don't let anyone instantiate this class.
   */
  private StringValuedEnumReflect() {
    //no action here
  }

  /**
   * All Enum constants (instances) declared in the specified class. 
   * @param enumClass Class to reflect
   * @return Array of all declared EnumConstants (instances).
   */
  private static <T extends Enum<T>> T[] getValues(Class<T> enumClass) {
    return enumClass.getEnumConstants();
  }

  /**
   * All possible string values of the string valued enum.
   * @param enumClass Class to reflect.
   * @return Available string values.
   */
  public static <T extends Enum<T> & StringValuedEnum> String[] getStringValues(Class<T> enumClass) {
    T[] values = getValues(enumClass);
    String[] result = new String[values.length];
    for (int i = 0; i < values.length; i++) {
      result[i] = values[i].getValue();
    }
    return result;
  }

  /**
   * Name of the enum instance which hold the especified string value.
   * If value has duplicate enum instances than returns the first occurency.
   * @param enumClass Class to inspect.
   * @param value String.
   * @return name of the enum instance.
   */
  public static <T extends Enum<T> & StringValuedEnum> String getNameFromValue(Class<T> enumClass, String value) {

    T[] values = getValues(enumClass);
    for (int i = 0; i < values.length; i++) {
      if (values[i].getValue().compareTo(value) == 0) {
        return values[i].name();
      }
    }

    return "";
  }

  
  public static <T extends Enum<T> & StringValuedEnum> T getEnum(Class<T> enumClass, String value) {
    String name = getNameFromValue(enumClass, value);
    return Enum.valueOf(enumClass, name);  
  }
}
