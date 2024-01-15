package com.example.gxttest.server;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.example.gxttest.share.GreetingService;

@Service("greet")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class GreetingServiceImpl implements GreetingService {

  public String greetServer(String input) {
    return "Hello, " + input + "!";
  }

}
