package ch.ess.tools.schemacompare.compare;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import ch.ess.tools.schemacompare.dbobj.DbExportedKey;
import ch.ess.tools.schemacompare.dbobj.DbObject;

/**
 * @author sr
 */
public class ExportedKeyComparer extends BaseComparer {

  public ExportedKeyComparer(Map<String, ? extends DbObject> oldColumns, Map<String, ? extends DbObject> newColumns) {
    super(oldColumns, newColumns);
  }

  public boolean compare(PrintWriter writer) {

    if (compareObjects()) {
      if (!getNewObjectsList().isEmpty()) {
        writer.println("    NEUER EXPORTED KEY: ");
        for (Iterator it = getNewObjectsList().iterator(); it.hasNext();) {
          DbExportedKey dt = (DbExportedKey)it.next();
          writer.println("    " + dt.getPkColumnName() + " --> " + dt.getFkTableName() + "." + dt.getFkColumnName());
        }
        writer.println();
      }

      if (!getOldObjectsList().isEmpty()) {
        writer.println("    GELOESCHTER EXPORTED KEY: ");
        for (Iterator it = getOldObjectsList().iterator(); it.hasNext();) {
          DbExportedKey dt = (DbExportedKey)it.next();
          writer.println("    " + dt.getPkColumnName() + " --> " + dt.getFkTableName() + "." + dt.getFkColumnName());
        }
        writer.println();
      }

      if (!getChangedObjectsList().isEmpty()) {
        writer.println("    GEAENDERTER EXPORTED KEY: ");
        for (Iterator it = getChangedObjectsList().iterator(); it.hasNext();) {
          DbExportedKey dt = (DbExportedKey)it.next();
          writer.println("    " + dt.getPkColumnName() + " --> " + dt.getFkTableName() + "." + dt.getFkColumnName());

//          DbExportedKey oldT = (DbExportedKey)getOldObjects().get(dt.getName());
//          DbExportedKey newT = (DbExportedKey)getNewObjects().get(dt.getName());
//          writer.println(oldT);
//          writer.println(newT);

        }
      }
      return true;
    }

    return false;
  }

}