/*
 * File: ./HELLOAPP/HELLO.JAVA
 * From: HELLO.IDL
 * Date: Sun Aug 23 16:42:49 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package HelloApp;
public interface Hello
    extends org.omg.CORBA.Object {
    String sayHello(String message)
        throws HelloApp.HelloPackage.cantWriteFile;
    String lastMessage()
        throws HelloApp.HelloPackage.cantReadFile;
}
