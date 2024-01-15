package ch.ess.cal.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import ch.ess.base.dao.AbstractDao;
import ch.ess.base.model.User;
import ch.ess.cal.model.Contact;
import ch.ess.cal.service.search.ContactDocument;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@SpringBean(id = "contactDao", autowire = Autowire.BYTYPE)
public class ContactDao extends AbstractDao<Contact> {

  public ContactDao() {
    super(Contact.class);
  }

//  @Transactional(readOnly = true)
//  @SuppressWarnings("unchecked")
//  public List<Object[]> getCategoryCount() {
//    Query query = getSession().createQuery(
//        "select contact.category, count(contact) from Contact contact group by contact.category");
//    return query.list();
//  }
  
  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true)
  public List<Contact> findAll(User user) {
    if (user == null) {
      Query query = getSession().createQuery(
      "from Contact contact where size(contact.users) = 0");
      return query.list();  
    }

    Query query = getSession().createQuery(
        "select contact from Contact contact inner join contact.users as user where user = :user");
    query.setEntity("user", user);
    return query.list();    
  }
  
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Object[]> getCategoryCount(final User user) {
    Query query;
    if (user != null) {
      query = getSession().createQuery(
          "select contact.category, count(contact) from Contact contact inner join contact.users as user where user = :user group by contact.category");
      query.setEntity("user", user);
    } else {
      query = getSession().createQuery(
      "select contact.category, count(contact) from Contact contact where size(contact.users) = 0 group by contact.category");    
    }
    return query.list();
  }

  @Transactional(readOnly = true)
  public List<Document> listAllContact() {

    List<Contact> contacts = findAll();
    List<Document> documentList = new ArrayList<Document>();

    for (Contact contact : contacts) {
      ContactDocument contactDocument = new ContactDocument(contact);
      documentList.add(contactDocument.getDocument());
    }
    return documentList;
  }

//  @Transactional(readOnly = true)
//  @SuppressWarnings("unchecked")
//  public List<Contact> findWithCategory(final String category) {
//
//    Criteria criteria = getSession().createCriteria(Contact.class);
//
//    if (StringUtils.isNotBlank(category)) {
//      String str = category.trim().toLowerCase();
//      criteria.add(Restrictions.eq("category", str));
//    }
//
//    return criteria.list();
//  }
  
  @Transactional(readOnly = true)
  @SuppressWarnings("unchecked")
  public List<Contact> findWithCategory(final String category, final User user) {
    if (user == null) {
      Query query = getSession().createQuery(
      "from Contact contact where category = :category and size(contact.users) = 0");
      query.setString("category", category);
      return query.list();  
    }

    Query query = getSession().createQuery(
        "select contact from Contact contact inner join contact.users as user where user = :user and category = :category");
    query.setEntity("user", user);
    query.setString("category", category);
    return query.list();          
  }

}