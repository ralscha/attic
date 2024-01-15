package ch.ess.cal.dao;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.base.model.User;
import ch.ess.cal.model.Group;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id = "groupDao", autowire = Autowire.BYTYPE)
public class GroupDao extends AbstractDao<Group> {
  
  public GroupDao() {
    super(Group.class);
  }
  
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Group> find(final Locale locale, final String groupName) {

    if (StringUtils.isNotBlank(groupName)) {
      Query queryObject = getSession()
          .createQuery("select g from Group g inner join g.translation t inner join t.translationTexts tt where tt.locale = :locale and tt.text like :text");
      queryObject.setLocale("locale", locale);
      queryObject.setString("text", groupName + "%");
      return queryObject.list();
    }

    //wenn kein groupName angegeben wurde
    return findAll();
  }
  
  @Transactional(readOnly = true)
  public List<Group> findDocumentGroups() {
    return findByCriteria(Restrictions.eq("documentGroup", true));
  }
  
  @Transactional(readOnly = true)
  public List<Group> findTimeGroups() {
    return findByCriteria(Restrictions.eq("timeGroup", true));
  }
  
  @Transactional(readOnly = true)
  public List<Group> findTaskGroups() {
    return findByCriteria(Restrictions.eq("taskGroup", true));
  }

  @Transactional(readOnly = true)
  public List<Group> findEventGroups() {
    return findByCriteria(Restrictions.eq("eventGroup", true));
  }


  @Override
  @Transactional
  public void delete(String id) {
    delete(findById(id));
  }

  @Override
  @Transactional
  public void delete(Group group) {
    for (User user : group.getUsers()) {
      user.getGroups().remove(group);
    }
    for (User user : group.getAccessUsers()) {
      user.getAccessGroups().remove(group);
    }
    getSession().delete(group);
  }

  @Override
  @Transactional
  public void deleteAll() {
    List<Group> groups = findAll();
    for (Group group : groups) {
      delete(group);
    }
  }

  @Override
  @Transactional
  public boolean canDelete(final Group group) {
    return size(group.getEvents()) == 0 && size(group.getFilePermission()) == 0 && size(group.getDirectoryPermission()) == 0;
  }

}