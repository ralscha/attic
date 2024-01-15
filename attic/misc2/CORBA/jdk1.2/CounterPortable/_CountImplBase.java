/*
 * File: ./COUNTERPORTABLE/_COUNTIMPLBASE.JAVA
 * From: COUNT.IDL
 * Date: Sat Aug 22 06:54:29 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package CounterPortable;
public abstract class _CountImplBase extends org.omg.CORBA.DynamicImplementation implements CounterPortable.Count {
    // Constructor
    public _CountImplBase() {
         super();
    }
    // Type strings for this class and its superclases
    private static final String _type_ids[] = {
        "IDL:CounterPortable/Count:1.0"
    };

    public String[] _ids() { return (String[]) _type_ids.clone(); }

    private static java.util.Dictionary _methods = new java.util.Hashtable();
    static {
      _methods.put("_get_sum", new java.lang.Integer(0));
      _methods.put("_set_sum", new java.lang.Integer(1));
      _methods.put("increment", new java.lang.Integer(2));
     }
    // DSI Dispatch call
    public void invoke(org.omg.CORBA.ServerRequest r) {
       switch (((java.lang.Integer) _methods.get(r.op_name())).intValue()) {
           case 0: // CounterPortable.Count.sum
              {
              org.omg.CORBA.NVList _list = _orb().create_list(0);
              r.params(_list);
              int ___result = this.sum();
              org.omg.CORBA.Any __result = _orb().create_any();
              __result.insert_long(___result);
              r.result(__result);
              }
              break;
           case 1: // CounterPortable.Count.sum
              {
              org.omg.CORBA.NVList _list = _orb().create_list(0);
              org.omg.CORBA.Any _arg = _orb().create_any();
              _arg.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long));
              _list.add_value("arg", _arg, org.omg.CORBA.ARG_IN.value);
              r.params(_list);
              int arg;
              arg = _arg.extract_long();
              this.sum(arg);
              org.omg.CORBA.Any a = _orb().create_any();
              a.type(_orb().get_primitive_tc(org.omg.CORBA.TCKind.tk_void));
              r.result(a);
              }
              break;
           case 2: // CounterPortable.Count.increment
              {
              org.omg.CORBA.NVList _list = _orb().create_list(0);
              r.params(_list);
              int ___result;
                            ___result = this.increment();
              org.omg.CORBA.Any __result = _orb().create_any();
              __result.insert_long(___result);
              r.result(__result);
              }
              break;
            default:
              throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
       }
 }
}
