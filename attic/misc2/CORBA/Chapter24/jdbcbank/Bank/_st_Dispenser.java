package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank._st_Dispenser
<li> <b>Source File</b> Bank/_st_Dispenser.java
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
public class _st_Dispenser extends org.omg.CORBA.portable.ObjectImpl implements Bank.Dispenser {
  protected Bank.Dispenser _wrapper = null;
  public Bank.Dispenser _this() {
    return this;
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:Bank/Dispenser:1.0"
  };
  /**
  <p>
  Operation: <b>::Bank::Dispenser::reserveDebitCreditObject</b>.
  <pre>
    ::Bank::DebitCredit reserveDebitCreditObject(
    )
    raises(
      ::Bank::BankException
    );
  </pre>
  </p>
  */
  public Bank.DebitCredit reserveDebitCreditObject(
  ) throws
    Bank.BankException {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("reserveDebitCreditObject", true);
      org.omg.CORBA.StringHolder _exception_id = new org.omg.CORBA.StringHolder();
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, _exception_id);
      if(_exception_id.value != null) {
        if(_exception_id.value.equals(Bank.BankExceptionHelper.id())) {
          throw Bank.BankExceptionHelper.read(_input);
        }
        throw new org.omg.CORBA.MARSHAL
          ("Unexpected User Exception: " + _exception_id.value);
      }
      Bank.DebitCredit _result;
      _result = Bank.DebitCreditHelper.read(_input);
      return _result;
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      return reserveDebitCreditObject();
    }
  }
  /**
  <p>
  Operation: <b>::Bank::Dispenser::releaseDebitCreditObject</b>.
  <pre>
    void releaseDebitCreditObject(
      in ::Bank::DebitCredit debitCreditObject
    )
    raises(
      ::Bank::BankException
    );
  </pre>
  </p>
  */
  public void releaseDebitCreditObject(
    Bank.DebitCredit debitCreditObject
  ) throws
    Bank.BankException {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("releaseDebitCreditObject", true);
      Bank.DebitCreditHelper.write(_output, debitCreditObject);
      org.omg.CORBA.StringHolder _exception_id = new org.omg.CORBA.StringHolder();
      org.omg.CORBA.portable.InputStream _input = this._invoke(_output, _exception_id);
      if(_exception_id.value != null) {
        if(_exception_id.value.equals(Bank.BankExceptionHelper.id())) {
          throw Bank.BankExceptionHelper.read(_input);
        }
        throw new org.omg.CORBA.MARSHAL
          ("Unexpected User Exception: " + _exception_id.value);
      }
    }
    catch(org.omg.CORBA.TRANSIENT _exception) {
      releaseDebitCreditObject(
        debitCreditObject
      );
    }
  }
}
