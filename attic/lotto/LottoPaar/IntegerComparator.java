
import com.taligent.util.*;

public class IntegerComparator extends Object  implements Comparator
{
    public int compare(Object a, Object b) {
        int ia = ((Integer)a).intValue();
        int ib = ((Integer)b).intValue();

        if (ia < ib)
            return -1;
        if (ia > ib)
            return 1;

        return 0;
        }
    }