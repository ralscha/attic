
public final class CompareUpReceiver implements BinaryPredicate {
    public boolean execute(Object first, Object second) {
                
        int r = ((SWIFTHeader)first).getAddressReceiver().compareTo(((SWIFTHeader)second).getAddressReceiver());
        if (r == 0) {
            r = ((SWIFTHeader)first).getProcCenter().compareTo(((SWIFTHeader)second).getProcCenter());            
            if (r == 0) {
                r = ((SWIFTHeader)first).getTOSN().compareTo(((SWIFTHeader)second).getTOSN());
            }
        }
        return (r > 0 ? true : false);
    }
}
