package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank._DispenserImplBase
<li> <b>Source File</b> Bank/_DispenserImplBase.java
<li> <b>IDL Source File</b> DebitCredit.idl
<li> <b>IDL Absolute Name</b> ::Bank::Dispenser
<li> <b>Repository Identifier</b> IDL:Bank/Dispenser:1.0
</ul>
<b>IDL definition:</b>
<pre>
    interface Dispenser {
      ::Bank::DebitCredit reserveDebitCreditObject(
      )
      raises(
        ::Bank::BankException
      );
      void releaseDebitCreditObject(
        in ::Bank::DebitCredit debitCreditObject
      )
      raises(
        ::Bank::BankException
      );
    };
</pre>
</p>
*/
abstract public class _DispenserImplBase extends org.omg.CORBA.portable.Skeleton implements Bank.Dispenser {
  protected Bank.Dispenser _wrapper = null;
  public Bank.Dispenser _this() {
    return this;
  }
  protected _DispenserImplBase(java.lang.String name) {
    super(name);
  }
  public _DispenserImplBase() {
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:Bank/Dispenser:1.0"
  };
  public org.omg.CORBA.portable.MethodPointer[] _methods() {
    org.omg.CORBA.portable.MethodPointer[] methods = {
      new org.omg.CORBA.portable.MethodPointer("reserveDebitCreditObject", 0, 0),
      new org.omg.CORBA.portable.MethodPointer("releaseDebitCreditObject", 0, 1),
    };
    return methods;
  }
  public boolean _execute(org.omg.CORBA.portable.MethodPointer method, org.omg.CORBA.portable.InputStream input, org.omg.CORBA.portable.OutputStream output) {
    switch(method.interface_id) {
    case 0: {
      return Bank._DispenserImplBase._execute(_this(), method.method_id, input, output); 
    }
    }
    throw new org.omg.CORBA.MARSHAL();
  }
  public static boolean _execute(Bank.Dispenser _self, int _method_id, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.OutputStream _output) {
    switch(_method_id) {
    case 0: {
      try {
        Bank.DebitCredit _result = _self.reserveDebitCreditObject();
        Bank.DebitCreditHelper.write(_output, _result);
      }
      catch(Bank.BankException _exception) {
        Bank.BankExceptionHelper.write(_output, _exception);
        return true;
      }
      return false;
    }
    case 1: {
      try {
        Bank.DebitCredit debitCreditObject;
        debitCreditObject = Bank.DebitCreditHelper.read(_input);
        _self.releaseDebitCreditObject(debitCreditObject);
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
