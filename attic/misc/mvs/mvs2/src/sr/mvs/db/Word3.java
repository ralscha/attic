package sr.mvs.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Word3 implements Serializable {

    /** identifier field */
    private long id;

    /** persistent field */
    private int word1;

    /** persistent field */
    private int word2;

    /** persistent field */
    private int word3;

    /** persistent field */
    private int hits;

    /** full constructor */
    public Word3(int word1, int word2, int word3, int hits) {
        this.word1 = word1;
        this.word2 = word2;
        this.word3 = word3;
        this.hits = hits;
    }

    /** default constructor */
    public Word3() {
      //no action
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public int getWord1() {
        return this.word1;
    }

    public void setWord1(int word1) {
        this.word1 = word1;
    }
    public int getWord2() {
        return this.word2;
    }

    public void setWord2(int word2) {
        this.word2 = word2;
    }
    public int getWord3() {
        return this.word3;
    }

    public void setWord3(int word3) {
        this.word3 = word3;
    }
    public int getHits() {
        return this.hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Word3) ) return false;
        Word3 castOther = (Word3) other;
        return new EqualsBuilder()
            .append(this.id, castOther.id)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .toHashCode();
    }

}
