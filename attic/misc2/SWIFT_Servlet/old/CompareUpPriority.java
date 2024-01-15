

public final class CompareUpPriority implements BinaryPredicate {
    public boolean execute(Object first, Object second) {
                
        int r = ((SWIFTHeader)first).getPriority().compareTo(((SWIFTHeader)second).getPriority());
        if (r == 0) {
            r = ((SWIFTHeader)first).getTOSN().compareTo(((SWIFTHeader)second).getTOSN());
        }
        return (r > 0 ? true : false);
    }

}
