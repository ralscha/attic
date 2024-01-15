
public class CountServer {
    static public void main(String[] args) {
        try {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, null);
            org.omg.CORBA.BOA boa = orb.BOA_init();
            CountImpl count = new CountImpl("My Count");
            boa.obj_is_ready(count);
            boa.impl_is_ready();                        
        } catch (org.omg.CORBA.SystemException e) {
            System.err.println(e);
        }
    }
    
}
