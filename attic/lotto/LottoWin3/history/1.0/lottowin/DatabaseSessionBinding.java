
package lottowin;

import javax.servlet.http.*;
import com.objectmatter.bsf.*;
import ch.ess.util.pool.*;

public class DatabaseSessionBinding implements HttpSessionBindingListener {

  private Database db;

  public DatabaseSessionBinding(Database db) {
    this.db = db;
  }


  public void valueBound(HttpSessionBindingEvent event) {
    //do nothing
  }

  public void valueUnbound(HttpSessionBindingEvent event) {
    PoolManager.returnDatabase(db);
  }  


}
