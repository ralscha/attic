import java.util.*;
import java.io.*;

public class AppProperties {
    
    private static final AppProperties instance = new AppProperties();
    private Properties appProps = null;
        
    protected AppProperties() {
        
        Properties defaultProps = new Properties();
        defaultProps.put("database", "SWIFTInput.odb");
        defaultProps.put("logPath", "log");
        defaultProps.put("port", "80");
                
        try {
            appProps = new Properties(defaultProps);
            FileInputStream in = new FileInputStream("SWIFT.properties");
            appProps.load(in);            
            in.close();
        } catch (Exception f) {
            DiskLog.log(DiskLog.WARNING, getClass().getName(), "" + f);
        }

    }
    
    public static String getProperty(String key) {
        return (String)instance.appProps.get(key);
    }
        

}