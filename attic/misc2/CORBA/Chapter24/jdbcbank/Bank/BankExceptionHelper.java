package Bank;
/**
<p>
<ul>
<li> <b>Java Class</b> Bank.BankExceptionHelper
<li> <b>Source File</b> Bank/BankExceptionHelper.java
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
abstract public class BankExceptionHelper {
  private static org.omg.CORBA.ORB _orb() {
    return org.omg.CORBA.ORB.init();
  }
  public static Bank.BankException read(org.omg.CORBA.portable.InputStream _input) {
    if(!_input.read_string().equals(id())) {
      throw new org.omg.CORBA.MARSHAL("Mismached repository id");
    }
    Bank.BankException result = new Bank.BankException();
    result.reason = _input.read_string();
    return result;
  }
  public static void write(org.omg.CORBA.portable.OutputStream _output, Bank.BankException value) {
    _output.write_string(id());
    _output.write_string(value.reason);
  }
  public static void insert(org.omg.CORBA.Any any, Bank.BankException value) {
    org.omg.CORBA.portable.OutputStream output = any.create_output_stream();
    write(output, value);
    any.read_value(output.create_input_stream(), type());
  }
  public static Bank.BankException extract(org.omg.CORBA.Any any) {
    if(!any.type().equal(type())) {
      throw new org.omg.CORBA.BAD_TYPECODE();
    }
    return read(any.create_input_stream());
  }
  private static org.omg.CORBA.TypeCode _type;
  public static org.omg.CORBA.TypeCode type() {
    if(_type == null) {
      org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[1];
      members[0] = new org.omg.CORBA.StructMember("reason", _orb().get_primitive_tc(org.omg.CORBA.TCKind.tk_string), null);
      _type = _orb().create_exception_tc(id(), "BankException", members);
    }
    return _type;
  }
  public static java.lang.String id() {
    return "IDL:Bank/BankException:1.0";
  }
}
