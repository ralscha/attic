package ch.ess.dbexport;

import java.util.Enumeration;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Main {

  static {
    Enumeration<String> e = LogManager.getLogManager().getLoggerNames();
    while (e.hasMoreElements()) {
      String logger = e.nextElement();

      Logger rootLogger = LogManager.getLogManager().getLogger(logger);
      Handler[] handlers = rootLogger.getHandlers();
      for (int i = 0; i < handlers.length; i++) {
        rootLogger.removeHandler(handlers[i]);
      }
    }

    SLF4JBridgeHandler.install();

    try {
      Class.forName("net.sourceforge.jtds.jdbc.Driver");
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException ex) {
      LoggerFactory.getLogger(Main.class).error("loading jdbc drivers", ex);
    }
  }

  @SuppressWarnings("static-access")
  public static void main(String[] args) {

    Options options = new Options();
    Option option = OptionBuilder.withDescription("Filename des Schema/Daten Zipfiles (z.B. c:/temp/db.zip)").hasArg().withArgName(
        "zipFile").isRequired().create("f");
    options.addOption(option);

    option = OptionBuilder.withDescription("DB Server IP. Default localhost").hasArg().withArgName("ipAdresse").create("ip");
    options.addOption(option);

    option = OptionBuilder.withDescription("DB Name").hasArg().withArgName("name").isRequired().create("db");
    options.addOption(option);

    option = OptionBuilder.withDescription("DB Instanzname fuer MS SQL Server").hasArg().withArgName("name").create("i");
    options.addOption(option);

    option = OptionBuilder.withDescription("DB Username. Default sa").hasArg().withArgName("username").create("u");
    options.addOption(option);

    option = OptionBuilder.withDescription("DB Password").hasArg().withArgName("password").create("p");
    options.addOption(option);

    option = OptionBuilder.withDescription("DB Typ. Default mssql").hasArg().withArgName("mysql|mssql").create("t");
    options.addOption(option);

    option = OptionBuilder.withDescription("DB Exportieren oder importieren. Default export").hasArg().withArgName("export|import").create(
        "e");
    options.addOption(option);

    CommandLineParser parser = new GnuParser();
    try {
      CommandLine cmd = parser.parse(options, args);

      String dbTyp = "mssql";
      String dbIp = cmd.getOptionValue("ip");
      String dbName = cmd.getOptionValue("db");
      String fileName = cmd.getOptionValue("f");
      String username = cmd.getOptionValue("u");
      String password = cmd.getOptionValue("p");
      String instanzName = cmd.getOptionValue("i");
      String work = "export";
      
      if (username == null) {
        username = "sa";
      }
      if (dbIp == null) {
        dbIp = "localhost";
      }

      if (cmd.hasOption("t")) {
        String dt = cmd.getOptionValue("t");

        if (!"mysql".equals(dt) && !"mysql".equals(dt)) {
          throw new IllegalArgumentException();
        }

        dbTyp = dt;
      }

      if (cmd.hasOption("e")) {
        String e = cmd.getOptionValue("e");
        if (!"export".equals(e) && !"import".equals(e)) {
          throw new IllegalArgumentException();
        }
        work = e;
      }

      String baseFileName;
      if (fileName.endsWith(".zip")) {
        baseFileName = fileName.substring(0, fileName.indexOf(".zip"));
      } else {
        baseFileName = fileName;
      }
      
      String dbUrl;
      if ("mysql".equals(dbTyp)) {
        dbUrl = "jdbc:mysql://";
        dbUrl += dbIp;
        dbUrl += "/";
        dbUrl += dbName;
        
        if ("import".equals(work)) {
          dbUrl += "?sessionVariables=FOREIGN_KEY_CHECKS=0";
        }
      } else {
        //jdbc:jtds:sqlserver://localhost/ct59;instance=mssqlserver
        dbUrl = "jdbc:jtds:sqlserver://";
        dbUrl += dbIp;
        dbUrl += "/";
        dbUrl += dbName;
        if (instanzName != null) {
          dbUrl += ";instance=" + instanzName;
        }
      }

      DBTool tool = new DBTool(baseFileName, dbUrl, username, password, dbTyp);
      if ("export".equals(work)) {
        tool.exportDb();
      } else {
        tool.importDb();
      }

    } catch (IllegalArgumentException e) {
      printHelp(options);
    } catch (ParseException e) {
      printHelp(options);
    }

  }

  private static void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.setSyntaxPrefix("Aufruf: ");
    formatter.printHelp("java -jar dbtool.jar [OPTION] ", options);
  }

}
