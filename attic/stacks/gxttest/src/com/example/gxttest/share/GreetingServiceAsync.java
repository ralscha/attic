package com.example.gxttest.share;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface GreetingServiceAsync {
  void greetServer(String input, AsyncCallback<String> callback);
}
