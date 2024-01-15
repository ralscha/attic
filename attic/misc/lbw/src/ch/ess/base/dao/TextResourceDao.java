package ch.ess.base.dao;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.model.TextResource;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id = "textResourceDao", autowire = Autowire.BYTYPE)
public class TextResourceDao extends AbstractDao<TextResource> {

  public TextResourceDao() {
    super(TextResource.class);
  }
  

  @Transactional(readOnly = true)
  public TextResource find(final String name) {
    return findByCriteriaUnique(Restrictions.eq("name", name));
  }

  @Transactional(readOnly = true)
  public String getText(final String name, final String locale) {
    Query queryObject = getSession()
        .createQuery("select tt.text from TextResource tr inner join tr.translation t inner join t.translationTexts tt where tr.name = :name and tt.locale = :locale");
    queryObject.setString("name", name);
    queryObject.setString("locale", locale);
    return (String)queryObject.setMaxResults(1).uniqueResult();

  }

}