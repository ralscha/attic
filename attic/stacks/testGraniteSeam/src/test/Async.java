package test;

import java.util.Date;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.annotations.async.Expiration;
import org.jboss.seam.annotations.async.IntervalDuration;
import org.jboss.seam.core.Events;

@Name("async")
@TideEnabled
public class Async {

  @Asynchronous
  @SuppressWarnings("unused")
  public void doSomething(@Expiration Date when, @IntervalDuration Long interval) {
    System.out.println("RAISE EVENT");
    Events.instance().raiseEvent("notification");
  }

}
