package ch.ess.hpadmin.service;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ch.ess.hpadmin.entity.HomepageField;
import ch.ess.hpadmin.entity.HomepageFieldTextres;
import ch.ess.hpadmin.entity.HomepageFieldTextresId;
import ch.ess.hpadmin.entity.HomepageLanguage;
import ch.ess.hpadmin.entity.HomepageTextResource;
import ch.ess.hpadmin.model.TextResource;

@Component
public class TextResourceService {

  @Autowired
  private SessionFactory sessionFactory;

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<TextResource> getAll() {
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HomepageField.class);
    List<HomepageField> fields = criteria.list();
    
    List<TextResource> result = new ArrayList<TextResource>();
    
    for (HomepageField homepageField : fields) {
      TextResource tr = new TextResource();
      tr.setFieldName(homepageField.getName());
      tr.setFieldId(homepageField.getId());
      
      for (HomepageFieldTextres textres : homepageField.getHomepageFieldTextreses()) {
        if ("de".equals(textres.getHomepageLanguage().getIso())) {
          tr.setTextDe(textres.getHomepageTextResource().getText());
          tr.setTextDe(tr.getTextDe().replace("\r", ""));
          tr.setTextDeId(textres.getHomepageTextResource().getId());
        } else if ("en".equals(textres.getHomepageLanguage().getIso())) {
          tr.setTextEn(textres.getHomepageTextResource().getText());
          tr.setTextEn(tr.getTextEn().replace("\r", ""));
          tr.setTextEnId(textres.getHomepageTextResource().getId());
        }
      }
      
      result.add(tr);
    }
    
    return result;
  }
  
  
  @Transactional
  public void deleteTextResource(TextResource tr) {
    
    HomepageField hf = (HomepageField)sessionFactory.getCurrentSession().get(HomepageField.class, tr.getFieldId());
    
    for (HomepageFieldTextres textres : hf.getHomepageFieldTextreses()) {      
      sessionFactory.getCurrentSession().delete(textres.getHomepageTextResource());      
      sessionFactory.getCurrentSession().delete(textres);
    }
    
    sessionFactory.getCurrentSession().delete(hf);
  
  }
  
  @Transactional
  public void update(TextResource tr) {
    
    
    
    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HomepageLanguage.class);
    criteria.add(Restrictions.eq("iso", "de"));
    HomepageLanguage de = (HomepageLanguage)criteria.uniqueResult();
    
    criteria = sessionFactory.getCurrentSession().createCriteria(HomepageLanguage.class);
    criteria.add(Restrictions.eq("iso", "en"));
    HomepageLanguage en = (HomepageLanguage)criteria.uniqueResult();
    
    
    
    if (tr.getFieldId() != null && tr.getFieldId() > 0) {
          
      HomepageField hf = (HomepageField)sessionFactory.getCurrentSession().get(HomepageField.class, tr.getFieldId());
    
      if (tr.getTextDeId() == null || tr.getTextDeId() == 0) {
        HomepageTextResource deTr = new HomepageTextResource();
        deTr.setText(tr.getTextDe());
        sessionFactory.getCurrentSession().save(deTr);
        
        HomepageFieldTextresId id = new HomepageFieldTextresId();
        id.setFieldId(hf.getId());
        id.setLangId(de.getId());
        
        
        HomepageFieldTextres tDe = new HomepageFieldTextres();
        tDe.setId(id);
        tDe.setHomepageField(hf);
        tDe.setHomepageLanguage(de);
        tDe.setHomepageTextResource(deTr);
        sessionFactory.getCurrentSession().save(tDe);
      } else {
        HomepageTextResource trDe = (HomepageTextResource)sessionFactory.getCurrentSession().get(HomepageTextResource.class, tr.getTextDeId());
        trDe.setText(tr.getTextDe());        
      }
      
      
      if (tr.getTextEnId() == null || tr.getTextEnId() == 0) {
        HomepageTextResource enTr = new HomepageTextResource();
        enTr.setText(tr.getTextEn());
        sessionFactory.getCurrentSession().save(enTr);
        
        HomepageFieldTextresId id = new HomepageFieldTextresId();
        id.setFieldId(hf.getId());
        id.setLangId(en.getId());
        
        HomepageFieldTextres tEn = new HomepageFieldTextres();
        tEn.setId(id);
        tEn.setHomepageField(hf);
        tEn.setHomepageLanguage(en);
        tEn.setHomepageTextResource(enTr);
        sessionFactory.getCurrentSession().save(tEn);
      } else {
        HomepageTextResource trEn = (HomepageTextResource)sessionFactory.getCurrentSession().get(HomepageTextResource.class, tr.getTextEnId());
        trEn.setText(tr.getTextEn());        
      } 
      
      
    } else {
      
      

      
      HomepageField hf = new HomepageField();
      hf.setName(tr.getFieldName());
      sessionFactory.getCurrentSession().save(hf);
      
      
      HomepageTextResource deTr = new HomepageTextResource();
      deTr.setText(tr.getTextDe());
      sessionFactory.getCurrentSession().save(deTr);
      
      
      HomepageFieldTextresId id = new HomepageFieldTextresId();
      id.setFieldId(hf.getId());
      id.setLangId(de.getId());
      
      HomepageFieldTextres tDe = new HomepageFieldTextres();
      tDe.setId(id);
      tDe.setHomepageField(hf);
      tDe.setHomepageLanguage(de);
      tDe.setHomepageTextResource(deTr);
      sessionFactory.getCurrentSession().save(tDe);
      
      
      HomepageTextResource enTr = new HomepageTextResource();
      enTr.setText(tr.getTextEn());
      sessionFactory.getCurrentSession().save(enTr);
      
      id = new HomepageFieldTextresId();
      id.setFieldId(hf.getId());
      id.setLangId(en.getId());
      
      HomepageFieldTextres tEn = new HomepageFieldTextres();
      tEn.setId(id);
      tEn.setHomepageField(hf);
      tEn.setHomepageLanguage(en);
      tEn.setHomepageTextResource(enTr);
      sessionFactory.getCurrentSession().save(tEn);
      
      
    }
    

  }
  
  
}
