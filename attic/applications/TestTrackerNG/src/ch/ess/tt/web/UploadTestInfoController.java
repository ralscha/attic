package ch.ess.tt.web;

import java.io.IOException;
import javax.activation.FileTypeMap;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ch.ess.tt.service.TestService;

@Controller
public class UploadTestInfoController {

  @Autowired
  private TestService testService;
    
  @SuppressWarnings("unchecked")
  @RequestMapping("/uploadTestInfo.action")
  public void export(
      @RequestParam(value = "no", required = true)int no,
      @RequestParam(value = "testId", required = true)Integer testId,
      @RequestParam(value = "Filedata", required = true)MultipartFile file,
      HttpServletResponse response) throws IOException {
    
    String mimeType = FileTypeMap.getDefaultFileTypeMap().getContentType(file.getOriginalFilename());

    testService.insertAttachment(no, testId, file.getOriginalFilename(), mimeType, file.getSize(), file.getInputStream());
    
    file.getInputStream().close();
    response.setStatus(HttpServletResponse.SC_OK);

  }

}