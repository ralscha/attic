package ch.ralscha.prototype.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GreetingServiceAsync {

	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
}
