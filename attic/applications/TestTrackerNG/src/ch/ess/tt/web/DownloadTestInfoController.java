package ch.ess.tt.web;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ch.ess.tt.service.Attachment;
import ch.ess.tt.service.TestService;

@Controller
public class DownloadTestInfoController {

  @Autowired
  private TestService testService;

  @SuppressWarnings("unchecked")
  @RequestMapping("/downloadTestInfo.action")
  public void export(@RequestParam(value = "no", required = true)int no, 
                     @RequestParam(value = "testId", required = true)Integer testId, 
                     HttpServletResponse response) throws IOException {

    Attachment attachment = testService.getAttachment(no, testId);

    response.setContentType(attachment.getMimeType());
    OutputStream out = response.getOutputStream();
    IOUtils.copy(attachment.getAttachmentInputStream(), out);
    attachment.getAttachmentInputStream().close();
    out.close();

  }

}