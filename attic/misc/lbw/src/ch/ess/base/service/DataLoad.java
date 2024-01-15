package ch.ess.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.PermissionDao;
import ch.ess.base.dao.TextResourceDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.dao.UserGroupDao;
import ch.ess.base.model.Permission;
import ch.ess.base.model.TextResource;
import ch.ess.base.model.Translation;
import ch.ess.base.model.TranslationText;
import ch.ess.base.model.User;
import ch.ess.base.model.UserGroup;
import ch.ess.base.model.UserGroupPermission;
import ch.ess.base.model.UserUserGroup;
import ch.ess.base.xml.permission.Permissions;
import ch.ess.base.xml.textresource.Resource;
import ch.ess.base.xml.textresource.TextResources;
import ch.ess.lbw.dao.WerkDao;
import ch.ess.lbw.model.Werk;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id = "dataLoadService", autowire = Autowire.BYTYPE)
public class DataLoad {

  private UserGroupDao userGroupDao;
  private UserDao userDao;
  private PermissionDao permissionDao;
  private TextResourceDao textResourceDao;
  private TextResources textResources;
  private Permissions permissions;
  private WerkDao werkDao;

  public void setPermissionDao(final PermissionDao permissionDao) {
    this.permissionDao = permissionDao;
  }

  public void setTextResourceDao(final TextResourceDao textResourceDao) {
    this.textResourceDao = textResourceDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setUserGroupDao(final UserGroupDao userGroupDao) {
    this.userGroupDao = userGroupDao;
  }

  public void setTextResources(final TextResources textResources) {
    this.textResources = textResources;
  }

  public void setPermissions(final Permissions permissions) {
    this.permissions = permissions;
  }

  
  
  public void setWerkDao(WerkDao werkDao) {
    this.werkDao = werkDao;
  }

  @Transactional
  public void initLoad() {

    updatePermissions();
    loadTextResources();

    //create usergroup
    List<UserGroup> groupList = userGroupDao.findAll();
    if (groupList.isEmpty()) {
      UserGroup adminGroup = new UserGroup();
      adminGroup.setGroupName("administrator");

      for (Permission permission : permissionDao.findAll()) {
        UserGroupPermission userGroupPermission = new UserGroupPermission();
        userGroupPermission.setUserGroup(adminGroup);
        userGroupPermission.setPermission(permission);

        adminGroup.getUserGroupPermissions().add(userGroupPermission);
      }

      userGroupDao.save(adminGroup);

      UserGroup userGroup = new UserGroup();
      userGroup.setGroupName("user");
      userGroupDao.save(userGroup);
    }

    //create users
    List userList = userDao.findAll();
    if (userList.isEmpty()) {

      User newUser = new User();
      newUser.setUserName("admin");
      newUser.setFirstName("John");
      newUser.setName("Administrator");
      newUser.setEmail("schaer@ess.ch");
      newUser.setLocale(Locale.GERMAN);
      newUser.setPasswordHash(DigestUtils.shaHex("admin"));

      groupList = userGroupDao.find("administrator");
      if (!groupList.isEmpty()) {

        UserGroup userGroup = groupList.get(0);

        UserUserGroup userUserGroup = new UserUserGroup();
        userUserGroup.setUser(newUser);
        userUserGroup.setUserGroup(userGroup);

        newUser.getUserUserGroups().add(userUserGroup);
        userGroup.getUserUserGroups().add(userUserGroup);
      }

      userDao.save(newUser);

      newUser = new User();
      newUser.setUserName("user");
      newUser.setFirstName("Ralph");
      newUser.setName("User");
      newUser.setEmail("schaer@ess.ch");
      newUser.setLocale(Locale.GERMAN);
      newUser.setPasswordHash(DigestUtils.shaHex("user"));

      groupList = userGroupDao.find("user");
      if (!groupList.isEmpty()) {

        UserGroup userGroup = groupList.get(0);

        UserUserGroup userUserGroup = new UserUserGroup();
        userUserGroup.setUser(newUser);
        userUserGroup.setUserGroup(userGroup);

        newUser.getUserUserGroups().add(userUserGroup);
        userGroup.getUserUserGroups().add(userUserGroup);

      }

      userDao.save(newUser);

    }
    
    List<Werk> werkList = werkDao.findAll();
    if (werkList.isEmpty()) {
      Werk werk = new Werk();
      werk.setName("Geretsried");
      werkDao.save(werk);
      
      werk = new Werk();
      werk.setName("Ebersdorf");
      werkDao.save(werk);
      
      werk = new Werk();
      werk.setName("Nordhalben");
      werkDao.save(werk);
      
      werk = new Werk();
      werk.setName("Wackersdorf");
      werkDao.save(werk);
      
    }

  }

  private void updatePermissions() {

    if (permissions == null) {
      return;
    }

    //read all permissions from db
    final Map<String, Permission> dbPermissionMap = new HashMap<String, Permission>();

    List<Permission> resultList = permissionDao.findAll();

    for (Permission permission : resultList) {
      dbPermissionMap.put(permission.getPermission(), permission);
    }

    for (ch.ess.base.xml.permission.Permission permission : permissions.getPermissions()) {

      //is permission already in db
      if (dbPermissionMap.containsKey(permission.getKey())) {
        dbPermissionMap.remove(permission.getKey());
      } else {
        //not in db, insert
        Permission newPermission = new Permission();
        newPermission.setPermission(permission.getKey());
        permissionDao.save(newPermission);
      }

    }

    //dbPermissionMap contains now all permissions no longer in xml file
    for (Permission dbPermission : dbPermissionMap.values()) {
      permissionDao.delete(dbPermission);
    }
  }

  private void loadTextResources() {

    final Map<String, Resource> trmap = textResources.getResources();
    for (Map.Entry<String, Resource> entry : trmap.entrySet()) {
      String key = entry.getKey();
      Resource res = entry.getValue();

      TextResource textResource = textResourceDao.find(key);
      if (textResource == null) {
        //does not exists on db
        textResource = new TextResource();
        textResource.setName(key);

        Translation translation = new Translation();

        if (res.getText() != null) {
          for (Entry<String, String> localEntry : res.getText().entrySet()) {
            TranslationText tt = new TranslationText();
            tt.setLocale(localEntry.getKey());
            tt.setText(localEntry.getValue());
            tt.setTranslation(translation);
            translation.getTranslationTexts().add(tt);
          }
        }

        textResource.setTranslation(translation);

        textResourceDao.save(textResource);
      } else {
        //exists on db
        Translation translation = textResource.getTranslation();
        Set<TranslationText> dbTrans = translation.getTranslationTexts();

        Map<String, String> memTrans = res.getText();

        List<TranslationText> deleteList = new ArrayList<TranslationText>();

        for (TranslationText tt : dbTrans) {

          if (memTrans.get(tt.getLocale()) == null) {
            deleteList.add(tt);
          }
        }

        for (TranslationText tt : deleteList) {
          dbTrans.remove(tt);
        }

        for (Entry<String, String> en : memTrans.entrySet()) {
          String loc = en.getKey();

          if (!containsLocale(dbTrans, loc)) {
            TranslationText tt = new TranslationText();
            tt.setLocale(loc);
            tt.setText(en.getValue());
            tt.setTranslation(translation);
            translation.getTranslationTexts().add(tt);
          }
        }

      }

    }

  }

  private boolean containsLocale(final Set<TranslationText> translationTextsSet, final String locale) {
    if (translationTextsSet == null) {
      return false;
    }

    for (TranslationText tt : translationTextsSet) {
      if (tt.getLocale().equals(locale)) {
        return true;
      }
    }

    return false;
  }

}