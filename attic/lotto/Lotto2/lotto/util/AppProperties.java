package lotto.util;

import java.util.*;
import java.io.*;
import lotto.html.*;

public class AppProperties {
    
    private static final AppProperties instance = new AppProperties();
    private Properties appProps = null;
    private Vector ftpconnections;
    
    public static AppProperties getInstance() {
        return instance;
    }
    
    protected AppProperties() {
        
        Properties defaultProps = new Properties();
        defaultProps.put("htmlPath", "c:/temp/lotto");
        defaultProps.put("htmlQuotenPath", "c:/temp/lotto/quoten");
        defaultProps.put("templatePath", "lotto/html");
        
        defaultProps.put("hostName1", "ftp.ftp.xoom.com");
        defaultProps.put("userName1", "ralph_schaer");
        defaultProps.put("password1", "mexiko");

        defaultProps.put("hostNameXOOM", "ftp.xoom.com");
        defaultProps.put("userNameXOOM", "ralph_schaer");
        defaultProps.put("passwordXOOM", "mexiko");

        defaultProps.put("dbHelperFile1", "Ausspielungen.odf");
        defaultProps.put("dbHelperFile2", "Ausspielungen.odt");
        defaultProps.put("dbZip", "AusspielungenDB.zip");
        defaultProps.put("dbZipPath", "");
        defaultProps.put("homeDirectory", "/homepages/Ralph/Lotto");        
        defaultProps.put("database", "Ausspielungen.odb");
        defaultProps.put("lottoURL", "http://www.loterie.ch/lotterie/swisslotto/");
        defaultProps.put("lottoGewinnquoteURL", "http://www.loterie.ch/lotterie/swisslotto/swlrap.htm");
        defaultProps.put("jokerGewinnquoteURL", "http://www.loterie.ch/lotterie/swisslotto/swljok.htm");
        defaultProps.put("lottoFile", "swisslotto.html");
        defaultProps.put("lottoGewinnquoteFile", "swlrap.htm");
        defaultProps.put("jokerGewinnquoteFile", "swljok.htm");
        defaultProps.put("teletextURL", "http://194.158.230.225:9090/telenet/SF1/281/1.t-html");
        defaultProps.put("teletextfile", "teletextlotto.html");    
        
        defaultProps.put("logPath", "");
        
        defaultProps.put("webAccess", "false"); 
        defaultProps.put("proxyHost", "");
        defaultProps.put("proxyPort", "");        
        
        try {
            appProps = new Properties(defaultProps);
            FileInputStream in = new FileInputStream("appProperties");
            appProps.load(in);            
            in.close();
        } catch (Exception f) {
            DiskLog.getInstance().log(DiskLog.WARNING, getClass().getName(), "AppProperties : " + f);
        }

        createftpconnections();
                
        // Proxy
        if ((getProperty("proxyHost")).length() > 0) {
            System.getProperties().put("proxySet", "true");
            System.getProperties().put("proxyHost", getProperty("proxyHost"));
            System.getProperties().put("proxyPort", getProperty("proxyPort")); 
        }

    }
    
    private void createftpconnections() {
        ftpconnections = new Vector();
        
        String username = "userName";
        String hostname = "hostName";
        String password = "password";
        String homedirectory = "homeDirectory";
        
        int i = 1;
        while (getProperty(username+i) != null) {
            FtpConnection ftpc = new FtpConnection(getProperty(hostname+i),
                                                   getProperty(username+i),
                                                   getProperty(password+i),
                                                   getProperty(homedirectory+i));
            ftpconnections.addElement(ftpc);     
            i++;
        }
    }
    
    public Vector getFtpConnections() {
        return (Vector)ftpconnections.clone();
    }
    
    public String getProperty(String key) {
        return (String)appProps.get(key);
    }
        

}