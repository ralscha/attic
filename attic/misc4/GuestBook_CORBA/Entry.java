
import java.text.*;
import java.io.*;
import java.util.*;

public class Entry implements Serializable {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    private String name;
    private String message;
    private String time;
    
    public Entry(String name, String message) {
        this.name = name;
        this.message = message;
        this.time = dateFormat.format(new Date());
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
        
    public void setMessage(String message) {
        this.message = message;
        this.time = dateFormat.format(new Date());
    }
    
    public String getMessage() {
        return message;
    }
           
    public String getTime() {
        return time;
    }    
}