
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.xml.sax.SAXException;
import org.apache.struts.digester.*;
import net.sourceforge.osage.mapping.*;

public final class OsageDatabase {

  private ArrayList databaseList;

  public static void main(String args[]) {
    new OsageDatabase().go();
  }

  public void addDatabase(DatabaseMap dm) {
    databaseList.add(dm);
  }

  private void go() {
    databaseList = new ArrayList();

    File file = new File("database.xml");

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
  
    for (int i = 0; i < databaseList.size(); i++) {
      DatabaseMap dm = (DatabaseMap)databaseList.get(i);
      System.out.println(dm.getName());
      System.out.println(dm.getVendor());

      ConnectionSourceMap csm = (ConnectionSourceMap)dm.getConnectionSourceMap();
      if (csm instanceof PoolMap) {
        System.out.println("PoolMap");
        System.out.println("class = " + ((PoolMap)csm).getClassName());
      } else if (csm instanceof DriverMap) {
        System.out.println("DriverMap");
        System.out.println("class = " +((DriverMap)csm).getClassName());
        System.out.println("url = " + ((DriverMap)csm).getUrl());
      } else if (csm instanceof DataSourceMap) {
        System.out.println("DatasourceMap");
        System.out.println("name = " + ((DataSourceMap)csm).getName());
      }

      List params = dm.getParams();
      for (int j = 0; j < params.size(); j++) {
        Param param = (Param)params.get(j);
        System.out.println(param.getName()+"="+param.getValue());
      }
      System.out.println("---------");
    }
    


  }


}
