package lotto;

import lotto.*;

import java.util.Properties;
import com.odi.*;
import com.odi.util.*;

public class DbManager {
	private static DbManager instance = new DbManager();
	
	private Database db;
	private Session session;

	private static final String ROOT_STR = "Ausspielungen";
	 
	private Ausspielungen ausspielungen;
		
	public static Ausspielungen getAusspielungen() {        
		return instance.ausspielungen;
	}
	 
	public static void initialize(String dbName, boolean createNew) {
		
		Properties prop = new Properties();
		prop.put("com.odi.useDatabaseLocking", "false");
		
		instance.session = Session.createGlobal(null, prop);        
	
		if (createNew) {
		  try {
		      Database.open(dbName, ObjectStore.UPDATE).destroy();
		  } catch(DatabaseNotFoundException e) { }
		}
	
	    try {
	        instance.db = Database.open(dbName, ObjectStore.OPEN_UPDATE);
	    } catch (DatabaseNotFoundException e) {
	        instance.db = Database.create(dbName, ObjectStore.ALL_READ | ObjectStore.ALL_WRITE);
	    }
	    Transaction tr = Transaction.begin(ObjectStore.UPDATE);
	    try {
	        instance.ausspielungen = (Ausspielungen)instance.db.getRoot(ROOT_STR);
	    } catch (DatabaseRootNotFoundException e) {
	       instance.db.createRoot(ROOT_STR, instance.ausspielungen = new Ausspielungen());
	    }        
	    
	    tr.commit(ObjectStore.RETAIN_HOLLOW);        
	}
	
	public static void shutdown() {
	    instance.db.close();
	    instance.session.terminate();
	}

	public static Transaction startUpdateTransaction() {
		return(Transaction.begin(ObjectStore.UPDATE));        
	}
	
	public static Transaction startReadTransaction() {
		return(Transaction.begin(ObjectStore.READONLY));
	}
	 
	public static void commitTransaction(Transaction tr) {
	  tr.commit(ObjectStore.RETAIN_HOLLOW);
	}
	
	public static void commitReadOnlyTransaction(Transaction tr) {
	   tr.commit(ObjectStore.RETAIN_READONLY);
	}

}