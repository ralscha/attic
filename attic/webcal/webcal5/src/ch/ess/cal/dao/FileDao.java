package ch.ess.cal.dao;

import java.util.Set;

import ch.ess.base.dao.AbstractDao;
import ch.ess.base.model.User;
import ch.ess.cal.model.File;
import ch.ess.cal.model.FilePermission;
import ch.ess.cal.model.Group;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "fileDao", autowire = Autowire.BYTYPE)
public class FileDao extends AbstractDao<File> {

  public FileDao() {
    super(File.class);
  }

  public boolean hasWritePermission(File file, User user) {
    Set<FilePermission> permissions = file.getFilePermission();
    for (FilePermission permission : permissions) {
      if (permission.getUser() != null) {
        if (permission.getWritePermission()) {
          if (permission.getUser().equals(user)) {
            return true;
          }
        }
      } else {
        if (permission.getWritePermission()) {
          Group group = permission.getGroup();
          if (group.getUsers().contains(user)) {
            return true;
          }
        }
      }

    }

    return false;
  }

  public boolean hasReadOrWritePermission(File file, User user) {
    Set<FilePermission> permissions = file.getFilePermission();
    for (FilePermission permission : permissions) {
      if (permission.getUser() != null) {
        if (permission.getUser().equals(user)) {
          return true;
        }
      } else {
        Group group = permission.getGroup();
        if (group.getUsers().contains(user)) {
          return true;
        }
      }

    }

    return false;
  }
}
