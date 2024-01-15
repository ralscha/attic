package ch.sr.lotto.update;

public class FtpConnection {
    
    private String server;
    private String user;
    private String password;
    
    public FtpConnection() {
    	this(null, null, null);
    }
    
    public FtpConnection(String server, String user, String password) {
        this.server = server;
        this.user = user;
        this.password = password;
    }
    
    public String getServer() {
        return server;
    }
    
    public String getUser() {
        return user;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setServer(String server) {
    	this.server = server;
    }
    
    public void setUser(String user) {
    	this.user = user;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
}