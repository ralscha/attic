package lotto.html;

import com.objectspace.jgl.*;

public class CompareFlopEntryPaar implements BinaryPredicate {
    public boolean execute( Object first, Object second ) {
        return (((EntryPaar)first).compare2((EntryPaar)second));
    }
}
