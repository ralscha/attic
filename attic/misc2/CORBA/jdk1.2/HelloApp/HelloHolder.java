/*
 * File: ./HELLOAPP/HELLOHOLDER.JAVA
 * From: HELLO.IDL
 * Date: Sun Aug 23 16:42:49 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package HelloApp;
public final class HelloHolder
     implements org.omg.CORBA.portable.Streamable{
    //	instance variable 
    public HelloApp.Hello value;
    //	constructors 
    public HelloHolder() {
	this(null);
    }
    public HelloHolder(HelloApp.Hello __arg) {
	value = __arg;
    }

    public void _write(org.omg.CORBA.portable.OutputStream out) {
        HelloApp.HelloHelper.write(out, value);
    }

    public void _read(org.omg.CORBA.portable.InputStream in) {
        value = HelloApp.HelloHelper.read(in);
    }

    public org.omg.CORBA.TypeCode _type() {
        return HelloApp.HelloHelper.type();
    }
}
