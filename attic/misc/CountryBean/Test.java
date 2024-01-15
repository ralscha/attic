
import java.util.*;


public class Test {

  public static void main(String[] args) {
  
    Locale l = new Locale("de", "CH");
    Locale len = new Locale("en", "");

    CountryBean cb = new CountryBean(l);
    cb.addTop("CH");
    cb.addTop("DE");
    cb.addTop("AT");
    
    String[] iso = cb.getIsoCodes();
    String[] name = cb.getNames();

    HTMLReservedChars hrc = new HTMLReservedChars();

    for (int i = 0; i < iso.length; i++) {
      System.out.print(iso[i]);
      System.out.print(" ");
      System.out.println(hrc.process(name[i]));
    }  
    
  }
}