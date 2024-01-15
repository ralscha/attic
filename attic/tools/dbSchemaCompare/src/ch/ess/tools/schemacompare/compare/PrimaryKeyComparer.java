package ch.ess.tools.schemacompare.compare;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import ch.ess.tools.schemacompare.dbobj.DbObject;
import ch.ess.tools.schemacompare.dbobj.DbPrimaryKey;

/**
 * @author sr
 */
public class PrimaryKeyComparer extends BaseComparer {

  public PrimaryKeyComparer(Map<String, ? extends DbObject> oldColumns, Map<String, ? extends DbObject> newColumns) {
    super(oldColumns, newColumns);
  }

  public boolean compare(PrintWriter writer) {

    if (compareObjects()) {
      if (!getNewObjectsList().isEmpty()) {
        writer.println("    NEUER PRIMARY KEY: ");
        for (Iterator it = getNewObjectsList().iterator(); it.hasNext();) {
          DbPrimaryKey dt = (DbPrimaryKey)it.next();
          writer.println("    " + dt.getName());
        }
        writer.println();
      }

      if (!getOldObjectsList().isEmpty()) {
        writer.println("    GELOESCHTER PRIMARY KEY: ");
        for (Iterator it = getOldObjectsList().iterator(); it.hasNext();) {
          DbPrimaryKey dt = (DbPrimaryKey)it.next();
          writer.println("    " + dt.getName());
        }
        writer.println();
      }

      if (!getChangedObjectsList().isEmpty()) {
        writer.println("    GEAENDERTER PRIMARY KEY: ");
        for (Iterator it = getChangedObjectsList().iterator(); it.hasNext();) {
          DbPrimaryKey dt = (DbPrimaryKey)it.next();
          writer.println("    " + dt.getName());

//          DbPrimaryKey oldT = (DbPrimaryKey)getOldObjects().get(dt.getName());
//          DbPrimaryKey newT = (DbPrimaryKey)getNewObjects().get(dt.getName());
//          writer.println(oldT);
//          writer.println(newT);

        }
      }
      return true;
    }

    return false;
  }

}
