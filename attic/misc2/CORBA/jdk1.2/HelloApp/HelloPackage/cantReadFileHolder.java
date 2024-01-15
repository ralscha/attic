/*
 * File: ./HELLOAPP/HELLOPACKAGE/CANTREADFILEHOLDER.JAVA
 * From: HELLO.IDL
 * Date: Sun Aug 23 16:42:49 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package HelloApp.HelloPackage;
public final class cantReadFileHolder
     implements org.omg.CORBA.portable.Streamable{
    //	instance variable 
    public HelloApp.HelloPackage.cantReadFile value;
    //	constructors 
    public cantReadFileHolder() {
	this(null);
    }
    public cantReadFileHolder(HelloApp.HelloPackage.cantReadFile __arg) {
	value = __arg;
    }

    public void _write(org.omg.CORBA.portable.OutputStream out) {
        HelloApp.HelloPackage.cantReadFileHelper.write(out, value);
    }

    public void _read(org.omg.CORBA.portable.InputStream in) {
        value = HelloApp.HelloPackage.cantReadFileHelper.read(in);
    }

    public org.omg.CORBA.TypeCode _type() {
        return HelloApp.HelloPackage.cantReadFileHelper.type();
    }
}
