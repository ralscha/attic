package ch.ralscha.prototype.client;

import com.extjs.gxt.ui.client.GXT;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class GxtDemo implements EntryPoint {

	@Override
	public void onModuleLoad() {
		HelloWorldExample hello = new HelloWorldExample();
		RootPanel.get().add(hello);

		GXT.hideLoadingPanel("loading");
	}

}
