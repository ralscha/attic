package lotto.html;

public class FtpConnection {
    
    String hostname;
    String username;
    String password;
    String homedir;
    
    public FtpConnection(String hostname, String username, String password, String homedir) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.homedir  = homedir;
    }
    
    public FtpConnection(String hostname, String username, String password) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.homedir  = "";
    }
    
    public String getHostname() {
        return hostname;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getHomedir() {
        return homedir;
    }
    
}
