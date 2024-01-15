/*
 * File: ./COUNTERPORTABLE/_COUNTSTUB.JAVA
 * From: COUNT.IDL
 * Date: Sat Aug 22 06:54:29 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package CounterPortable;
public class _CountStub
	extends org.omg.CORBA.portable.ObjectImpl
    	implements CounterPortable.Count {

    public _CountStub(org.omg.CORBA.portable.Delegate d) {
          super();
          _set_delegate(d);
    }

    private static final String _type_ids[] = {
        "IDL:CounterPortable/Count:1.0"
    };

    public String[] _ids() { return (String[]) _type_ids.clone(); }

    //	IDL operations
    //	Implementation of attribute ::sum
    public int sum() {
           org.omg.CORBA.Request r = _request("_get_sum");
           r.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long));
           r.invoke();
           int __result;
           __result = r.return_value().extract_long();
           return __result;
   }
    public void sum(int arg) {
           org.omg.CORBA.Request r = _request("_set_sum");
           org.omg.CORBA.Any _sum = r.add_in_arg();
           _sum.insert_long(arg);
           r.invoke();
   }
    //	    Implementation of ::CounterPortable::Count::increment
    public int increment()
 {
           org.omg.CORBA.Request r = _request("increment");
           r.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long));
           r.invoke();
           int __result;
           __result = r.return_value().extract_long();
           return __result;
   }

};
