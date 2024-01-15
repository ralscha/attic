package ch.ess.base.web.upload;

import javax.servlet.http.HttpSession;

public class UploadMonitor {
  public UploadInfo getUploadInfo(HttpSession session) {
    //    HttpServletRequest req = WebContextFactory.get().getHttpServletRequest();

    if (session.getAttribute("uploadInfo") != null) {
      return (UploadInfo)session.getAttribute("uploadInfo");
    }
    return new UploadInfo();

  }
}
