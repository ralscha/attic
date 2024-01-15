
import java.io.*;
import java.util.*;
import org.xml.sax.SAXException;
import org.apache.struts.digester.*;
import net.sourceforge.osage.mapping.*;

public class ReadXML {

	private ArrayList databaseList;
	private ArrayList mapsList;

  
	public void addDatabase(DatabaseMap dm) {
	  databaseList.add(dm);
	}

	public void addClass(ClassMap cp) {
	  mapsList.add(cp);
	}

  public void readDatabase(String fileName) {
    databaseList = new ArrayList();

    File file = new File(fileName);

    // Define a digester for our use
    Digester digester = new Digester();
    //digester.setDebug(999);
    digester.setValidating(true);

    //add rules
    digester.push(this);

    digester.addObjectCreate("db/database", "net.sourceforge.osage.mapping.DatabaseMap");
    digester.addSetProperties("db/database");
   
    
    Map substMap = new HashMap();
    substMap.put("class", "className");   

    digester.addObjectCreate("db/database/driver", "net.sourceforge.osage.mapping.DriverMap");
    digester.addSetProperties("db/database/driver", substMap);
    digester.addSetNext("db/database/driver", "setConnectionSourceMap",
    		    "net.sourceforge.osage.mapping.ConnectionSourceMap");
  
    digester.addObjectCreate("db/database/pool", "net.sourceforge.osage.mapping.PoolMap");
    digester.addSetProperties("db/database/pool", substMap);
    digester.addSetNext("db/database/pool", "setConnectionSourceMap",
    		    "net.sourceforge.osage.mapping.ConnectionSourceMap");

    digester.addObjectCreate("db/database/datasource", "net.sourceforge.osage.mapping.DataSourceMap");
    digester.addSetProperties("db/database/datasource");
    digester.addSetNext("db/database/datasource", "setConnectionSourceMap",
    		    "net.sourceforge.osage.mapping.ConnectionSourceMap");

    digester.addObjectCreate("db/database/param", "net.sourceforge.osage.mapping.Param");
    digester.addSetProperties("db/database/param");
    digester.addSetNext("db/database/param", "addParam",
    		    "net.sourceforge.osage.mapping.Param");

    digester.addSetNext("db/database", "addDatabase",
    		    "net.sourceforge.osage.mapping.DatabaseMap");



    // Parse the specified XML file
    try {
      digester.parse(file);
    } catch (IOException e) {
      System.out.println("IOException: " + e);
      e.printStackTrace(System.out);
      System.exit(2);
    }
    catch (SAXException e) {
      System.out.println("SAXException: " + e);
      e.printStackTrace(System.out);
      System.exit(3);
    }
  
  }

  public void readMaps(String fileName) {
    mapsList = new ArrayList();
    File file = new File(fileName);

    // Define a digester for our use
    Digester digester = new Digester();
    //digester.setDebug(999);
    digester.setValidating(true);

    //add rules
    digester.push(this);

    Map substMap = new HashMap();
    substMap.put("database", "databaseName");   
    digester.addObjectCreate("maps/class", "net.sourceforge.osage.mapping.ClassMap");
    digester.addSetProperties("maps/class", substMap);
    digester.addSetNext("maps/class", "addClass", "net.sourceforge.osage.mapping.ClassMap");

    digester.addObjectCreate("maps/class/key-generator", "net.sourceforge.osage.mapping.KeyGenMap");
    digester.addSetProperties("maps/class/key-generator");
    digester.addSetNext("maps/class/key-generator", "setKeyGenMap",
    		    "net.sourceforge.osage.mapping.KeyGenMap");

    digester.addObjectCreate("maps/class/sequence", "net.sourceforge.osage.mapping.SequenceMap");
    digester.addSetProperties("maps/class/sequence");
    digester.addSetNext("maps/class/sequence", "setKeyGenMap",
    		    "net.sourceforge.osage.mapping.KeyGenMap");

    digester.addObjectCreate("maps/class/highlow", "net.sourceforge.osage.mapping.HighLowMap");
    digester.addSetProperties("maps/class/highlow");
    digester.addSetNext("maps/class/highlow", "setKeyGenMap",
    		    "net.sourceforge.osage.mapping.KeyGenMap");

    digester.addObjectCreate("maps/class/field", "net.sourceforge.osage.mapping.AttributeMap");
    digester.addSetProperties("maps/class/field");
    digester.addSetNext("maps/class/field", "addAttributeMap",
    		    "net.sourceforge.osage.mapping.AttributeMap");

    substMap.clear();
    substMap.put("table", "tableName");
    substMap.put("primary-key", "isKeyColumn");
    substMap.put("nullable", "isNullable");
    substMap.put("decimal", "decimalDigits");
    digester.addObjectCreate("maps/class/field/sql", "net.sourceforge.osage.mapping.ColumnMap");
    digester.addSetProperties("maps/class/field/sql", substMap);
    digester.addSetNext("maps/class/field/sql", "setColumnMap",
    		    "net.sourceforge.osage.mapping.ColumnMap");

    digester.addObjectCreate("maps/class/relation/sql-key/sql", "net.sourceforge.osage.mapping.ColumnMap");
    digester.addSetProperties("maps/class/relation/sql-key/sql", substMap);
    digester.addSetNext("maps/class/relation/sql-key/sql", "setColumnMap",
    		    "net.sourceforge.osage.mapping.ColumnMap");
    
    substMap.clear();
    substMap.put("oneToOne", "isOneToOne");
    substMap.put("class", "relatedClass");
    substMap.put("autoSave", "isSaveAutomatic");
    substMap.put("autoDelete", "isDeleteAutomatic");
    digester.addObjectCreate("maps/class/relation", "net.sourceforge.osage.mapping.RelationMap");
    digester.addSetProperties("maps/class/relation", substMap);
    digester.addSetNext("maps/class/relation", "addRelationMap",
    		    "net.sourceforge.osage.mapping.RelationMap");

    substMap.clear();
    substMap.put("foreign", "foreignKey");
    digester.addObjectCreate("maps/class/relation/key", "net.sourceforge.osage.mapping.FieldKeyMap");
    digester.addSetProperties("maps/class/relation/key", substMap);
    digester.addSetNext("maps/class/relation/key", "addKeys",
    		    "net.sourceforge.osage.mapping.KeyMap");

    substMap.clear();
    substMap.put("foreign", "foreignKey");
    substMap.put("sql", "columnMap");   
    digester.addObjectCreate("maps/class/relation/sql-key", "net.sourceforge.osage.mapping.SQLKeyMap");
    digester.addSetProperties("maps/class/relation/sql-key", substMap);
    digester.addSetNext("maps/class/relation/sql-key", "addKeys",
    		    "net.sourceforge.osage.mapping.KeyMap");

    // Parse the specified XML file
    try {
      digester.parse(file);
    } catch (IOException e) {
      System.out.println("IOException: " + e);
      e.printStackTrace(System.out);
      System.exit(2);
    }
    catch (SAXException e) {
      System.out.println("SAXException: " + e);
      e.printStackTrace(System.out);
      System.exit(3);
    }
  
  }


	public static void main(String args[]) {
    ReadXML rx = new ReadXML();
    rx.readDatabase("database.xml");
    rx.readMaps("maps.xml");
    //the data is now in mapsList and databaseList    
	}


}