public final class LessString implements BinaryPredicate {
 
    public boolean execute( Object first, Object second ) {
        return first.toString().compareTo( second.toString() ) > 0;
    }
}