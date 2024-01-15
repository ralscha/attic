package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank._DebitCreditImplBase
<li> <b>Source File</b> Bank/_DebitCreditImplBase.java
<li> <b>IDL Source File</b> DebitCredit.idl
<li> <b>IDL Absolute Name</b> ::Bank::DebitCredit
<li> <b>Repository Identifier</b> IDL:Bank/DebitCredit:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface DebitCredit {
      void debitCreditTransaction(
        in double random1,
        in double random2,
        in boolean staticTxn
      )
      raises(
        ::Bank::BankException
      );
    };
</pre>
</p>
*/
abstract public class _DebitCreditImplBase extends org.omg.CORBA.portable.Skeleton implements Bank.DebitCredit {
  protected Bank.DebitCredit _wrapper = null;
  public Bank.DebitCredit _this() {
    return this;
  }
  protected _DebitCreditImplBase(java.lang.String name) {
    super(name);
  }
  public _DebitCreditImplBase() {
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:Bank/DebitCredit:1.0"
  };
  public org.omg.CORBA.portable.MethodPointer[] _methods() {
    org.omg.CORBA.portable.MethodPointer[] methods = {
      new org.omg.CORBA.portable.MethodPointer("debitCreditTransaction", 0, 0),
    };
    return methods;
  }
  public boolean _execute(org.omg.CORBA.portable.MethodPointer method, org.omg.CORBA.portable.InputStream input, org.omg.CORBA.portable.OutputStream output) {
    switch(method.interface_id) {
    case 0: {
      return Bank._DebitCreditImplBase._execute(_this(), method.method_id, input, output); 
    }
    }
    throw new org.omg.CORBA.MARSHAL();
  }
  public static boolean _execute(Bank.DebitCredit _self, int _method_id, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.OutputStream _output) {
    switch(_method_id) {
    case 0: {
      try {
        double random1;
        random1 = _input.read_double();
        double random2;
        random2 = _input.read_double();
        boolean staticTxn;
        staticTxn = _input.read_boolean();
        _self.debitCreditTransaction(random1,random2,staticTxn);
      }
      catch(Bank.BankException _exception) {
        Bank.BankExceptionHelper.write(_output, _exception);
        return true;
      }
      return false;
    }
    }
    throw new org.omg.CORBA.MARSHAL();
  }
}
