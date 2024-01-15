package sr.mvs.db;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Beginlist implements Serializable {

    /** identifier field */
    private long id;

    /** persistent field */
    private int word1;

    /** persistent field */
    private int word2;

    /** persistent field */
    private int total;

    /** full constructor */
    public Beginlist(int word1, int word2, int total) {
        this.word1 = word1;
        this.word2 = word2;
        this.total = total;
    }

    /** default constructor */
    public Beginlist() {
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
    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Beginlist) ) return false;
        Beginlist castOther = (Beginlist) other;
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
