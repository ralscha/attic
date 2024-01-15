package ch.sr.lottowin.db;

import com.objectmatter.bsf.*;

public class UserUtil {

  public static Joker getJoker(Database db, User user, long id) {
    OQuery query = new OQuery();
    query.add(id, "id");
    query.add(user.getUserid(), "userid");
    query.setMaxCount(1);

    Joker[] jokers = (Joker[]) db.get(Joker.class, query);
    if (jokers != null) {
      return jokers[0];
    } 
    return null;
    
    
  }


  public static Tip getTip(Database db, User user, long id) {
    OQuery query = new OQuery();
    query.add(id, "id");
    query.add(user.getUserid(), "user");
    query.setMaxCount(1);

    Tip[] tips = (Tip[]) db.get(Tip.class, query);
    if (tips != null) {
      return tips[0];
    } 
    return null;
     
  }
}
