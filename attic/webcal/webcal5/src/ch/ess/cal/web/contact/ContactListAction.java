package ch.ess.cal.web.contact;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import ch.ess.base.Util;
import ch.ess.base.dao.TextResourceDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.dao.ContactDao;
import ch.ess.cal.model.Contact;
import ch.ess.cal.model.ContactAttribute;
import ch.ess.cal.service.search.SearchEngine;
import ch.ess.cal.web.time.TimeListForm;
import ch.ess.cal.web.time.UserHelper;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ContactListAction extends AbstractListAction {

  private ContactDao contactDao;
  private SearchEngine searchEngine;
  private TextResourceDao textResourceDao;
  private UserDao userDao;

  public void setContactDao(final ContactDao contactDao) {
    this.contactDao = contactDao;
  }

  public void setSearchEngine(final SearchEngine searchEngine) {
    this.searchEngine = searchEngine;
  }

  public void setTextResourceDao(final TextResourceDao textResourceDao) {
    this.textResourceDao = textResourceDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }	
  
  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    SimpleListDataModel dataModel = new SimpleListDataModel();

    MapForm searchForm = (MapForm)ctx.form();
    String searchText = searchForm.getStringValue("searchText");
    String category = searchForm.getStringValue("category");
    String search = searchForm.getStringValue("search");
    String showMode = searchForm.getStringValue("showMode");

    User user = Util.getUser(ctx.session(), userDao);
    
    List<Contact> contacts = new ArrayList<Contact>();
    if (StringUtils.isNotBlank(search)) {
      if ("1".equals(search)) {
        
        if ("privateOnly".equals(showMode)) {
          contacts = contactDao.findWithCategory(category, user);
        } else if ("publicOnly".equals(showMode)) {
          contacts = contactDao.findWithCategory(category, null);
        } else {
          List<Contact> contacts1 = contactDao.findWithCategory(category, user);
          List<Contact> contacts2 = contactDao.findWithCategory(category, null);
          contacts.addAll(contacts1);
          contacts.addAll(contacts2);          
        }         
        
        searchForm.getValueMap().remove("searchText");
        searchForm.getValueMap().remove("all");
      } else if ("2".equals(search)) {                
        if ("privateOnly".equals(showMode)) {
          contacts = contactDao.findAll(user);
        } else if ("publicOnly".equals(showMode)) {
          contacts = contactDao.findAll(null);
        } else {
          List<Contact> contacts1 = contactDao.findAll(user);
          List<Contact> contacts2 = contactDao.findAll(null);            
          contacts.addAll(contacts1);
          contacts.addAll(contacts2); 
        }        
        
        searchForm.getValueMap().remove("searchText");
        searchForm.getValueMap().remove("category");
      }

    } else if (StringUtils.isNotBlank(searchText)) {

      Set<String> contactIdList = searchEngine.search(searchText, showMode, user);
      if (contactIdList != null) {
        for (String id : contactIdList) {
          Contact contact = contactDao.getById(id);
          if (contact != null) {
            contacts.add(contact);
          }
        }
      }

      searchForm.getValueMap().remove("all");
      searchForm.getValueMap().remove("category");
    } else {
      if ("privateOnly".equals(showMode)) {
        contacts = contactDao.findAll(user);
      } else if ("publicOnly".equals(showMode)) {
        contacts = contactDao.findAll(null);
      } else {
        List<Contact> contacts1 = contactDao.findAll(user);
        List<Contact> contacts2 = contactDao.findAll(null);            
        contacts.addAll(contacts1);
        contacts.addAll(contacts2);                 
      } 
      searchForm.getValueMap().remove("category");
      searchForm.setValue("all", "1");
    }

    searchForm.getValueMap().remove("search");

    Configuration configuration = new Configuration();

    for (Contact contact : contacts) {

      DynaBean dynaBean = new LazyDynaBean("ContactList");

      dynaBean.set("id", contact.getId().toString());

      if (!contact.getContactAttributes().isEmpty()) {

        ContactAttribute caFirstName = contact.getContactAttributes().get("firstName");
        ContactAttribute caLastName = contact.getContactAttributes().get("lastName");
        ContactAttribute caCompanyName = contact.getContactAttributes().get("companyName");
        ContactAttribute caEmail = contact.getContactAttributes().get("email");

        if (caFirstName != null) {
          dynaBean.set("firstName", caFirstName.getValue());
        }

        if (caLastName != null) {
          dynaBean.set("lastName", caLastName.getValue());
        }

        if (caCompanyName != null) {
          dynaBean.set("companyName", caCompanyName.getValue());
        }

        if (caEmail != null) {
          dynaBean.set("email", caEmail.getValue());
        }

        String info = getInfo(contact, getLocale(ctx.request()), configuration);
        dynaBean.set("info", info);

        ContactAttribute caPhone = contact.getContactAttributes().get("businessNumber");
        if (caPhone == null) {
          caPhone = contact.getContactAttributes().get("homeNumber");
        }
        if (caPhone == null) {
          caPhone = contact.getContactAttributes().get("homeNumber2");
        }
        if (caPhone == null) {
          caPhone = contact.getContactAttributes().get("mobileNumber");
        }
        if (caPhone == null) {
          caPhone = contact.getContactAttributes().get("officeNumber2");
        }
        if (caPhone == null) {
          caPhone = contact.getContactAttributes().get("companyMainPhoneNumber");
        }

        if (caPhone != null) {
          dynaBean.set("phone", caPhone.getValue());
        }
      }

      dataModel.add(dynaBean);

    }

    return dataModel;
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    searchEngine.deleteContactDocumentSync(key);
    contactDao.delete(key);
  }

  private Map<String, String> createTemplateContext(Contact contact) {
    Map<String, String> ctxMap = new HashMap<String, String>();

    Map<String, ContactAttribute> contactAttributes = contact.getContactAttributes();

    for (ContactAttribute contactAttribute : contactAttributes.values()) {
      ctxMap.put(contactAttribute.getField(), contactAttribute.getValue());
    }

    return ctxMap;
  }

  private String getInfo(Contact contact, Locale locale, Configuration configuration) throws IOException,
      TemplateException {

    Map<String, String> templateContext = createTemplateContext(contact);
    String infoTemplateText = textResourceDao.getText("contact.info", locale.toString());
    Template infoTemplate = new Template("info", new StringReader(infoTemplateText), configuration);
    String info = FreeMarkerTemplateUtils.processTemplateIntoString(infoTemplate, templateContext);

    StringBuffer sb = new StringBuffer(info.length());
    for (int i = 0; i < info.length(); i++) {
      char c = info.charAt(i);
      if ((c != '\n') && (c != '\r') && (c != '\t')) {
        sb.append(c);
      }

    }

    return StringEscapeUtils.escapeJavaScript(sb.toString());

  }
}
