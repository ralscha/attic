package ch.ess.cal.dao;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.cal.model.ResourceGroup;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "resourceGroupDao", autowire = Autowire.BYTYPE)
public class ResourceGroupDao extends AbstractDao<ResourceGroup> {

  
  public ResourceGroupDao() {
    super(ResourceGroup.class);
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<ResourceGroup> find(final Locale locale, final String resourceGroupName) {

    if (StringUtils.isNotBlank(resourceGroupName)) {
      Query queryObject = getSession()
          .createQuery("select rg from ResourceGroup rg inner join rg.translation t inner join t.translationTexts tt where tt.locale = :locale and tt.text like :text");
      queryObject.setLocale("locale", locale);
      queryObject.setString("text", resourceGroupName + "%");
      return queryObject.list();

    }

    //wenn kein resourceGroupName angegeben wurde
    return findAll();
  }

  @Override
  public boolean canDelete(final ResourceGroup resourceGroup) {
    return size(resourceGroup.getResources()) == 0;
  }

}