package sr.mvs.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Wordlist implements Serializable {

    /** identifier field */
    private int hash;

    /** persistent field */
    private String word;

    /** full constructor */
    public Wordlist(int hash, java.lang.String word) {
        this.hash = hash;
        this.word = word;
    }

    /** default constructor */
    public Wordlist() {
      //no action
    }

    public int getHash() {
        return this.hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }
    public java.lang.String getWord() {
        return this.word;
    }

    public void setWord(java.lang.String word) {
        this.word = word;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Wordlist) ) return false;
        Wordlist castOther = (Wordlist) other;
        return new EqualsBuilder()
            .append(this.hash, castOther.hash)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(hash)
            .toHashCode();
    }

}
