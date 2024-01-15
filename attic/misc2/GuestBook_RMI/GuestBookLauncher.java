import com.sun.java.swing.*;
import java.net.*;

public class GuestBookLauncher extends JApplet {
    
    public void init() {     
        URL base = getDocumentBase();
        String serverName = "//"+ base.getHost() + ":" + getParameter("registryPort") +
                           "/GuestBook";
        
        netscape.security.PrivilegeManager.enablePrivilege("UniversalConnect");
        new GuestBookFrame(true, serverName);
    }
    
    public static void main(String[] args) {
        if (args.length == 1)
            new GuestBookFrame(false, "//"+args[0]+"/GuestBook");
        else
            System.out.println("java GuestBookLauncher <serverName:port>");
    }
    
}