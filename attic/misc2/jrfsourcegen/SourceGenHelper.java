public class SourceGenHelper {

  public static String capitalize(String str) {
    return (str.substring(0, 1).toUpperCase() + str.substring(1));
  }

  public static String uncapitalize(String str) {
    return (str.substring(0, 1).toLowerCase() + str.substring(1));
  }

}



