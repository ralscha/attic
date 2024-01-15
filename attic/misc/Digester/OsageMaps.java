
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.xml.sax.SAXException;
import org.apache.struts.digester.*;
import net.sourceforge.osage.mapping.*;

public final class OsageMaps {

  private ArrayList mapsList;

  public static void main(String args[]) {
    new OsageMaps().go();
  }

  public void addClass(ClassMap cp) {
    mapsList.add(cp);
  }

  private void go() {
    mapsList = new ArrayList();

    File file = new File("maps.xml");

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

    //check parsed file  
    for (int i = 0; i < mapsList.size(); i++) {
      ClassMap cm = (ClassMap)mapsList.get(i);
      System.out.println("Name: " + cm.getName());
      System.out.println("Database: " + cm.getDatabaseName());
      KeyGenMap kgm = cm.getKeyGenMap();
      if (kgm != null) {
        if (kgm instanceof HighLowMap) {
          HighLowMap hlm = (HighLowMap)kgm;
          System.out.println("HighLowMap");
          System.out.println("field: " + hlm.getField());
          System.out.println("keyColumn: " + hlm.getKeyColumn());
          System.out.println("table: " + hlm.getTable());
          System.out.println("tableColumn: " + hlm.getTableColumn());
        } else if (kgm instanceof SequenceMap) {
          SequenceMap sm = (SequenceMap)kgm;
          System.out.println("SequenceMap");
          System.out.println("Field: " + sm.getField());
          System.out.println("Name: " + sm.getName());
        } else {
          System.out.println("KeyGenMap");
          System.out.println("Field: " +  kgm.getField());
          System.out.println("Type: " + kgm.getType());
        }
      }
       
      List attr = cm.getAttributeMaps();
      for (int j = 0; j < attr.size(); j++) {
        AttributeMap am = (AttributeMap)attr.get(j);
        System.out.println("AttributeMap");
        System.out.println("convertor: " + am.getConvertor());
        System.out.println("name: " + am.getName());
        System.out.println("type: " + am.getType());

        ColumnMap cma = am.getColumnMap();
        System.out.println("ColumnMap");
        System.out.println("tableName: " + cma.getTableName());
        System.out.println("isKeyColumn: " + cma.getIsKeyColumn());
        System.out.println("isNullable: " + cma.getIsNullable());
        System.out.println("decimalDigits: " + cma.getDecimalDigits());
        System.out.println("length: " + cma.getLength());
        System.out.println("name: " + cma.getName());
        System.out.println("type: " + cma.getType());
      }

      List relation = cm.getRelationMaps();
      for (int j = 0; j < relation.size(); j++) {
        RelationMap rm = (RelationMap)relation.get(j);
        System.out.println("RelationMap");
        System.out.println("isOneToOne: " + rm.getIsOneToOne());
        System.out.println("relatedClass: " + rm.getRelatedClass());
        System.out.println("isSaveAutomatic: " + rm.getIsSaveAutomatic());
        System.out.println("isDeleteAutomatic: " + rm.getIsDeleteAutomatic());
        System.out.println("retrieve: " + rm.getRetrieve());
        System.out.println("name: " + rm.getName());

        List keys = rm.getKeys();
        for (int n = 0; n < keys.size(); n++) {
          KeyMap km = (KeyMap)keys.get(n);
          if (km instanceof FieldKeyMap) {
            FieldKeyMap fkm = (FieldKeyMap)km;
            System.out.println("FieldKeyMap");
            System.out.println("foreignKey: " + fkm.getForeignKey());
            System.out.println("name: " + fkm.getName());
          } else if (km instanceof SQLKeyMap) {
            SQLKeyMap skm = (SQLKeyMap)km;
            System.out.println("SQLKeyMap");
            System.out.println("foreignKey: " + skm.getForeignKey());
            ColumnMap cma = skm.getColumnMap();
            System.out.println("ColumnMap");
            System.out.println("tableName: " + cma.getTableName());
            System.out.println("isKeyColumn: " + cma.getIsKeyColumn());
            System.out.println("isNullable: " + cma.getIsNullable());
            System.out.println("decimalDigits: " + cma.getDecimalDigits());
            System.out.println("length: " + cma.getLength());
            System.out.println("name: " + cma.getName());
            System.out.println("type: " + cma.getType());

          }
        }


      }

      System.out.println("---------");
    }
    


  }


}
