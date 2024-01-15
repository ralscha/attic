
public final class CompareDownTOSN implements BinaryPredicate {
    public boolean execute(Object first, Object second) {
        int r = ((SWIFTHeader)first).getTOSN().compareTo(((SWIFTHeader)second).getTOSN());
        return (r < 0 ? true : false);
    }
}