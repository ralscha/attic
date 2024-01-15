/*
 * File: ./HELLOAPP/_HELLOIMPLBASE.JAVA
 * From: HELLO.IDL
 * Date: Sun Aug 23 16:42:49 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package HelloApp;
public abstract class _HelloImplBase extends org.omg.CORBA.DynamicImplementation implements HelloApp.Hello {
    // Constructor
    public _HelloImplBase() {
         super();
    }
    // Type strings for this class and its superclases
    private static final String _type_ids[] = {
        "IDL:HelloApp/Hello:1.0"
    };

    public String[] _ids() { return (String[]) _type_ids.clone(); }

    private static java.util.Dictionary _methods = new java.util.Hashtable();
    static {
      _methods.put("sayHello", new java.lang.Integer(0));
      _methods.put("lastMessage", new java.lang.Integer(1));
     }
    // DSI Dispatch call
    public void invoke(org.omg.CORBA.ServerRequest r) {
       switch (((java.lang.Integer) _methods.get(r.op_name())).intValue()) {
           case 0: // HelloApp.Hello.sayHello
              {
              org.omg.CORBA.NVList _list = _orb().create_list(0);
              org.omg.CORBA.Any _message = _orb().create_any();
              _message.type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
              _list.add_value("message", _message, org.omg.CORBA.ARG_IN.value);
              r.params(_list);
              String message;
              message = _message.extract_string();
              String ___result;
              try {
                            ___result = this.sayHello(message);
              }
              catch (HelloApp.HelloPackage.cantWriteFile e0) {
                            org.omg.CORBA.Any _except = _orb().create_any();
                            HelloApp.HelloPackage.cantWriteFileHelper.insert(_except, e0);
                            r.except(_except);
                            return;
              }
              org.omg.CORBA.Any __result = _orb().create_any();
              __result.insert_string(___result);
              r.result(__result);
              }
              break;
           case 1: // HelloApp.Hello.lastMessage
              {
              org.omg.CORBA.NVList _list = _orb().create_list(0);
              r.params(_list);
              String ___result;
              try {
                            ___result = this.lastMessage();
              }
              catch (HelloApp.HelloPackage.cantReadFile e0) {
                            org.omg.CORBA.Any _except = _orb().create_any();
                            HelloApp.HelloPackage.cantReadFileHelper.insert(_except, e0);
                            r.except(_except);
                            return;
              }
              org.omg.CORBA.Any __result = _orb().create_any();
              __result.insert_string(___result);
              r.result(__result);
              }
              break;
            default:
              throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
       }
 }
}
