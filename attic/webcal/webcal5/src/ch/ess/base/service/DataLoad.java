package ch.ess.base.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.Constants;
import ch.ess.base.dao.PermissionDao;
import ch.ess.base.dao.TextResourceDao;
import ch.ess.base.dao.UserConfigurationDao;
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
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.dao.HolidayDao;
import ch.ess.cal.model.Category;
import ch.ess.cal.model.Email;
import ch.ess.cal.model.Holiday;
import ch.ess.cal.service.HolidayRegistry;
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
  private HolidayDao holidayDao;
  private CategoryDao categoryDao;
  private HolidayRegistry holidayRegistry;
  private UserConfigurationDao userConfigurationDao;
  
  
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
 
  public void setHolidayDao(final HolidayDao holidayDao) {
    this.holidayDao = holidayDao;
  }

  public void setCategoryDao(final CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  public void setHolidayRegistry(final HolidayRegistry holidayRegistry) {
    this.holidayRegistry = holidayRegistry;
  }

  public void setUserConfigurationDao(UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  @Transactional
  public void initLoad() {

    updatePermissions();
    loadTextResources();

    updateHolidays();
    holidayRegistry.init();

    //create usergroup
    List<UserGroup> groupList = userGroupDao.findAll();
    if (groupList.isEmpty()) {
      UserGroup adminGroup = new UserGroup();
      adminGroup.setGroupName("administrator");

      for (Permission permission : permissionDao.findAll()) {
        if (permission.getFeature() == null || (Constants.hasFeatures() && Constants.hasFeature(permission.getFeature()))) {
          UserGroupPermission userGroupPermission = new UserGroupPermission();
          userGroupPermission.setUserGroup(adminGroup);
          userGroupPermission.setPermission(permission);
  
          adminGroup.getUserGroupPermissions().add(userGroupPermission);
        }
      }

      userGroupDao.save(adminGroup);

      UserGroup userGroup = new UserGroup();
      userGroup.setGroupName("user");
      userGroupDao.save(userGroup);
    }

    //create users
    List<User> userList = userDao.findAll();
    if (userList.isEmpty()) {

      User newUser = new User();
      newUser.setUserName("admin");
      newUser.setFirstName("John");
      newUser.setName("Administrator");
      newUser.setLocale(Locale.GERMAN);
      newUser.setTimeZone(TimeZone.getDefault());
      newUser.setEnabled(true);
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

      Email email = new Email();
      email.setUser(newUser);
      email.setDefaultEmail(false);
      email.setEmail("ralphschaer@yahoo.com");
      email.setSequence(1);
      newUser.getEmails().add(email);

      email = new Email();
      email.setUser(newUser);
      email.setDefaultEmail(true);
      email.setEmail("schaer@ess.ch");
      email.setSequence(0);
      newUser.getEmails().add(email);

      userDao.save(newUser);

    Config userConfig = userConfigurationDao.getUserConfig(newUser);
      userConfig.setProperty(UserConfig.HIGHLIGHT_WEEKENDS, true);
      userConfigurationDao.save(newUser, userConfig);


      newUser = new User();
      newUser.setUserName("user");
      newUser.setFirstName("Ralph");
      newUser.setName("User");
      newUser.setLocale(Locale.ENGLISH);
      newUser.setTimeZone(TimeZone.getDefault());
      newUser.setEnabled(true);
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

      email = new Email();
      email.setUser(newUser);
      email.setDefaultEmail(true);
      email.setEmail("sr@ess.ch");
      email.setSequence(0);
      newUser.getEmails().add(email);

      userDao.save(newUser);
      
      userConfig = userConfigurationDao.getUserConfig(newUser);
      userConfig.setProperty(UserConfig.HIGHLIGHT_WEEKENDS, true);
      userConfigurationDao.save(newUser, userConfig);

      //Categories
      if (categoryDao.getSize(null) == 0) {
        loadCategory();
      }

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
        if (permission.getFeature() != null) {
          Permission dbPerm = dbPermissionMap.get(permission.getKey());
          dbPerm.setFeature(permission.getFeature());
          permissionDao.save(dbPerm);
        }
        dbPermissionMap.remove(permission.getKey());
      } else {
        //not in db, insert
        Permission newPermission = new Permission();
        newPermission.setPermission(permission.getKey());
        newPermission.setFeature(permission.getFeature());
        permissionDao.save(newPermission);
      }

    }

    //dbPermissionMap contains now all permissions no longer in xml file
    for (Permission dbPermission : dbPermissionMap.values()) {      
      permissionDao.delete(dbPermission);
    }
  }

  public void updateHolidays() {
    createNewHolidayIfNotExists("goodFriday", new String[]{"Good Friday", "Karfreitag"});
    createNewHolidayIfNotExists("easterSunday", new String[]{"Easter Sunday", "Ostersonntag"});
    createNewHolidayIfNotExists("easterMonday", new String[]{"Easter Monday", "Ostermontag"});
    createNewHolidayIfNotExists("ascension", new String[]{"Ascension", "Auffahrt"});
    createNewHolidayIfNotExists("whitSunday", new String[]{"Whit Sunday", "Pfingstsonntag"});
    createNewHolidayIfNotExists("whitMonday", new String[]{"Whit Monday", "Pfingstmontag"});
    createNewHolidayIfNotExists("christmasEve", new String[]{"Christmas Eve", "Heilig Abend"}, new Integer(
        Calendar.DECEMBER), new Integer(24));
    createNewHolidayIfNotExists("christmas", new String[]{"Christmas", "Weihnachten"}, new Integer(Calendar.DECEMBER),
        new Integer(25));
    createNewHolidayIfNotExists("stStephensDay", new String[]{"St Stephens Day", "Stefanstag"}, new Integer(
        Calendar.DECEMBER), new Integer(26));
    createNewHolidayIfNotExists("newYearsEve", new String[]{"New Years Eve", "Silvester"}, new Integer(
        Calendar.DECEMBER), new Integer(31));
    createNewHolidayIfNotExists("newYearsDay", new String[]{"New Years Day", "Neujahr"}, new Integer(Calendar.JANUARY),
        new Integer(1));
  }

  private void createNewHolidayIfNotExists(final String builtin, String[] trans) {
    createNewHolidayIfNotExists(builtin, trans, null, null);
  }

  private void createNewHolidayIfNotExists(final String builtin, String[] trans, Integer month, Integer dayOfMonth) {
    Holiday hol = holidayDao.findWithBuiltin(builtin);
    if (hol == null) {

      Holiday newHol = new Holiday();
      newHol.setActive(Boolean.TRUE);
      newHol.setBuiltin(builtin);
      newHol.setAfterDay(null);
      newHol.setDayOfMonth(dayOfMonth);
      newHol.setDayOfWeek(null);
      newHol.setFromYear(null);
      newHol.setMonthNo(month);
      newHol.setToYear(null);

      Translation translation = new Translation();

      TranslationText tt = new TranslationText();
      tt.setLocale(Locale.ENGLISH.getLanguage());
      tt.setText(trans[0]);
      tt.setTranslation(translation);
      translation.getTranslationTexts().add(tt);

      tt = new TranslationText();
      tt.setLocale(Locale.GERMAN.getLanguage());
      tt.setText(trans[1]);
      tt.setTranslation(translation);
      translation.getTranslationTexts().add(tt);

      newHol.setTranslation(translation);

      holidayDao.save(newHol);

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
        textResource.setFeature(res.getFeature());

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

        if (StringUtils.isNotBlank(res.getFeature())) {
          textResource.setFeature(res.getFeature());
          textResourceDao.save(textResource);
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

  public void loadCategory() {
    //categories
    Category newCat = new Category();
    newCat.setColour("FF9900");
    newCat.setIcalKey("BUSINESS");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Geschäft", "Business"));
    categoryDao.save(newCat);

    newCat = new Category();
    newCat.setColour("0000FF");
    newCat.setIcalKey("APPOINTMENT");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Verabredung", "Appointment"));
    categoryDao.save(newCat);

    newCat = new Category();
    newCat.setColour("FF33FF");
    newCat.setIcalKey("EDUCATION");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Ausbildung", "Education"));
    categoryDao.save(newCat);

    newCat = new Category();
    newCat.setColour("FFFF00");
    newCat.setIcalKey("HOLIDAY");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Feiertag", "Holiday"));
    categoryDao.save(newCat);

    newCat = new Category();
    newCat.setColour("FF3333");
    newCat.setIcalKey("MEETING");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Sitzung", "Meeting"));
    categoryDao.save(newCat);

    newCat = new Category();
    newCat.setColour("00CCCC");
    newCat.setIcalKey("MISCELLANEOUS");
    newCat.setDefaultCategory(true);
    newCat.setTranslation(getTranslation("Verschiedenes", "Miscellaneous"));
    categoryDao.save(newCat);

    newCat = new Category();
    newCat.setColour("3300FF");
    newCat.setIcalKey("PERSONAL");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Privat", "Personal"));
    categoryDao.save(newCat);

    newCat = new Category();
    newCat.setColour("FF00CC");
    newCat.setIcalKey("PHONE CALL");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Telefonanruf", "Phone Call"));
    categoryDao.save(newCat);

    newCat = new Category();
    newCat.setColour("FF3300");
    newCat.setIcalKey("SICK DAY");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Krank", "Sick Day"));
    categoryDao.save(newCat);

    newCat = new Category();
    newCat.setColour("33FFFF");
    newCat.setIcalKey("SPECIAL OCCASSION");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Spezielle Gelegenheit", "Special Occassion"));
    categoryDao.save(newCat);

    newCat = new Category();
    newCat.setColour("669900");
    newCat.setIcalKey("TRAVEL");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Reisen", "Travel"));
    categoryDao.save(newCat);

    newCat = new Category();
    newCat.setColour("FFFF33");
    newCat.setIcalKey("VACATION");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Ferien", "Vacation"));
    categoryDao.save(newCat);
    
    newCat = new Category();
    newCat.setColour("00CCFF");
    newCat.setIcalKey("WORK AT HOME");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Zu Hause Arbeiten", "Work At Home"));
    categoryDao.save(newCat);    

    newCat = new Category();
    newCat.setColour("99CC00");
    newCat.setIcalKey("MILITARY");
    newCat.setDefaultCategory(false);
    newCat.setTranslation(getTranslation("Militär", "Military"));
    categoryDao.save(newCat);
  }

  private Translation getTranslation(String german, String english) {
    Translation translation = new Translation();

    TranslationText tt = new TranslationText();
    tt.setLocale(Locale.GERMAN.getLanguage());
    tt.setText(german);
    tt.setTranslation(translation);
    translation.getTranslationTexts().add(tt);

    tt = new TranslationText();
    tt.setLocale(Locale.ENGLISH.getLanguage());
    tt.setText(english);
    tt.setTranslation(translation);
    translation.getTranslationTexts().add(tt);

    return translation;

  }
}
