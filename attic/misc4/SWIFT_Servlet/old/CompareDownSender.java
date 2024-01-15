public final class CompareDownSender implements BinaryPredicate {
    public boolean execute(Object first, Object second) {
                
        int r = ((SWIFTHeader)first).getAddressSender().compareTo(((SWIFTHeader)second).getAddressSender());
        if (r == 0) {
            r = ((SWIFTHeader)first).getTOSN().compareTo(((SWIFTHeader)second).getTOSN());
        }
        return (r < 0 ? true : false);
    }
}
