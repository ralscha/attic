package dbdemo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class User implements Serializable {

    /** identifier field */
    private String userID;

    /** nullable persistent field */
    private String userName;

    /** nullable persistent field */
    private String password;

    /** nullable persistent field */
    private String emailAddress;

    /** nullable persistent field */
    private java.util.Date lastLogon;

    /** full constructor */
    public User(java.lang.String userID, java.lang.String userName, java.lang.String password, java.lang.String emailAddress, java.util.Date lastLogon) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
        this.lastLogon = lastLogon;
    }

    /** default constructor */
    public User() {
    }

    /** minimal constructor */
    public User(java.lang.String userID) {
        this.userID = userID;
    }

    public java.lang.String getUserID() {
        return this.userID;
    }

    public void setUserID(java.lang.String userID) {
        this.userID = userID;
    }

    public java.lang.String getUserName() {
        return this.userName;
    }

    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    public java.lang.String getPassword() {
        return this.password;
    }

    public void setPassword(java.lang.String password) {
        this.password = password;
    }

    public java.lang.String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(java.lang.String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public java.util.Date getLastLogon() {
        return this.lastLogon;
    }

    public void setLastLogon(java.util.Date lastLogon) {
        this.lastLogon = lastLogon;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("userID", getUserID())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof User) ) return false;
        User castOther = (User) other;
        return new EqualsBuilder()
            .append(this.getUserID(), castOther.getUserID())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getUserID())
            .toHashCode();
    }

}
