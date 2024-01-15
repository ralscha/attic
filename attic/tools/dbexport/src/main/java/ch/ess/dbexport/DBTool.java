package ch.ess.dbexport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.xml.parsers.ParserConfigurationException;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.JdbcConnection;
import liquibase.database.structure.ForeignKey;
import liquibase.database.structure.Index;
import liquibase.database.structure.PrimaryKey;
import liquibase.database.structure.Table;
import liquibase.diff.Diff;
import liquibase.diff.DiffResult;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.LockException;
import liquibase.resource.FileSystemResourceAccessor;
import org.apache.commons.io.IOUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.ExcludeTableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.ext.mssql.MsSqlConnection;
import org.dbunit.ext.mysql.MySqlConnection;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Sets;

public class DBTool {

  private String schemaFileName;
  private String dataFileName;
  private String zipFileName;
  private String dbUrl;
  private String username;
  private String password;
  private String dbTyp;

  public DBTool(String fileName, String dbUrl, String username, String password, String dbTyp) {
    this.schemaFileName = fileName + "_schema.xml";
    this.dataFileName = fileName + "_data.xml";
    this.zipFileName = fileName + ".zip";

    this.dbUrl = dbUrl;
    this.username = username;
    this.password = password;
    this.dbTyp = dbTyp;
  }

  public void exportDb() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(dbUrl, username, password);

      exportSchema(conn);
      exportData(conn);
      compress();

      File schemaFile = new File(schemaFileName);
      schemaFile.delete();

      File dataFile = new File(dataFileName);
      dataFile.delete();

    } catch (SQLException e) {
      LoggerFactory.getLogger(Main.class).error("exportDb", e);
    } catch (IOException e) {
      LoggerFactory.getLogger(Main.class).error("exportDb", e);
    } catch (ParserConfigurationException e) {
      LoggerFactory.getLogger(Main.class).error("exportDb", e);
    } catch (DatabaseException e) {
      LoggerFactory.getLogger(Main.class).error("exportDb", e);
    } catch (DatabaseUnitException e) {
      LoggerFactory.getLogger(Main.class).error("exportDb", e);
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          //do nothing
        }
      }
    }

  }

  public void importDb() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(dbUrl, username, password);

      uncompress();      
      importSchema(conn);
      importData(conn);
      
      File schemaFile = new File(schemaFileName);
      schemaFile.delete();

      File dataFile = new File(dataFileName);
      dataFile.delete();      
      
    } catch (IOException e) {
      LoggerFactory.getLogger(Main.class).error("importDb", e);
    } catch (SQLException e) {
      LoggerFactory.getLogger(Main.class).error("importDb", e);
    } catch (DatabaseException e) {
      LoggerFactory.getLogger(Main.class).error("importDb", e);
    } catch (LockException e) {
      LoggerFactory.getLogger(Main.class).error("importDb", e);
    } catch (LiquibaseException e) {
      LoggerFactory.getLogger(Main.class).error("importDb", e);
    } catch (DatabaseUnitException e) {
      LoggerFactory.getLogger(Main.class).error("importDb", e);
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          //do nothing
        }
      }
    }
  }

  private void importData(Connection conn) throws DatabaseUnitException, SQLException, DataSetException, IOException {
    IDatabaseConnection connection;

    if ("mysql".equals(dbTyp)) {
      connection = new MySqlConnection(conn, conn.getCatalog());
    } else {
      connection = new MsSqlConnection(conn);
    }
    
    File importFile = new File(dataFileName);
    File tmpImportFile = new File(dataFileName+"tmp");
    BufferedWriter br = new BufferedWriter(new FileWriter(tmpImportFile));
    DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(importFile)));
    String line;
    while((line = dis.readLine()) != null) {
      line = line.replace("&#11", "");
      line = line.replace("(1)", "1");
      br.write(line);
    }
    br.close();
    dis.close();
    
    
    FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
    IDataSet dataSet = builder.build(tmpImportFile);

    Statement stmt = conn.createStatement();
    
    try {
      stmt.execute("alter table DATABASECHANGELOG drop constraint UQ_DBCL_ORDEREXEC");
      stmt.execute("alter table  DATABASECHANGELOG drop column ORDEREXECUTED");
      stmt.execute("alter table DATABASECHANGELOG drop column EXECTYPE");
    } catch (Exception e) {
      //do nothing
    }
    
    if ("mssql".equals(dbTyp)) {
      stmt.execute("EXEC sp_MSForEachTable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL'");
      InsertIdentityOperation.CLEAN_INSERT.execute(connection, dataSet);        
      stmt.execute("EXEC sp_MSForEachTable 'ALTER TABLE ? CHECK CONSTRAINT ALL'");        
    } else {
      DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
    }
  }

  private void importSchema(Connection conn) throws DatabaseException, LockException, LiquibaseException {
    Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));
    Liquibase liquibase = new Liquibase(schemaFileName, new FileSystemResourceAccessor(), database);
    liquibase.dropAll();      
    liquibase.update(null);
  }
  
  private void uncompress() throws IOException {
    File zipFile = new File(zipFileName);
    ZipInputStream zip = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
    
    ZipEntry entry;
    while((entry = zip.getNextEntry()) != null) {
      if (entry.getName().indexOf("_schema") != -1) {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(schemaFileName));
        IOUtils.copy(zip, bos);
        bos.close();
      } else {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dataFileName));
        IOUtils.copy(zip, bos);
        bos.close();      
      }
    }
  }

  private void compress() throws IOException {
    File zipFile = new File(zipFileName);
    ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
    zip.setMethod(ZipOutputStream.DEFLATED);
    zip.setLevel(Deflater.BEST_COMPRESSION);

    //Add schema file
    File schemaFile = new File(schemaFileName);
    ZipEntry zipEntry = new ZipEntry(schemaFile.getName());
    zipEntry.setTime(schemaFile.lastModified());
    zip.putNextEntry(zipEntry);
    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(schemaFile));
    IOUtils.copy(bis, zip);
    bis.close();
    zip.closeEntry();

    //Add data file
    File dataFile = new File(dataFileName);
    zipEntry = new ZipEntry(dataFile.getName());
    zipEntry.setTime(dataFile.lastModified());
    zip.putNextEntry(zipEntry);
    bis = new BufferedInputStream(new FileInputStream(dataFile));
    IOUtils.copy(bis, zip);
    bis.close();
    zip.closeEntry();

    zip.close();

  }

  @SuppressWarnings("unchecked")
  private void exportSchema(Connection conn) throws IOException, DatabaseException, ParserConfigurationException {

    InputStream is = DBTool.class.getResourceAsStream("/tables.ignore");
    List<String> lines = IOUtils.readLines(is);
    is.close();
    Set<String> ignoreTables = Sets.newHashSet(lines);

    Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));

    Diff diff = new Diff(database, (String)null);
    DiffResult diffResult = diff.compare();

    removeTables(ignoreTables, diffResult);
    removeIndexes(ignoreTables, diffResult);
    removePks(ignoreTables, diffResult);
    removeFks(ignoreTables, diffResult);

    for (ForeignKey fk : diffResult.getMissingForeignKeys()) {
      fk.setDeleteRule(null);
      fk.setUpdateRule(null);
    }

    File f = new File(schemaFileName);
    f.delete();
    diffResult.printChangeLog(schemaFileName, database);
  }

  @SuppressWarnings("unchecked")
  private void exportData(Connection conn) throws IOException, DatabaseUnitException, SQLException {
    InputStream is = DBTool.class.getResourceAsStream("/data.ignore");
    List<String> lines = IOUtils.readLines(is);
    is.close();

    IDatabaseConnection connection;

    if ("mysql".equals(dbTyp)) {
      connection = new MySqlConnection(conn, conn.getCatalog());
    } else {
      connection = new MsSqlConnection(conn);
    }
    IDataSet fullDataSet = new FilteredDataSet(new ExcludeTableFilter(lines.toArray(new String[lines.size()])), connection.createDataSet());

    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dataFileName+"tmp"));
    FlatXmlDataSet.write(fullDataSet, bos);
    bos.close();

    BufferedReader br = new BufferedReader(new FileReader(dataFileName+"tmp"));
    BufferedWriter bw = new BufferedWriter(new FileWriter(dataFileName));
    String line;
    while((line = br.readLine()) != null) {
      line = line.replace("&#1;", "");
      line = line.replace("&#2;", "");      
      line = line.replace("&#19;", "");
      line = line.replace("&#14;", "");
      line = line.replace("&#12;", "");
      line = line.replace("&#11", "");
      

      bw.write(line);
      bw.write("\n");
    }
    br.close();
    bw.close();

    File d = new File(dataFileName+"tmp");
    d.delete();
    
  }

  private void removePks(Set<String> ignoreTables, DiffResult diffResult) {
    Set<PrimaryKey> removePks = Sets.newHashSet();
    for (PrimaryKey pk : diffResult.getMissingPrimaryKeys()) {
      if (ignoreTables.contains(pk.getTable().getName())) {
        removePks.add(pk);
      }
    }
    diffResult.getMissingPrimaryKeys().removeAll(removePks);
  }

  private void removeFks(Set<String> ignoreTables, DiffResult diffResult) {
    Set<ForeignKey> removeFks = Sets.newHashSet();
    for (ForeignKey fk : diffResult.getMissingForeignKeys()) {
      if (ignoreTables.contains(fk.getPrimaryKeyTable().getName())) {
        removeFks.add(fk);
      }
    }
    diffResult.getMissingForeignKeys().removeAll(removeFks);
  }

  private void removeIndexes(Set<String> ignoreTables, DiffResult diffResult) {
    Set<Index> removeIndexes = Sets.newHashSet();
    for (Index ix : diffResult.getMissingIndexes()) {
      if (ignoreTables.contains(ix.getTable().getName())) {
        removeIndexes.add(ix);
      }
    }
    diffResult.getMissingIndexes().removeAll(removeIndexes);
  }

  private void removeTables(Set<String> ignoreTables, DiffResult diffResult) {
    Set<Table> removeTables = Sets.newHashSet();
    for (Table t : diffResult.getMissingTables()) {
      if (ignoreTables.contains(t.getName())) {
        removeTables.add(t);
      }
    }
    diffResult.getMissingTables().removeAll(removeTables);
  }
}
