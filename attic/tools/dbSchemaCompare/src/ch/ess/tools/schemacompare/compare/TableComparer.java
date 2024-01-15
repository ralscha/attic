package ch.ess.tools.schemacompare.compare;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import ch.ess.tools.schemacompare.dbobj.DbTable;

/**
 * @author sr
 */
public class TableComparer extends BaseComparer {
  
  public TableComparer(Map<String, DbTable> oldObjects, Map<String, DbTable> newObjects) {
    super(oldObjects, newObjects);
  }
  
  public boolean compare(PrintWriter writer) {

    if (compareObjects()) {
      if (!getNewObjectsList().isEmpty()) {
        writer.println("NEUE TABELLEN: ");
        for (Iterator it = getNewObjectsList().iterator(); it.hasNext();) {
          DbTable dt = (DbTable)it.next();
          writer.println("  " + dt.getName());      
        }
        writer.println();
      }
      
      if (!getOldObjectsList().isEmpty()) {
        writer.println("GELOESCHTE TABELLEN: ");
        for (Iterator it = getOldObjectsList().iterator(); it.hasNext();) {
          DbTable dt = (DbTable)it.next();
          writer.println("  " + dt.getName());      
        }
        writer.println();
      }
      
      if (!getChangedObjectsList().isEmpty()) {
        writer.println("GEAENDERTE TABELLEN: ");
        for (Iterator it = getChangedObjectsList().iterator(); it.hasNext();) {
          DbTable dt = (DbTable)it.next();
          writer.println("  " + dt.getName());
          
          DbTable oldT = (DbTable)getOldObjects().get(dt.getName());
          DbTable newT = (DbTable)getNewObjects().get(dt.getName());
          
          ColumnComparer columnComparer = new ColumnComparer(oldT.getColumns(), newT.getColumns());
          columnComparer.compare(writer);
          
          IndexComparer indexComparer = new IndexComparer(oldT.getIndexes(), newT.getIndexes());
          indexComparer.compare(writer);
      
          PrimaryKeyComparer pkComparer = new PrimaryKeyComparer(oldT.getPrimaryKeys(), newT.getPrimaryKeys());
          pkComparer.compare(writer);
          
          ExportedKeyComparer ekComparer = new ExportedKeyComparer(oldT.getExportedKeys(), newT.getExportedKeys());
          ekComparer.compare(writer);
          
          writer.println();
        }
      }
      
      return true;
      
    } 
    
    writer.println("SCHEMAS SIND GLEICH");
    return false;
    
  }
}
