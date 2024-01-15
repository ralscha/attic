/*
 * File: ./COUNTERPORTABLE/COUNT.JAVA
 * From: COUNT.IDL
 * Date: Sat Aug 22 06:54:29 1998
 *   By: idltojava Java IDL 1.2 Nov 10 1997 13:52:11
 */

package CounterPortable;
public interface Count
    extends org.omg.CORBA.Object {
    int sum();
    void sum(int arg);
    int increment()
;
}
