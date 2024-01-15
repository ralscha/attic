

import java.util.*;

public class Table {
	private String name;
  private String origname;
  private String schema;
	private Map keyMap;
	private List manyReferenceList;
	private List oneReferenceList;
	private String pack;
	private String keygen;
	private Map substMap;
	private Set boolSet;
	private Map dbsubstMap;	
	private String output;
	private Set nullableSet;
  private Set ignoreSet;
	
	public Table() {
		name = null;
    origname = null;
		keyMap = new HashMap();
		manyReferenceList = null;
		oneReferenceList = null;
		pack = null;
		keygen = null;
		substMap = null;
		boolSet = null;
		output = null;
    schema = null;
    nullableSet = null;
    ignoreSet = null;
	}


	public void setName(String name) {
    int pos = name.indexOf(".");
    if (pos != -1) {
  		this.name = name.substring(pos+1);
      this.schema = name.substring(0, pos);
    } else {
      this.name = name;
    }
    this.origname = this.name;
    this.name = this.name.toLowerCase();
	}
	
  public String getOrigName() {
    return origname;
  }

  public String getSchema() {
    return schema;
  }

	public String getName() {
		return name;
	}

	public void setKeyMap(Map map) {
    if (map == null)
      map = new HashMap();
		keyMap = map;
	}
	
	public Map getKeyMap() {
		return keyMap;
	}

	public void setSubstMap(Map map) {
		substMap = map;
	}
	
	public Map getSubstMap() {
		return substMap;
	}

	public void setManyReferenceList(List list) {
		manyReferenceList = list;
	}
	
	public List getManyReferenceList() {
		return manyReferenceList;
	}
	
	public void setOneReferenceList(List list) {
		oneReferenceList = list;
	}
	
	public List getOneReferenceList() {
		return oneReferenceList;
	}
	
	public void setPackage(String str) {
		pack = str;
	}
	
	public String getPackage() {
		return pack;
	}
	
	public void setKeyGen(String str) {
		keygen = str;
	}
	
	public String getKeyGen() {
		return keygen;
	}
	
	public void setBoolSet(Set boolSet) {
		this.boolSet = boolSet;
	}
	
	public Set getBoolSet() {
		return boolSet;
	}
	
	public void setDBSubstMap(Map substMap) {
		this.dbsubstMap = substMap;
	}
	
	public Map getDBSubstMap() {
		return dbsubstMap;
	}

	public void setOutput(String out) {
		output = out;
	}	
	
	public String getOutput() {
		return output;
	}
	
	public void setNullableSet(Set nullableSet) {
		this.nullableSet = nullableSet;
	}
	
	public Set getNullableSet() {
		return nullableSet;
	}

  public void setIgnoreSet(Set ignoreSet) {
    this.ignoreSet = ignoreSet;
  }

  public Set getIgnoreSet() {
    return ignoreSet;
  }
}