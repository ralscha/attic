package ch.ess.base.web.upload;

import javax.servlet.http.HttpSession;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy(name="uploadMonitor")
public class UploadMonitor {
  @RemoteMethod
  public UploadInfo getUploadInfo(HttpSession session) {
    //    HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();

    if (session.getAttribute("uploadInfo") != null) {
      return (UploadInfo)session.getAttribute("uploadInfo");
    }
    return new UploadInfo();

  }
}
