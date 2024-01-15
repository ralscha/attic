

public final class CompareDownMT implements BinaryPredicate {
    public boolean execute(Object first, Object second) {
                
        int r = ((SWIFTHeader)first).getMessageType().compareTo(((SWIFTHeader)second).getMessageType());
        if (r == 0) {
            r = ((SWIFTHeader)first).getTOSN().compareTo(((SWIFTHeader)second).getTOSN());
        }
        return (r < 0 ? true : false);
    }
}
