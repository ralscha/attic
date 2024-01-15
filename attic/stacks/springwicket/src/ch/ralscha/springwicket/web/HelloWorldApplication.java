package ch.ralscha.springwicket.web;

import org.apache.wicket.protocol.http.WebApplication;

public class HelloWorldApplication extends WebApplication {

  public HelloWorldApplication() {

  }

  @Override
  public Class<Tables> getHomePage() {
    return Tables.class;
  }
}