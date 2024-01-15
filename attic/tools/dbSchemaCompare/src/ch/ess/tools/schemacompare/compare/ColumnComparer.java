package ch.ess.tools.schemacompare.compare;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import ch.ess.tools.schemacompare.dbobj.DbColumn;
import ch.ess.tools.schemacompare.dbobj.DbObject;

/**
 * @author sr
 */
public class ColumnComparer extends BaseComparer {
  
  public ColumnComparer(Map<String, ? extends DbObject> oldColumns, Map<String, ? extends DbObject> newColumns) {
    super(oldColumns, newColumns);
  }
  
  public boolean compare(PrintWriter writer) {
    
    if (compareObjects()) {    
      if (!getNewObjectsList().isEmpty()) {
        writer.println("    NEUE COLUMNS: ");
        for (Iterator it = getNewObjectsList().iterator(); it.hasNext();) {
          DbColumn dt = (DbColumn)it.next();
          writer.println("    " + dt.getName());      
        }
        writer.println();
      }
      
      if (!getOldObjectsList().isEmpty()) {
        writer.println("    GELOESCHTE COLUMNS: ");
        for (Iterator it = getOldObjectsList().iterator(); it.hasNext();) {
          DbColumn dt = (DbColumn)it.next();
          writer.println("    " + dt.getName());      
        }
        writer.println();
      }
      
      if (!getChangedObjectsList().isEmpty()) {
        writer.println("    GEAENDERTE COLUMNS: ");
        for (Iterator it = getChangedObjectsList().iterator(); it.hasNext();) {
          DbColumn dt = (DbColumn)it.next();
          writer.println("    " + dt.getName());      
        }
      }
      return true;
    }
    
    return false;
  }
  
  
}
