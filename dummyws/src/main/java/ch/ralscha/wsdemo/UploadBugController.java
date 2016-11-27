package ch.ralscha.wsdemo;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UploadBugController {


  @RequestMapping(value = "/uploadBug", method = RequestMethod.POST)
  @ResponseBody
  public String handleUpload(InputStream is) throws IOException {

    throw new IllegalStateException("this is a bug");
  }

}
