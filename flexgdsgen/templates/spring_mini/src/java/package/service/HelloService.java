package @packageProject@.service;

import java.util.Date;
import org.granite.messaging.service.annotations.RemoteDestination;
import org.springframework.stereotype.Service;

@Service
@RemoteDestination(id="helloService")
public class HelloService {

  public String hello() {
    return "Hi Client : " + new Date();
  }

}
