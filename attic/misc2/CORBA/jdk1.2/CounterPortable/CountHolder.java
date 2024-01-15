/*
 * File: ./COUNTERPORTABLE/COUNTHOLDER.JAVA
 * From: COUNT.IDL
 * Date: Sat Aug 22 06:54:29 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package CounterPortable;
public final class CountHolder
     implements org.omg.CORBA.portable.Streamable{
    //	instance variable 
    public CounterPortable.Count value;
    //	constructors 
    public CountHolder() {
	this(null);
    }
    public CountHolder(CounterPortable.Count __arg) {
	value = __arg;
    }

    public void _write(org.omg.CORBA.portable.OutputStream out) {
        CounterPortable.CountHelper.write(out, value);
    }

    public void _read(org.omg.CORBA.portable.InputStream in) {
        value = CounterPortable.CountHelper.read(in);
    }

    public org.omg.CORBA.TypeCode _type() {
        return CounterPortable.CountHelper.type();
    }
}
