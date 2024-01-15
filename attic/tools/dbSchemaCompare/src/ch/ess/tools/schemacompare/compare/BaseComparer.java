package ch.ess.tools.schemacompare.compare;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.CollectionUtils;
import ch.ess.tools.schemacompare.dbobj.DbObject;


public class BaseComparer {

  private Map<String, ? extends DbObject> newObjects;
  private Map<String, ? extends DbObject> oldObjects;

  List<DbObject> newObjectsList;
  List<DbObject> oldObjectsList;
  List<DbObject> changedObjectsList;

  public BaseComparer(Map<String, ? extends DbObject> oldObjects, Map<String, ? extends DbObject> newObjects) {
    this.oldObjects = oldObjects;
    this.newObjects = newObjects;

    newObjectsList = new ArrayList<DbObject>();
    oldObjectsList = new ArrayList<DbObject>();
    changedObjectsList = new ArrayList<DbObject>();
  }

  public boolean compareObjects() {
    Set<String> tmpSet = new HashSet<String>();

    Collection changedObjects = CollectionUtils.disjunction(newObjects.values(), oldObjects.values());
    for (Iterator it = changedObjects.iterator(); it.hasNext();) {
      DbObject dbObj = (DbObject)it.next();

      if (!tmpSet.contains(dbObj.getName())) {
        tmpSet.add(dbObj.getName());

        if (newObjects.containsKey(dbObj.getName()) && oldObjects.containsKey(dbObj.getName())) {
          changedObjectsList.add(dbObj);
        } else if (newObjects.containsKey(dbObj.getName()) && !oldObjects.containsKey(dbObj.getName())) {
          newObjectsList.add(dbObj);
        } else {
          oldObjectsList.add(dbObj);
        }
      }
    }

    return !changedObjectsList.isEmpty() || !newObjectsList.isEmpty() || !oldObjectsList.isEmpty();

  }

  public List<DbObject> getChangedObjectsList() {
    return changedObjectsList;
  }

  public List<DbObject> getNewObjectsList() {
    return newObjectsList;
  }

  public List<DbObject> getOldObjectsList() {
    return oldObjectsList;
  }

  public Map<String, ? extends DbObject> getNewObjects() {
    return newObjects;
  }

  public Map<String, ? extends DbObject> getOldObjects() {
    return oldObjects;
  }
}