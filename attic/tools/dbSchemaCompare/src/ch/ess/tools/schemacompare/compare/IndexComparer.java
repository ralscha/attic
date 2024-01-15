package ch.ess.tools.schemacompare.compare;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import ch.ess.tools.schemacompare.dbobj.DbIndex;
import ch.ess.tools.schemacompare.dbobj.DbObject;

/**
 * @author sr
 */
public class IndexComparer extends BaseComparer {

  public IndexComparer(Map<String, ? extends DbObject> oldColumns, Map<String, ? extends DbObject> newColumns) {
    super(oldColumns, newColumns);
  }

  public boolean compare(PrintWriter writer) {

    if (compareObjects()) {
      if (!getNewObjectsList().isEmpty()) {
        writer.println("    NEUER INDEX: ");
        for (Iterator it = getNewObjectsList().iterator(); it.hasNext();) {
          DbIndex dt = (DbIndex)it.next();
          writer.println("    " + dt.getIndexName() + " (" + dt.getColumnName() + ")");
        }
        writer.println();
      }

      if (!getOldObjectsList().isEmpty()) {
        writer.println("    GELOESCHTER INDEX: ");
        for (Iterator it = getOldObjectsList().iterator(); it.hasNext();) {
          DbIndex dt = (DbIndex)it.next();
          writer.println("    " + dt.getIndexName() + " (" + dt.getColumnName() + ")");
        }
        writer.println();
      }

      if (!getChangedObjectsList().isEmpty()) {
        writer.println("    GEAENDERTER INDEX: ");
        for (Iterator it = getChangedObjectsList().iterator(); it.hasNext();) {
          DbIndex dt = (DbIndex)it.next();
          writer.println("    " + dt.getIndexName() + " (" + dt.getColumnName() + ")");
        }
      }
      return true;
    }

    return false;
  }

}
