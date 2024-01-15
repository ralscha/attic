package ch.ess.cal.dao;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.cal.model.Resource;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "resourceDao", autowire = Autowire.BYTYPE)
public class ResourceDao extends AbstractDao<Resource> {
  
  public ResourceDao() {
    super(Resource.class);
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Resource> find(final Locale locale, final String resourceName) {

    if (StringUtils.isNotBlank(resourceName)) {

      Query queryObject = getSession()
          .createQuery("select r from Resource r inner join r.translation t inner join t.translationTexts tt where tt.locale = :locale and tt.text like :text");
      queryObject.setLocale("locale", locale);
      queryObject.setString("text", resourceName + "%");
      return queryObject.list();

    }
    
    //wenn kein resourceGroupName angegeben wurde
    return findAll();

  }

  @Override
  public boolean canDelete(final Resource resource) {
    return size(resource.getEvents()) == 0;
  }

}