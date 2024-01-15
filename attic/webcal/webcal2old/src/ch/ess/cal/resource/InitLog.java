package ch.ess.cal.resource;

import java.io.*;
import java.util.*;

import javax.servlet.*;

import org.apache.log4j.*;

public class InitLog {

  public static void init(ServletContext context) {

    File tmpDir = (File) context.getAttribute("javax.servlet.context.tempdir");
    File tmpFile = new File(tmpDir, "webcal.log");

    Properties props = new Properties();    
    props.put("log4j.rootCategory", "INFO, A2");
    props.put("log4j.appender.A2", "org.apache.log4j.RollingFileAppender");
    props.put("log4j.appender.A2.Append", "true");
    props.put("log4j.appender.A2.File", tmpFile.getPath());
    props.put("log4j.appender.A2.MaxFileSize", "5MB");
    props.put("log4j.appender.A2.MaxBackupIndex", "5");
    props.put("log4j.appender.A2.layout", "org.apache.log4j.PatternLayout");
    props.put("log4j.appender.A2.layout.ConversionPattern", "%d{dd.MM.yyyy HH:mm:ss.SSS} %-5.5p %-35.35c %-30x - %m%n");

    PropertyConfigurator.configure(props);
    

  }
}
