package ch.ess.startstop.service;

import java.util.Date;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.async.QuartzTriggerHandle;
import org.jboss.seam.log.Log;
import org.quartz.SchedulerException;

@Name("pollerStarter")
@Scope(ScopeType.SESSION)
@TideEnabled
@AutoCreate
public class PollerStarter {

  @Logger
  private Log log;

  private QuartzTriggerHandle handle;

  @In
  private Poller poller;

  public void start() {
    handle = poller.sendAsync(new Date(), 5000L);
  }

  @Destroy
  public void stop() {
    try {
      handle.cancel();
    } catch (SchedulerException e) {
      log.error("cancel", e);
    }
  }

}
