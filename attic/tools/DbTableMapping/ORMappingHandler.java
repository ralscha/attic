
import org.xml.sax.*;
import java.util.*;

public class ORMappingHandler {

	
	private String driver = null;
	private String url = null;
	private String user = null;
	private String password = null;
	private String pack = null;
	private Map keyMap = null;
	private List tableList = null;
	private List manyList = null;
	private List oneList = null;
	private Map substMap = null;
	private String substdb = null;
	private String substprg = null;
	private Set boolSet = null;
	private String bool = null;
	private String dbsubstcol = null;
	private String nullablecol = null;
	private String dbsubsttype = null;	
	private Map dbsubstMap = null;
	private String output = null;
	private Set nullableSet = null;	
	private Set ignoreSet = new HashSet();
		
	private Table table;
	private Key key;
	private Relation relation;
	
	public String getDriver() {
		return driver;
	}
	
	public String getURL() {
		return url;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getPassword() {
		return password;
	}
	
	public List getTableList() {
		return tableList;
	}
	
	/*-------------------------*/
	
	public void startormapping() {
	}
	
	public void endormapping() {
	}
	
	public void textOfdriver(String str) {
		driver = str;		
	}
	
	public void textOfurl(String str) {
		url = str;
	}

	public void textOfuser(String str) {
		user = str;
	}
	
	public void textOfpassword(String str) {
		password = str;
	}
	
	public void textOfpackage(String str) {
		pack = str;
	}
	
	public void textOfoutput(String str) {
		output = str;
	}
	
	public void starttable() {
		table = new Table();
		table.setPackage(pack);
	}
	
	public void endtable() {
		if (tableList == null)
			tableList = new ArrayList();
		
		table.setOneReferenceList(oneList);
		table.setManyReferenceList(manyList);
		table.setKeyMap(keyMap);
		table.setSubstMap(substMap);
		table.setBoolSet(boolSet);
		tableList.add(table);	
		table.setOutput(output);
		table.setDBSubstMap(dbsubstMap);
		table.setIgnoreSet(ignoreSet);

		oneList = null;
		manyList = null;
		keyMap = null;
		substMap = null;
		boolSet = null;
		dbsubstMap = null;
    ignoreSet = new HashSet();
		
	}
	
	public void startsubst() {
		if (substMap == null)
			substMap = new HashMap();
	}
	
	public void textOfdb(String str) {
		substdb = str;
	}
	
	public void textOfprg(String str) {
		substprg = str;
	}
	
	public void endsubst() {

		substMap.put(substdb, substprg);
		
		substdb = null;
		substprg = null;
	}
	
	
	public void startnullable() {
		if (nullableSet == null)
			nullableSet = new HashSet();
	}
	
	public void endnullable() {
		table.setNullableSet(nullableSet);
		nullableSet = null;
	}
	
  public void startignore() {
    if (ignoreSet == null)
      ignoreSet = new HashSet();
  }

  public void textOfignore(String str) {
    ignoreSet.add(str);
  }

	public void startdbsubst() {
		if (dbsubstMap == null)
			dbsubstMap = new HashMap();
	}
	
	public void textOfcol(String str) {
		dbsubstcol = str;
		if (nullableSet != null)
			nullableSet.add(str);
	}
	
	public void textOftype(String str) {
		dbsubsttype = str;
	}
	
	public void enddbsubst() {
		dbsubstMap.put(dbsubstcol,dbsubsttype);
	}
	
	public void startboolean() {
		if (boolSet == null)
			boolSet = new HashSet();
	}
	
	public void textOfboolean(String str) {
		bool = str;
	}
	
	public void endboolean() {
		boolSet.add(bool);
	}
	
	public void textOfname(String str) {
		table.setName(str);
	}
	
	public void textOfkeygen(String str) {
		table.setKeyGen(str);
	}

	public void startkey(AttributeList list) {
		key = new Key();	
		key.setAutoInc(new Boolean(list.getValue("auto")).booleanValue());
	}

	public void textOfkey(String str) {
		key.setKeyName(str);
	}
	
	public void endkey() {
		if (keyMap == null)
			keyMap = new HashMap();
		
		keyMap.put(key.getKeyName().toUpperCase(), key); 	
	}
	
	
	public void startmanyreference() {
		relation = new Relation();
	}
	
	public void endmanyreference() {
		if (manyList == null)
			manyList = new ArrayList();
		
		manyList.add(relation);	
	}
	
	public void startonereference() {
		relation = new Relation();
	}
	
	public void endonereference() {
		if (oneList == null)
			oneList = new ArrayList();
		
		oneList.add(relation);		
	}
	
	public void textOfcolumn(String str) {
		relation.setColumn(str);
	}
	
	public void textOfrefcolumn(String str) {
		relation.setRefColumn(str);
	}
	
	public void textOfreftable(String str) {
		relation.setRefTable(str);
	}
	
}

  