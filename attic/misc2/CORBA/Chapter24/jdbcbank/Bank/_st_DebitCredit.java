package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank._st_DebitCredit
<li> <b>Source File</b> Bank/_st_DebitCredit.java
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
public class _st_DebitCredit extends org.omg.CORBA.portable.ObjectImpl implements Bank.DebitCredit {
  protected Bank.DebitCredit _wrapper = null;
  public Bank.DebitCredit _this() {
    return this;
  }
  public java.lang.String[] _ids() {
    return __ids;
  }
  private static java.lang.String[] __ids = {
    "IDL:Bank/DebitCredit:1.0"
  };
  /**
  <p>
  Operation: <b>::Bank::DebitCredit::debitCreditTransaction</b>.
  <pre>
    void debitCreditTransaction(
      in double random1,
      in double random2,
      in boolean staticTxn
    )
    raises(
      ::Bank::BankException
    );
  </pre>
  </p>
  */
  public void debitCreditTransaction(
    double random1,
    double random2,
    boolean staticTxn
  ) throws
    Bank.BankException {
    try {
      org.omg.CORBA.portable.OutputStream _output = this._request("debitCreditTransaction", true);
      _output.write_double(random1);
      _output.write_double(random2);
      _output.write_boolean(staticTxn);
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
      debitCreditTransaction(
        random1,
        random2,
        staticTxn
      );
    }
  }
}
