package ch.ess.hpadmin.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.hpadmin.entity.HomepageField;
import ch.ess.hpadmin.entity.HomepagePage;
import ch.ess.hpadmin.entity.HomepageTemplate;
import ch.ess.hpadmin.model.Page;

@Component
public class PageService {

  @Autowired
  private SessionFactory sessionFactory;

  @Transactional
  public void deletePage(int id) {
    HomepagePage hpPage = (HomepagePage)sessionFactory.getCurrentSession().get(HomepagePage.class, id);
    sessionFactory.getCurrentSession().delete(hpPage);
  }
  
  @Transactional
  public void save(List<Page> pages) {
    for (Page page : pages) {
      
      HomepagePage hpPage;
      
      if (page.getId() == -1) {
        hpPage = new HomepagePage();
        
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(HomepageField.class);
        crit.add(Restrictions.eq("name", page.getName()));
        if (crit.uniqueResult() == null) {
          HomepageField field = new HomepageField();
          field.setName(page.getName());
          sessionFactory.getCurrentSession().save(field);
        }
        
      } else {
        hpPage = (HomepagePage)sessionFactory.getCurrentSession().get(HomepagePage.class, page.getId());
        
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(HomepageField.class);
        crit.add(Restrictions.eq("name", page.getName()));
        if (crit.uniqueResult() == null) {
          HomepageField field = new HomepageField();
          field.setName(page.getName());
          sessionFactory.getCurrentSession().save(field);
        }
      }

      
      //parent
      Criteria crit = sessionFactory.getCurrentSession().createCriteria(HomepagePage.class);
      crit.add(Restrictions.eq("name", page.getParentName()));
      HomepagePage parent = (HomepagePage)crit.uniqueResult();      
      hpPage.setHomepagePage(parent);
      
      if (parent != null) {
        hpPage.setHomepagePage(parent);
        parent.getHomepagePages().add(hpPage);
      } else {
        if (hpPage.getHomepagePage() != null) {
           hpPage.getHomepagePage().getHomepagePages().remove(hpPage);
        }
        hpPage.setHomepagePage(null);
      }
      
      hpPage.setName(page.getName());
      
      if (!StringUtils.isBlank(page.getTitle())) {
        hpPage.setTitle(page.getTitle());
      } else {
        hpPage.setTitle(null);
      }
            
      if (page.getVisibility() != null && page.getVisibility().booleanValue()) {
        hpPage.setVisibility((short)2);
      } else {
        hpPage.setVisibility((short)0);
      }
      
      hpPage.setPriority(page.getPriority().shortValue());      
      
      boolean hasTemplate = false;
      if (StringUtils.isBlank(page.getTemplateRight())) {
        page.setTemplateRight(null);        
      } else {
        hasTemplate = true;
      }
      
      if (StringUtils.isBlank(page.getTemplateMain())) {
        page.setTemplateMain(null);
      } else {
        hasTemplate = true;
      }
            
      if (hpPage.getHomepageTemplate() != null) {
        if (hasTemplate) {
          hpPage.getHomepageTemplate().setTplMain(page.getTemplateMain());
          hpPage.getHomepageTemplate().setTplRight(page.getTemplateRight());
        } else {
          HomepageTemplate template = hpPage.getHomepageTemplate();          
          hpPage.setHomepageTemplate(null);
          
          template.getHomepagePages().remove(hpPage);
          
          sessionFactory.getCurrentSession().delete(template);
          
        }        
      } else {
        if (hasTemplate) {
          HomepageTemplate htempl = new HomepageTemplate();
          htempl.setTplMain(page.getTemplateMain());
          htempl.setTplRight(page.getTemplateRight());
          htempl.getHomepagePages().add(hpPage);
          
          sessionFactory.getCurrentSession().save(htempl);
          
          hpPage.setHomepageTemplate(htempl);
        }
      }
      
      
      if (page.getId() == -1) {
        sessionFactory.getCurrentSession().save(hpPage);
      }
      

    }
  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Page> getAll() {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HomepagePage.class);
    List<HomepagePage> pagesList = criteria.list();
    
    List<Page> pageList = new ArrayList<Page>();
    for (HomepagePage homepagePage : pagesList) {
      
      Page page = new Page();
      
      page.setId(homepagePage.getId());
      page.setName(homepagePage.getName());
      page.setTitle(homepagePage.getTitle());
      
      if (homepagePage.getPriority() != null) {
        page.setPriority(homepagePage.getPriority().intValue());
      } else {
        page.setPriority(null);
      }
      
      if (homepagePage.getVisibility() != null && homepagePage.getVisibility() == 2) {
        page.setVisibility(true);
      } else {
        page.setVisibility(false);
      }
      
      if (homepagePage.getHomepagePage() != null) {
        page.setParent(homepagePage.getHomepagePage().getId());
        page.setParentName(homepagePage.getHomepagePage().getName());
      } else {
        page.setParent(null);
        page.setParentName(null);
      }
      
      if (homepagePage.getHomepageTemplate() != null) {
        page.setTemplateMain(homepagePage.getHomepageTemplate().getTplMain());
        page.setTemplateRight(homepagePage.getHomepageTemplate().getTplRight());
      } else {
        page.setTemplateMain(null);
        page.setTemplateRight(null);
      }
      
      
      pageList.add(page);
      
    }
        
    return pageList;
           
  }
}
