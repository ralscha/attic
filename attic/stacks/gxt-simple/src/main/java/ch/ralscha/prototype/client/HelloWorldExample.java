package ch.ralscha.prototype.client;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class HelloWorldExample extends LayoutContainer {

	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server. Please check your network connection and try again.";

	final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	public HelloWorldExample() {
		setLayout(new FlowLayout(10));

		final Window window = new Window();
		window.setSize(500, 300);
		window.setPlain(true);
		window.setModal(true);
		window.setBlinkModal(true);
		window.setHeading("Hello Window");
		window.setLayout(new FitLayout());

		TabPanel panel = new TabPanel();
		panel.setBorders(false);
		TabItem item1 = new TabItem("Hello World 1");
		item1.addText("Hello...");
		item1.addStyleName("pad-text");

		TabItem item2 = new TabItem("Hello World 2");
		item2.addText("... World!");
		item2.addStyleName("pad-text");

		TabItem item3 = new TabItem("Hello Server");
		item3.addStyleName("pad-text");

		Button helloServerButton = new Button("Hello Server");
		helloServerButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {

				greetingService.greetServer("gxtsimple", new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						Info.display("Failure", SERVER_ERROR + " " + caught.toString());
					}

					@Override
					public void onSuccess(String result) {
						Info.display("Success", result);
					}
				});

			}
		});

		LayoutContainer c = new LayoutContainer();
		HBoxLayout layout = new HBoxLayout();
		HBoxLayoutData spacer = new HBoxLayoutData(new Margins(5, 5, 5, 5));
		c.setLayout(layout);
		c.add(helloServerButton, spacer);
		item3.add(c);

		panel.add(item1);
		panel.add(item2);
		panel.add(item3);

		window.add(panel, new FitData(4));

		window.addButton(new Button("Hello"));
		window.addButton(new Button("World"));

		Button btn = new Button("Hello World");
		btn.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				window.show();
			}
		});
		add(btn);

	}

}