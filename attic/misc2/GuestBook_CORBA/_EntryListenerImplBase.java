/**
<p>
<ul>
<li> <b>Java Class</b> _EntryListenerImplBase
<li> <b>Source File</b> _EntryListenerImplBase.java
<li> <b>IDL Absolute Name</b> ::EntryListener
<li> <b>Repository Identifier</b> IDL:EntryListener:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface EntryListener {
      void newEntryArrived(
        in ::Entry arg0
      );
    };
</pre>
</p>
*/
abstract public class _EntryListenerImplBase extends org.omg.CORBA.portable.Skeleton implements EntryListener {
  protected EntryListener _wrapper = null;
  public EntryListener _this() {
    return this;
  }
  protected _EntryListenerImplBase(java.lang.String name) {
    super(name);
  }
  public _EntryListenerImplBase() {
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:EntryListener:1.0"
  };
  public org.omg.CORBA.portable.MethodPointer[] _methods() {
    org.omg.CORBA.portable.MethodPointer[] methods = {
      new org.omg.CORBA.portable.MethodPointer("newEntryArrived", 0, 0),
    };
    return methods;
  }
  public boolean _execute(org.omg.CORBA.portable.MethodPointer method, org.omg.CORBA.portable.InputStream input, org.omg.CORBA.portable.OutputStream output) {
    switch(method.interface_id) {
    case 0: {
      return _EntryListenerImplBase._execute(_this(), method.method_id, input, output); 
    }
    }
    throw new org.omg.CORBA.MARSHAL();
  }
  public static boolean _execute(EntryListener _self, int _method_id, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.OutputStream _output) {
    switch(_method_id) {
    case 0: {
      Entry arg0;
      arg0 = (Entry)_input.read_estruct("Entry");
      _self.newEntryArrived(arg0);
      return false;
    }
    }
    throw new org.omg.CORBA.MARSHAL();
  }
}
