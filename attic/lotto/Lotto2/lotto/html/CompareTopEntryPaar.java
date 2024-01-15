package lotto.html;

import com.objectspace.jgl.*;

public class CompareTopEntryPaar implements BinaryPredicate {
    public boolean execute(Object first, Object second) {
        return (((EntryPaar)first).compare1((EntryPaar)second));
    }
}
