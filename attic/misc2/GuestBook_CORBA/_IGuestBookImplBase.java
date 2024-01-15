/**
<p>
<ul>
<li> <b>Java Class</b> _IGuestBookImplBase
<li> <b>Source File</b> _IGuestBookImplBase.java
<li> <b>IDL Absolute Name</b> ::IGuestBook
<li> <b>Repository Identifier</b> IDL:IGuestBook:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface IGuestBook {
      void addEntry(
        in ::EntryListener arg0,
        in ::Entry arg1
      );
      ::java::util::Vector getEntries();
      void addEntryListener(
        in ::EntryListener arg0
      );
      void removeEntryListener(
        in ::EntryListener arg0
      );
    };
</pre>
</p>
*/
abstract public class _IGuestBookImplBase extends org.omg.CORBA.portable.Skeleton implements IGuestBook {
  protected IGuestBook _wrapper = null;
  public IGuestBook _this() {
    return this;
  }
  protected _IGuestBookImplBase(java.lang.String name) {
    super(name);
  }
  public _IGuestBookImplBase() {
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:IGuestBook:1.0"
  };
  public org.omg.CORBA.portable.MethodPointer[] _methods() {
    org.omg.CORBA.portable.MethodPointer[] methods = {
      new org.omg.CORBA.portable.MethodPointer("addEntry", 0, 0),
      new org.omg.CORBA.portable.MethodPointer("getEntries", 0, 1),
      new org.omg.CORBA.portable.MethodPointer("addEntryListener", 0, 2),
      new org.omg.CORBA.portable.MethodPointer("removeEntryListener", 0, 3),
    };
    return methods;
  }
  public boolean _execute(org.omg.CORBA.portable.MethodPointer method, org.omg.CORBA.portable.InputStream input, org.omg.CORBA.portable.OutputStream output) {
    switch(method.interface_id) {
    case 0: {
      return _IGuestBookImplBase._execute(_this(), method.method_id, input, output); 
    }
    }
    throw new org.omg.CORBA.MARSHAL();
  }
  public static boolean _execute(IGuestBook _self, int _method_id, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.OutputStream _output) {
    switch(_method_id) {
    case 0: {
      EntryListener arg0;
      arg0 = EntryListenerHelper.read(_input);
      Entry arg1;
      arg1 = (Entry)_input.read_estruct("Entry");
      _self.addEntry(arg0,arg1);
      return false;
    }
    case 1: {
      java.util.Vector _result = _self.getEntries();
      _output.write_estruct(_result, "java.util.Vector");
      return false;
    }
    case 2: {
      EntryListener arg0;
      arg0 = EntryListenerHelper.read(_input);
      _self.addEntryListener(arg0);
      return false;
    }
    case 3: {
      EntryListener arg0;
      arg0 = EntryListenerHelper.read(_input);
      _self.removeEntryListener(arg0);
      return false;
    }
    }
    throw new org.omg.CORBA.MARSHAL();
  }
}
