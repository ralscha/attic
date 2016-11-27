package @packageProject@.service;

import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.framework.EntityQuery;
import @packageProject@.entity.User;

@Restrict("#{identity.loggedIn}")
@TideEnabled
public class UserQuery extends EntityQuery<User> {
  //no action  
}
