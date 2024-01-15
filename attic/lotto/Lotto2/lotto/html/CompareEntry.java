package lotto.html;

import com.objectspace.jgl.*;

public class CompareEntry implements BinaryPredicate {
    public boolean execute( Object first, Object second ) {
        return (((Entry)first).compare((Entry)second));
    }
}
