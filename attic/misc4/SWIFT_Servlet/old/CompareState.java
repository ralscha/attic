public final class CompareState implements BinaryPredicate {
    public boolean execute(Object first, Object second) {
                
        if (((State)first).getStateDate().before(((State)second).getStateDate()))   
            return true;
        else 
            return false;
    }
}
