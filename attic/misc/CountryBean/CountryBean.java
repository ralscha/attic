
import java.util.*;

public class CountryBean {

  private static Map countryMap = new HashMap();
  private final static String SEPARATOR = "---------------------";

  private List topList;
  private Locale locale;
  private String[] namesArray;
  private String[] isoCodesArray;
  private boolean dirty;

  public CountryBean() {
    this(null);
  }

  public CountryBean(Locale locale) {
    topList = new ArrayList();
        
    if (locale == null)
      this.locale = Locale.getDefault();
    else
      this.locale = locale;

    loadResource();
    dirty = true;
  }

  public String[] getIsoCodes() {
    initArrays();
    return isoCodesArray;
  }

  public String[] getNames() {
    initArrays();
    return namesArray;
  }

  public void addTop(String isoCode) {
    if (isoCode != null) {
      topList.add(isoCode);
    }
    dirty = true;
  }

  private void initArrays() {
    synchronized(countryMap) {

      Map cMap = (Map)countryMap.get(locale.toString());

      if ((cMap != null) && (dirty)) {

        System.out.println("INIT ARRAYS");

        int size;
        int startIndex;
    
        if (topList.size() > 0) {
          size = cMap.size() + topList.size() + 1;
          startIndex = topList.size() + 1;
        } else {
          size = cMap.size();
          startIndex = 0;
        }

        namesArray = new String[size];
        isoCodesArray = new String[size];


        List tmpList = new ArrayList(cMap.values());
        Collections.sort(tmpList);

        for (int i = 0; i < tmpList.size(); i++) {
          Country c = (Country)tmpList.get(i);
          namesArray[i+startIndex] = c.getName();
          isoCodesArray[i+startIndex] = c.getIsoCode();
        }

        int currentTopIndex = 0;
        if (topList.size() > 0) {
        
          for (int i = 0; i < topList.size(); i++) {
            String top = (String)topList.get(i);            
            Country c = (Country)cMap.get(top);          
       
            namesArray[currentTopIndex] = c.getName();
            isoCodesArray[currentTopIndex] = c.getIsoCode();
            currentTopIndex++;       
          }
               
          namesArray[currentTopIndex] = SEPARATOR;
          isoCodesArray[currentTopIndex] = "";
        }

        dirty = false;
      }
    }    
  }

  private void loadResource() {
    synchronized (countryMap) {
      if (countryMap.get(locale.toString()) == null) { 
 
        System.out.println("LOAD RESOURCE");

        ResourceBundle rb = ResourceBundle.getBundle("country", locale);
        Map tmpMap = new HashMap();

        Enumeration e = rb.getKeys();
        while (e.hasMoreElements()) {
          String key = (String)e.nextElement();
      
          Country c = new Country();
          c.setIsoCode(key);
          c.setName(rb.getString(key));

          tmpMap.put(c.getIsoCode(), c);
        }

        countryMap.put(locale.toString(), tmpMap);
      }      
    }
  }  
    

}