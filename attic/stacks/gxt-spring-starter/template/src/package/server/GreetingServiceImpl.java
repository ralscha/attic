package @packageProject@.server;

import org.springframework.stereotype.Service;
import @packageProject@.share.GreetingService;

@Service("greet")
public class GreetingServiceImpl implements GreetingService {

  public String greetServer(String input) {
    return "Hello, " + input + "!";
  }

}
