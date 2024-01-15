

public final class CompareUpDuplicate implements BinaryPredicate {
    public boolean execute(Object first, Object second) {
                
        int r = ((SWIFTHeader)first).getDuplicate().compareTo(((SWIFTHeader)second).getDuplicate());
        if (r == 0) {
            r = ((SWIFTHeader)first).getTOSN().compareTo(((SWIFTHeader)second).getTOSN());
        }
        return (r > 0 ? true : false);
    }
}
