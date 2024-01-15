/*
 * File: ./HELLOAPP/_HELLOSTUB.JAVA
 * From: HELLO.IDL
 * Date: Sun Aug 23 16:42:49 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package HelloApp;
public class _HelloStub
	extends org.omg.CORBA.portable.ObjectImpl
    	implements HelloApp.Hello {

    public _HelloStub(org.omg.CORBA.portable.Delegate d) {
          super();
          _set_delegate(d);
    }

    private static final String _type_ids[] = {
        "IDL:HelloApp/Hello:1.0"
    };

    public String[] _ids() { return (String[]) _type_ids.clone(); }

    //	IDL operations
    //	    Implementation of ::HelloApp::Hello::sayHello
    public String sayHello(String message)
        throws HelloApp.HelloPackage.cantWriteFile {
           org.omg.CORBA.Request r = _request("sayHello");
           r.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
           org.omg.CORBA.Any _message = r.add_in_arg();
           _message.insert_string(message);
           r.exceptions().add(HelloApp.HelloPackage.cantWriteFileHelper.type());
           r.invoke();
           java.lang.Exception __ex = r.env().exception();
           if (__ex instanceof org.omg.CORBA.UnknownUserException) {
               org.omg.CORBA.UnknownUserException __userEx = (org.omg.CORBA.UnknownUserException) __ex;
               if (__userEx.except.type().equals(HelloApp.HelloPackage.cantWriteFileHelper.type())) {
                   throw HelloApp.HelloPackage.cantWriteFileHelper.extract(__userEx.except);
               }
           }
           String __result;
           __result = r.return_value().extract_string();
           return __result;
   }
    //	    Implementation of ::HelloApp::Hello::lastMessage
    public String lastMessage()
        throws HelloApp.HelloPackage.cantReadFile {
           org.omg.CORBA.Request r = _request("lastMessage");
           r.set_return_type(org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_string));
           r.exceptions().add(HelloApp.HelloPackage.cantReadFileHelper.type());
           r.invoke();
           java.lang.Exception __ex = r.env().exception();
           if (__ex instanceof org.omg.CORBA.UnknownUserException) {
               org.omg.CORBA.UnknownUserException __userEx = (org.omg.CORBA.UnknownUserException) __ex;
               if (__userEx.except.type().equals(HelloApp.HelloPackage.cantReadFileHelper.type())) {
                   throw HelloApp.HelloPackage.cantReadFileHelper.extract(__userEx.except);
               }
           }
           String __result;
           __result = r.return_value().extract_string();
           return __result;
   }

};
