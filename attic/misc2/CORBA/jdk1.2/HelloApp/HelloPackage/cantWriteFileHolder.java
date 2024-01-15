/*
 * File: ./HELLOAPP/HELLOPACKAGE/CANTWRITEFILEHOLDER.JAVA
 * From: HELLO.IDL
 * Date: Sun Aug 23 16:42:49 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package HelloApp.HelloPackage;
public final class cantWriteFileHolder
     implements org.omg.CORBA.portable.Streamable{
    //	instance variable 
    public HelloApp.HelloPackage.cantWriteFile value;
    //	constructors 
    public cantWriteFileHolder() {
	this(null);
    }
    public cantWriteFileHolder(HelloApp.HelloPackage.cantWriteFile __arg) {
	value = __arg;
    }

    public void _write(org.omg.CORBA.portable.OutputStream out) {
        HelloApp.HelloPackage.cantWriteFileHelper.write(out, value);
    }

    public void _read(org.omg.CORBA.portable.InputStream in) {
        value = HelloApp.HelloPackage.cantWriteFileHelper.read(in);
    }

    public org.omg.CORBA.TypeCode _type() {
        return HelloApp.HelloPackage.cantWriteFileHelper.type();
    }
}
