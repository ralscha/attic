



package mvs.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;

/**
 * Class Word3.
 */
public class Word3 {

    /* attributes */
    private long word3;
    private long hits;
    private OReference word12;
    

    /** Creates a new Word3 */    
    public Word3() {
    }
    
    
    /** Accessor for attribute word3 */
    public long getWord3() {
        return word3;
    }
    
    /** Accessor for attribute hits */
    public long getHits() {
        return hits;
    }
    
    
    /** Mutator for attribute word3 */
    public void setWord3(long newWord3) {
        word3 = newWord3;
    }
    
    /** Mutator for attribute hits */
    public void setHits(long newHits) {
        hits = newHits;
    }
    
    
    /** Accessor for reference word12 */
    public Word12 getWord12() throws BODBException {
        return (Word12)word12.get();
    }
    
    
    /** Mutator for reference word12 */
    public void setWord12(Word12 newword12)
      throws BODBException, BOReferenceNotUpdatedException {
        word12.set(newword12);
    }
    
    
    
    
    
    
    
    
}



