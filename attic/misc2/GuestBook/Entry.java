import java.util.*;

public class Entry {
    
    private String name;
    private String message;
    private Date date;
    
    public Entry(String name, String message) {
        this.name = name;
        this.message = message;
        this.date = new Date();
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setMessage(String message) {
        this.message = message;
        this.date = new Date();
    }
    
    public String getMessage() {
        return message;
    }
           
    public Date getDate() {
        return date;
    }    
}