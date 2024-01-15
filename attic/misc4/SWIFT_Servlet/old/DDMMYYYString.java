public final class DDMMYYYString implements BinaryPredicate {
 
    /* 0123456789 */
    /* DD.MM.YYYY */
 
    public boolean execute( Object first, Object second ) {
               
        int r = ((String)first).substring(6,10).compareTo(((String)second).substring(6,10));
        if (r == 0) {
            r = ((String)first).substring(3,5).compareTo(((String)second).substring(3,5));
            if (r == 0)
                r = ((String)first).substring(0,2).compareTo(((String)second).substring(0,2));
        }            
        return (r > 0 ? true : false);
        
    }
}