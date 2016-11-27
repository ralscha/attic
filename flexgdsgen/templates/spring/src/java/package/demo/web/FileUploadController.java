package @packageProject@.demo.web;

import javax.activation.FileTypeMap;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

  @RequestMapping("/fileUpload.action")
  public void upload(@RequestParam(value = "testParam", required = true) String testParam,
      @RequestParam(value = "Filedata", required = true) MultipartFile file, HttpServletResponse response) {

    String mimeType = FileTypeMap.getDefaultFileTypeMap().getContentType(file.getOriginalFilename());
    System.out.println("Mime Type : " + mimeType);
    System.out.println("File Size : " + file.getSize());
    System.out.println("Test Param: " + testParam);
    //read conent with file.getBytes() or file.getInputStream()

    response.setStatus(HttpServletResponse.SC_OK);

  }

}