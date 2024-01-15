package jndi;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jnp.server.Main;

public class JNDITest {
  public static void main(String[] args) {
    try {
      Main.main(null);

/*
      java.naming.provider.url=localhost:1099
      java.naming.factory.initial=org.jnp.interfaces.NamingContextFactory
      java.naming.factory.url.pkgs=org.jboss.naming
*/

      Context context = new InitialContext();
      context.createSubcontext("ess");
      context.bind("/ess/test", "MyApp");


      Context context2 = new InitialContext();
      System.out.println((String)context2.lookup("/ess/test"));

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
