package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank.BankException
<li> <b>Source File</b> Bank/BankException.java
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
final public class BankException extends org.omg.CORBA.UserException {
  public java.lang.String reason;
  public BankException() {
  }
  public BankException(
    java.lang.String reason
  ) {
    this.reason = reason;
  }
  public java.lang.String toString() {
    org.omg.CORBA.Any any = org.omg.CORBA.ORB.init().create_any();
    Bank.BankExceptionHelper.insert(any, this);
    return any.toString();
  }
}
