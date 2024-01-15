package ch.ess.blankrc.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

import ch.ess.blankrc.model.Permission;
import ch.ess.blankrc.model.User;
import ch.ess.blankrc.model.UserGroup;
import ch.ess.blankrc.persistence.PermissionDao;
import ch.ess.blankrc.persistence.UserDao;
import ch.ess.blankrc.persistence.UserGroupDao;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2004/06/13 08:42:25 $ 
 * 
 * @spring.bean id="dataLoadService"
 * @spring.property name="permissionDao" reflocal="permissionDao"
 * @spring.property name="userDao" reflocal="userDao"
 * @spring.property name="userGroupDao" reflocal="userGroupDao"
 * @spring.property name="permissionData" ref="permissionData"
 */
public class DataLoad {

  //private static final Log LOG = LogFactory.getLog(DataLoad.class);

  private UserGroupDao userGroupDao;
  private UserDao userDao;
  private PermissionDao permissionDao;
  private PermissionData permissionData;

  public void setPermissionDao(PermissionDao permissionDao) {
    this.permissionDao = permissionDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setUserGroupDao(UserGroupDao userGroupDao) {
    this.userGroupDao = userGroupDao;
  }

  public void setPermissionData(PermissionData permissionData) {
    this.permissionData = permissionData;
  }

  public void initLoad() {

    updatePermissions();

    //create usergroup
    List grouplist = userGroupDao.list();
    if (grouplist.isEmpty()) {
      UserGroup adminGroup = new UserGroup();
      adminGroup.setGroupName("administrator");
      Set permissionSet = new HashSet();
      permissionSet.addAll(permissionDao.list());
      adminGroup.setPermissions(permissionSet);
      userGroupDao.save(adminGroup);

      UserGroup userGroup = new UserGroup();
      userGroup.setGroupName("user");
      userGroup.setPermissions(null);
      userGroupDao.save(userGroup);
    }

    //create users
    List userList = userDao.list();
    if (userList.isEmpty()) {

      User newUser = new User();
      newUser.setUserName("admin");
      newUser.setFirstName("John");
      newUser.setName("Administrator");
      newUser.setEmail("schaer@ess.ch");
      newUser.setLocale(Locale.ENGLISH);
      newUser.setPasswordHash(DigestUtils.shaHex("admin"));

      grouplist = userGroupDao.find("administrator");
      if (!grouplist.isEmpty()) {
        Set groupSet = new HashSet();
        groupSet.addAll(grouplist);
        newUser.setUserGroups(groupSet);
      }

      userDao.save(newUser);

      newUser = new User();
      newUser.setUserName("user");
      newUser.setFirstName("Ralph");
      newUser.setName("User");
      newUser.setEmail("schaer@ess.ch");
      newUser.setLocale(Locale.ENGLISH);
      newUser.setPasswordHash(DigestUtils.shaHex("user"));

      grouplist = userGroupDao.find("user");
      if (!grouplist.isEmpty()) {
        Set groupSet = new HashSet();
        groupSet.addAll(grouplist);
        newUser.setUserGroups(groupSet);
      }

      userDao.save(newUser);

    }

  }

  public void updatePermissions() {

    Set permissionSet = permissionData.getPermissions();
    if (permissionSet == null) {
      return;
    }

    //read all permissions from db
    Map dbPermissionMap = new HashMap();

    List resultList = permissionDao.list();

    for (Iterator it = resultList.iterator(); it.hasNext();) {
      Permission permission = (Permission)it.next();
      dbPermissionMap.put(permission.getPermission(), permission);
    }

    for (Iterator it = permissionSet.iterator(); it.hasNext();) {
      String permission = (String)it.next();

      //is permission already in db
      if (dbPermissionMap.containsKey(permission)) {
        dbPermissionMap.remove(permission);
      } else {
        //not in db, insert
        Permission newPermission = new Permission();
        newPermission.setPermission(permission);
        permissionDao.save(newPermission);
      }

    }

    //dbPermissionMap contains now all permissions no longer in xml file
    for (Iterator it = dbPermissionMap.values().iterator(); it.hasNext();) {
      Permission dbPermission = (Permission)it.next();
      permissionDao.delete(dbPermission);
    }
  }


}