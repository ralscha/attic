package ch.ess.blankrc.remote;

/**
 * @author sr
 */
public interface LogonService {

  public String logon(String username, String token);
  
  public void logoff(String username, String ticket);

}