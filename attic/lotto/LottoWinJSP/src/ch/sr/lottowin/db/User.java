

/*
 * Source generated by VBSF using template2.tmpl.
 */

package ch.sr.lottowin.db;

import com.objectmatter.bsf.*;

/**
 * Class User.
 */
public class User {

    /* attributes */
    private String userid;
    private String name;
    private String firstname;
    private String pass;
    private String language;
    private String email;
    private OCollection jokers;
    private OCollection tips;

    /** Creates a new User */    
    public User() {
        super();
    }
    /** Creates a new User with the supplied values */ 
    public User( String newuserid,
                 String newname,
                 String newfirstname,
                 String newpass,
                 String newlanguage,
                 String newemail) {
          userid = newuserid;
          name = newname;
          firstname = newfirstname;
          pass = newpass;
          language = newlanguage;
          email = newemail;
    }
    
    
    /** Accessor for attribute userid */
    public String getUserid() {
        return userid;
    }
    
    /** Accessor for attribute name */
    public String getName() {
        return name;
    }
    
    /** Accessor for attribute firstname */
    public String getFirstname() {
        return firstname;
    }
    
    /** Accessor for attribute pass */
    public String getPass() {
        return pass;
    }
    
    /** Accessor for attribute language */
    public String getLanguage() {
        return language;
    }
    
    /** Accessor for attribute email */
    public String getEmail() {
        return email;
    }
    
    /** Mutator for attribute userid */
    public void setUserid(String newUserid) {
        userid = newUserid;
    }
    
    /** Mutator for attribute name */
    public void setName(String newName) {
        name = newName;
    }
    
    /** Mutator for attribute firstname */
    public void setFirstname(String newFirstname) {
        firstname = newFirstname;
    }
    
    /** Mutator for attribute pass */
    public void setPass(String newPass) {
        pass = newPass;
    }
    
    /** Mutator for attribute language */
    public void setLanguage(String newLanguage) {
        language = newLanguage;
    }
    
    /** Mutator for attribute email */
    public void setEmail(String newEmail) {
        email = newEmail;
    }
    
    
    
    
    /** Returns all elements in the Jokers collection */
    public Joker[] getJokers() throws BODBException {
        return (Joker[])jokers.get();
    }
    
    /** Returns all elements in the Tips collection */
    public Tip[] getTips() throws BODBException {
        return (Tip[])tips.get();
    }
    
    /** Adds the supplied element to the Jokers collection */
    public void addJokers(Joker newJokers)
      throws BODBException, BOReferenceNotUpdatedException, BOUpdateConflictException {
        jokers.add(newJokers);
    }
    
    /** Adds the supplied element to the Tips collection */
    public void addTips(Tip newTips)
      throws BODBException, BOReferenceNotUpdatedException, BOUpdateConflictException {
        tips.add(newTips);
    }
    
    /** Removes the supplied element from the Jokers collection */
    public void removeJokers(Joker oldJokers)
      throws BODBException, BOReferenceNotUpdatedException, BOUpdateConflictException {
        jokers.remove(oldJokers);
    }
    
    /** Removes the supplied element from the Tips collection */
    public void removeTips(Tip oldTips)
      throws BODBException, BOReferenceNotUpdatedException, BOUpdateConflictException {
        tips.remove(oldTips);
    }
}


