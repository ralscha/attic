import java.io.*;
import COM.odi.util.*;

public class StateDescriptionMap {

    private Map descriptionMap;

    public StateDescriptionMap() {
        descriptionMap = new OSHashMap();
        
        String line;
        String fileName = AppProperties.getProperty("stateDescrFile");
        
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));     
            while ((line = buf.readLine()) != null) {
                
                try {            
                    StateDescription state = new StateDescription();
                
                    state.setCode(line.substring(0,2));
                    state.setColour(Integer.parseInt(line.substring(2,3)));
                    state.setDescription(line.substring(3));
                            
                    descriptionMap.put(state.getCode(), state);                        
                } catch (NumberFormatException nfe) { }
            }  
        } catch (FileNotFoundException fnfe) { 
            DiskLog.log(DiskLog.FATAL, getClass().getName(), "Constructor " + fnfe);                                    
        } catch (IOException ioe) {
            DiskLog.log(DiskLog.FATAL, getClass().getName(), "Constructor " + ioe);                                    
        }
    }

    public StateDescription getStateDescription(String code) {
        return (StateDescription)descriptionMap.get(code);
    }        

}