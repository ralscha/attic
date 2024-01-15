



package mvs.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;

/**
 * Class Word12.
 */
public class Word12 {

    /* attributes */
    private long word1;
    private long word2;
    private long total;
    private OCollection words3;
    

    /** Creates a new Word12 */    
    public Word12() {
    }
    
    
    /** Accessor for attribute word1 */
    public long getWord1() {
        return word1;
    }
    
    /** Accessor for attribute word2 */
    public long getWord2() {
        return word2;
    }
    
    /** Accessor for attribute total */
    public long getTotal() {
        return total;
    }
    
    
    /** Mutator for attribute word1 */
    public void setWord1(long newWord1) {
        word1 = newWord1;
    }
    
    /** Mutator for attribute word2 */
    public void setWord2(long newWord2) {
        word2 = newWord2;
    }
    
    /** Mutator for attribute total */
    public void setTotal(long newTotal) {
        total = newTotal;
    }
    
    
    
    
    
    /** Returns all elements in the Words3 collection */
    public Word3[] getWords3() throws BODBException {
        return (Word3[])words3.get();
    }
    
    
    /** Adds the supplied element to the Words3 collection */
    public void addWords3(Word3 newWords3) throws BOUpdateConflictException{
        words3.addOwned(newWords3);
    }
    
    
    
    
    
}



