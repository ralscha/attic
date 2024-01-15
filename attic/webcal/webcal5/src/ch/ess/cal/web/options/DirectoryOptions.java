package ch.ess.cal.web.options;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.Util;
import ch.ess.base.annotation.option.Option;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.dao.DirectoryDao;
import ch.ess.cal.model.Directory;

@Option(id = "directoryOptions")
public class DirectoryOptions extends AbstractOptions {

  private DirectoryDao directoryDao;
  private UserDao userDao;

  public void setDirectoryDao(final DirectoryDao directoryDao) {
    this.directoryDao = directoryDao;
  }
  
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }


  @Override
  public void init(final HttpServletRequest request) {

    User user = Util.getUser(request.getSession(), userDao);
    
    String exceptIdStr = (String)getTagAttributes().get("exceptDirectory");
    Integer exceptId = null;
    if (StringUtils.isNotBlank(exceptIdStr) && StringUtils.isNumeric(exceptIdStr)) {
      exceptId = new Integer(exceptIdStr);
    }
    
    List<Directory> directoryList = directoryDao.findAllRootDirectories(null);

    populate("", directoryList, exceptId, user);

  }

  private void populate(String indent, Collection<Directory> directories, Integer exceptId, User user) {
    for (Directory directory : directories) {
      if (directoryDao.hasReadOrWritePermission(directory, user)) {
        if (!directory.getId().equals(exceptId)) {
          add(indent + directory.getName(), directory.getId().toString());
  
          if (!directory.getChildren().isEmpty()) {
            populate(indent + " ", directory.getChildren(), exceptId, user);
          }
        }
      }
    }    
  }
  
}
