package ch.ess.cal.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.cal.Constants;
import ch.ess.cal.model.Category;
import ch.ess.cal.model.Email;
import ch.ess.cal.model.Holiday;
import ch.ess.cal.model.Permission;
import ch.ess.cal.model.TextResource;
import ch.ess.cal.model.Translation;
import ch.ess.cal.model.TranslationText;
import ch.ess.cal.model.User;
import ch.ess.cal.model.UserGroup;
import ch.ess.cal.model.UserGroupPermission;
import ch.ess.cal.model.UserUserGroup;
import ch.ess.cal.persistence.CategoryDao;
import ch.ess.cal.persistence.HolidayDao;
import ch.ess.cal.persistence.PermissionDao;
import ch.ess.cal.persistence.TextResourceDao;
import ch.ess.cal.persistence.UserConfigurationDao;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.persistence.UserGroupDao;
import ch.ess.cal.service.Config;
import ch.ess.cal.xml.textresource.Resource;
import ch.ess.cal.xml.textresource.TextResources;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/05/24 05:07:09 $ 
 *
 * @spring.bean id="dataLoadService"
 */
public class DataLoad {

  private UserGroupDao userGroupDao;
  private UserDao userDao;
  private PermissionDao permissionDao;
  private TextResourceDao textResourceDao;
  private TextResources textResources;
  private HolidayDao holidayDao;
  private CategoryDao categoryDao;
  private HolidayRegistry holidayRegistry;
  private Set<String> permissionData;
  private UserConfigurationDao userConfigurationDao;
  
  /**
   * @spring.property reflocal="userConfigurationDao" 
   */
  public void setUserConfigurationDao(final UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  /**
   * @spring.property reflocal="permissionDao"
   */
  public void setPermissionDao(final PermissionDao permissionDao) {
    this.permissionDao = permissionDao;
  }

  /**
   * @spring.property reflocal="textResourceDao" 
   */
  public void setTextResourceDao(final TextResourceDao textResourceDao) {
    this.textResourceDao = textResourceDao;
  }

  /**
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  /**
   * @spring.property reflocal="userGroupDao"
   */
  public void setUserGroupDao(final UserGroupDao userGroupDao) {
    this.userGroupDao = userGroupDao;
  }

  /**
   * @spring.property reflocal="textResources"
   */
  public void setTextResources(final TextResources textResources) {
    this.textResources = textResources;
  }

  /**
   * @spring.property ref="permissionData"
   */
  public void setPermissionData(final Set<String> permissionData) {
    this.permissionData = permissionData;
  }

  /**
   * @spring.property reflocal="holidayDao"
   */
  public void setHolidayDao(HolidayDao holidayDao) {
    this.holidayDao = holidayDao;
  }

  /**
   * @spring.property reflocal="categoryDao"
   */
  public void setCategoryDao(CategoryDao categoryDao) {
    this.categoryDao = categoryDao;
  }

  /**
   * @spring.property reflocal="holidayRegistry"
   */
  public void setHolidayRegistry(HolidayRegistry holidayRegistry) {
    this.holidayRegistry = holidayRegistry;
  }

  @Transactional
  public void initLoad() {

    updatePermissions();
    loadTextResources();

    updateHolidays();
    holidayRegistry.init();

    //create usergroup
    List<UserGroup> groupList = userGroupDao.list();
    if (groupList.isEmpty()) {
      UserGroup adminGroup = new UserGroup();
      adminGroup.setGroupName("administrator");

      for (Iterator<Permission> it = permissionDao.list().iterator(); it.hasNext();) {
        Permission permission = it.next();
        UserGroupPermission userGroupPermission = new UserGroupPermission();
        userGroupPermission.setUserGroup(adminGroup);
        userGroupPermission.setPermission(permission);

        adminGroup.getUserGroupPermissions().add(userGroupPermission);
      }

      userGroupDao.saveOrUpdate(adminGroup);

      UserGroup userGroup = new UserGroup();
      userGroup.setGroupName("user");
      userGroupDao.saveOrUpdate(userGroup);
    }

    //create users
    List userList = userDao.list();
    if (userList.isEmpty()) {

      User newUser = new User();
      newUser.setUserName("admin");
      newUser.setFirstName("John");
      newUser.setName("Administrator");
      newUser.setLocale(Locale.ENGLISH);
      newUser.setTimeZone(TimeZone.getDefault());
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

      userDao.saveOrUpdate(newUser);

    Config userConfig = userConfigurationDao.getUserConfig(newUser);
      userConfig.setProperty(UserConfig.HIGHLIGHT_WEEKENDS, true);
      userConfigurationDao.save(newUser, userConfig);


      newUser = new User();
      newUser.setUserName("user");
      newUser.setFirstName("Ralph");
      newUser.setName("User");
      newUser.setLocale(Locale.ENGLISH);
      newUser.setTimeZone(TimeZone.getDefault());
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

      userDao.saveOrUpdate(newUser);

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

    if (permissionData == null) {
      return;
    }

    //read all permissions from db
    final Map<String, Permission> dbPermissionMap = new HashMap<String, Permission>();

    List<Permission> resultList = permissionDao.list();

    for (Permission permission : resultList) {
      dbPermissionMap.put(permission.getPermission(), permission);
    }

    for (String permission : permissionData) {

      //is permission already in db
      if (dbPermissionMap.containsKey(permission)) {
        dbPermissionMap.remove(permission);
      } else {
        //not in db, insert
        Permission newPermission = new Permission();
        newPermission.setPermission(permission);
        permissionDao.saveOrUpdate(newPermission);
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
      tt.setLocale(Constants.ENGLISH_LOCALE.toString());
      tt.setText(trans[0]);
      tt.setTranslation(translation);
      translation.getTranslationTexts().add(tt);

      tt = new TranslationText();
      tt.setLocale(Constants.GERMAN_LOCALE.toString());
      tt.setText(trans[1]);
      tt.setTranslation(translation);
      translation.getTranslationTexts().add(tt);

      newHol.setTranslation(translation);

      holidayDao.saveOrUpdate(newHol);

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
          for (Iterator<Map.Entry<String, String>> it2 = res.getText().entrySet().iterator(); it2.hasNext();) {
            Map.Entry<String, String> localEntry = it2.next();
            TranslationText tt = new TranslationText();
            tt.setLocale(localEntry.getKey());
            tt.setText(localEntry.getValue());
            tt.setTranslation(translation);
            translation.getTranslationTexts().add(tt);
          }
        }

        textResource.setTranslation(translation);

        textResourceDao.saveOrUpdate(textResource);
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

        for (Iterator<Map.Entry<String, String>> itd = memTrans.entrySet().iterator(); itd.hasNext();) {
          Map.Entry<String, String> en = itd.next();
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

  public void loadCategory() {
    //categories
    Category newCat = new Category();
    newCat.setColour("FF9900");
    newCat.setIcalKey("BUSINESS");
    newCat.setUnknown(false);
    newCat.setTranslation(getTranslation("Geschäft", "Business"));
    categoryDao.saveOrUpdate(newCat);

    newCat = new Category();
    newCat.setColour("0000FF");
    newCat.setIcalKey("APPOINTMENT");
    newCat.setUnknown(false);
    newCat.setTranslation(getTranslation("Verabredung", "Appointment"));
    categoryDao.saveOrUpdate(newCat);

    newCat = new Category();
    newCat.setColour("99CC66");
    newCat.setIcalKey("EDUCATION");
    newCat.setUnknown(false);
    newCat.setTranslation(getTranslation("Ausbildung", "Education"));
    categoryDao.saveOrUpdate(newCat);

    newCat = new Category();
    newCat.setColour("FFFF00");
    newCat.setIcalKey("HOLIDAY");
    newCat.setUnknown(false);
    newCat.setTranslation(getTranslation("Feiertag", "Holiday"));
    categoryDao.saveOrUpdate(newCat);

    newCat = new Category();
    newCat.setColour("FF00FF");
    newCat.setIcalKey("MEETING");
    newCat.setUnknown(false);
    newCat.setTranslation(getTranslation("Sitzung", "Meeting"));
    categoryDao.saveOrUpdate(newCat);

    newCat = new Category();
    newCat.setColour("993333");
    newCat.setIcalKey("MISCELLANEOUS");
    newCat.setUnknown(true);
    newCat.setTranslation(getTranslation("Verschiedenes", "Miscellaneous"));
    categoryDao.saveOrUpdate(newCat);

    newCat = new Category();
    newCat.setColour("CCFF66");
    newCat.setIcalKey("PERSONAL");
    newCat.setUnknown(false);
    newCat.setTranslation(getTranslation("Privat", "Personal"));
    categoryDao.saveOrUpdate(newCat);

    newCat = new Category();
    newCat.setColour("FF00CC");
    newCat.setIcalKey("PHONE CALL");
    newCat.setUnknown(false);
    newCat.setTranslation(getTranslation("Telefonanruf", "Phone Call"));
    categoryDao.saveOrUpdate(newCat);

    newCat = new Category();
    newCat.setColour("FF3300");
    newCat.setIcalKey("SICK DAY");
    newCat.setUnknown(false);
    newCat.setTranslation(getTranslation("Krank", "Sick Day"));
    categoryDao.saveOrUpdate(newCat);

    newCat = new Category();
    newCat.setColour("33FFFF");
    newCat.setIcalKey("SPECIAL OCCASSION");
    newCat.setUnknown(false);
    newCat.setTranslation(getTranslation("Spezielle Gelegenheit", "Special Occassion"));
    categoryDao.saveOrUpdate(newCat);

    newCat = new Category();
    newCat.setColour("669900");
    newCat.setIcalKey("TRAVEL");
    newCat.setUnknown(false);
    newCat.setTranslation(getTranslation("Reisen", "Travel"));
    categoryDao.saveOrUpdate(newCat);

    newCat = new Category();
    newCat.setColour("FFFF66");
    newCat.setIcalKey("VACATION");
    newCat.setUnknown(false);
    newCat.setTranslation(getTranslation("Urlaub", "Vacation"));
    categoryDao.saveOrUpdate(newCat);

  }

  private Translation getTranslation(String german, String english) {
    Translation translation = new Translation();

    TranslationText tt = new TranslationText();
    tt.setLocale(Constants.GERMAN_LOCALE.toString());
    tt.setText(german);
    tt.setTranslation(translation);
    translation.getTranslationTexts().add(tt);

    tt = new TranslationText();
    tt.setLocale(Constants.ENGLISH_LOCALE.toString());
    tt.setText(english);
    tt.setTranslation(translation);
    translation.getTranslationTexts().add(tt);

    return translation;

  }
}
