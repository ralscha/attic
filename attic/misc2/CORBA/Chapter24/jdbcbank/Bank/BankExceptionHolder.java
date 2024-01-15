package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank.BankExceptionHolder
<li> <b>Source File</b> Bank/BankExceptionHolder.java
<li> <b>IDL Source File</b> DebitCredit.idl
<li> <b>IDL Absolute Name</b> ::Bank::BankException
<li> <b>Repository Identifier</b> IDL:Bank/BankException:1.0
</ul>
<b>IDL definition:</b>
<pre>
    exception BankException {
      string reason;
    };
</pre>
</p>
*/
final public class BankExceptionHolder implements org.omg.CORBA.portable.Streamable {
  public Bank.BankException value;
  public BankExceptionHolder() {
  }
  public BankExceptionHolder(Bank.BankException value) {
    this.value = value;
  }
  public void _read(org.omg.CORBA.portable.InputStream input) {
    value = BankExceptionHelper.read(input);
  }
  public void _write(org.omg.CORBA.portable.OutputStream output) {
    BankExceptionHelper.write(output, value);
  }
  public org.omg.CORBA.TypeCode _type() {
    return BankExceptionHelper.type();
  }
}
