package ch.ess.ldaptest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LdapSearch {

  // set up one connection for the Global Catalog port; 3268
  // and another for the normal LDAP port; 389    
  public static final String ldapUrlDC = "ldap://192.168.20.202:389";
  public static final String ldapUrlGC = "ldap://192.168.20.202:3268";

  public static final String objectClassUser = "user";
  public static final String objectClassGroup = "group";

  private String ldapUrl = ldapUrlDC;
  
  private String rootContext = "DC=ANET,DC=ESS,DC=CH";

  private String ldapUserName = "user"; //"ldapUser"; // alternative syntax: "CN=mr" + userContext

  private String ldapPassword = "password"; //"rammstein";
  
  private String groupMemberAttribute = "memberOf";
  private String accountNameAttribute = "sAMAccountName";
  private String firstnameAttribute = "givenName";
  private String lastnameAttribute = "sn";
  private String emailAttribute = "mail";

  private LdapContext ctx = null;

  public LdapSearch(String ldapUrl, String rootContext, String ldapUserName, String ldapPassword) {
    this.ldapUrl = ldapUrl;
    this.rootContext = rootContext;
    this.ldapUserName = ldapUserName;
    this.ldapPassword = ldapPassword;
  }

  protected LdapSearch() {
    //no action, use setters
  }

  /**
   * @param args
   * @throws NamingException 
   */
  public static void main(String[] args) throws NamingException {
    LdapSearch ad = new LdapSearch();

    String user = "mr";

    LdapUserInfo ui = ad.getUserInfo(user);
    
    System.out.println("User: " + user + ", First: " + ui.firstname + ", Last: " + ui.lastname + ", Email: " + ui.email);
    System.out.println("belongs to the following " + ui.groups.size() + " Groups:");
    Iterator i = ui.groups.iterator();
    while (i.hasNext()) {
      System.out.println("  - " + i.next());
    }
    
    System.out.println("User [mr] attributes 'ZIP' & 'City' = " + ad.getUserAttribute(user, "postalCode") + " " + ad.getUserAttribute(user, "l"));
    System.out.println("Group [ConTrackerAdmin] attribute 'Description' = " + ad.getGroupAttribute("ConTrackerAdmin", "description"));
  }

  public LdapUserInfo getUserInfo(String accountName) throws NamingException {
    initContext();
    List<String> returnedAttsList = new ArrayList<String>();
    returnedAttsList.add(accountNameAttribute);
    returnedAttsList.add(groupMemberAttribute);
    returnedAttsList.add(lastnameAttribute);
    returnedAttsList.add(emailAttribute);
    NamingEnumeration user = searchObject(objectClassUser, accountName, returnedAttsList);
    LdapUserInfo result = userInfo(user);
    closeContext();
    return result;
  }

  public Map getGroups(String accountName) throws NamingException {
    initContext();
    List<String> returnedAttsList = new ArrayList<String>();
    returnedAttsList.add(accountNameAttribute);
    returnedAttsList.add(groupMemberAttribute);
    NamingEnumeration user = searchObject(objectClassUser, accountName, returnedAttsList);
    Map result = groups(user);
    closeContext();
    return result;
  }
  
  public boolean isUserInGroup(String user, String group) throws NamingException {
    return getGroups(user).containsKey(group);
  }

  public void setObjectAttribute(String objectClassName, String objectName, String attributeName, String attributeValue) throws NamingException {
    ModificationItem[] mods = new ModificationItem[3];
    if (getObjectAttribute(objectClassName, objectName, attributeName) != null) {
      // attribute exists
      mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("mail", "geisel@wizards.com"));

    }
    else {
      // attribute doesnot exist, create it
      mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
          new BasicAttribute("telephonenumber", "+1 555 555 5555"));      
    }
  }
  
  public String getObjectAttribute(String objectClassName, String objectName, String attributeName) throws NamingException {
    String attributeValue = null;
    initContext();
    List<String> returnedAttsList = new ArrayList<String>();
    returnedAttsList.add(attributeName);
    NamingEnumeration user = searchObject(objectClassName, objectName, returnedAttsList);
    if (user != null) {
      SearchResult sr = (SearchResult)user.next();
      Attributes attrs = sr.getAttributes();
      if (attrs != null) {
        // Retrieve the attribute
        Attribute attr = attrs.get(attributeName);
        if (attr != null) attributeValue = (String)attr.get();
      }
    }
    closeContext();
    return attributeValue; 
  }

  public String getUserAttribute(String userName, String attributeName) throws NamingException {
    return getObjectAttribute(objectClassUser, userName, attributeName); 
  }

  public String getGroupAttribute(String groupName, String attributeName) throws NamingException {
    return getObjectAttribute(objectClassGroup, groupName, attributeName); 
  }

  private NamingEnumeration searchObject(String objectClassName, String accountName, List<String> returnedAttributes) throws NamingException {
    NamingEnumeration answer = null; 
    
    if (ctx != null) {

      // Create the search controls    
      SearchControls searchCtls = new SearchControls();

      // Specify the search scope
      searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

      // specify the LDAP search filter
      String searchFilter = "(&(objectClass=" + objectClassName + ")(" + accountNameAttribute + "=" + accountName + "))";

      // Specify the Base for the search
      // an empty dn for all objects from all domains in the forest
      String searchBase = rootContext;

      // Specify the attributes to return
      String[] returnedAtts = (String[])returnedAttributes.toArray(new String[returnedAttributes.size()]);
      searchCtls.setReturningAttributes(returnedAtts);

      // Search for objects in the GC using the filter
      answer = ctx.search(searchBase, searchFilter, searchCtls);
    }
    return answer;
  }

  private Map groups(NamingEnumeration user) throws NamingException {
    HashMap result = new HashMap();

    if (user != null) {

      // Loop through the search results (users)
      while (user.hasMoreElements()) {

        SearchResult sr = (SearchResult)user.next();

        // Get the attributes
        Attributes attrs = sr.getAttributes();
        if (attrs != null) {
          // Retrieve the 'memberOf' attribute
          Attribute memberOf = attrs.get(groupMemberAttribute);
          if (memberOf != null) {
            NamingEnumeration groups = memberOf.getAll();
            NameParser nameParser = ctx.getNameParser(sr.getName());

            // Iterate over the values
            while (groups.hasMoreElements()) {
              String value = (String)groups.nextElement();
              Name name = nameParser.parse(value);
              String key = name.get(name.size() - 1).substring(3);
              result.put(key, value);
            }
          }
        }
      }
    }
    return result;
  }

  private LdapUserInfo userInfo(NamingEnumeration user) throws NamingException {
    LdapUserInfo result = new LdapUserInfo();

    if (user != null) {

      // Loop through the search results (users)
      while (user.hasMoreElements()) {

        SearchResult sr = (SearchResult)user.next();

        // Get the attributes
        Attributes attrs = sr.getAttributes();
        if (attrs != null) {
          
          // Retrieve the 'givenName' attribute
          Attribute attr = attrs.get(firstnameAttribute);
          if (attr != null) result.firstname = (String)attr.get();

          // Retrieve the 'sn' attribute
          attr = attrs.get(lastnameAttribute);
          if (attr != null) result.lastname = (String)attr.get();

          // Retrieve the 'mail' attribute
          attr = attrs.get(emailAttribute);
          if (attr != null) result.email = (String)attr.get();
          
          // Retrieve the 'memberOf' attribute
          Attribute memberOf = attrs.get(groupMemberAttribute);
          if (memberOf != null) {
            NamingEnumeration groups = memberOf.getAll();
            NameParser nameParser = ctx.getNameParser(sr.getName());

            // Iterate over the values
            while (groups.hasMoreElements()) {
              String value = (String)groups.nextElement();
              Name name = nameParser.parse(value);
              String key = name.get(name.size() - 1).substring(3);
              result.groups.add(key); 
            }
          }
        }
      }
    }
    return result;
  }

  private void closeContext() throws NamingException {

    ctx.close();

  }

  private void initContext() throws NamingException {

    Hashtable env = new Hashtable();

    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

    // set security credentials, note using simple cleartext authentication
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, ldapUserName);
    env.put(Context.SECURITY_CREDENTIALS, ldapPassword);

    // connect to GC or DC
    env.put(Context.PROVIDER_URL, ldapUrl);

    // We need to chase referrals when retrieving attributes from the DC
    // as the object may be in a different domain
    env.put(Context.REFERRAL, "follow");

    // Create the initial directory context for DC or GC
    ctx = new InitialLdapContext(env, null);

  }

  /**
   * @return the ldapPassword
   */
  public String getLdapPassword() {
    return ldapPassword;
  }

  /**
   * @param ldapPassword the ldapPassword to set
   */
  public void setLdapPassword(String ldapPassword) {
    this.ldapPassword = ldapPassword;
  }

  /**
   * @return the ldapUrl
   */
  public String getLdapUrl() {
    return ldapUrl;
  }

  /**
   * @param ldapUrl the ldapUrl to set
   */
  public void setLdapUrl(String ldapUrl) {
    this.ldapUrl = ldapUrl;
  }

  /**
   * @return the ldapUserName
   */
  public String getLdapUserName() {
    return ldapUserName;
  }

  /**
   * @param ldapUserName the ldapUserName to set
   */
  public void setLdapUserName(String ldapUserName) {
    this.ldapUserName = ldapUserName;
  }

  /**
   * @return the rootContext
   */
  public String getRootContext() {
    return rootContext;
  }

  /**
   * @param rootContext the rootContext to set
   */
  public void setRootContext(String rootContext) {
    this.rootContext = rootContext;
  }
}
